import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.Vector;

/**
 * 
 */

/** This index is the main "Index" class that contains our nameIndex and location index. It contains
 * a nameIndexer object (my J3 solution), and contains a quadtree. I saw this as a location index
 * that "contains" a nameIndex - I didn't use a separate "location index" class!
 * @author  Josh Pandey
 *
 */
public class gigaIndex {

    /**  This is a class that has a nameIndex, and has a coordinate index. For some reason. So, this will take in a file or a GIS record,
     * and then index everything by NAME, and then also index everything by COORDINATE. Our hash table stores nameEntry objects, and
     * our quadtree stores coordinate objects. Note that this index is conceived as a location that "contains" a hashtable
     * name index. This is in the framework of "name search is a feature".
     */
    private nameIndexer name;
    private prQuadTree<coordinate> prquad;
    public long xMin;
    public long yMin;
    public long xMax;
    public long yMax;
    
    
    /** Constructor for our location index. 
     * 
     *  Use a separate method for creating a quadtree
     * */
    public gigaIndex() {
        name = new nameIndexer();
        prquad = null;
        
    }
    
    /** Constructs a quadtree with the following bounds. We use xMin, yMin, xMax, and yMax as the order here
     * 
     * @param westMin       xMin
     * @param northMin      yMin
     * @param westMax       xMax
     * @param northMax      yMax
     * 
     * */
    public void quadMaker(long westMin, long northMin, long westMax, long northMax) {
        this.prquad = new prQuadTree<coordinate>(westMin, northMin, westMax, northMax);
        this.xMin = westMin;
        this.yMin = northMin;
        this.xMax = westMax;
        this.yMax = northMax;
        
    }
    
    
    /* Insert a nameEntry object into our nameEntry
     * 
     */
    public void insertName(String gisrecord) throws IOException {
        name.megaInsert(gisrecord);
        
    }
    
    
    /** This is the main meat of the program, it handles all indexing and inserts each record
     * into nameEntry indexes and coordinate indexes. Note that these are 2 separate objects
     * 
     * @param filename    Filename 
     * @param Offset        The last offset in our database file
     */
    public int[] fullIndexTry(String filename, long lastOffset) {
        int nimport = 0;
        int limport = 0;
        int[] importnums = new int[2];
        try {
            /* Counters for name and location indexes*/
            
            File gis = new File(filename);
            RandomAccessFile r = new RandomAccessFile(gis, "r");

            // start:
            Scanner readfull = new Scanner(gis);
            // check the first line and get scanner and file to their starting positions
          

                            /* new line: this goes to the last offset. this is in the case where ther are 2, 10, or 824 new imports. we don't want to
                             * recreate the table and quadtree each time, so we just store the long pointer and index everything new. idk if this is right.
                             * 
                             * To make this work, we actually keep the "FEATURE NAME|FEATURE ID|STATE ALPHA" etc part of each record. We add the entire gis file every time,
                             * so that it runs right on the first import as well as import # 92381
                             */
            
                                            r.seek(lastOffset);
                                            readfull.nextLine();
                                            r.readLine();
            
            /* This is the main loop, which just goes through our database file and indexes every record it sees*/
            while (readfull.hasNextLine()) {
                // Offset
                Long offie = r.getFilePointer();
                // Grab the string as well - readfull is used for its (hasnextline)
                String readline = r.readLine();
                
                // Break if nothing was grabbed
                if (readline == null) {
                    break;
                }
                
                                    /* This seems slightly redundant, but it supports easily grabbing
                                     * any aspect of a record needed
                                     */
                Scanner scanner = new Scanner(readline);
                scanner.useDelimiter("\\|");

                scanner.next();                         /* Feature ID*/
                String featurename = scanner.next();    /* Feature name*/
                scanner.next();                         /* Feature class*/
                String featurestate = scanner.next();   /* Feature State*/
                scanner.next();                         /* State numeric*/
                scanner.next();                         /* County name*/
                scanner.next();                         /* County numeric*/
                String primLatN = scanner.next();
                String primLongW = scanner.next();
                primLatN = primLatN.substring(0, primLatN.length() - 1);
                primLongW = primLongW.substring(0, primLongW.length() - 1);
                
                                /* Logic to create a coordinate and put it into the tree*/
                        
                        /* This seems so messy and like an unnecessary check to m,e but it seems necessary!*/
                if (primLatN.contains("Unknow") || primLongW.contains("Unknow")) {
                    // Nothing to do here, this just avoids all problems of "Unknow" trying to be parsed into a Long in the next loop
                }
                else {
                        coordinate coordinate = new coordinate(Long.parseLong(primLatN), Long.parseLong(primLongW), offie);
                        //System.out.println("Inserting coordinate!!!! wow " + coordinate.toString() + "Or.. " + coordinate.getX() + "  and " + coordinate.getY());
                        prquad.insert(coordinate);
                        limport++;
                         
                }
                
                
                
                            /* Create a nameEntry and put it in nameIndex*/
                    String fullname = featurename + featurestate;
                    nameEntry n = new nameEntry(fullname, offie);
                    
                    /* null checks aren't needed here */
                    name.newInsert(n);
                    nimport++;

                scanner.close(); //************************************************

            }
            
            /* assign to int array*/
            importnums[0] = nimport;
            importnums[1] = limport;
            // close all of them:
            
            readfull.close();
            r.close();

            return importnums;
        } catch (FileNotFoundException e) {
            System.out.println(" Error in indexing ");
            e.printStackTrace();
            System.exit(100);
        }
        catch (IOException e) {
            
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    /** Getter for our nameIndex
     * 
     * @return  Our nameIndex!
     */
    public nameIndexer getNameIndex() {
        return this.name;
    }
    
    /* debug hash*/
    public void hashDebug(FileWriter o) {
        name.displayHashPrint(o);
    }
    
    public nameEntry nameLookUp(String together) {       
        //System.out.println(" in name lookup!");
        /* Note: instead of passing in null, I'll try assigning an arbitary offset and passing that in*/
        nameEntry l = new nameEntry(together, 0L);
       // System.out.println(" past nameEntry creation!");
        
        nameEntry fd = name.lookUp(l);
        
        if (fd != null) {
            return fd;
        }
        return null;
//        try {
//            return name.lookUp(l);
//        }
//        catch (Exception e) {
//            System.out.println(" excpetion.. hmmmmm. in the catcdh block of namelookup");
//            e.printStackTrace();
//        }
//        //nameEntry n = name.lookUp(new nameEntry(together, 0L));
////        if (n == null) {
////            System.out.println(" I'm in namelookup. n is null");
////        }
////        else {
////            System.out.println(n.key() + " < -- foudn this key!");
////            return n;
////        }
//        return null;
    }
    
    /* Range find method!*/
    public Vector<coordinate> findrange(long one, long two, long three, long four) {
        return prquad.find(one, two, three, four);
    }
    
    /* Simply writes to tree*/
    public void writeTree(FileWriter out) {
        prquad.printTreeHelper(out, prquad.rootget(), "  ", 4);
    }
    
    /* Retrieve prQuad*/
    public prQuadTree<coordinate> getprQuad() {
        return this.prquad;
    }

    /* Find our coordinate*/
    public coordinate findCoordinate(coordinate cr) {
        return (prquad.find(cr));
    }
    
    
    
    
}

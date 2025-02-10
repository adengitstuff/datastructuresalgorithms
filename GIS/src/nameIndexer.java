// On my honor:
//
// - I have not discussed the Java language code in my program with
// anyone other than my instructor or the teaching assistants
// assigned to this course.
//
// - I have not used Java language code obtained from another student,
// or any other unauthorized source, including the Internet, either
// modified or unmodified.
//
// - If any Java language code or documentation used in my program
// was obtained from another source, such as a text book or course
// notes, that has been clearly noted with a proper citation in
// the comments of my program.
//
// - I have not designed this program in such a way as to defeat or
// interfere with the normal operation of the grading code.
//
// Josh Pandey
// josh1
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/* This is the main nameIndexer class for this assignment. It uses an indexer method to
build our hash table, indexing entries according to the Donald Knuth hash function for strings.
 * @author Josh Pandey
 * @date 3.22.2021
 */

public class nameIndexer {

    // hash table!
    public hashTable<nameEntry> hash;

    /* Main constructor for nameIndexer. We don't need to initialize anything
     */
    public nameIndexer() {
        hash = new hashTable<>(null, 0.7);
    }
    
    /* Get table*/
    public hashTable<nameEntry> theTable() {
        return hash;
    }
    
    /* Print our hash table neatly onto a
     * filewriter object. I love that you can pass
     * filewriters in in Java
     */
    public void displayHashPrint(FileWriter o) {
        try {
            hash.displayHashWrite(o);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* An insert method that just called indexer - not
     * needed in this project.
     */
    public void megaInsert(String filename) throws IOException {
        hash = indexer(filename);
        //hash.displayx();
    }

    /** LookUp scours our hashtable to find the appropriate entry. It compares keys
     *
     * @param toFind        the nameEntry object itself to find
     * @return              reference to nameEntry found
     */
    public nameEntry lookUp(nameEntry toFind) {
        
        if (toFind == null) {
            //System.out.println(" Error: You are passing in a nameEntry to lookUp(nameEntry toFind) in nameIndexer, that is null");
            System.exit(100);
        }
        int to = 0;
        LinkedList<nameEntry> linkedlist = null;
        try {
             to = Math.abs(toFind.Hash() % hash.sizie());
        }
        catch (Exception e) {
            System.out.println("couldn't hash?");
            e.printStackTrace();
        }
       // int to = Math.abs((toFind.Hash()) % hash.sizie());

        //System.out.println("looking up : " + toFind.key() + "at location" + to);
        try {
        linkedlist = hash.table().get(to);
        }
        catch (Exception e) {
            System.out.println(" Error in linked list assignment, in getting linked list at hash table value. Line 100 in nameIndexer!");
            e.printStackTrace();
        }
        
        for (nameEntry n : linkedlist) {
            //nameEntry n = linkedlist.get(i);
            //System.out.println("Comparing : " + n.key() + "to " + toFind.key());

            /* Keys are the same here - return a reference to this nameEntry. Note that we handle
            longs being different in another method.
             */
            if (n.key().contentEquals(toFind.key())) {
                return n;
            }
        }
        // If we made it here, we didn't find the entry.
        System.out.println("Returning Null - did not find entry in hash table");
        return null;
    }

    /** This is the main method that retrieves records by offsets, parsing their latitudes,
     * longitudes, and featureclasses. This just uses a simple scanner, delimited by '|',
     * and just calls next. If I were writing this in a real setting, I actually WOULD NOT
     * use a loop to call scanner.next() (maybe future versions could want to retrieve a different
     * reecord type). I didn't use a loop here!
     *
     * @param offset            Long offset of the record to retrieve
     * @param filename          Filename to retrieve from
     * @return
     * @throws IOException
     */
    public String[] retriever(long offset, String filename) throws IOException {

        /* Our try blocks to ensure the params were correct were handled up above,
        so these aren't wrapped in try blocks. Initialize everything

         */
        File gisfile = new File(filename);
        RandomAccessFile gis = new RandomAccessFile(gisfile, "r");

        // call seek to go to offset
        gis.seek(offset);

        // get the full record
        String recordline = gis.readLine();

        /* This is the parsing logic. Just call a scanner and start parsing, storing everything
            as a string
         */
        Scanner parser = new Scanner(recordline);
        parser.useDelimiter("\\|");
        parser.next(); // Feature ID
        parser.next(); // Feature Name
        String featureclass = parser.next(); // Feature Class - **
        // I would actually write it this way - the long way - if I were in any professional setting,
        // so that anyone else could change what record we're retrieving!
        parser.next(); // State Alpha
        parser.next(); // State Numeric
        parser.next(); // County Alpha
        parser.next(); // County Numeric
        String primLatDMS = parser.next(); // Primary Latitude - DMS **
        String primLongDMS = parser.next(); // Primary Longitude - DMS **

        /* Shove 'em in arrays and return them!
         */
        String offsie = String.valueOf(offset);
        String[] strarr = new String[4];
        strarr[0] = offsie;
        strarr[1] = featureclass;
        strarr[2] = this.parseGPS(primLatDMS);
        strarr[3] = this.parseGPS(primLongDMS);

        return strarr;
    }

    /** Separate method to turn raw Latitudes and Longitudes into human-friendly format. Similar to project 1
     *
     * @param x     String of a latitude or longitude from the record, raw
     * @return      Parsed, "79d 49d 9s West" type of string
     */
    public String parseGPS(String x) {
        // If west:
        if (x.contains("W")) {
            String degs = x.substring(0, 3);
            /* If minutes is 08, for example, we want to print 8.
             */
            if (degs.charAt(0) == '0') {
                degs = degs.substring(1);
            }
            String mins = x.substring(3, 5);
            if (mins.charAt(0) == '0') {
                mins = mins.substring(1);
            }
            String secs = x.substring(5, 7);
            if (secs.charAt(0) == '0') {
                secs = secs.substring(1);
            }
            String full = degs + "d " + mins + "m " + secs + "s West";
            return full;
        }
        // If east:
        if (x.contains("N")) {
            String degs = x.substring(0, 2);
            // If secs is 04 for example, we want to print 4.
            if (degs.charAt(0) == '0') {
                degs = degs.substring(1);
            }
            String mins = x.substring(2, 4);
            if (mins.charAt(0) == '0') {
                mins = mins.substring(1);
            }
            String secs = x.substring(4, 6);
            if (secs.charAt(0) == '0') {
                secs = secs.substring(1);
            }

            String fullw = degs + "d " + mins + "m " + secs + "s North";
            return fullw;
        }

        return null;
    }
//    
//    public boolean createHash() {
//        hash = new hashTable<>(null, 1.0);
//        if (hash != null) {
//            return true;
//        }
//        return false;
//    }
//    
//    public void clearNameIndex() {
//        hash = null;
//        this.createHash();
//    }
    
    
    /* Public method to call insert2*/
    public void newInsert(nameEntry n) throws IOException {
        
        hash.insert2(n);
    }

    /** The meat of building the hash-table - indexer just opens up and builds our
     * hashtable, inserting each entry one by one.
     * @param filename          File name of the GIS record
     * @return                  The hashtable of nameEntries we built
     * @throws IOException
     */
    public hashTable<nameEntry> indexer(String filename) throws IOException {

        /* New hash table, and open up the file to start reading and parsing it.

         */
        //hash = new hashTable<>(null, 1.0);
        try {
            File gis = new File(filename);
            RandomAccessFile r = new RandomAccessFile(gis, "r");

            // start:
            Scanner readfull = new Scanner(gis);
            // check the first line and get scanner and file to their starting positions
            readfull.nextLine();
            r.readLine();

            // while scanner has next line, add those entries?
            while (readfull.hasNextLine()) {
                Long offie = r.getFilePointer();
                String readline = r.readLine();

                // In the VA_Highlands example, there is actually an empty line after the last result.
                // This should ideally be more carefully written, but this is to handle the edge case
                // where there are n numbers of extra lines at the end of a file. Since GIS data is
                // guaranteed to not have any breaks in it, the only null lines will be at the end of the file.
                if (readline == null) {
                    break;
                }
                Scanner scanner = new Scanner(readline);
                scanner.useDelimiter("\\|");

                // get past feature id:
                scanner.next();

                // getting feature name. THIS ASSUMES FILE OFFSET STARTS FROM THE BEGINNING
                String featurename = scanner.next();
                scanner.next();
                String featurestate = scanner.next();
                String fullname = featurename + featurestate;
                //Long offie = r.getFilePointer();
                nameEntry n = new nameEntry(fullname, offie);
                
                hash.insert2(n);
                // close scanner?
                scanner.close(); //************************************************


            }
            // close all of them:
            readfull.close();
            r.close();
            //hash.printAll();
            //System.out.println("the table at 256 like this: ");
            //hash.display();
            //System.out.println("new table, resized, like this : ");
            //hash.displayx();

            return hash;
        } catch (FileNotFoundException e) {

        }

        return null;
    }

}

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

/** This is our buffer pool - it uses ArrayList of Strings for the slots, simply calling add to add to the front.
 * Max slots are set at 15 for the entire project, therefore the constructor is just a base constructor
 * that establishes 15. It would be interesting to make maxsize change given the size of the input!
 * @author  Josh Pandey
 * 
 */
public class bufferPool {

    
    private ArrayList<String> buffer;
    private final int maxSize = 15;
    
    /** Bufferpool constructor just creates a new arrayList set
     * to the maximum size.
     * 
     */
    public bufferPool() {
        buffer = new ArrayList<String>(maxSize);
    }
    
    
    /** Add to the buffer! I used strings for the buffer, so all retrieval happens
     * by actually appending (to the front, to minimize scans) an offset to a GIS
     * record. I think this is a more efficient way to retrieve than creating a new GIS Record
     * class, since no fields are required this way. I could be wrong and would love to know
     * if a GIS Records class was the right solution!
     * 
     * @param str               The string to add to the buffer (an entire GIS record)
     * @param offset            The offset to add to this buffer (the Offset in our indexes, related to that GIS Record*/
     
    public void addToBuffer(String str, long offset) {
        // add most recently used items to the front. In 2 different
        // strings
        
        String offs = offset + "|";
        String total = offs + str;
        
        // Add to the front. ArrayList's add method automatically shifts every otehr
        // item in the arraylist back (forward to their index number).
        buffer.add(0, total);
        
        // Now, if the buffer's exceeded its max size, just resize it
        if (buffer.size() >= maxSize) {
            this.resize();
        }
    }
    
     /** Resize method for the buffer pool is really just
      * removing the last entry. How Darwinian!
      * 
      */
    public void resize() {
        // remove the last entry
        
        // can be just remove(14), but keep it like this:
        buffer.remove(buffer.size() - 1);
    }
    
    /** This method goes into the disk, opening up a 
     * database file, and then parses the string found in the database file
     * into the exact things to print in an array.
     * 
     * @param fl            The string name of the database file
     * @param offset        The long offset we're searching for
     * @command             An in-project code:
     *                      1 = what-is   2 = what-is-at   3 = what-is-in      
     */
    public String[] diskRetrieve(String fl, long offset, int command) {
        String[] arr = new String[5];
        try {
            RandomAccessFile raf = new RandomAccessFile(fl, "r");
            raf.seek(offset);
            String readline = raf.readLine();
            
            /* The following applies for any record, so we'll keep it as a general
             * rule:*/
             
                if (readline == null) {
                    raf.close();
                    return null;
                }
                
                /* if the buffer size is less than 15, add it :*/
                
                if (buffer.size() < 15) {
                    this.addToBuffer(readline, offset);
                }
                else {
                    this.resize();
                    this.addToBuffer(readline, offset);
                }
                
                /* I wonder if this is inefficient, but then I remember
                 * it's called essentially a maximum of the number of
                 * commands there are in a file; so it's likely constant,
                 * since n would be GIs records? Sorry if this is messy, it's
                 * neatly organized/clean reasoning to me! There is a better way
                 * to do this, most likely, though. I wanted to avoid loops because
                 * if I were in any professional setting, I'd keep it like this
                 * for easy access to any changes
                 */
                //System.out.println(" readline is : " + readline);
                Scanner scanner = new Scanner(readline);
                scanner.useDelimiter("\\|");
                scanner.next(); /* Feature ID*/
                String featureName = scanner.next(); /* Feature name*/
                scanner.next(); /* Feature class*/
                String state = scanner.next(); /* State alpha*/
                scanner.next(); /* State numeric*/
                String countyName = scanner.next(); /* County name*/
                scanner.next(); /* County numeric*/
                String primLatN = scanner.next();
                String primLongW = scanner.next();
                
                String westDeg = primLongW.substring(0, 3);
                String westMin = primLongW.substring(3, 5);
                String westSec = primLongW.substring(5, 7);
                String northDeg = primLatN.substring(0, 2);
                String northMin = primLatN.substring(2, 4);
                String northSec = primLatN.substring(4, 6);
                
                if (westDeg.charAt(0) == '0') {
                    westDeg = westDeg.substring(1);
                }
                if (westMin.charAt(0) == '0') {
                    westMin = westMin.substring(1);
                }
                if (westSec.charAt(0) == '0') {
                    westSec = westSec.substring(1);
                }
                
                if (northDeg.charAt(0) == '0') {
                    northDeg = northDeg.substring(1);
                }
                if (northMin.charAt(0) == '0') {
                    northMin = northMin.substring(1);
                }
                if (northSec.charAt(0) == '0') {
                    northSec = northSec.substring(1);
                }
                
                
                String totalWest = westDeg + "d " + westMin + "m "
                    + westSec + "s West,";
                String totalNorth = northDeg + "d " + northMin +"m "
                    + northSec + "s North";
            
            
                
                    /* Command logic: This follows so that in our main call,
                     * we can always just print arr[1], arr[2], arr[3], etc.
                     */
                    /* better way is with ints*/
                
                /* What is case. I used an int instead of comparing strings :*/
                if (command == 1) {
                    arr[0] = countyName;
                    arr[1] = totalNorth;
                    arr[2] = totalWest;
                }
                /* What is at case:*/
                if (command == 2) {
                    arr[0] = featureName;
                    arr[1] = countyName;
                    arr[2] = state;
                    arr[3] = totalWest; /* Used these to print onto logfile*/
                    arr[4] = totalNorth; /* Used this to print onto logfile*/
                }
                
                if (command == 3) {
                    arr[0] = featureName;
                    arr[1] = state;
                    arr[2] = primLatN;
                    arr[3] = primLongW;
                }
            
            raf.close();
            scanner.close();
            return arr;
        }
        catch (Exception e) { 
            System.out.println(" Exception in retriever method in GIS!");
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    

    /** This is the Retriever funciton that the actual writing to the logfile
     * depends on. Here, we access the buffer pool, and if we find an item,
     * we set up a new scanner to retrieve the right parts of that GIS record.
     * 
     * If the item isn't in the buffer pool, we set up a scanner (redundantly),
     * to then go into the database file and retrieve a record.
     * 
     * @param fl                    The Logfile's name, in case the item isn't in the buffer
     * @param offset                The offset of the record we're retrieving
     * @param command               An in-project code for command:
     *                              1 = "what is",  2 = " what_is_at", 3 = "what_is_in"
     *                              
     *                              It seemed better to compare ints than compare strings,
     *                              as there were only 3 cases.
     *                              
     * @return A string array of the exact things to write onto our logfile
     */
    public String[] retrieverFull(String fl, long offset, int command) {
        /* open the db file */
        String[] arr = new String[5];
        
        // on every call, just check the buffer to see if this is there:
        for (String item : buffer) {
            Scanner scanoff = new Scanner(item);
            scanoff.useDelimiter("\\|");
                                if (Long.valueOf(scanoff.next()) == offset) {
                                    // offsets are the same, retrieve this record
                                    
                                    scanoff.next();
                                    String featureName = scanoff.next(); /* Feature name*/
                                    scanoff.next(); /* Feature class*/
                                    String state = scanoff.next(); /* State alpha*/
                                    scanoff.next(); /* State numeric*/
                                    String countyName = scanoff.next(); /* County name*/
                                    scanoff.next(); /* County numeric*/
                                    String primLatN = scanoff.next();
                                    String primLongW = scanoff.next();
                                    
                                    String westDeg = primLongW.substring(0, 3);
                                    String westMin = primLongW.substring(3, 5);
                                    String westSec = primLongW.substring(5, 7);
                                    String northDeg = primLatN.substring(0, 2);
                                    String northMin = primLatN.substring(2, 4);
                                    String northSec = primLatN.substring(4, 6);
                                    
                                    if (westDeg.charAt(0) == '0') {
                                        westDeg = westDeg.substring(1);
                                    }
                                    if (westMin.charAt(0) == '0') {
                                        westMin = westMin.substring(1);
                                    }
                                    if (westSec.charAt(0) == '0') {
                                        westSec = westSec.substring(1);
                                    }

                                    if (northDeg.charAt(0) == '0') {
                                        northDeg = northDeg.substring(1);
                                    }
                                    if (northMin.charAt(0) == '0') {
                                        northMin = northMin.substring(1);
                                    }
                                    if (northSec.charAt(0) == '0') {
                                        northSec = northSec.substring(1);
                                    }
                                    
                                    String totalWest = westDeg + "d " + westMin + "m "
                                        + westSec + "s West,";

                                    String totalNorth = northDeg + "d " + northMin +"m "
                                        + northSec + "s North";
                                
                                
                                    
                                        /* Command logic: This follows so that in our main call,
                                         * we can always just print arr[1], arr[2], arr[3], etc.
                                         */
                                        /* better way is with ints*/
                                    
                                    /* What is case. I used an int instead of comparing strings :*/
                                    if (command == 1) {
                                        arr[0] = countyName;
                                        arr[1] = totalNorth;
                                        arr[2] = totalWest;
                                    }
                                    /* What is at case:*/
                                    if (command == 2) {
                                        arr[0] = featureName;
                                        arr[1] = countyName;
                                        arr[2] = state;
                                        arr[3] = totalWest; /* Used these to print onto logfile*/
                                        arr[4] = totalNorth; /* Used this to print onto logfile*/
                                    }
                                    
                                    if (command == 3) {
                                        arr[0] = featureName;
                                        arr[1] = state;
                                        arr[2] = primLatN;
                                        arr[3] = primLongW;
                                    }
                                
                                scanoff.close();
                                return arr;
                                    
                                }
                                scanoff.close();
        }
        
                        /* Repeating the same logic as above. But now we access the disk. */
        
            /* if we got here, we need ot go to the disk since it wasn't in the buffer.*/
        
        arr = this.diskRetrieve(fl, offset, command);
        return arr;
            } /* end retriever*/
    
    /** Write out our buffer pool
     * in MRU -> LRU order
     * 
     * @param out       FileWriter to write to
     */
    public void displayWrite(FileWriter out) throws IOException {
        out.write("MRU " + "\n");
        
        for (String item : buffer) {
            out.write(item + "\n");
        }
        
        out.write("LRU" + "\n");
    } /* end displayWRite*/
    
} /* End clasS*/

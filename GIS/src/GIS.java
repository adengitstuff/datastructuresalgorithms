import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

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

/**
 * 
 */

/**
 * The main GIS class acts as the controller for the entire project! I use a
 * buffer pool of Strings, as I thought
 * it would be more efficient than a separate GIS Record object for every single
 * record (e.g. if N > 100,000,000, I didn't
 * want to store fields).
 * 
 * @author Josh Pandey
 * @date 4/18/2021
 *
 */
public class GIS {

    private gigaIndex giga;
    private RandomAccessFile dbfile;
    private FileWriter logfile;
    private String dbfilename;
    private long lastOffset = 0L;
    private bufferPool buff;

    /**
     * GIS Constructor
     * 
     * @param Database
     *            File, Command Script file, log file!
     */
    public GIS() {
        giga = new gigaIndex();
        buff = new bufferPool();

    }


    /*
     * Main method checks to see if all files are in order,
     * instantiating the right things!
     */
    public static void main(String[] args) {

        GIS gis = new GIS();
/* Database file check! */
        try {
            File data = new File(args[0]);

            if (data.exists()) {
                data.delete();
                // delete the file
                // recreate it and continue?
            }
            gis.dbFileInitialize(args[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
        } /* End try block */

/*
 * Log file - do we actually need to do all of this? What if new file
 * automatically etc?
 */

        try {
            File log = new File(args[2]);

            if (log.exists()) {
                // delete and recreate
                log.delete();

            }
            gis.logFileCreate(args[2]);

        }
        catch (Exception e) {
            e.printStackTrace();
        } /* End log file block */

/* Command file block! */
        try {
            File command = new File(args[1]);

            if (command.exists()) {
                // pass the File to another function, perhaps in another class?
                // read the command file line by line
                gis.commandParser(command, args[0], args[1], args[2]);
            }
            else {
                System.out.println(
                    "The command file was not found. Remember the order is: java GIS [database] [command] [logfile]");
                System.out.println("The program is now exiting!");
                /* actually exit the program */
                System.exit(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } /* End command file block */

    } /* End Main! */


    /* Create our log file */
    public void logFileCreate(String filename) {
        try {
            logfile = new FileWriter(filename);
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }


    /* Initialize our database file */
    public void dbFileInitialize(String filename) {
        try {
            dbfile = new RandomAccessFile(filename, "rw");
            dbfilename = filename;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** This function takes in a GIS Filename, appends every entry onto
     * a database file, and then instantly indexes all entries that were added in.
     * This is done right away (and not in another method) so that after each loop,
     * the offset of the end of the database file can be saved, and therefore we never
     * have to add the same entry two times. I thought this was a necessity for a program like this
     * in some theoretical case where N was 10,000,000, for example, but there is definitely
     * a cleaner way to index and call index outside of this method.
     * 
     * @param filename          The GIS file path
     * @return                  An int array of size 2, [0] = number of records indexed by name,
     *                                                  [1] = number of records indexed by location
     */
    public int[] databaseFileAppend(String filename) {

        try {
            // dbfile = new FileWriter("dbfile!.txt");
            File gis = new File(filename);
            RandomAccessFile r = new RandomAccessFile(gis, "r");

            // start:
            Scanner xs = new Scanner(gis);
            
            while (xs.hasNextLine()) {
                String str = xs.nextLine();
                // System.out.println(" x : " + str);
                dbfile.writeBytes(str + "\n");

                // dbfile.append(str + "\n")
            }
            /* This is the last offset */
            // lastOffset = dbfile.

            // we want to call it here before we update it...

            
            /* Index right away, such that we can store the file offset of our
            database file*/
            int[] totals = giga.fullIndexTry(dbfilename, lastOffset);
            
            /* Lastoffset is now the last entry of our db file.
             * Even if 5, 10, or 20 new imports, we still index each record only once
             */
            lastOffset = dbfile.getFilePointer();
       

            r.close();
            xs.close();
            return totals;
            // dbfile.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Everything is separated by tab lines - awesome! This is the main
     * "Controller"
     * method, but one that will write to our log file directly in the method.
     * 
     * String.split() is used here in lieu of creating of a command class, as
     * it was mentioned in the project spec that every command in every script
     * is indented with tab characters. The string array is declared and made null
     * after each command, to avoid declaring a new string array for each command. 
     * 
     * Commands are parsed into a string array, and passed into the rest of the program.
     * An offset is retrieved, and then passed into our buffer pool! What our buffer pool
     * returns is actually a string arrray of the exact thing to log and write. The "Controller"
     * method is abstracted as a "Writer" method in this interpretation/design!
     * 
     * @param rawCommandFile            The script or command file to execute
     * @param args0, args1, and args3   Args passed in, to write to output.
     * @throws IOException 
     * 
     */
    public void commandParser(File rawCommandFile, String args0, String args1, String args2) throws IOException {

        /* We're passed in a file. So declare a new random access file and a scanner
         * to use scanner's nextline method. (I'm not sure if this is the most intelligent
         * way, I've always done it like this!)
         */
        RandomAccessFile r = new RandomAccessFile(rawCommandFile, "r");        
        Scanner xs = new Scanner(rawCommandFile);
        int nn = 1;
        
        /* I use string's split() method to keep the parameters to the commands
         * in an array. Declare array here to conserve memory!
         */
        String[] tabs = null;
        
        /* While the scanner has a next line, read the current line*/
        while (xs.hasNextLine()) {
            try {
                String x = r.readLine();
                
                if (x.charAt(0) != ';') {
                    /* Might be better to declare 1 array here, and then
                     * keep making it empty at each pass?
                     */
                    /* Main parser logic : */
                    
                    if (x.contains("world\t")) {
                        tabs = x.split("\\t");
                        for (int i = 0; i < tabs.length; i++) {
                            /* Didn't use StringUtils.chop here, not sure if it would compile and stuff on TA's pc? Am I okay to use it?*/
                            tabs[i] = tabs[i].substring(0, (tabs[i].length() - 1));
                        }
                        
                        // call our index to create a location index
                        try {
                            giga.quadMaker(Long.parseLong(tabs[3]), Long.parseLong(tabs[2]), Long.parseLong(tabs[4]), Long.parseLong(tabs[1]));
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                                                        /* Writing logic. This is just handled in one place*/
                        logfile.write("\n");
                        logfile.write("GIS Program" + "\n");
                        logfile.write("dbFile:" + "\t" + args0 + "\n");
                        logfile.write("script :" + "\t" + args1 + "\n");
                        logfile.write("log: " + "\t" + args2 + "\n");
                        logfile.write("Start time: " + new java.util.Date() + "\n");
                        logfile.write("Quadtree children are printed in the order SW SE NE NW" + "\n");
                        logfile.write("-----------------------------------------------------------" + "\n");
                        logfile.write("\n" + "Latitude/longitude values in index entries are shown as "
                            + "signed integers, in total seconds." + "\n" + "World boundaries are set to:" +
                            "\n");
                        // call our function to neatly parse our world boundaries into ints:
                        int[] rightInts = this.worldBoundaries(tabs);
                        logfile.write("\t" + "\t" + "   " + rightInts[3] + "\n");
                        logfile.write("\t" + "-" + rightInts[1] + "\t" + "\t" + "\t" + "-" + rightInts[0] + "\n");
                        logfile.write("\t" + "\t" + "   " + rightInts[2] + "\n");
                        logfile.write("------------------------------------------------------------------------------------" + "\n");
                        
                        
                                                tabs = null;
                    }


                    
                    
                        /* This is the case where the script contains import. Append the GIS record to our database file, and then index everything that hasn't been indexed
                         * in that file*/
                        
                    if (x.contains("import\t")) {
                        
                    
                        int c = x.indexOf("\t");
                        String argsgis = x.substring(c);
                        /* declare an int array, to use to store how many records were indexed. We need to display this right
                        after we import*/
                        int[] numtots = null;
                            /* The following are just lines i made while writing, that I just left in for fun, lel*/

                                        /* Let's run through this case of multiple imports logically */
                                    /* We imported something new. So we add it onto a db file, then we go through that db
                                     * file from where we were last, adding everything. 
                                     */
                                        /* We want to open up the db file again, at that offset, then import everything we see.*/
                    
                        try {
                            
                            
                            /* We have established the GIS file to read and have it as a random access file. so... */
                            // declare our num array to be the int array returned by databasefileappend (this returns how many items were indexed)
                            numtots = databaseFileAppend(argsgis.trim());

                            
                        }
                        catch (Exception e) { 
                            System.out.println(" Exception in the IMPORT part of commandParser function. ");
                            e.printStackTrace();
                        }
                        
                        
                                                /* FileWriter block. */
                            logfile.write("Command " + nn + ":   Import" + "\t" + argsgis.trim() + "\n");
                            logfile.write("\n" );
                            logfile.write("Imported Features by name:" + "\t" + numtots[0] +  "\n");
                            logfile.write("Imported Locations: " + "\t" + "\t" + numtots[1] + "\n");
                            logfile.write("Average name length: " + "\t" + "\n");
                            logfile.write("------------------------------------------------------------------------------------" + "\n");
                            nn++;
                    }
                    
                    
                    
                    
                                    /* This is the case where the command is a "what_is" command. Create a nameentry and 
                                     * find it in our hashtable! Use our bufferpool to retrieve the string to put in.
                                     */
                    
                    if (x.contains("what_is\t")) {
                        
                        //String[] whatis = x.split("\\t");
                        
                        /* Split tabs into each individual component*/
                        tabs = x.split("\\t");
                        String together = tabs[1].trim() + tabs[2].trim();
                        nameEntry n = null;

                                                     /* FileWriter try:*/
                                 logfile.write("Command " + nn + ": " + x + "\n");
                                 logfile.write("\n");

                                     
                                 /* look up the nameentry. null case is handled*/
                                 n = giga.nameLookUp(together);
                                 
                                 if (n != null) {
                                     for (long l2 : n.locations()) {
                                         String[] arrs = buff.retrieverFull(dbfilename, l2, 1);
                                         logfile.write(l2 + ": " + arrs[0] + "(" + arrs[2] + arrs[1] + ")" + "\n");
                                         
                                     }
                                 }
                                 else {
                                     logfile.write("Nothing was found at " + tabs[1] + " " + tabs[2] + "\n");
                                 }

                                 logfile.write("------------------------------------------------------------------------------------" + "\n");
                        
                        nn++;
                        tabs = null;
                    }
                    
                    /* This is the case where the command is "what is at "*/
                    if (x.contains("what_is_at\t")) {
                        tabs = x.split("\\t");

                        String n = tabs[1].substring(0, tabs[1].length() - 1);
                        String w = tabs[2].substring(0, tabs[2].length() - 1);
                        Long xlng = Long.parseLong(n);
                        Long ylng = Long.parseLong(w);
                        coordinate k = null;
                        k = giga.findCoordinate(new coordinate(xlng, ylng, 0L));
                        String[] westn = neatParser(tabs[1], tabs[2]);

                            /*FileWriter:*/
                        logfile.write("Command " + nn + ": " + x + "\n");
                        logfile.write("\n");
                        
                        if (k == null) {
                            logfile.write("Nothing was found at (" + westn[0] + ", " + westn[1] + ")" + "\n");
                        }else {
                            
                                                /*FileWriter try*/
                                logfile.write("\t" + "The following features were found at (" + westn[0] + ", " + westn[1] + "): \n");
                                for (int i = 0; i < k.offsets().size(); i++) {
                                    String[] recx = buff.retrieverFull(dbfilename, k.offsets().get(i), 2);
                                    
                                    logfile.write("\t" + k.offsets().get(i) + ": " + recx[0] + " " + recx[1] + " " + recx[2] + "\n");
                                }
                        }
                                logfile.write("------------------------------------------------------------------------------------" + "\n");

                        nn++;
                        tabs = null;
                    }
                    
                    
                    /* This is the "what is in" case - a range search*/
                    if (x.contains("what_is_in\t")) {                                            
                        tabs = x.split("\\t");                        
//          
//                        String n = tabs[1].substring(0, tabs[1].length() - 1);
//                        String w = tabs[2].substring(0, tabs[2].length() - 1);
            
                        // Get the "neat" version of the long values - i.e 370200N 0820200W   120 120 into: 370000, 370400, 082000, 0820400. 
                        // Do this to pass into our rangesearch.
                        long[] la = this.geoLogic(tabs);
                        
                        Vector<coordinate> v = giga.getprQuad().find(la[0], la[1], la[2], la[3]);
                        
                        
                                                                 /* FileWrite try: */
                                             logfile.write("Command " + nn + ": " + x + "\n");
                                             logfile.write("\n");
                                             String[] westnorth = null;
                                             try {
                                             westnorth = this.neatParser(tabs[1], tabs[2]);
                                             }
                                             catch(Exception e) {                                                
                                                 e.printStackTrace();
                                             } 
                                             
                                             // If the vector is empty, nothing is found:
                                             if(v.isEmpty()) {

                                                     logfile.write(" Nothing was found in (" + westnorth[0] + "+/-" + tabs[3] + ". " + 
                                                 westnorth[1] + "+/-" + tabs[4] + ")" + ")\n");
                                             
                                             }
                                             // Else, write what was found:
                                             else {

                                                 logfile.write("\t" + "The following features were found in (" + westnorth[0] + " +/-" + tabs[3] +
                                                     ", " + westnorth[1] + " +/-" + tabs[4] + ")\n");
                                                 for (coordinate elem: v) {
                                                     for (int i = 0; i < elem.offsets().size(); i++) {
                                                         String[] arr = buff.retrieverFull(dbfilename, elem.offsets().get(i), 3);
                                                         String[] neatver = this.neatParser(arr[2], arr[3]);
                                                         
                                                         logfile.write("\t" + elem.offsets().get(i) + ": " + arr[0] +
                                                             " " + arr[1] + " (" + neatver[0] + ", " + neatver[1] + ") \n");
                                                     }
                                                 }
                                             }
                                             logfile.write("------------------------------------------------------------------------------------" + "\n");
                      
                        nn++;
                        tabs = null;
                    }
                    
                    /* Debug cases simply call display methods.*/
                    if (x.contains("debug\t")) {
                        
                        
                        /* In this case, there's no logic, we just print. So write the command first*/
                        logfile.write("Command " + nn + ": " + x + "\n");                                        
                        String hashquad = x.substring(x.indexOf("\t")).trim();
                        
                        if (hashquad.contentEquals("hash")) {
                            logfile.write("This is a command to print the hash table. On it!" + "\n");
                            giga.hashDebug(logfile);
                            logfile.write("\n");
                        }
                        else if (hashquad.contentEquals("quad")) {
                            logfile.write("This is a command to print the Tree. On it!!" + "\n");
                            giga.writeTree(logfile);
                            logfile.write("\n");
                        }
                        else if (hashquad.contentEquals("pool")) {
                            buff.displayWrite(logfile);
                        }
                        
                                            
                                            logfile.write("------------------------------------------------------------------------------------" + "\n");
                                            nn++;
                                            tabs = null;
                    } /* end t == debug*/
                    
                    if (x.contains("quit")) { 
                    
                                                        /* FileWrite try:*/
                                        logfile.write("Command "  + nn +": " + "\t" + x + "\n");
                                        logfile.write("\n");
                                        logfile.write("Terminating execution of commands." + "\n");
                                        logfile.write( "End time : " + new java.util.Date() + "\n");
                                        logfile.write("------------------------------------------------------------------------------------------------");
                                        System.out.println("All commands processed. The log file " + args2 + " is ready. Exiting now");
                        this.quitExit();
                        System.exit(0);
                        break;
                    }
                    
                    
                } /* end if char at 0 = ; */
                else {                                  /* This is for if the sentence starts with a semicolon. We want to write this to the logfile*/
                    
                }
                
            }
            catch (IOException e) {                
                e.printStackTrace();
            }
            //r.close();
        } /* End while loop*/
        r.close();
    } /* end command parser */


    /**
     * This function just neatly converts everything into
     * degrees/minutes/systems seconds. I wrote this for edge cases
     * of having 5,000 or 10,000 value rectangles. This takes in a string array
     * of a command (split by tab characters),
     * calculates all addition/subtraction logic, and outputs the right degrees,
     * minutes, and seconds in the order:
     * xMin, yMin, xMax, yMax
     * 
     * This is done 100% entirely for edge cases - so this would support any
     * possibility of the range box being numbers like:
     * 1023
     * 7821
     * 19213
     * And all range values will be right!
     * 
     * @param tabs                  String array of the command, split 
     * 
     */
    public long[] geoLogic(String[] tabs) {

        if (tabs == null) {
            System.out.println("null array passed into geoLogic method");
        }
        /* Take in each value and parse it as a long */
        long[] gps = new long[4];
        String north = tabs[1].substring(0, tabs[1].length() - 1);
        String west = tabs[2].substring(0, tabs[2].length() - 1);

        /* North case: */
        long degN = Long.valueOf(north.substring(0, 2)); // declare longs separately to iterate through
        long minN = Long.valueOf(north.substring(2, 4));
        long secN = Long.valueOf(north.substring(4, 6));
        long degN2 = degN;
        long minN2 = minN;
        long secN2 = secN;

        /* West case: */
        long degW = Long.valueOf(west.substring(0, 3)); // declare this to iterate through
        long minW = Long.valueOf(west.substring(3, 5));
        long secW = Long.valueOf(west.substring(5, 7));

        long degW2 = degW;
        long minW2 = minW;
        long secW2 = secW;

        /*
         * So much memory wasted for this! But it's not that bad, since it
         * happens only once per call. So it's not happening
         * per entry. Feel less bad. Also it's assignments, so negligible.
         */

        /*
         * I think i found the best logic. Basically check if it raises above
         * 60; if it does, go left (from seconds to minutes, from minutes
         * to deg. check if it raises above 60. if it does, go left again.
         * 
         * now, get the number divided by 60, ignoring all .023123 values. add
         * that to this current value.
         * get the remainder of number % 60.
         */

        long xToAdd = Long.valueOf(tabs[3]);
        long yToAdd = Long.valueOf(tabs[4]);
        // System.out.println(yToAdd);
        /* North Case: */

        /*
         * I thought about this for awhile and came up with this. I'm not sure if it's efficient - this is
         * for every conceivable edge case around lat/long - if the
         * input was 1506, and if it was at, say, 87, 58, 54, for example. The alternative is obviously
         * to divide the loop into as many degrees as possible, if possible, then as many minutes as possible,
         * if possible, but then one still needs to check for if minutes are 59. I feel intuitively that there is 
         * an efficient way to do this, but I'm unsure what it is!
         * 
         */

        for (int i = 0; i < xToAdd; i++) {
            secN++;

            if (secN == 60) {
                minN++;
                secN = 0L;

                if (minN == 60) {
                    degN++;
                    minN = 0L;
                }

            }
        }
        if (minN < 10) {
            /* put a leading 0? */
            degN = degN * 10;
        }
        if (secN < 10) {
            minN = minN * 10;
        }

        long xmax = Long.valueOf(new String(Long.toString(degN) + Long.toString(
            minN) + Long.toString(secN)));


        for (int a = 0; a < yToAdd; a++) {
            secW++;

            if (secW == 60) {
                minW++;
                secW = 0L;

                if (minW == 60) {
                    degW++;
                    minW = 0L;
                }
            }
        } /* This is the West - so yMax - number */

        if (secW < 10) {
            minW = minW * 10;
        }

        if (minW < 10) {
            degW = degW * 10;
        }

        long ymax = Long.valueOf(new String(Long.toString(degW) + Long.toString(
            minW) + Long.toString(secW)));
//
// /* Now for the subtractions. NORTH CASE:*/

        for (int i = 0; i < xToAdd; i++) {

            if (secN2 == 0) {
                minN2--;
                secN2 = 60L;

                if (minN2 == 0) {
                    degN2--;
                    minN2 = 60L;
                }
            }
            secN2--;

            /*
             * Please tell me this is smart and I didn't just make this program
             * O(N^58 or anything)
             */
        } /* This is the North - so xMin - number */

        if (minN2 < 10) {
            /* put a leading 0? */
            degN2 = degN2 * 10;
        }
        if (secN2 < 10) {
            minN2 = minN2 * 10;
        }
        long xmin = Long.valueOf(new String(Long.toString(degN2) + Long
            .toString(minN2) + Long.toString(secN2)));

        /* WEST CASE: */
        for (int i = 0; i < yToAdd; i++) {

            secW2--;

            if (secW2 == 0) {
                minW2--;
                secW2 = 60L;

                if (minW2 == 0) {
                    degW2--;
                    minW2 = 60L;
                }
            }
        } /* This is the West - so yMin - number */
        if (secW2 < 10) {
            minW2 = minW2 * 10;
        }

        if (minW2 < 10) {
            degW2 = degW2 * 10;
        }

        long ymin = Long.valueOf(new String(Long.toString(degW2) + Long
            .toString(minW2) + Long.toString(secW2)));
////
//        System.out.println(" SUBTRACTIONS : NORTH! : Degrees : " + degN2
//            + " MINUTES : " + minN2 + "SECCCCS : " + secN2);
//        System.out.println(" And west : " + degW2 + " minutes   :   " + minW2
//            + " Seconds : " + secW2);
////

        gps[0] = xmin;
        gps[1] = ymin;
        gps[2] = xmax;
        gps[3] = ymax;
        return gps;
    }


    /**
     * Gets in a string array of tabs, and returns world boundaries
     * in seconds.
     * 
     * @param tabs commands
     */
    public int[] worldBoundaries(String[] tabs) {
//
// String degW = tabs[1].substring(0, 3);
// String minW = tabs[1].substring(3, 5);
// String secW = tabs[1].substring(5, 7);
//
        int[] newInts = { 0, 0, 0, 0 };

        /* Did it in this way for debugging purposes: */

        int totalWmin = ((Integer.valueOf(tabs[2].substring(0, 3)) * 3600))
            + (Integer.valueOf(tabs[2].substring(3, 5)) * 60) + Integer.valueOf(
                tabs[2].substring(5, 7));

        int totalWmax = ((Integer.valueOf(tabs[1].substring(0, 3)) * 3600))
            + (Integer.valueOf(tabs[1].substring(3, 5)) * 60) + Integer.valueOf(
                tabs[1].substring(5, 7));

        int totalNmin = ((Integer.valueOf(tabs[3].substring(0, 2)) * 3600))
            + (Integer.valueOf(tabs[3].substring(2, 4)) * 60) + Integer.valueOf(
                tabs[3].substring(4, 6));

        int totalNmax = ((Integer.valueOf(tabs[4].substring(0, 2)) * 3600))
            + (Integer.valueOf(tabs[4].substring(2, 4)) * 60) + Integer.valueOf(
                tabs[4].substring(4, 6));


        newInts[0] = totalWmin;
        newInts[1] = totalWmax;
        newInts[2] = totalNmin;
        newInts[3] = totalNmax;
        return newInts;

    }


    /**
     * Parses digit latitude and longitudes into
     * neat Strings
     * 
     * @param n
     *            North value
     * @param w
     *            west value
     */
    public String[] neatParser(String n, String w) {

        /* take N value and turn it into a neat string: */
        String[] neat = new String[3];
        String degn = n.substring(0, 2);
        String minn = n.substring(2, 4);
        String secn = n.substring(4, 6);

        /*
         * Note: For some reason, substring(1) didn't work here. I had to use
         * the 2nd call to
         * substring with 2 params, in order to get this to work. Odd.
         */
        if (secn.charAt(0) == '0') {
            secn = secn.substring(1, secn.length());
        }
        if (minn.charAt(0) == '0') {
            minn = minn.substring(1, minn.length());
        }
        if (degn.charAt(0) == '0') {
            degn = degn.substring(1, degn.length());
        }

        String north = degn + "d " + minn + "m " + secn + "s North";
        /* Now west values */

        String degw = w.substring(0, 3);
        String minw = w.substring(3, 5);
        String secw = w.substring(5, 7);

        if (secw.charAt(0) == '0') {
            secw = secw.substring(1, secw.length());
        }
        if (minw.charAt(0) == '0') {
            minw = minw.substring(1, minw.length());
        }
        if (degw.charAt(0) == '0') {
            degw = degw.substring(1, degw.length());
        }

        String west = degw + "w " + minw + "m " + secw + "s West";

        /*
         * it seems like a waste of memory to declare strings separately,
         * however it will happen only a few times per run
         * so I just used this
         */
        neat[0] = west;
        neat[1] = north;
        return neat;
    }


    /* Close all files */
    public void quitExit() {
        try {
            logfile.close();
            dbfile.close();

        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }
}

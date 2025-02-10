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
// interfere with the normal operation of the supplied grading code.
//
// Josh Pandey
// Josh1

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * This program parses commands and GIS record files in order to index certain
 * GIS records, and perform simple search
 * queries. Given the simplicity and repetitive nature of the search queries, I
 * really thought of it as a program
 * that interacted with computer-made, 100% integrity GIS record files (little
 * to no chance of human error), and thus
 * avoided new classes or new objects.
 * 
 * @author Josh Pandey
 * @date 2.4.2021
 *
 */
public class GISParser {

    /**
     * I didn't abstract this as a piece of software to interact with users; we
     * were given that
     * input files would always have full integrity, and from the beginning I
     * always thought about this
     * project as a project that would, in theory, interact with computer-made
     * pieces of information that couldn't be wrong.
     * Therein, I didn't see an actual need for lots of objects and classes or
     * interweaving parts; it felt intuitively to me
     * that it was a project rooted in reading and extracting substrings from
     * files that would always be 100% accurate, that weren't
     * made by humans.
     * 
     * In this machine-to-machine framework I didn't write a command class or a
     * recordGISclass - I just wrote a main arg that would
     * extract and write files. Please note that this wasn't out of laziness,
     * but it was out of earnest thought - I could definitely be wrong
     * and would love to see where my thinking could be corrected. I just
     * couldn't see a need for lots of objects and classes - they seemed
     * redundant in the absence of a user, and in operations to simply read and
     * write to files!
     */
    public GISParser() {

    }


    public static void main(String[] args)
        throws FileNotFoundException,
        IOException {

        if (args.length == 4) {
            // ** clean up and check for good inputs

            if (args[0].contentEquals("-search")) {
                secondTry(args[1], args[2], args[3]);
            }
            else {
                System.out.println("Did you mean to type -search? Try again");
            }
        }

        else if (args.length == 3 && args[0].equals("-index")) {

            // create a string of the recordfile name for later:
            String recordfilename = args[1];
            // Get args 3 and create the string filename:
            String logfilename = args[2]; // ** WRAP ALL IN try blocks

            File testFile = null;
            try {
                testFile = new File(args[1]);
                if (!testFile.exists()) {
                    System.out.println("Error: could not find config file "
                        + args[0]);

                    return;
                }

                RandomAccessFile randie = new RandomAccessFile(testFile, "r");
                // skip the first line:
                randie.readLine();

                FileWriter filie = new FileWriter(logfilename);

                filie.write(recordfilename
                    + " contains the following records: \n");

                // This was the simplest and most intuitive method I could
                // devise for writing and reading. It uses
                // a scanner and a randomaccessfile at the same time to index.
                // I'm not sure about the efficiency
                // or if scans count as operations!

                Scanner eofScan = new Scanner(testFile);
                // set scanner to next line, to match randomfile
                eofScan.nextLine();
                eofScan.useDelimiter("\\|");
                while (eofScan.hasNextLine()) {
                    long q = randie.getFilePointer();
                    filie.write("\n \t" + q);
                    eofScan.next();
                    String s = eofScan.next();
                    filie.write("\t" + s);
                    randie.readLine();
                    eofScan.nextLine();

                }
                eofScan.close();
                filie.close();
                randie.close();

            }

            finally {

                // finally block: not sure.
            }
        }
    }


    /**
     * This is a method that completes the "Search" feature. It uses 1 helper
     * method. It handles offset
     * logic inside of itself, and uses a helper method to simply return strings
     * that will be written
     * to the log file.
     * 
     * 
     * @param gisrecords
     *            The GIS records file as a string
     * @param commands
     *            The commands file as a string
     * @param logfile
     *            The log files as a string
     * @throws IOException
     */
    public static void secondTry(
        String gisrecords,
        String commands,
        String logfile)
        throws IOException {

        File gis = new File(gisrecords);
        File cmds = new File(commands);
        FileWriter log = null;

        String stringcmd = null;
        int count = 0; // basic count of commands;

        try {
            log = new FileWriter(logfile);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scancmds = new Scanner(cmds); // use a scanner to find
        // the next command. ignore everything except "Show"
        RandomAccessFile randcmds = new RandomAccessFile(cmds, "r");
        // access GIS file now, to close outside the loop
        RandomAccessFile gisread = new RandomAccessFile(gis, "r");
        /// READING THE COMMANDS FILE:

        while (scancmds.hasNextLine()) {
            String yy = randcmds.readLine();
            if (yy.contains("quit")) {
                log.write("\t" + count + ": " + "quit \n");
                log.write("\t" + "Exiting");
                log.close();
            }
            if (yy.contains("show")) {

                // open the GIS file to read from. do this early for offset
                // checks:

                // Get the offset as an integer/long to feed into seek()!
                count = count + 1;
                int sub = yy.indexOf('\t');
                stringcmd = yy.substring(0, sub);
                System.out.println("the command is ~~~: " + stringcmd);

                String intoffset = yy.substring(sub + 1, yy.length());
                long off = Integer.parseInt(intoffset);

                // Write the first line of the file:

                // If offset is negative
                if (off < 0) {
                    log.write("\t" + count + ":  " + yy + "\n");
                    log.write("\t " + "Offset is not positive" + "\n");
                }
                else if (off > gis.length()) {
                    log.write("\t" + count + ":  " + yy + "\n");
                    log.write("\t " + "Offset is too large" + "\n");
                }

                else {
                    // Read the GIS file. Use seek() to go to the offset and
                    // read the line

                    // first, check if the last character was a newline
                    // character. this is a relatively inefficient
                    // way to do this (create a new randomaccessfile), but it's
                    // late and I'm scared to mess with pointers
                    RandomAccessFile readcheck = new RandomAccessFile(gis, "r");

                    readcheck.seek(off - 1);
                    byte newlinecheck = readcheck.readByte();
                    if (newlinecheck != '\n') {
                        log.write("\t" + count + ":  " + yy + "\n");
                        log.write("\t  " + "Offset is unaligned" + "\n");
                    }
                    else {
                        gisread.seek(off);
                        String ss = gisread.readLine();
                        System.out.println(yy + " ~ " + intoffset);
                        System.out.println("the string ss, after offset is: "
                            + ss);

                        // Fetch the right data from the line

                        String result = fetcher(ss, stringcmd);
                        System.out.println(
                            "THE RETURN, (THIS SHOULD BE ELEVATION)" + result);
                        // Code to actually write the file:
                        log.write("\t" + count + ":  " + yy + "\n");
                        log.write("\t  " + result + "\n");
                    }
                    readcheck.close();
                }
            }
            scancmds.nextLine();
            // randcmds.readLine();
        }

        scancmds.close();
        randcmds.close();
        gisread.close();

    }


    /**
     * This is a separate "Fetcher" method that parses individual lines and
     * returns
     * the right output. Commands are passed in as strings and then parsed with
     * contains,
     * since command files will always have 100% integrity
     * 
     * @param fullString
     *            A full string in a GIS record
     * @param command
     *            The given command itself (whether name, elevation, quit,
     *            latitude, etc)
     * @return A string of the right output to write into the log file.
     */
    public static String fetcher(String fullString, String command) {

        Scanner xs = null;
        // check if string is null
        if (fullString != null) {
            xs = new Scanner(fullString);
            xs.useDelimiter("\\|");
        }
        // opening logfile code:
        // ELEVATION SCAN:
        String scanned = null;

        if (command.contains("elevation")) {
            for (int i = 0; i < 17; i++) {
                if (xs.hasNext()) {
                    scanned = xs.next();
                }

                if (scanned.equals("Unknown")) {
                    scanned = "Coordinate is not given";
                }
                else if (scanned.equals("")) {
                    scanned = "Elevation is not given";
                }
            }
//            System.out.println(
//                " ~ "
//                    + scanned);
        }

        else if (command.contains("name")) {
            xs.next();
            scanned = xs.next();
        }

        else if (command.contains("latitude")) {
            for (int i = 0; i < 8; i++) {
                if (xs.hasNext()) {
                    scanned = xs.next();
                }

                if (scanned.equals("Unknown")) {
                    scanned = " Coordinate is not given";
                }

                // change latitude to human-friendly vibes!
                else if (scanned.length() > 6) { // ** use a constant here!
                    String degs = scanned.substring(0, 2);
                    String mins = scanned.substring(2, 4);
                    if (mins.charAt(0) == '0') {
                        mins = mins.substring(1);
                    }
                    String secs = scanned.substring(4, 6);
                    if (secs.charAt(0) == '0') {
                        secs = secs.substring(1);
                    }
                    String dir = scanned.substring(6, 7);
                    String full = degs + "d " + mins + "m " + secs + "s ";

                    if (dir.contentEquals("N")) {
                        dir = "North";
                    }
                    if (dir.contentEquals("S")) {
                        dir = "South";
                    }
                    full = full + dir;
                    scanned = full;

                    // test/whiteboard logic for checking newline:

                    // offset-1 seek
                    // raf.readChar(); previousbyte. readbyte
                    // char == '\n'
                    //

                }

            }
        }
        else if (command.contains("longitude")) {
            for (int i = 0; i < 9; i++) {
                if (xs.hasNext()) {
                    scanned = xs.next();
                }

                if (scanned.equals("Unknown")) {
                    scanned = "Coordinate is not given";
                }

                else if (scanned.length() > 7) {
                    String degs = scanned.substring(0, 3);
                    String mins = scanned.substring(3, 5);
                    if (mins.charAt(0) == '0') {
                        mins = mins.substring(1);
                    }
                    String secs = scanned.substring(5, 7);
                    if (secs.charAt(0) == '0') {
                        secs = secs.substring(1);
                    }
                    String dir = scanned.substring(7, 8);

                    if (dir.contentEquals("W")) {
                        dir = "West";
                    }
                    if (dir.contentEquals("E")) {
                        dir = "East";
                    }

                    String full = degs + "d " + mins + "m " + secs + "s " + dir;
                    scanned = full;
                }
            }
        }
        else if (command.contains("quit")) {
            scanned = "Exiting";
            xs.close();
        }

        return scanned;

    }

}

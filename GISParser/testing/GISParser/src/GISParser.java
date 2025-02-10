import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
/**
 * 
 */
/**
 * @author A
 *
 */
public class GISParser {

    private int feature_ID;
    private String feature_name;
    private String feature_class;
    
    
    
     /** I didn't abstract this as a piece of software to interact with users; we were given that
      * input files would always have full integrity, and from the beginning I always thought about this
      * project as a project that would, in theory, interact with computer-made pieces of information that couldn't be wrong.
      * Therein, I didn't see an actual need for lots of objects and classes or interweaving parts; it felt intuitively to me
      * that it was a project rooted in reading and extracting substrings from files that would always be 100% accurate, that weren't
      * made by humans.
      * 
      * In this machine-to-machine framework I didn't write a command class or a recordGISclass - I just wrote a main arg that would
      * extract and write files. Please note that this wasn't out of laziness, but it was out of earnest thought - I could definitely be wrong
      * and would love to see where my thinking could be corrected. I just couldn't see a need for lots of objects and classes - they seemed
      * redundant in the absence of a user, and in operations to simply read and write to files!
      */
    public GISParser() {
        
        
        
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

        
        if (args.length == 4) {                                                         //** clean up and check for good inputs
            secondTry(args[1], args[2], args[3]);
        }
        
        else {                                                                          //*** clean up, check for good inputs
        System.out.println(args[0]);
        System.out.println(args[1]);
        System.out.println(args[2]);
        System.out.println(args[3]);
        //create a string of the recordfile name for later:
        String recordfilename = args[1];
        //Get args 3 and create the string filename:
        String logfilename = args[2];                                               //** WRAP ALL IN try blocks
       
        File testFile = null;
        try {
            testFile = new File(args[1]);
            if ( !testFile.exists()) {
                System.out.println("Error: could not find config file " + args[0]);
                
                return;
            }
            
            RandomAccessFile randie = new RandomAccessFile(testFile, "r");
            //skip the first line:
            randie.readLine();
//                                                                                    String x = randie.readLine();
//                                                                                    long nana = randie.getFilePointer();
//                                                                                    String y = randie.readLine();
//                                                                                    long zaza = randie.getFilePointer();
//                                                                                    randie.readLine();
//                                                                                    long aaaa = randie.getFilePointer();
//                                                                                    System.out.println("first offset" + nana);
//                                                                                    System.out.println(x);
//                                                                                    System.out.println(y);
//                                                                                    System.out.println("secondoffset" + zaza);
                                                                                    
           
     // NOTE: wrap this all in DOES THE FILE EVEN EXIST                                          //** add checks for file existing
            
            FileWriter filie = new FileWriter(logfilename);
            
            filie.write(recordfilename + " contains the following records: \n");
//                                                                                    filie.write(nana + "\t yuh" + "\t \t \t" + zaza);
//                                                                                    filie.write("\n" + aaaa + "   okay, this is the NEW WHILE-LOOP PART:\n");
            // counter for longs;
            // while loop test:
           
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
            
            filie.write("\n end of the TING!");
            
            filie.close();                                                  //** close log files too
            randie.close();
            
          //recordScan(randie.readLine());
          //System.out.println(randie.getFilePointer());
          //String yuh = randie.readLine();
          //System.out.println(randie.getFilePointer());
          //System.out.println(yuh);
            
            // after:
            
          //String yoyo = randie.readLine();
            //ys = new RecordGIS();
            //ys.createRecord(record)
            
            
            //System.out.println("okay, so the new record has a feature ID OF   " + ys.getFeatureID());
            //System.out.println("and a new feature name of     : " + ys.getFeatureName());
            //System.out.println("and a new feature CLASS of yeeet " + ys.getFeatureClass());
        }
        
        finally {
            
            
            
        }
        }
    }
    
    public static void secondTry(String gisrecords, String commands, String logfile) throws IOException {
        
        File gis = new File(gisrecords);
        File cmds = new File(commands);
        FileWriter log = null;
        
        String stringcmd = null;
        int count = 0; // basic count of commands;
                                                                                      //** same thing, check for bad inputs, wrap in try blocks
       
        try {
            log = new FileWriter(logfile);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
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
                    
                    //open the GIS file to read from. do this early for offset checks:
                    
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
                        // Read the GIS file. Use seek() to go to the offset and read the line
                                          
                        gisread.seek(off);
                        String ss = gisread.readLine();                                       
                        System.out.println(yy + " ~ " + intoffset);                    
                        System.out.println("the string ss, after offset is: " + ss);
                        
                        // Fetch the right data from the line
                        
                        String result = fetcher(ss, stringcmd);
                        System.out.println("THE RETURN, (THIS SHOULD BE ELEVATION)" + result);
                        
                        
                        // Long block of code to actually write the file, probably:
                        
                        
                            log.write("\t" + count + ":  " + yy + "\n");
                            log.write("\t  " + result + "\n");    
                            
                        }
                  }
                scancmds.nextLine();
                //randcmds.readLine();
            }
        
        scancmds.close();
        randcmds.close();
        gisread.close();
       
        
    }
    
    public static String fetcher(String fullString, String command) {
        
        Scanner xs = null;                           
        if (fullString != null) {                             
        xs = new Scanner(fullString);
        xs.useDelimiter("\\|");
        }
        //opening logfile code:       
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
            }
            System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + scanned);
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
                else if (scanned.length() > 6) {                                                             //** use a constant here!
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
                        
                        // offset-1 seek
                        // raf.readChar(); previousbyte.   readbyte 
                        // char == '\n'
                        // 
                        
                        //byte previousByte = rf.readByte();
                        //return ( previousByte != '\n' );

                }
                
                
            }
        }
        else if(command.contains("longitude")) {
            for (int i = 0; i < 9; i++) {
                if (xs.hasNext()) {
                    scanned = xs.next();
                }
                
                if (scanned.equals("Unknown")) {
                    scanned = "Coordinate is not given";
                }
                
                else if (scanned.length () > 7) {
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
        else if(command.contains("quit")) {
            scanned = "Exiting";
            xs.close();
        }
        
        return scanned;
        
        
  // name scan:
        
        
        
  // latitude scan?
    }
    
 

public static void recordScan(String record) {
    Scanner scan = new Scanner(record);
    scan.useDelimiter("\\|");
    if (scan.hasNext()) {
        String yo = scan.next();
        System.out.println("          ~               " + yo);
        
    }
    
    if (scan.hasNext()) {
        String ya = scan.next();
        System.out.println("          ~               " + ya);
    }
    
    if (scan.hasNext()) {
        String yoyo = scan.next();
        System.out.println("          ~               " + yoyo);
    }
    
    scan.close();
    
}

   
    public void setFeatureID(int feature) {
        this.feature_ID = feature;
    }
    
    public void setFeatureName(String featurename) {
        this.feature_name = featurename;
    }
    
    public void setFeatureClass(String featureclass) {
        this.feature_class = featureclass;
    }
    
    public int getFeatureID() {
        return this.feature_ID;
    }
    
    public String getFeatureName() {
        return this.feature_name;
    }
    
    public String getFeatureClass() {
        return this.feature_class;
    }
    
    /**
    public GISParser createRecord(String record) {
        Scanner scannie = new Scanner (record);
        
        scannie.useDelimiter("\\|");
        
        //build a recordGIS object
        
        GISParser recordie = new GISParser();
        
        if (!record.isEmpty() && scannie.hasNext()) {
            String featureID = scannie.next();
            int intfeatureID = Integer.parseInt(featureID);
            recordie.setFeatureID(intfeatureID);
        }
        
        if (scannie.hasNext()) {
            String featureName = scannie.next();
            recordie.setFeatureName(featureName);
        }
        
        if (scannie.hasNext()) {
            String featureClass = scannie.next();
            recordie.setFeatureClass(featureClass);
        }
        
        scannie.close();
        
        return recordie;
        
    }*/
    
}

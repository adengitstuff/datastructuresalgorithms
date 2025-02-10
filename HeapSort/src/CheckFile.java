import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;

/**
 * CheckFile: Check to see if a file is sorted. This assumes that each record is
 * a pair of short ints with the first short being the key value
 *
 * @author CS3114 Instructor and TAs
 * @version 03/15/2016
 */

public class CheckFile {

    /**
     * This method checks a file to see if it is properly sorted.
     *
     * @param filename
     *            a string containing the name of the file to check
     * @return true if the file is sorted, false otherwise
     * @throws Exception
     *             either and IOException or a FileNotFoundException
     */
    public static boolean areKeysSorted(String filename) throws Exception {
        int errors = 0;
        FileInputStream fis = new FileInputStream(filename);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream in = new DataInputStream(bis);
        // Prime with the first record
        short key2 = in.readShort();
        in.readShort(); // skips data
        int recCount = 0;

        try {
            while (true) {
                short key1 = key2;
                recCount++;
                key2 = in.readShort();
                in.readShort(); // skips data
                if (key1 > key2) {
                    errors++;
                }
            }
        }
        catch (EOFException e) {
            System.out.println("Records processed: " + recCount);
            System.out.println("Sorting errors: " + errors);
        }
        in.close();
        return errors == 0;
    }
}

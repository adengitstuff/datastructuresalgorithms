
/**
 * Generator of the byte file
 * 
 * @author CS Staff
 * @version 2021.08.01
 */
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ByteFileGenerator {

    static final int maxVal = Short.MAX_VALUE;
    private Random randGen;
    private String fileName;

    public ByteFileGenerator(String fileName) {
        long seed = fileName.toLowerCase().hashCode();
        this.randGen = new Random(seed);
        this.fileName = fileName;
    }


    /**
     * Generates the byte file
     * 
     * @param numRecords
     *            number of records you want your byte file to have
     * @throws IOException
     */
    public void generate(int numRecords) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file, false);
        DataOutputStream outs = new DataOutputStream(fos);

        for (int j = 0; j < numRecords; j++) {
            short key = (short) randGen.nextInt(maxVal);
            short val = (short) randGen.nextInt(maxVal);
            byte[] testB = new byte[4];
            for (int i = 0; i < 2; i++) {
                testB[i] = (byte)(key >> (8 - 8 * i));
                testB[i + 2] = (byte)(val >> (8 - 8 * i));
            }
            outs.write(testB);
        }
        outs.close();
    }
    
}

/**
 * 
 */
package towerofhanoi;
import student.TestCase;

/** The following are test cases for our Disk class. We test compareTo
 * in order to ensure the right logic follows, as this method will be used
 * for the rest of the program extensively!
 * 
 * @author Aden Pandey 
 *
 *
 */
public class DiskTest extends TestCase {

    private Disk disc;
    
    /** Set up our test fixture. Note that we only need a new Disk
     * to be instantiated!
     */
    public void setUp()
    {
        disc = new Disk(5);
    }
    
   /** The following are tests for our COMPARE TO Method. We will
     * call compareTo when two objects are equal, when one is larger than 
     * the current disc, and when one is smaller.
     * 
     * We will not test for edge cases like when otherDisk is greater than 0.5,
     * OR when 1 Disk is a negative number, or if it has a null size. 
     * The logic is simply that Disk's constructor takes in an int, which
     *  handles these edge cases.
     */
    
    
    
    /** Tests "Compare To" in the 
     * normal, vanilla scenario - they are equal! 
     */
    public void testCompareToE()
    {
        Disk disc2 = new Disk(5);
        assertEquals(0, disc.compareTo(disc2));        
    }
    
    /** Test compare to, when the disk sizes are
     * different ints! This is testing the iteration where:
     * otherDisk is GREATER than this disk. 
     * 
     * Essentially, test a negative number is returned if argument disk
     * is bigger!
     */
    public void testCompareToX()
    {
        Disk disc3 = new Disk(11);
        assertTrue(disc.compareTo(disc3) < 0);
        assertEquals(-6, disc.compareTo(disc3));
    }
    
    /**
     * When a new disk is compared, and this is true:
     * otherDisk is SMALLER than this disk.
     */
    public void testCompareToY()
    {
        Disk disc4 = new Disk(2);
        assertTrue(disc.compareTo(disc4) > 0);
        assertEquals(3, disc.compareTo(disc4));
    }
    
    /** Test compareTo() when the object being passed in 
     * is null.
     */
    public void testCompareToNull()
    {
        Disk nullDisc = null;
        Exception ex = null;
        
        try {
            disc.compareTo(nullDisc);
        }
        catch (Exception exception)
        {
            ex = exception;
        }
        
        assertNotNull(ex);
        assertSame(ex.getClass(), IllegalArgumentException.class);
    }
    
    /** The following section tests our ToString method. There is not much
     * content here - we are running Integer.toString, and the width field is 
     * initialized as an int. No extreme edge cases exist, as we can't test for 
     * a null toString.
     */
    
    /** The normal, vanilla test of our toString method!
     */
    public void testToString()
    {
        assertEquals("5", disc.toString());
        
        Disk newDiskString = new Disk(1045);
        
        assertEquals("1045", newDiskString.toString());
    }
    
    
    /** I'm really iffy on test cases about equals,
     * since I'm unsure why tf this method exists? We test Equals when an object
     * is the same class, (one when they're equal, one when not equal), then 
     * when it's another class, and finally a null class.
     * 
     */
    
    /** We test equals when object is null. Note that the 
     * obj != null  line in our code must appear first in the if statement,
     * to avoid all possible null-pointer exceptions.
     */
    public void testEquals()
    {
        Object object = null;
        
        assertFalse(disc.equals(object));
    }
    
    /** We test our equals method when the two
     * objects being compared are the same object!
     */
    public void testEqualsSame()
    {
        Disk discX = new Disk(5);
        
        assertTrue(disc.equals(discX));
    }
    
    /** Test equals when two Disks are of the class
     * Disk, but contain different widths.
     */
    public void testEqualsSameDiff()
    {
        Disk discY = new Disk(6);
        assertFalse(disc.equals(discY));
    }
    
    /** Test equals when it's a non-disk object
     * being compared!
     */
    public void testEqualsDiffObj()
    {
        String stringy = "Hi, TA's! I love you!";
        
        assertFalse(disc.equals(stringy));
    }
    
}

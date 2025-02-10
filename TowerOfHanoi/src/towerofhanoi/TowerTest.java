/**
 * 
 */
package towerofhanoi;
import student.TestCase;

/** Test our Tower class, ensuring it correctly
 * uses the Position enum, and that it correctly
 * overrides and implements 'push()'!
 * 
 * @author Aden Pandey 
 *
 *
 */
public class TowerTest extends TestCase {

    private Tower tower; 
    private Disk disc5;
    private Disk disc10;
    private Disk disc1;    
    
    /** Instantiate a tower of position MIDDLE, and
     * discs of various sizes named easily. This sets up our
     * test fixture!
     */
    public void setUp()
    {
        tower = new Tower(Position.MIDDLE);
        disc5 = new Disk(5);
        disc10 = new Disk(10);
        disc1 = new Disk(1);
 
    }
    
    /** Test that the position() method is returning
     * the right tower.
     */
    public void testPosition()
    {
        assertEquals(Position.MIDDLE, tower.position());
    }
    
    /** Test that the push() method is pushing a disc
     * onto a tower. We use size(), an inherited method,
     * to ensure the push is right.
     */
    public void testPush()
    {
        assertEquals(0, tower.size());
        tower.push(disc1);
        assertEquals(1, tower.size());
    }
    
    /** Test push on 2 objects - both valid
     * pushes.
     */
    public void testPush2()
    {
        assertEquals(0, tower.size());
        tower.push(disc10);
        tower.push(disc1);
        assertEquals(2, tower.size());
    }
    
    /** Test push with the first invalid 
     * push - pushing disk 10 onto disk 1.
     * We ensure an illegalstate exception is thrown,
     * and not any other exception, by checking the classes
     * of our exceptions, comparing it to the IllegalState
     * instantiated in our setup. 
     * 
     * Note that we cannot use IllegalStateException.class or
     * getClass() without instantiating it!
     */
    public void testPush3()
    {
        assertEquals(0, tower.size());
        tower.push(disc1);
        assertFalse(tower.isValid(disc10));
        
        Exception ex = null;
        
        try {
            tower.push(disc10);
        }
        catch (Exception exception)
        {
            ex = exception;
        }
        
        assertNotNull(ex);
        assertSame(ex.getClass(), IllegalStateException.class);
        assertEquals(1, tower.size());
    }
    
    /** This is an edge case test, which ideally should never happen.
     * This is only included because, theoretically, human error could
     * construct 2 disks of the same size
     */
    public void testPush4()
    {
        Disk discSame = new Disk(5);
        tower.push(disc5);
        assertFalse(tower.isValid(discSame));
        
        Exception ex = null;
        
        
        try {
            tower.push(discSame);
        }
        catch (Exception exception)
        {
            ex = exception;
        }
        
        assertSame(ex.getClass(), IllegalStateException.class);
        assertEquals(1, tower.size());
    }
    
    /** Test our illegalArgument exception, ensuring it is throw
     * when a disk passed in is null!
     * 
     */
    public void testPush5()
    {
        Disk discNull = null;
        
        tower.push(disc1);
        
        Exception ex = null;
       
        try {
            tower.push(discNull);
        }
        catch (Exception exception)
        {
            ex = exception;
        }
        
        assertSame(ex.getClass(), IllegalArgumentException.class);
        assertEquals(1, tower.size());
    }
    
}

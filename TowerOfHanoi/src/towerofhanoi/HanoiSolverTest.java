/**
 * 
 */
package towerofhanoi;
import student.TestCase;

/** A HanoiSolver Test Case!
 * @author Aden Pandey 
 *
 *
 */
public class HanoiSolverTest extends TestCase {

    private HanoiSolver hanoi;
    private Tower lefty;
    private Tower righty;
    private Tower middie;
    
    /** Set up our test fixture, getting the towers
     * in the HanoiSolver ready beforehand, such that we can
     * call methods on them and test them.
     */
    public void setUp()
    {
        hanoi = new HanoiSolver(3);
        lefty = hanoi.getTower(Position.LEFT);
        righty = hanoi.getTower(Position.RIGHT);
        middie = hanoi.getTower(Position.MIDDLE);
    }
    
    /** Test that the disk() method returns the number of 
     * disks!
     */
    public void testDisks()
    {
        assertEquals(3, hanoi.disks());
    }
    
    /** This section will test our GetTower methods.
     * Note that there are no edge case tests (for a null
     * argument passed in), as a null Position won't be resolved into a 
     * variable!
     */
    
    
    /** Test getTower(), ensuring the switch statement returns the left
     * tower.
     * 
     */
    public void testGetTower()
    {
        Tower test = hanoi.getTower(Position.LEFT);        
        assertEquals(Position.LEFT, test.position());
        assertNotSame(Position.RIGHT, test.position());
    }
    
    /** Test getTower(), ensuring the switch statement returns the
     * middle tower.
     */
    public void testGetTower2()
    {
        Tower test = hanoi.getTower(Position.MIDDLE);
        assertEquals(Position.MIDDLE, test.position());
    }
    
    /** Test getTower(), ensuring the switch statement
     * returns the right tower.
     */
    public void testGetTower3()
    {
        Tower test = hanoi.getTower(Position.RIGHT);
        assertEquals(Position.RIGHT, test.position());
    }
    
    /** Test the default call to getTower(), which should
     * return the middle tower.
     */
    public void testGetTower4()
    {
        Tower test = hanoi.getTower(Position.DEFAULT);
        assertEquals(Position.MIDDLE, test.position());
        assertNotSame(Position.LEFT, test.position());
    }
    
    /** Test toString(), ensuring HanoiSolver is
     * correctly reading and interpreting Towers.
     * 
     */
    public void testToString()
    {
        lefty.push(new Disk(5));
        righty.push(new Disk(5));
        middie.push(new Disk(11));
        
        assertEquals("[5][11][5]", hanoi.toString());
    }
    
    /** Test toString on 1 single disk, ensuring that
     * the call to middle.toString() can properly return
     * a simple empty "[]".
     */
    public void testToString2()
    {       
        lefty.push(new Disk(5));
        
        assertEquals("[5][][]", hanoi.toString());
    }
    
    /** The following section is commented out - the private
     * solve towers were temporarily made public, just to test
     * if they worked as expected!*/
     
    public void testSolveTowers()
    {
        righty.push(new Disk(10));
        righty.push(new Disk(5));
        righty.push(new Disk(2));
        
        hanoi.solve();
        
        //assertEquals(0, righty.size());
        assertEquals(3, lefty.size());
        assertEquals(0, righty.size());
        
    }
    
    /** A test method for our solution,
     * with 5 disks.
     */
    public void testSolves()
    {
        HanoiSolver hanie = new HanoiSolver(5);
        Tower leftX = hanie.getTower(Position.LEFT);
        Tower rightX = hanie.getTower(Position.RIGHT);
        
        rightX.push(new Disk(10));
        rightX.push(new Disk(9));
        rightX.push(new Disk(8));
        rightX.push(new Disk(7));
        rightX.push(new Disk(6));
        
        assertEquals(5, rightX.size());
        assertEquals(0, leftX.size());
        
        hanie.solve();
        
        assertEquals(5, leftX.size());
        //assertEquals("[]", leftX.toString());
    }
    
    /** A test solution for HanoiSolver, with
     * 4 disks!
     */
    public void testSolving()
    {
        HanoiSolver lala = new HanoiSolver(4);       
        
        Tower r = lala.getTower(Position.RIGHT);
        Tower l = lala.getTower(Position.LEFT);
        
        r.push(new Disk(6));
        r.push(new Disk(5));
        r.push(new Disk(4));
        r.push(new Disk(3));
        
        assertEquals(4, r.size());
        assertEquals(0, l.size());
        
        lala.solve();
        
        assertEquals(0, r.size());
        
    }
    
    
    
    
}

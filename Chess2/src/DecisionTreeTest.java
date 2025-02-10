/**
 * 
 */
import student.TestCase;
/** This is the test of our decisionTree!
 * @author Josh Pandey
 * @version 8-2-2021
 *
 */
public class DecisionTreeTest extends TestCase {
    
    DecisionTree tree;
    /** Set up our test fixture
     * 
     */
    public void setUp() {
        //State s = new State("castle");
        tree = new DecisionTree("Stonewall");
    }
    
    /** Test our constructor!*/
    
    public void testConstructor( ) {
        assertEquals("Stonewall", tree.getRootId());
    }
    
    /**
     * Test a getetr method for root ID
     */
    public void testGetRootId() {
        DecisionTree s = new DecisionTree("yxzw");
        assertEquals("yxzw", s.getRootId());
    }
    
    /** 
     * Test start print!
     */
    public void testStartPrint() {
        
    }
    
    
    
    
    /** FOR GEORGE*/
    

}

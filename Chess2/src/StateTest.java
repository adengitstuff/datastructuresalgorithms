/**
 * 
 */
import student.TestCase;
/**
 * Our state test class!
 * 
 * @author Josh Pandey
 * @version 8-2-2021
 *
 */
public class StateTest extends TestCase {

    State state;
    
    /** Setup method
     * 
     */
    public void setUp() {
        state = new State("cream");
    }
    
    /** test constructor
     * 
     */
    public void testConstructor() {
        State st = null;
        assertNull(st);
        st = new State("brickwall");
        assertNotNull(st);
        assertEquals(st.getId(), "brickwall");
    }
    
    /** Test our other constructor
     * 
     */
    public void testConstructor2() {
        State st = new State("vanilla", 2);
        assertEquals(st.getFitness(), 2);
        assertEquals(st.getId(), "vanilla");
    }
    
    /** Test get ID
     * 
     */
    public void testGetId() {
        assertEquals("cream", state.getId());
    }
    
    /** Test setMoves*/
    public void testSetMoves() {
        assertEquals("", state.getMoves());
        state.setMoves("Qa1");
        assertEquals("Qa1", state.getMoves());
    }
    
    /** Test setting fitness
     * 
     */
    public void testSetFitness() {
        assertEquals(0, state.getFitness());
        state.setFitness(2);
        assertEquals(2, state.getFitness());
    }
    
    /**
     * Test appending moves
     */
    public void testAppendMoves() {
        assertEquals("", state.getMoves());
        state.appendMoves("Qa1");
        assertEquals("Qa1", state.getMoves());
        
    }
    /**
     * Test adding fitness
     */
    public void testAddFitness() {
        assertEquals(0, state.getFitness());
        state.addFitness(2);
        assertEquals(2, state.getFitness());
        state.addFitness(90);
        assertEquals(92, state.getFitness());
    }
    
    /** 
     * Test getting moves
     */
    public void testGetMoves() {
        state.setMoves("Qa1");
        assertEquals("Qa1", state.getMoves());
    }
    

    public void testMain() {
        Chess.main(new String[]{"Stonewall", "3"});
        Chess.main(new String[] {
            "Stonewall", "mxnnqkqukzuz", "3"
        });
        Chess.main(new String[] {
            "quickWin"
        });
    }
    
}

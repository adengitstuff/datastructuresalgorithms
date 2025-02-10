/**
 * 
 */
import student.TestCase;
/**
 * @author Josh Pandey
 * @version 8-2-2021
 *
 */
public class QueueTest extends TestCase {

    Queue q;
    State s;
    /** 
     * Setup our queue test
     */
    public void setUp() {
        q = new Queue();
        s = new State("sicilian");
    }
    
    /** 
     * Test initialization
     */
    public void testInit() {
        assertEquals(0, q.length());
    }
    
    
    /**
     * Test our constructor
     */
    public void testConstructor() {
        Queue x = new Queue();
        assertNotNull(x);
        assertEquals(0, x.length());
        
    }
    
    /** We ignore size,
     * so this test isf ine
     */
    public void testConstructor2() {
        Queue z = new Queue(55);
        assertNotNull(z);
        
    }
    
    /**
     * Test enqueue
     */
    public void testEnqueue() {
        assertEquals(0, q.length());
        q.enqueue(s);
        assertEquals(1, q.length());
    }
    
    /** 
     * Test dequeue
     */
    public void testDequeue() {
        q.enqueue(s);
        assertEquals(1, q.length());
        assertEquals(q.frontValue(), s);
        q.dequeue();
        assertEquals(0, q.length());
        assertNull(q.frontValue());
    }
    
    /**
     * Test dequeue's if condition
     */
    public void testDequeueIf() {
        //assertNull(q.frontValue());
        q.dequeue();
        assertNull(q.frontValue());
    }
    
    /** Test the front value
     * 
     */
    public void testFrontValue() {
        q.enqueue(s);
        assertEquals(q.frontValue(), s);
    }
    
    /**
     * Test the front value and check if null
     */
    public void testFrontValueNull() { 
        assertNull(q.frontValue());
    }
    
    /**
     * Test for isEmpty!
     */
    public void testEmpty() {
        Queue x = new Queue();
        assertTrue(x.isEmpty());
    }
    
    
    
    
    
}

/**
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Josh Pandey (josh1)
 * 
 */
package spacecolonies;
//import java.util.NoSuchElementException;
import queue.EmptyQueueException;
import student.TestCase;
//import org.junit.Assert;

/** This test class for ArrayQueue uses a "Cascading" type of approach - 
 * the tests gradually build up on one another, such that each step in the class
 * is tested. For example, instead of testing ensureCapacity by adding 20 elements,
 * ensureCapacity is tested also in getSize, by adding 1 item, then 10 items, then 
 * 10 + 1 items, then 20+ items.
 * @author Aden Pandey
 *
 */
public class ArrayQueueTest extends TestCase {

    private ArrayQueue<String> arq;
    ArrayQueue<String> arqE;
    
    /** Set up our text fixture.
     * 
     */
    public void setUp() {
        arq = new ArrayQueue<String>();
        arqE = new ArrayQueue<String>();
    }
    
    /** Test our get length method.
     * 
     */
    public void testGetLength() {
        assertEquals(11, arq.getLength());
    }
    
    /** Test our get size method when empty.
     * 
     */
    public void testGetSize1() {
        assertEquals(0, arq.getSize());
    }
    
    /** Ensure our get size method works
     * in the normal, vanilla case.
     */
    public void testGetSize2() {
        assertEquals(0, arq.getSize());
        arq.enqueue("Hi");
        assertEquals(1, arq.getSize());
    }
    
    /** Assure our getSize method works
     * when more items are added in.
     */
    public void testGetSize3() {
        for (int i = 0; i < 10; i++) {
            arq.enqueue("Hi" + i);
        }
        
        assertEquals(10, arq.getSize());               
    }
    
    /** Ensure our getSize works at the "edge"
     * before capacity is expanded.
     */
    public void testGetSize4() {
        for (int i = 0; i < 10; i++) {
            arq.enqueue("Hi" + i);
        }
        
        arq.enqueue("Hello");        
        arq.enqueue("Number 12");
        
        assertEquals(12, arq.getSize());
        assertEquals("Hi0", arq.getFront());
    }
    
    /** Ensure our ensure capacity properly
     * expands our capacity.
     * 
     */
    public void testEnsureCapacity() {
        assertEquals(arq.getLength(), 11);
        
        for (int i = 0; i < 10; i++) {
            arq.enqueue("Hi" + i);
        }
        
        assertEquals(11, arq.getCurrentCapacity());
        arq.enqueue("Hello");
        arq.enqueue("Number 12");
        assertEquals(22, arq.getCurrentCapacity());
        
        assertEquals(12, arq.getSize());
        assertEquals(22, arq.getLength());
        assertEquals("Hi0", arq.getFront());
    }
    
    /** Ensure our EnsureCapacity works!
     * 
     */
    public void testEnsureCapacity2() {
        
        assertEquals(arq.getLength(), 11);
        assertEquals(11, arq.getCurrentCapacity());
        
        for (int i = 0; i < 10; i++) {
            arq.enqueue("Hi" + i);
        }
        
        arq.enqueue("Hello");
        arq.enqueue("Number 12");
        arq.enqueue("Hi!");
        
        assertEquals(13, arq.getSize());
        assertEquals(22, arq.getLength());
        assertEquals("Hi0", arq.getFront());
        assertEquals(22, arq.getCurrentCapacity());
    }
    
    /** Test our ensure capacity under
     * adding lots of entries (30+);
     */
    public void testEnsureCapacityStress() {
        
        assertEquals(arq.getLength(), 11);
        assertEquals(0, arq.getSize());
        assertEquals(11, arq.getCurrentCapacity());
        
        for (int i = 0; i < 31; i++) {
            arq.enqueue("Hi" + " " + i);
        }
        
        assertNotNull(arq);
        assertEquals(31, arq.getSize());
        assertEquals(44, arq.getLength());
        
        arq.enqueue("An extra!");
        
        assertEquals(32, arq.getSize());
        assertEquals(44, arq.getLength());
        assertEquals(44, arq.getCurrentCapacity());
    }
    
    /** This test should never be needed! It's included to run
     * 1 time, in order to test capacity on some weird edge case
     * (10 or 11 expansions)
    public void testEnsureCapacity5X() {
        
    }*/
    
    /** Test our get front method when an arrayQueue
     * is empty.
     * 
     */
    public void testGetFrontEmpty() {
        Exception exc = null;
        
        try { 
            arq.getFront();
        }
        catch (Exception exception) {
            exc = exception;
        }
        
        assertNotNull(exc);
        assertEquals(exc.getClass(), EmptyQueueException.class);
    }
    
    /** Test our getFront method in the normal,
     * "Vanilla" case.
     */
    public void testGetFront1() {
        arq.enqueue("Hi");
        
        assertEquals("Hi", arq.getFront());
    }
    
    /** Test our Enqueue method. Enqueue
     * 2 objects. The last one should be the front.
     */
    public void testGetFront2() {
        arq.enqueue("Hi");
        arq.enqueue("Two");
        
        assertEquals("Hi", arq.getFront());
    }
    
    /** Ensure our dequeue gets rid of
     * the first objected added in.
     */
    public void testGetFrontDeq() {
        arq.enqueue("Hi");
        arq.enqueue("This");
        arq.dequeue();
        
        assertEquals("This", arq.getFront());
    }
    
    /** Ensure getFront works even on
     * dequeues, testing multiple versions.
     */
    public void testGetFrontDeQ2() {
        for (int i = 0; i < 10; i++) {
            arq.enqueue("Hi" + i);
        }
        
        assertEquals("Hi0", arq.getFront());
        
        arq.dequeue();
        
        assertEquals("Hi1", arq.getFront());
        
        for (int i = 0; i < 5; i++) {
            arq.dequeue();
        }
        
        assertEquals("Hi6", arq.getFront());
    }
    
    /** Test our clear method - relatively simple. This should
     * throw an empty queue exception.
     * 
     */
    public void testClear() {
        for (int i = 0; i < 5; i++) {
            arq.enqueue("i" + i);
        }
        
        assertEquals(5, arq.getSize());
        assertEquals(11, arq.getLength());
        
        arq.clear();
        
        assertEquals(0, arq.getSize());
        
        Exception e = null; 
        
        try {
            arq.getFront();
        }
        catch (Exception exception) {
            e = exception;
        }
        
        assertNotNull(e);
        assertEquals(e.getClass(), EmptyQueueException.class);
        
       
    }
    
    /** Test our toArray method. This is
     * simple, and should return indeces of 
     * 0, 1, and 2.
     */
    public void testToArray() {
  
        arq.enqueue("three");
        arq.enqueue("two");
        arq.enqueue("one");
        
        assertEquals(3, arq.toArray().length);
        
        Object[] toA = arq.toArray();
        assertEquals("three", toA[0]);
        assertEquals("two", toA[1]);
        assertEquals("one", toA[2]);
                
    }
    
    /** Ensure our toArray works and throws
     * the right exceptions.
     */
    public void testToArrayOut() {

        arq.enqueue("three");
        arq.enqueue("two");
        arq.enqueue("one");
        
        assertEquals(3, arq.toArray().length);
        
        Object[] toA = arq.toArray();
        
        Exception ex = null;
        
        try { 
            String x = (String)toA[4];
        }
        catch (Exception exception) {
            ex = exception;
        }
        
        assertNotNull(ex);
        assertEquals(ex.getClass(), ArrayIndexOutOfBoundsException.class);
    }
    
    /** Ensure toArray works when expanded.
     * 
     */
    public void testToArrayExpand() {
        
        for (int i = 0; i < 31; i++) {
            arq.enqueue("Hi" + " " + i);
        }
        
        Object[] toArray = arq.toArray();
        
        assertEquals(31, toArray.length);
        
        arq.enqueue("one more");
        
        Object[] toArray2 = arq.toArray();
        
        //The logic is as follows:
        
        // the length of the toArrays are only 31, and 32...
        assertEquals(31, toArray.length);
        assertEquals(32, toArray2.length);
        
        // but the length of the original ArrayQueue should be 44 (ensured capacity)
        
        assertEquals(44, arq.getLength());
        
        // therefore, the full array isn't copied over, but the values are.
        
        // Now to test if an array is copied over with the results indexed at 0, and no null values.
        // we check that the indexes are properly adding the values in order,
        // with 2 random values:
        assertEquals("Hi 21", toArray2[21]);
        assertEquals("Hi 30", toArray2[30]);
        
        // then, we ensure that the original arrayqueue still has a
        // capacity of 44.
        assertEquals(44, arq.getCurrentCapacity());
        
        // we check the last index to see the difference between toArrays - 
        // one that didn't add an extra string, and one that did
        // add an extra string
        assertEquals("one more", toArray2[31]);
        
        // we can definitely say that the toArray works, but there are no
        // edge cases tested here.
    }
         
    /** Ensure our toArray works when
     * our arrayqueue is empty, throwing an exception.
     */
    public void testToArrayEmpty() {
        // test toArray when empty. shoudl throw an emptyqueue exception:
        
        
        Exception ex = null;
        
        try { 
            arq.toArray();
        }
        catch (Exception exception) {
            ex = exception;
        }
        
        assertNotNull(ex);
        assertEquals(ex.getClass(), EmptyQueueException.class);
    }
    
    /** Test our toString method.
     * 
     */
    public void testToString() {
        
        assertEquals(0, arq.getSize());
        
        arq.enqueue("John Doe A:3 M:1 X:1 Wants: To be loved");
        arq.enqueue("Jim Jonny A:3 M:2 T:1 Wants: lel");
        
        assertEquals(2, arq.getSize());
        assertEquals("[John Doe A:3 M:1 X:1 Wants: To be loved, Jim Jonny A:3 M:2 T:1 Wants: lel]", arq.toString());
    }
    
    /** Test our equals method when 2 strings are the same.
     * 
     */
    public void testEqualsE() {
        
        arq.enqueue("hi");
        arqE.enqueue("hi");
        
        assertTrue(arq.equals(arqE));
    }
    
    /** Test our equals method when the queue
     * isn't empty.
     */
    public void testEqualsNE() {
        
        arq.enqueue("hi");
        arqE.enqueue("hi");
        arqE.enqueue("yo");
        
        assertFalse(arq.equals(arqE));
    }
    
    /** Test equals when the string is not equal
     * (period at the end of second one).
     */
    public void testEqualsSizeSame() {
        arq.enqueue("1");
        arqE.enqueue("1.");
        
        assertFalse(arq.equals(arqE));
    }
    
    /** We do 2 separate for-loops here because for some reason,
     * in using "i" to enqueue the first time, Java seems to null
     * out the value/release the stored value of "i" in memory. This means
     * one can't enqueue 2 "i"'s in one for-loop.
     */
    public void testEqualsScale() {
        
        for (int i = 0; i < 12; i++) {
            arq.enqueue("hi" + i);
        }
        for (int j = 0; j < 12; j++) {
            arqE.enqueue("hi" + j);
        }
        
        
        assertEquals(22, arq.getCurrentCapacity());
        assertTrue(arqE.equals(arq));
    }
    
    /** Test our equals method under an edge case
     * (2 objects are the same, but in a different order).
     */
    public void testEqualsEdge() {
        
        arq.enqueue("hi");
        arq.enqueue("yo");
        
        arqE.enqueue("yo");
        arqE.enqueue("hi");
        
        assertFalse(arq.equals(arqE));
    }
    
    /** Test our equals method when the
     * arrayqueue being passed in is null.
     */
    public void testEqualsNone() {
        
        arq.enqueue("1");
        
        assertFalse(arq.equals(arqE));
    }
    
    /** Test our equals method when a different
     * class than an ArrayQueue is added in.
     */
    public void testEqualsClass() {

        int[] testInt = new int[2];
        
        testInt[0] = 3;
        testInt[1] = 5;
        
        assertFalse(arq.equals(testInt));
        
    }
    
    /** Test equals when our value is null.
     * 
     */
    public void testEqualsNull() {
        
        String yes = null;
        
        assertFalse(arq.equals(yes));
    }
}

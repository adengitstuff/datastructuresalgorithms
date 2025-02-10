/**
 * 
 */
package towerofhanoi;
import java.util.EmptyStackException;
import student.TestCase;

/** The abstraction for this test case includes testing when
 * <T> is a string. Methods like size(), peek(), and pop() can be tested 
 * in this way.
 * We will also add tests involving Disks, ensuring compatibility - 
 * toString(), for example, should ideally be tested with strings and Disks.
 * @author Aden Pandey 
 *
 *
 */
public class LinkedStackTest extends TestCase {

    private LinkedStack<String> linkie;
    private LinkedStack<String> emptyLinkie;
    //private LinkedStack<Disk> linkDisc;
    private Exception exTest;
    
    /** Set up our test fixture, including 
     * instantiating an EmptyStackException!
     */
    public void setUp()
    {
        linkie = new LinkedStack<String>();
        emptyLinkie = new LinkedStack<String>();
        //linkDisc = new LinkedStack<Disk>();
        exTest = new EmptyStackException();
    }
    
    /** We test our toString method first, and extensively,
     * such that we can use it to print out contents for other tests.
     * The order of the toString method is important - the last items
     * added onto a stack (the "top"), should be printed out first!
     */
    public void testToString()
    {
        linkie.push("firstPush");
        linkie.push("secondPush");
        linkie.push("lastPushHoo");
        assertEquals("[lastPushHoo, secondPush, firstPush]", linkie.toString());
    }
    
    /** We test toString with a Disk, ensuring 
     * the widths are properly stored in a string.
     */
    public void testToString2()
    {
        LinkedStack<Disk> diskStack = new LinkedStack<Disk>();
        diskStack.push(new Disk(5));
        diskStack.push(new Disk(4));
        diskStack.push(new Disk(3));
        
        assertEquals("[3, 4, 5]", diskStack.toString());
    }
    
    /** Test our size method, in the normal,
     * expected case.
     */
    public void testSize()
    {
        assertEquals(0, linkie.size());
        linkie.push("yeet");
        assertEquals(1, linkie.size());
    }
    
    
    
    

    // ---------------------------------------------------------------------
    
    /** The following section will test our peek, push, and pop methods. 
     * In order to test for the RIGHT exception being thrown, we 
     * check the class of exception with a new EmptyStackException in our setup.
     * 
     * This is a take on tackling NullPointers being thrown 
     * instead of EmptyStackExcpetions - nullPointers will be a separate class.
     */
    
    // ----------------------------------------------------------------------
    
    
    
    
    
    /** Test #1 of our push method will call push
     * multiple times to ensure iteration of size is handled, and then
     * print out the contents of our linked stack as a string. This ensures
     * a triple barrier against any errors or bugs
     */
    public void testPush()
    {
        linkie.push("test");
        assertEquals(1, linkie.size());
        linkie.push("hello!");
        assertEquals(2, linkie.size());
        assertEquals("[hello!, test]", linkie.toString());
    }
    
    /** Push's behavior should not be different
     * when a stack is empty. We test it anyway,
     * to ensure thoroughness.
     */
    public void testPushEmpty()
    {
        assertEquals(0, emptyLinkie.size());
        emptyLinkie.push("hi");
        assertEquals(1, emptyLinkie.size());
    }
    
    /** This will test our peek method, when peek
     * is called on an EmptyStack!
     * 
     */
    public void testPeekEmpty()
    {
        Exception ex = null;       
        
        try {
            emptyLinkie.peek();
        }
        catch (Exception exception)
        {
            ex = exception;
        }
        
        assertNotNull(ex);
        assertSame(ex.getClass(), exTest.getClass());
    }
    
    /** This tests the expected version of peek(),
     * ensuring that the top of the stack is returned.
     * 
     */
    public void testPeekVanilla()
    {
        linkie.push("first");
        
        assertEquals("first", linkie.peek());
    }
    
    /** This tests iterating pushes, such that
     * the 'topNode' changes. This ensures peek keeps returning
     * the top node, as it's changing.
     */
    public void testPeekTop()
    {
        linkie.push("fakeTop");
        linkie.push("realTop");
        
        assertEquals("realTop", linkie.peek());
    }
    
    /** Test clear, printing out the linked
     * Stack at the end to ensure it's totally cleared!
     */
    public void testClear()
    {
        linkie.push("one");
        linkie.push("two");
        
        assertEquals(2, linkie.size());
        
        linkie.clear();
        
        assertEquals(0, linkie.size());
        assertEquals("[]", linkie.toString());
    }
    
    /** Clear's behavior should not change when 
     * the stack is already empty. We check it anyways, to ensure
     * no exceptions are thrown. Note that if setNextNode() is included
     * in our clear method, then it throws a nullPointerException!
     */
    public void testClearEmpty()
    {
        assertEquals(0,  emptyLinkie.size());

        Exception except = null;
        
        try {
            emptyLinkie.clear();
        }
        catch (Exception exception2)
        {
            except = exception2;
        }
        
        assertNull(except);
        assertEquals(0, emptyLinkie.size());
        
    }
    
    
    /** Test our pop method, in the expected
     * scenario.
     */
    public void testPop()
    {
        linkie.push("one");
        assertEquals("one", linkie.pop());
        assertEquals(0, linkie.size());
    }
    
    /** Test pop ensuring topNode is 
     * actually returned even when there are multiple entries.
     */
    public void testPop2()
    {
        linkie.push("one");
        linkie.push("two");
        assertEquals("two", linkie.pop());
        assertEquals("one", linkie.peek());
    }
    
    /** Call pop when our linked stack
     * is empty, ensuring the right exception is thrown.
     */
    public void testPopEmpty()
    {
        Exception exe = null;
        
        try {
            emptyLinkie.pop();
        }
        catch (Exception exception)
        {
            exe = exception;
        }
        
        assertNotNull(exe);
        assertSame(exe.getClass(), exTest.getClass());
    }
    
    
    
}

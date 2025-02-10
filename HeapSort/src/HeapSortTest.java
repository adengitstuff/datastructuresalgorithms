import student.TestCase;

/** This is our test case class
 * 
 * @author Josh Pandey
 *
 */
public class HeapSortTest extends TestCase {

    /**
     * An artificial test to get initial coverage for the
     * main method. Delete or modify this test.
     */
    
    public void testMain() {
        HeapSort dum = new HeapSort();
        assertNotNull(dum);
        String[] args = {"", "", ""};
        HeapSort.main(args);
        
        // check that nothing was printed out
        assertEquals(systemOut().getHistory(), ""); 
    }

}

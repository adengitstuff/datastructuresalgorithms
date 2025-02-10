// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Josh Pandey (josh1)

package spacecolonies;
import java.io.FileNotFoundException;
import java.text.ParseException;
import student.TestCase;


/** ColonyCalculator is tested with this class, testing our 
 * accept and reject methods and ensuring they enqueue, dequeue,
 * and add to arrays properly.
 * 
 * @author Aden Pandey
 *
 */
public class ColonyCalculatorTest extends TestCase {
    
    ColonyCalculator calc;
    ArrayQueue<Person> q;
    Planet[] p;
    
    /** Set up our test fixture.
     * 
     */
    public void setUp() throws FileNotFoundException, ParseException, SpaceColonyDataException {
        ColonyReader colonyReaderX = new ColonyReader("input.txt", "planets.txt");
        calc = new ColonyCalculator(colonyReaderX.readQueueFile("input.txt"), colonyReaderX.readPlanetFile("planets.txt"));
        q = calc.getQueue();
        p = calc.getPlanets();
        
    }
    
    /** Test our accept method - this should
     * deqeue our queue.
     */
    public void testAccept() {
        assertEquals(23, q.getSize());
        assertEquals(0, p[0].getPopulationSize());
        calc.accept();
        assertEquals(22, q.getSize());
        assertEquals(0, p[0].getPopulationSize());
        
    }
    
    /** Test our "is eligible" helper method, in the normal
     * "Vanilla" case.
     */
    public void testIsEligible() {
        Person person = new Person("John Wick", 5, 1, 1, "p");
        Planet p = new Planet("p", 1, 1, 1, 10);
        
        assertTrue(calc.isEligible(person, p));
            
    }
    
    /** Test our isEligible method when it should
     * return false.
     */
    public void testIsEligible2() {
        Person person = new Person("John Wick", 5, 1, 1, "p");
        Planet p = new Planet("p", 1, 5, 1, 10);
        
        assertFalse(calc.isEligible(person, p));

    }
    
    /** Test our reject method - it shoudl dequeue,
     * and add a person object to the AList of person objects.
     */
    public void testReject() {
        assertEquals(23, q.getSize());
        Person person = q.getFront();
        calc.reject();
        assertEquals(22, q.getSize());
        assertFalse(calc.getRejectBus().isEmpty());
        assertTrue(calc.getRejectBus().contains(person));
        
    }
    
    /** Test our getQueue helper method.
     * 
     */
    public void testGetQ() {
        assertEquals(23, q.getSize());
    }
    
    /** Test our getPlanet helper method.
     * 
     */
    public void testGetPlanet() {
        assertEquals(3, p.length);
    }

}

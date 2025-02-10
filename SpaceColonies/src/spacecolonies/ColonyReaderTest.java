/**
 * 
 */
package spacecolonies;
import java.io.FileNotFoundException;
import java.text.ParseException;
import student.TestCase;

/** Test our ColonyReader class, ensuring it's reading and
 * creating the right ArrayQueues and Planet arrays!
 * @author Aden Pandey
 *
 */
public class ColonyReaderTest extends TestCase {
    
    ColonyReader creader;
    Planet[] testPlanet;
    ArrayQueue<Person> peepz;
    
    /** Sets up our test fixture.
     * 
     */
    public void setUp() throws SpaceColonyDataException, FileNotFoundException, ParseException {
        creader = new ColonyReader("input.txt", "planets.txt");
    }
    
    /** Test our read planet file 
     * method.
     * @throws FileNotFoundException
     * @throws ParseException
     * @throws SpaceColonyDataException
     */
    public void testReadPlanetFile() throws FileNotFoundException, ParseException, SpaceColonyDataException {
        testPlanet = creader.readPlanetFile("planets.txt");
        //assertEquals(testPlanet[0], "Hi");
        assertNotNull(testPlanet[2]);
        assertEquals("Planet1", testPlanet[0].getName());
    }
    
    /** Tests our readQueueFile method, ensuring it reads
     * file and creates the right queues.
     * @throws FileNotFoundException
     * @throws ParseException
     * @throws SpaceColonyDataException
     */
    public void testReadQueueFile( ) throws FileNotFoundException, ParseException, SpaceColonyDataException {
        
        peepz = creader.readQueueFile("input.txt");
        Person bob = new Person("Bob Marley", 5, 3, 1, "Planet1");
        Person nik = new Person("Nikola Tesla", 5, 3, 1, "Planet1");
        
        assertTrue(bob.equals(peepz.getFront()));
        peepz.dequeue();
        //assertEquals("Nikola Tesla A:2 M:2 T:5 Wants: Planet2", peepz.getFront());
        assertEquals(nik.getName(), peepz.getFront().getName());
    }
    
    /** Test 1 ArrayQueue equals another ArrayQueue*/
    

}

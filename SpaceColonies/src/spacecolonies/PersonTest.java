/**
 * 
 */
package spacecolonies;
import student.TestCase;

/**
 * @author A
 *
 */
public class PersonTest extends TestCase {

    private Person person;
    
    public void setUp() {
        
        person = new Person("John Smith", 5, 3, 3, "Wakiki");
    }
    
    public void testGetName() {
        assertEquals("John Smith", person.getName());
    }
    
    public void testGetSkills() {
        Skills skilly = new Skills(5, 3, 3);
        
        assertEquals(skilly, person.getSkills());
    }
    
    public void testGetPlanetName() {
        assertEquals("Wakiki", person.getPlanetName());
    }
    
    /** We'll test toString in the normal, vanilla
     * instance!
     */
    public void testToString1() {
        
        assertEquals("John Smith A:5 M:3 T:3 Wants: Wakiki", person.toString());
    }
    
    public void testEquals1() {
        Person person2 = new Person("John Smith", 5, 3, 3, "Wakiki");
        
        assertTrue(person.equals(person2));
    }
}

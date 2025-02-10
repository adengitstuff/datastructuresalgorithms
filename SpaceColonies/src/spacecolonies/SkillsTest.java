// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Josh Pandey (josh1)

package spacecolonies;
import student.TestCase;

/** Test our Skills class!
 * @author Aden P
 *
 *
 */
public class SkillsTest extends TestCase{

    Skills sk;
    Skills planet;
    
    /** Set up our test fixture.
     * 
     */
    public void setUp() {
        sk = new Skills(1, 2, 3);
        planet = new Skills(1, 5, 5);
    }
    
    /** Test our GetAgriculture method.
     * 
     */
    public void testGetAg() {
        assertEquals(1, sk.getAgriculture());
    }
    
    /** Test our getMedicine method.
     * 
     */
    public void testGetMed() {
        assertEquals(2, sk.getMedicine());
    }
    
    /** Test our getTech method.
     * 
     */
    public void testGetTech() {
        assertEquals(3, sk.getTechnology());
    }
    
    /** Test our isBelow method. A planet skill
     * object is created therein. Note this is tested as it would
     * be used in the program (a planet is what isBelow is called upon,
     * even though the name suggests a person is called upon).
     */
    
    public void testIsBelow() {
        assertFalse(planet.isBelow(sk));
    }
    
    /** Test our isBelow method when a person
     * meets the minimum requirements. IsBelow on the planet
     * should return true.
     */
    public void testIsBelowT() {
        Skills sk2 = new Skills(5, 5, 5);
        
        assertTrue(planet.isBelow(sk2));
    }
}

/**

 * 
 */

import student.TestCase;
/**
 * @author A
 *
 */
public class LinkTest extends TestCase{

    Link link;
    State state;
    public void setUp() {
        state = new State("Stone");
        link = new Link(state);
    }
    
    public void testLinkConstructor() {
        //link = new Link(state);
        assertNotNull(link);
        assertEquals(link.element(), state);
    }
    
    public void testLinkConstructor2() {
        Link l = null;
        assertNull(l);
        l = new Link(state, link);
        assertNotNull(l);
        assertEquals(l.next(), link);
        assertEquals(l.element(), state);
    }
    
    public void testElement() {
        assertEquals(link.element(), state);
        State stnew = new State("meow");
        link = new Link(stnew);
        assertEquals(link.element(), stnew);
    }
    
}

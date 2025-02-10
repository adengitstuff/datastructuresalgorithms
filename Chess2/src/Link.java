/**
 * 
 */

/**
 * This Link class is a link for our queue. It's super simple - a link just contains a state
 * adn has a pointer to the next link.
 * 
 * @author Josh Pandey
 * @version 8-2-2021
 *
 */
public class Link {
    
    private State state;
    private Link next;
    
    /** Constructor for our Link Object
     * 
     * @param it            Object to use as the element
     * @param nx            The next Link
     */
    public Link(Object it, Link nx) {
        this.state = (State)it;
        this.next = nx;
    }
    
    /** Constructor with just state
     * 
     * @param s         The state to set as element
     */
    public Link(State s) {
        this.state = s;
    }
    
    /** 
     * Getter method for element
     * @return          The current state of this link
     */
    public State element() {
        return this.state;
    }
    
    /**
     * Getter method for next link
     * @return          The next link
     */
    public Link next() {
        return this.next;
    }
    
    /**
     * Setter method for next link!
     * @param l             The link to set thsi one's next link as
     * @return              true if updated
     */
    public boolean setNext(Link l) {
        this.next = l;
        return true;
    }

}

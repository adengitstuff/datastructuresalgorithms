/**
 * 
 */

/**
 * Note: This Link class is taken from OpenDSA!
 * The linkedList implementation uses non-generic
 * "Node" class, but the Stack uses a fully generic
 * Link class
 * 
 * @author OpenDSA/Josh Pandey
 * @version 7-19-2021
 *
 */

public class Link<E> { // Singly linked list node class
    private E e; // Value for this node
    private Link<E> n; // Point to next node in list

    /** Constructors! 
     * 
     * @param it        element to add
     * @param inn       Next link
     */
    Link(E it, Link<E> inn) {
        this.e = it;
        this.n = inn;
    }

    /** Second constructor, with just
     * a poiner to the next link
     * 
     * @param inn
     */
    Link(Link<E> inn) {
        e = null;
        n = inn;
    }

    /** Getter method for element
     * 
     * @return  the element
     */
    E element() {
        return e;
    } // Return the value

    /** Setter method for the element
     * 
     * @param   Element to set to
     * @return  The element this link
     *          is set to
     */
    E setElement(E it) {
        return e = it;
    } // Set element value

    /** Getter method for next link
     * 
     * @return      Next link
     */
    public Link<E> next() {
        return n;
    } // Return next link

    /** Setter method for the next link
     * 
     * @param inn       The next link for
     *                  this link.
     * @return          The new "next" link.
     */
    public Link<E> setNext(Link<E> inn) {
        return n = inn;
    } // Set next link
}

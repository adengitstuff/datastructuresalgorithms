/**
 * 
 */

/** This is our inner Node class! These
 * are links for our LinkedList, and this
 * is taken largely from OpenDSA.
 * 
 * @author Josh Pandey
 * @version 7-16-2021
 *
 */
public class Node {

    /** these are the nodes in our linked list!*/
    
    private Node next;
    private int elementx;
    private boolean isTail;
    private boolean isHead;
    
    /** Node constructor
     * 
     * @param element   The element to assign
     * @param nxt       Next node
     */
    Node(int element, Node nxt) {
        this.elementx = element;
        this.next = nxt;
        this.isTail = false;
        this.isHead = false;
    }
    
    /** Second cnostructor, just point to next
     * node.
     * 
     * @param nxt
     */
    Node(Node nxt) {
        this.next = nxt;
    }
//    
//    Node (int elem) {
//        this.elementx = elem;
//    }
//    
    /** Getter method for element
     * 
     * @return the element
     */
    public int element() {
        return this.elementx;
    }
    
    /** Getter method for next node
     * 
     * @return  The next node
     */
    public Node next() {
        return this.next;
    }
    
    /** Setter method for a node's element.
     * 
     * @param newElement     New element to set to
     * @return              The element itself
     */
    public int setElement(int newElement) {
        
        /* if we try to set a vaule greater than 9, return false!*/
        
        if (newElement > 9) {
            System.out.println("set Element tried to set a number greater than 9");
            return this.elementx;            
        }
        
        this.elementx = newElement;
        return this.elementx;
    }
    
    /** Setter method for the next node.
     * 
     * @param nxt   Next ndoe
     * @return      the next node
     */
    public Node setNext(Node nxt) {
        this.next = nxt;
        return this.next;
    }
    
    /** Set a node as the tail. Rarely used
     * 
     */
    public void setTail() {
        this.isTail = true;
    }
    
    /** "Unset "node as tail node
     * 
     */
    public void deSetTail() {
        this.isTail = false;
    }
    
    /** Unset node as head node
     * 
     */
    public void deSetHead() {
        this.isHead = false;
    }
    
    /** Set node as head node
     * 
     */
    public void setHead() {
        this.isHead = true;
    }
    
    /** Return true if this is
     * a tail node
     * 
     * @return  true if tail
     */
    public boolean isTail() {
        return this.isTail;
    }
    
    /** Returns true if this is the head
     * 
     * @return  True if head node
     */
    public boolean isHead() {
        return this.isHead;
    }
    
}

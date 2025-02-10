/**
 *
 */

/** This is a LinkedList class, largeyl taken from OpenDSA! 
 * It isn't generic, instead simply using Nodes and ints
 * directly for our LinkedList for the progarm.
 * 
 * @author OpenDSA/Josh Pandey
 * @version 7-17-2021
 *
 */
public class LinkedList {

    private Node head;
    private Node tail;
    private Node curr;
    private int size;
   
    /** LinkedList constructor!!
     * 
     */
    public LinkedList() {
        this.clear();
    }
   
    /* clear creates a new tail and head */
   
    /** Add method for our linked list
     * 
     * @param x     int to add onto
     * @return      true if added
     */
    public boolean add(int x) {
       
//        curr = head;
//        while (curr.next() != this.tail) {
//            curr = curr.next();
//        }
        if (curr == this.head) {
            System.out.println("true");
        }
        //System.out.println(" Calling setnext. curr is " + curr.element() + " and curr next is : " + curr.next());
        curr.setNext(new Node(curr.element(), curr.next()));
        
        curr.setElement(x);
       
        /* but now we're on the case if we're adding it to the very end
         *
         */
       
        if (this.tail == curr) {
           // System.out.println("I'm always benig triggered");
            tail = curr.next();
            tail.setTail();
            curr.deSetTail();
        }
        // but i don't understand hwo tail is still a nul lnode here!
        this.size++;
        return true;
       
    }
    
    /** TrimZeroes method is added by me. This program
     * worked totally fine but added zeroes to the
     * tail of a linked list. Since the ilnked list
     * is a reversed representation of anumber, the zeroes
     * had no meaning at all (like "00057" is just "57").
     * Thsi method just removes all the zeroes at the
     * tail.
     * 
     * This gets results ready for system.out
     * 
     * @return          True if zeroes removed
     */
    public boolean trimZeroes() {
        Node x = this.head();
        
        // go to the tail, tot he last element
        // that was not 0
        
        if (x.next() == this.tail) {                        
            /*** <== potential error! this was "if x == this.tail"*/
            return true;
        }
        
        while (x.next().next() != null) {
            x = x.next();
        }
        
        if (x.next().element() == 0) {
            x.setNext(null);
            this.tail = x;
            this.trimZeroes();
            return false;
        }
        else {
            // all zeroes haev beent rimmed
            return true;
        }
        
        // at the edge
        
        // we're skipping the head's leading 0's, which are important
        
        
    }
    
    /** Move curr one step left; never ued
     * 
     * @return      The node one step left
     */
    public Node prev() {
      if (head.next() == curr) {
          return null; // No previous element
      }
      Node temp = head;
      // March down list until we find the previous element
      while (temp.next() != curr) temp = temp.next();
      curr = temp;
      return curr;
    }

    /** turn this linked list into an int.
     * Only useful for lower numbers, used
     * primarily for testing
     * 
     * @return
     */
    public int toInt() {
        Node z = this.head().next();
        int multiplier = 1;
        int total = 0;
        
        while (z.next() != null) {
            int x = z.element() * multiplier;
            total = total + x;
            multiplier = multiplier * 10;
            z = z.next();
        }
        
        return total;
    }
   
    /** Returns true if this number is even
     * 
     * @return
     */
    public boolean isEven() {
        if (head.next().element() % 2 == 0) {
            
        }
        else {
            
        }
        return (head.next().element() % 2 == 0);
    }
   
    /** Add an int x t othe front of the list.
     * Useful for adding zeroes in.
     * 
     * @param x     The number to add
     * @return      True if added
     */
    public boolean realAddFront(int x) {
        Node temp = head;
        head.setNext(new Node(x, head.next()));
        this.size++;
        return true;
    }
   
    /** Append just appends an int to the
     * end of the list.
     * 
     * @param x     Int to append
     * @return
     */
    public boolean append(int x) {
        tail.setNext(new Node(null));
        tail.setElement(x);
        tail = tail.next();
        this.size++;
        return true;
    }
   
    /** Secondary addFront method,
     * used in testing. Might be deletable
     * 
     * @param x     int to add
     * @return
     */
    public boolean addFront(int x) {
       
        if (curr == tail) {
            curr = head;
        }
        int temp = curr.element();
        curr.setNext(new Node(x, curr.next()));
        curr = curr.next();
        return true;
        // begin case
    }
   
    /** Simple printer method to
     * print out the ilnked list.
     * 
     */
    public void printstuff() {
        Node current = this.head;
       
        while (current.next() != null) {
            System.out.println("Curr: " + current.element() + " -->");
            current = current.next();
        }
       
        System.out.println("I'm going to try to prin the tail:");
        System.out.println("tail is : " + tail.element());
    }
   
    /** Getter method for listsize.
     * 
     * @return
     */
    public int listSize() {
        return this.size;
    }
   
    /** Getetr method for current node
     * 
     * @return  Current node
     */
    public Node curr() {
        return this.curr;
    }
   
    /** Getter method for tail node
     * 
     * @return  Tail node
     */
    public Node tail() {
        return this.tail;
    }
   
    /** Getetr method for head node
     * 
     * @return head node
     */
    public Node head() {
        return this.head;
    }
    
    /** Returns true if list is empty
     * 
     * @return      true if size == 0;
     */
    public boolean isEmpty() {
        return (this.listSize() == 0);
    }
   
    /** Tertiary addToFrnot method, used
     * only in testnig
     * 
     * @param element       int to add
     */
    public void addToFront(int element) {
        Node tempNode = head.next();
        Node newnode = new Node(element, tempNode);

        head.setNext(newnode);
        System.out.println("head is still + " + head.element());
        this.size++;
    }
   
    /** Claer method for our linked lst; used
     * for initialization
     * 
     */
    public void clear() {
        this.tail = new Node(null);
        this.curr = tail;
        head = new Node(tail); /* we pass in to 1 of the Node constructors teh tail,
                                sow e maek a new node pointig to the tail*/
       
        tail.setTail();
        head.setHead();
   
        this.size = 0;
    }
   
}

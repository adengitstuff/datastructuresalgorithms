/**
 * 
 */

/**
 * This is our Queue class - it's what we've studied in class!
 * 
 * @author Josh Pandey
 * @version 8-2-2021
 *
 */
public class Queue implements QueueInterface {

    private Link front; // Pointer to front queue node
    private Link rear; // Pointer to rear queue node
    private int size; // Number of elements in queue

    // Constructors
    /**
     * Constructors for our Queue
     * 
     */
    Queue() {
        init();
    }


    /**
     * Constructor for our queue, but we can ignore size
     * 
     * @param size
     *            The size
     */
    Queue(int size) {
        init();
    } // Ignore size


    /**
     * Initialize our queue
     * 
     */
    void init() {
        rear = new Link(null);
        front = rear;
        size = 0;
    }


    /**
     * Enqueue something to our queue
     * 
     * @param it        The state to enqueue! 
     * @return          True if enqueued
     */
    public boolean enqueue(Object it) {
        rear.setNext(new Link(it, null));
        rear = rear.next();
        size++;
        return true;
    }


    /**
     * Dequeue something from our queue
     * @return      The object that was dequeued
     */
    public Object dequeue() {
        if (size == 0) {
            return null;
        }
        Object it = front.next().element(); // Store the value
        front.setNext(front.next().next()); // Advance front
        if (front.next() == null) {
            rear = front; // Last element
        }
        size--;
        return it; // Return element
    }


    /** Return the front element 
     * 
     * @return          The object in front
     * */
    public Object frontValue() {
        if (size == 0) {
            return null;
        }
        return front.next().element();
    }


    /**
     * Return the queue size
     * 
     * @return          The length
     **/
    public int length() {
        return size;
    }


    /**
     * isEmpty check!
     * 
     * @return     True if empty!
     * 
     */
    public boolean isEmpty() {
        return size == 0;
    }

}

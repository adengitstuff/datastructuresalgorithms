/**
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Josh Pandey (josh1)
 * 
 */
package spacecolonies;

import queue.EmptyQueueException;
import queue.QueueInterface;

/** ArrayQueue class will instantiate an ArrayQueue. We use modulo operators
 * to keep track of the front and back index, as well as return isFull and isEmpty
 * statements properly.
 * 
 * @author Aden Pandey
 *
 */
public class ArrayQueue<T> implements QueueInterface<T> {

    private T[] q;
    public static final int DEFAULT_CAPACITY = 10;
    private int currentCapacity;
    public static final int MAX_CAPACITY = 100;
    private int front;
    private int back;
    private int size;
    
    @SuppressWarnings("unchecked")
    /** Instantiate an ArrayQueue, setting currentCapacity
     * equal to our default capacity static variable + 1.
     */
    public ArrayQueue() {
        
        q = (T[]) new Object[DEFAULT_CAPACITY + 1];
        currentCapacity = DEFAULT_CAPACITY + 1;
        size = 0;
        front = 0;
        back = DEFAULT_CAPACITY;
    }
    
    /** Returns the length of our ArrayQueue.
     * 
     * @return the length of our ArrayQueue.
     */
    public int getLength() {        
        return q.length;
    }
    
    /** Returns the size of our arrayQueue.
     * 
     * @return the size of our arrayQueue.
     */
    public int getSize() {
        return this.size;
    }
    
    /** Returns a boolean on whether the ArrayQueue is empty
     * or not.
     * 
     * @return true if empty
     */
    public boolean isEmpty() {                
        return (front == ((back + 1) % q.length));
    }
    
    /** Gets the current capacity of the ArrayQueue.
     * 
     * @return current capacity.
     */
    public int getCurrentCapacity() {
        return currentCapacity;
    }
    
    /** Returns whether our arrayqueue is full or not, using a
     * modulo operator.
     * @return true if full.
     */
    private boolean isFull() {        
        // Because a queue of 11 will be full at 10...
        // so the currentCapacity is 11, and the length (+ 1
        // of the size) also equals that. idfk.
        return (front == ((back + 2) % q.length));
    }
    
    /** This is an O(1) implementation of an enqueue method.
     * 
     * @param newEntry  The new entry to add in!
     */
    public void enqueue(T newEntry) {
        
        // ensure the capacity!
        this.ensureCapacity();
        
        // THE CONCEPT: back index points to an item
        // already in the array. therein, we need to increment
        // backindex 1st, in order to add it after that last item.
        
        // in order to increment backindex, we need to ensure
        // the capacity first. therefore, ensure capacity.
        
        // increment the back index. we can't just + 1,
        // since it's circular. so modulo operator.
        
        back = ((back + 1) % q.length); 
        
        // add the item to the queue
        
        q[back] = newEntry;
        
        // increment the size!
        
        size++;
    }
    
    /** Ensures the capacity in our arrayqueue is right
     * 
     */
    public void ensureCapacity() {
        // first, check if it's full
        
        if (this.isFull()) {
            
            T[] preQ = q;
            int newSize = 2 * preQ.length;
            currentCapacity = newSize;
            @SuppressWarnings("unchecked")
            T[] tempQ = (T[]) new Object[newSize];
            q = tempQ;
            
            System.arraycopy(preQ, 0, q, 0, preQ.length);
            
            front = 0;
            back = preQ.length - 2;
        }
        // if it is full, then, create a new array with
        //          double the slots that the current one has.
        // for each 0 to the length, i <, copy the items over?
    }
    
    /** Returns the front of the ArrayQueue.
     * 
     * @return the front entry, kept by the front index.
     */
    public T getFront() {
        if (this.isEmpty()) {
            throw new EmptyQueueException();
        }
        else {
            return q[front];
        }
    }
    
    /** Deqeues our arrayQueue.
     * 
     * @return  The item that was dequeued.
     */
    public T dequeue() {
                
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        else {
            T tempFront = q[front];
            q[front] = null;
            front = ((front + 1) % q.length);
            size--;
            return tempFront;
        }
    }
    
    /** Clears our arrayqueue!
     * 
     */
    public void clear() { 
        // (T[]) new Object[DEFAULT_CAPACITY + 1];
        currentCapacity = DEFAULT_CAPACITY + 1;
        size = 0;
        front = 0;
        back = DEFAULT_CAPACITY;
        
    }
    
    /** Returns a new, "neat" array without
     * back or front indexes, with all objects in place.
     * @return Array of objects in our arrayQueue.
     */
    public Object[] toArray() {
        
        if (this.isEmpty()) {
            throw new EmptyQueueException();
        }
        else {
            @SuppressWarnings("unchecked")
            T[] x = (T[]) new Object[this.getSize()];
            
            System.arraycopy(q, front, x, 0, this.getSize());
            
            return x;
        }
    }
    
    /** Returns the string version of our
     * array.
     */
   public String toString() {
       
       if (this.isEmpty()) {
           return "[]";
       }
       else {
       StringBuilder stringbuilder = new StringBuilder();
       stringbuilder.append("[");
       
       // this was the single toughest thing to look up in the api. wow
       // to even rephrase this in the right way - wrapping around a queueu... jesus
       for (int i = front; i != back; i = (i+1)%currentCapacity) {
           String qString = q[i].toString();
           
           stringbuilder.append(qString);
           stringbuilder.append(", ");
       }
       
       String lastQ = q[back].toString();
       stringbuilder.append(lastQ);
       stringbuilder.append("]");
                     
       return stringbuilder.toString();
       
       }
   }
   
   /** Returns whether one arrayQueue is equal to 
    * another arrayQueue.
    * @return true if equals
    */
   @SuppressWarnings({"unchecked" })
   public boolean equals(Object obj) {
       
       if (obj == null || this.getClass() != obj.getClass()) {
           return false;
       }
       else if (this.getSize() != ((ArrayQueue<T>)obj).getSize()) {
           return false;
       }
       else {
       
           boolean theseEquals = true;
           
           Object[] otherQ = ((ArrayQueue<T>)obj).toArray();
           
        Object[] thisQ = this.toArray();
        
        for (int i = 0; i < this.getSize(); i++) {
                       
            T thisT = (T)thisQ[i];
            T otherT = (T)otherQ[i];
            
            if (! thisT.equals(otherT)) {
                theseEquals = false;
            }
           }
        
        return theseEquals;
       }
       
   }
    
    
}

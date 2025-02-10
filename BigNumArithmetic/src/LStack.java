
/**
 * Note: This Stack class is taken from OpenDSA!
 * 
 * @author OpenDSA
 *
 * @param <E> the type to set this LStack to -
 * A stack of LinkedLists in this implementation
 */

// Linked stack implementation
public class LStack<E> {
    private Link<E> top; // Pointer to first element
    private int size; // Number of elements

    /** Constructor!
     * 
     */
    LStack() {
        top = null;
        size = 0;
    }

    /** Second cnostructor!
     * 
     * @param size  Size field
     */
    LStack(int size) {
        top = null;
        size = 0;
    }


    /** Claer the stack,
     * reinitializng it.
     * 
     */
    public void clear() {
        top = null;
        size = 0;
    }


    /** The classic stack push operation,
     * as a boolean.
     * 
     * @param it      element to push
     * @return      true if pushed
     */
    public boolean push(E it) {
        top = new Link<E>(it, top);
        size++;
        return true;
    }


    /** The classic pop operation for a stack,
     * returns the top element
     * 
     * @return  The top element on the stack
     */
    public E pop() {
        if (top == null) {
            return null;
        }
        E it = top.element();
        this.top = top.next();
        size--;
        return it;
    }

    /** Returns the top value - like
     * a "peek "operation.
     *  
     * @return The top element
     */
    public E topValue() { // Return top value
        if (top == null) {
            return null;
        }
        return top.element();
    }


    /** Return stack length
     * 
     * @return  stack size
     */
    public int length() {
        return size;
    }


    /** Simple isEmpty method to
     * check if stack si at size 0
     * 
     * @return      true if size == 0
     */
    public boolean isEmpty() {
        return size == 0;
    }
}

/**
 * 
 */
package towerofhanoi;

import java.util.EmptyStackException;
import stack.StackInterface;

/** Her we write a linked stack, linked by nodes,
 * that will include a Node class on the bottom :).
 * This seems straightforward, but even a tiny error
 * will reverberate throughout the program, and thus 
 * each implementation is carefully thought through. 
 * 
 * @param <T>       The type of object to stack.
 * @author Aden Pandey 
 *
 *
 */
public class LinkedStack<T> implements StackInterface<T> {

    private int size;
    private Node<T> topNode;
    
    /** Construct our topNode to equal null, for now, and
     * instantiate size to 0.
     */
    public LinkedStack()
    {
        topNode = null;
        size = 0;
    }
    
    /** Return the private field, size. As node methods
     * increment and decrement, size is maintained.
     * @return size         An int value of the size.
     */
    public int size()
    {
        return size;
    }
    

    /** The push method is arguably the most important for the program,
     * but we will write an override for the Towers later on. This linked
     * stack push will simply add an object to the 'top' of the stack,
     * ensuring it can still be added into a stack of size 0.
     * 
     * @param newThing      The object to be pushed and stacked!
     */
    @SuppressWarnings("unchecked")
    public void push(T newThing)
    {
        /** This follows the exact instructions.
         * There is a suppress warnings check!
         */
        Node<T> newNode = new Node<T>(newThing);
        
        if (topNode == null)
        {
            topNode = newNode;
        }
        else {
            newNode.setNextNode(topNode);
            topNode = newNode;
        }
        size++;
    }
    
    /** Peek will return the object at the top of the stack, without
     * actually removing it. This is extremely helpful later on in incorporating
     * graphics or Windows API's, as we have separated this project into a 
     * front-end and a back-end.
     * 
     * @return              The object at the top of the stack.
     * @throws              EmptyStackException if empty.
     */
    public T peek()
    {
        
        if (isEmpty())
        {
            /** Note: in some labs I had difficulty using
             * this.isEmpty, and it gave errors. So I'm using
             * isEmpty now - I don't know the deeper difference between the two,
             * or which one is more evolved
             */
            
            throw new EmptyStackException();
        }
        
        else
        {
            return (T)topNode.getData();
        }
    }
    
    /** Pop removes the item at the 'top' of the stack. It checks if it's
     * empty, throwing an EmptyStackException if it is, and then uses Java's
     * garbage collection to arrange nextNodes such that the top node is gone.
     * 
     * @return                      The object at the top of the stack.
     * 
     */
    public T pop()
    {
        if (!isEmpty())
        {
            
            //T temporaryNode = this.peek();
            Node<T> tempNode = topNode;

            /** I really want to account for a list size of 1 here!!!
             * It might be that it automatically does, since getNext is null,
             * and thus a "null" next node simply removes the first node?
             */
            topNode = topNode.getNextNode();
            size--;
            return tempNode.getData();
            //return (T)temporaryNode.getData();
        }
        else
        {
            throw new EmptyStackException();
        }
    }
    
    /** A simple boolean, returning if our size is equal to 0.
     * Note that (size == 0 && firstNode == null) is not implemented
     * here, to avoid possible errors caused by other methods not
     * setting the firstNode properly. Because stacks are singly linked,
     * size == 0 is a dependable boolean.
     * 
     * @return              True if size is 0, False if not.
     */
    public boolean isEmpty()
    {
        return (size == 0);
        /** or, return size == 0 && firstNode == null?)
         *
         */
    }
    
    /** Clear the stack of all entries, removing any and all
     * entries through Java's garbage collection! Note that 
     * setting topNode and topNode's next equal to null handles our
     * cases. 
     * 
     * 
     */
    public void clear()
    {   
        
        //topNode.setNextNode(null);
        topNode = null;        
        size = 0;
    }
    
    /** A toString method, using a stringbuilder to append
     * each node in the stack as a string, and then returning that
     * string. This is a must for thoroughly testing linked stacks.
     * 
     * @return              The String of objects currently in the stack!
     * 
     */
    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder("[");
        Node<T> currentNode = topNode;
        
        /**
        for (int i = 0; i < size; i++)
        {
            stringbuilder.append(currentNode + ", ");
            currentNode = currentNode.getNextNode();
        }**/
        
        while (currentNode != null)
        {
            if (stringbuilder.length() > 1)
            {
                stringbuilder.append(", ");
            }
            
            stringbuilder.append(currentNode.getData());
            currentNode = currentNode.getNextNode();
                
        }
        
        stringbuilder.append("]");
        
        return stringbuilder.toString();
    }
    
    /** The following should be thought of as a 'new section'.
     * This is a new class for Node, which will set the data and
     * next fields of Node.
     * 
     * @author Josh Pandey (josh1)
     * @version 2019.10.22 
     * 
     * @param <T>       The object type - String, Integer, Disk, etc.
     */
    public class Node<T> {
      
        private Node<T> next;
        private T data;
        
        /** Pass in data to our Node and store that data
         * in the data field. Pass it on to constructor #2.
         * 
         * 
         * @param intel         The data type. For Disks, the width.
         */
        public Node(T intel)
        {
            this(intel, null);
        }
        
        /** Construct a Node object with a data field and
         * a next node, for our linked stack. 
         * 
         * @param intelX        The data for our Node.
         * @param nodeX         The next Node in our list!
         */
        public Node(T intelX, Node<T> nodeX)
        {        
            data = intelX;
            next = nodeX;
        }
        
        /** This will return the next Node in our stack.          
         * @return          The next node, stored in the 'next' field.
         */
        public Node<T> getNextNode()
        {
            return next;
        }
        
        /** This returns the data in our Node, immensely
         * useful for checking disk widths and for the entire rest of the 
         * program.
         * 
         * @return              The data the Node holds.
         */
        public T getData()
        {
            return data;
        }
        
        /** Set the 'next' field equal to a new
         * node. This is useful in removing objects, adding objects,
         * and linked stacks magic in general.
         * 
         * @param nodeN             The next node to set it to.
         */
        public void setNextNode(Node<T> nodeN)
        {
            this.next = nodeN;
        }
        
        
    }
    
}

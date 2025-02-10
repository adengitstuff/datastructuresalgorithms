/**
/**
 * @author Josh Pandey
 * @version 8-2-2021
 *
 */

public interface QueueInterface {
    
    
    public boolean enqueue(Object it);
    
    public Object dequeue();
    
    public Object frontValue();
    
    public int length();
    
    public boolean isEmpty();

}

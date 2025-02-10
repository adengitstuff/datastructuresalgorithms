/**
 * 
 */
package towerofhanoi;

/** The Tower class will extend LinkedStack,
 * but create a new version of it - push is constrained to only
 * pushing smaller disks onto larger disks!
 * 
 * @author Aden Pandey 
 *
 *
 */
public class Tower extends LinkedStack<Disk> {

    private Position posit;
    
    /** Our Tower constructor takes a position, 
     * specificed in the Position enum, and then 
     * sets that equal to a field. Note that 
     * a position of Right is specified as Position.RIGHT,
     * and left as Position.LEFT,
     * 
     * @param position        Right, Left, or Middle.
     * 
     */
    public Tower(Position position)
    {
        super();
        posit = position;       
    }
    
    /** Return the position field for the Tower.
     * 
     * @return          The Position specified1
     */
    public Position position()
    {
        return posit;
    }
    
    /** This will override LinkedStack's push method,
     * such that we make checks to ensure that the right
     * and valid pushes are only being pushed onto the Tower class.
     * Exceptions are thrown in the right cases. A separate
     * isValid boolean is use, for clarity and style.
     * 
     * @param disk              The disk to push on to the Tower.
     */
    @Override
    public void push(Disk disk)
    {
        
        if (disk == null)
        {
            throw new IllegalArgumentException();
        } 
        else if (!isValid(disk))
        {
            throw new IllegalStateException();
        }
        else
        {
            super.push(disk);
        }
        
        
    }  
    
    /** Simple boolean to clarify logic flow. 
     * 
     * @param diskX     - the disk in push.
     * @return          True if it is a valid push.
     */
    public boolean isValid(Disk diskX)
    {
        return (isEmpty() || this.peek().compareTo(diskX) > 0);
        
    }
   
}

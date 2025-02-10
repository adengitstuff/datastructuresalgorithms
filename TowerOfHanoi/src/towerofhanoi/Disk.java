/**
 * 
 */
package towerofhanoi;

import java.awt.Color;
import java.util.Random;
import CS2114.Shape;

/** The Disk class extends shape, such that it can be added onto
 * a Window later! We create a disk object, and write a compareTo method
 * that compares a disk's size to another, incoming disk (
 * we implement Comparable<Disk> to make this possible!)
 * 
 * @author Aden Pandey * @version 2019.10.21
 *
 */
public class Disk extends Shape implements Comparable<Disk> {
    
    /** The super constructor for Disk takes 4 parameters:
     * x, y, width, and height. Here we simply pass the static final
     * int, disk height, and width to the super constructor. Note that
     * we also use Random() instead of Math.random in order to 
     * create a random Color choice for the disk's color. 
     * 
     * @param wide          The width desired for a Disk.
     */
    public Disk(int wide)
    {

        super(0, 0, wide, PuzzleWindow.DISK_HEIGHT);

        Random random = new Random();
    
        Color randomC = new Color(
            random.nextInt(256), random.nextInt(256), random.nextInt(256));
        
        this.setBackgroundColor(randomC);
    }
    
    /** Compare a separate disk being passed in as an argument,
     * ensuring a codified response as a comparison to their widths 
     * (negative if THIS disk is smaller than the INCOMING disk, 
     * positive if the opposite, 0 if equal).
     * 
     * @return             The difference in width
     * @param otherDisk    The other Disk, which we will compare this to.
     */
    public int compareTo(Disk otherDisk)
    {        

        
        if (otherDisk != null)
        {
            return (getWidth() - otherDisk.getWidth());
        }
        
        else
        {
            throw new IllegalArgumentException();
        }
    }
    
    /** Returns a toString version of the object's
     * widths. This is particularly useful for testing the entire
     * solution later on!
     * 
     * @return                 The String version of the width!
     */
    public String toString()
    {
        return Integer.toString(this.getWidth());
    }
    
    /** An equals method, designed simply to return
     * if two disks have the same width.
     * 
     * @return             True if two disks have equal widths!
     * @param              obj - The object to be compared.
     */
    public boolean equals(Object obj)
    {
        
        if (obj != null && obj.getClass().equals(this.getClass()))
        {
            return (this.getWidth() == ((Shape)obj).getWidth());                
        }
        
        else            
        {
            return false;
        }
    }

}

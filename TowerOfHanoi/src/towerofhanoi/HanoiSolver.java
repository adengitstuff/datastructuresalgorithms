/**
 * 
 */
package towerofhanoi;

import java.util.Observable;

/** HanoiSolver is the "solver" class for any
 * Tower of Hanoi puzzle! This is the last "layer"
 * of the back end and can be abstracted as a recursive
 * solving algorithm at its essence. 
 * 
 * The algorithm's runtime is (2^n - 1). Because the most effective solution is 
 * (2^n - 1), the time for
 * 80 disks, for example, would be around 38,334,786,263,782,000 
 * years. That's a lot of years.
 * 
 * @author Aden Pandey 
 *
 *
 */

@SuppressWarnings("deprecation")
public class HanoiSolver extends Observable {

    private Tower leftX;
    private Tower middleX;
    private Tower rightX;
    private int numDisc;
    
    /** Create our HanoiSolver class, with the number
     * of disks desired being passed in as a parameter.
     * Note that a reasonable number of disks should be passed in -
     * @param numDisks      The number of disks desired!       
     */
    public HanoiSolver(int numDisks)
    {
        numDisc = numDisks;
        leftX = new Tower(Position.LEFT);
        middleX = new Tower(Position.MIDDLE);
        rightX = new Tower(Position.RIGHT);
        
    }
    
    /** Return the number of disks in our solver.
     * 
     * @return      The number of disks in our solution.
     */
    public int disks()
    {
        return numDisc;
    }
    
    /** Get the tower corresponding to a position - so, to get the
     * Tower linked stack corresponding to the right tower, pass in
     * a position (Position.RIGHT). A switch statement is used, with the default
     * as middleX.
     * 
     * @param pos       The Position enum as an argument!
     * @return          The Tower which corresponds to that enum.
     */
    public Tower getTower(Position pos)
    {
        
        switch(pos)
        {
            case LEFT:
                return leftX;
            case MIDDLE:
                return middleX;
            case RIGHT:
                return rightX;
            default:
                return middleX;
           
        }
        
    }
    
    /** A toString method of our HanoiSolver. Because the towers
     * are linked stacks, we simply use the toString method provided by the
     * towers.
     * @return      A string representation of the Towers' contents!
     */
    public String toString()
    {
        /** to test toString you will need to instantiate a HanoiSolver object
         * and push disks onto its towers.
         */
        return (leftX.toString() + middleX.toString() + rightX.toString());
    }
    
    /** This move method method is the 'first layer' in the 'real move'
     * method that a user will see on the screen. A disk must be pushed from
     * one Tower (a linked stack) to another Tower. We simply use
     * the push and pop methods provided by tower. Observers are then
     * notified of the move.
     * @param src       The 'source' tower - where the disk is FROM.
     * @param destini   The 'destination' tower - where the disk is GOING.
     * @param destini
     */
    private void move(Tower src, Tower destini)
    {               
        destini.push(src.pop());
        this.setChanged();
        this.notifyObservers(destini.position());
        
    }    
    
    /** The solution to the Towers of Hanoi: a recursive algorithm,
     * intaking the current number of disks, and the various towers.
     * We abstract the entire puzzle as a movement to an 'extra' tower,
     * such that the bottom disk is moved to the right tower. Then, 
     * each 'next move' is abstracted as a microscopic version of this larger
     * change, going further and further until the right move is made!
     * 
     * @param currentDisks       - The current # of disks in the puzzle.
     * @param start             - The starting tower, where a disk is.
     * @param mid               - The 'middle' or auxiliary or 'extra' tower!
     * @param end               - The 'endpoint' of the disk.
     */
    public void solveTowers(int currentDisks, Tower start, Tower mid, Tower end)
    {
        if (currentDisks == 1)
        {
            this.move(start, end);
        }
        else
        {
            solveTowers(currentDisks - 1, start, end, mid);
            this.move(start, end);
            solveTowers(currentDisks - 1, mid, start, end);
        }
        
    }    
    
    /** This is simply the initial call to solveTowers(), specifying a situation
     * wherein all disks are on the right tower, and moving onto the left
     * tower, in size order.
     */
    public void solve()
    {
        solveTowers(numDisc, rightX, middleX, leftX);
    }
    

}

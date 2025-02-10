import java.util.ArrayList;


/** This is our coordinate object, which holds x and y coordinates for
 * GIS records! It implements a Compare2D interface, given in J2
 * @author Josh Pandey
 *
 */

public class coordinate implements Compare2D<coordinate>{

    private long xCoord;
    private long yCoord;
    private ArrayList<Long> prOffsets;
    
    /** Constructor. Just initialize to 0, and make a new 
     * arraylist
     * 
     */
    public coordinate() {
        xCoord = 0;
        yCoord = 0;
        // initialize arraylist?
        /** the arraylist is unlimited, interesting? */
        prOffsets = new ArrayList<Long>();
    }
    
    /** Another constructor, passing every field in.
     * 
     * @param x             x Coordinate (Latitude)
     * @param y             y Coordinate (Longitude)
     * @param offset        Offset
     */
    public coordinate(long x, long y, long offset) {
        this.xCoord = x;
        this.yCoord = y;
        prOffsets = new ArrayList<Long>();
        prOffsets.add(offset);
    }
    
    /** Return equals if x and y coordinates are the same. This is for adding
     * multiple coordinates into one coordinate's prOffsets.
     * 
     */
    public boolean equals(Object other) {
        if (other == null || (other.getClass() != this.getClass())) {
            return false;
        }
        coordinate o = (coordinate) other;
        return (o.getX() == this.getX() && o.getY() == this.getY());
    }
    @Override
    /** Getter for x coord
     * 
     */
    public long getX() {
        // TODO Auto-generated method stub
        return this.xCoord;
    }

    @Override
    /** Getter for Y coord
     * 
     */
    public long getY() {
        // TODO Auto-generated method stub
        return this.yCoord;
    }
    
    /** Getter for offsets
     * 
     * @return  ArrayList of offsets
     */
    public ArrayList<Long> offsets() {
        return this.prOffsets;
    }

    @Override
    /** Calculate direction from a given point.
     * 
     * @return Direction logic. This could be much simpler but I kept this.
     */
    public Direction directionFrom(double X, double Y) {

        if((X < xCoord && Y <= yCoord) || 
            (X == yCoord && Y == yCoord)){
        return Direction.NE;
    }
    else if(X >= xCoord && Y < yCoord){
        return Direction.NW;
    }
    else if(X > xCoord && Y >= yCoord ){
        return Direction.SW;
    }
    else{
       return Direction.SE; 
    }
        
    }

    @Override
    /** Check what quadrant this coordinate is in given 4 doubles. 
     * This is the root logic of the entire location index.
     * 
     * @return      Direction of the quadrant that thsi should go in!
     */
    public Direction inQuadrant(
        double xLo,
        double xHi,
        double yLo,
        double yHi) {


        if (! this.inBox(xLo, xHi, yLo, yHi)) {
            return Direction.NOQUADRANT;
        }
        
        return directionFrom( ((xLo + xHi) / 2.0), ((yLo + yHi)/2.0));
//        
//        if (xd > xNEW) {
//            /* it's eastern*/
//            if (yd >= yNEW) {
//                return Direction.NE;
//            }
//            else {
//                return Direction.SE;
//            }
//        }
//        else {
//            /* it's in the western. we do checks in the insert call itself so it's always withinj the world*/
//            /* x can still be on the median line. if northern quadrant it'lll be NW but southern it'll still be SE*/
//            if (yd == yNEW) {
//                return Direction.NE;
//            }
//            if (yd > yNEW) {
//                return Direction.NW;
//            } 
//            else { /* Yd is def less than ynew*/
//                // yd can still equal yd, whichi s sisu
//                return Direction.SW;
//            }
//        }
        
    }

    @Override
    /** Check if it's in a box, given 4 doubles.
     * 
     * @return true if in box
     */
    public boolean inBox(double xLo, double xHi, double yLo, double yHi) {

        double xd = this.xCoord;
        double yd = this.yCoord;
       
//        System.out.println(" This might be the only problem!!! :" );
//        System.out.println(" xd is : " + xd);
//        System.out.println(" yd is : " + yd);
//        System.out.println("xlo, xhi, ylo, yhi : " + xLo + " " + xHi + " " + yLo + " " + yHi);
        if ((xd < xLo) || (xd > xHi) || (yd < yLo) || (yd > yHi)){
            //System.out.println( " xd < xlo");
            return false;
        }
        return true;
//        if (xd > xHi) {
//            //System.out.println("xd > xhi");
//            return false;
//        }
//        if (yd < yLo) {
//            //System.out.println("yd < ylo");
//            return false;
//        }
//        if (yd > yHi) { 
//            //System.out.println("yd >yHi");
//            return false;
//        }
//        
//        if(xd < xLo || xd > xHi || yd < yLo || yd > yHi) {
//            return false;
//        }
    }
    
    
    /** ToString method for the coordinate
     * This should display it in MRU ---> LRU order, which, because
     * we've added with add(0, end), is just the arraylist in order. No 
     *complex logic is needed
     * 
     */
    public String toString() {
        StringBuilder coor = new StringBuilder("[(" + this.xCoord + ", " + this.yCoord + "");
        for (int i = 0; i < this.offsets().size(); i++) {
            coor.append(", " + this.offsets().get(i));
        }
        coor.append("] ");
        return new String(coor);
    }
    
    
    

}

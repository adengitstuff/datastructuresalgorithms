package cs3114.J2.Client;
import cs3114.J2.DS.Compare2D;
import cs3114.J2.DS.Direction;

public class Point implements Compare2D<Point> {

	private long xcoord;
	private long ycoord;
	
	public Point() {
		xcoord = 0;
		ycoord = 0;
	}
	public Point(long x, long y) {
		xcoord = x;
		ycoord = y;
	}
	
	// For the following methods, let P designate the Point object on which
	// the method is invoked (e.g., P.getX()).

    // Reporter methods for the coordinates of P.
	public long getX() {
		return xcoord;
	}
	public long getY() {
		return ycoord;
	}
	
	// Determines which quadrant of the region centered at P the point (X, Y),
	// consistent with the relevent diagram in the project specification;
	// returns NODQUADRANT if P and (X, Y) are the same point.
	public Direction directionFrom(long X, long Y) {
		
        // complete implementation!
		return Direction.NOQUADRANT;        // actually not reachable
	}
	
	// Determines which quadrant of the specified region P lies in,
	// consistent with the relevent diagram in the project specification;
	// returns NOQUADRANT if P does not lie in the region. 
	public Direction inQuadrant(double xLo, double xHi, double yLo, double yHi) {
		
        // This assumes this function is intended to be "given these coordinates, which quadrant should P lie in?"
	    
	    if (this.getX() > xHi || this.getX() < xLo || this.getY() > yHi || this.getY() < yLo) {
	        return Direction.NOQUADRANT;
	    }
	    else {
	        double centX = (xHi - xLo)/2;
	        double centY = (yHi - yLo)/2;
	        
	        if (this.getX() > centX && this.getY() >= centY) {
	            return Direction.NE;
	        }
	        else if (this.getX() <= centX && this.getY() < centY) {
	            return Direction.SW;
	        }
	        else if (this.getX() >= centX && this.getY() < centY) {
	            return Direction.SE;
	        }
	        else if (this.getX() < centX && this.getY() > centY) {
	            return Direction.NW;
	        }
	        else if (this.getX() == centX && this.getY() == centY) {
	            return Direction.NE;
	        }
	        else {
	            return Direction.NOQUADRANT;
	        }
	    }
	    
	    /**
	    if ( (this.getX() >= xLo) && (this.getX() <= ((xHi - xLo)/2)) && (this.getY() > ((yHi - yLo) / 2)) && (this.getY() <= yHi)) {
	        return Direction.NW;
	    }
	    
	    else if ( (this.getX() > ((xHi - xLo) / 2)) && ( this.getX() <= xHi) && (this.getY() >= ((yHi - yLo) / 2)) && (this.getY() <= yHi)) {
	        return Direction.NE;
	    }
	    else if ( (this.getX() >= xLo) && (this.getX() < ((xHi - xLo)/2)) && (this.getY() >= yLo) && (this.getY() <= ((yHi - yLo) / 2))) {
	        return Direction.SW;
	    }
	    else if (  (this.getX() <= xHi) && (this.getX() >= ((xHi - xLo)/2)) && (this.getY() >= yLo) && (this.getY() < ((yHi - yLo)/2))) {
	        return Direction.SE;
	    }*/
	    
	    /**
	    if (this.getX() > ((xHi - xLo) / 2) && this.getX() > xLo) {
	        if (this.getY() >= ((yHi - yLo) / 2) && this.getY() >= yLo) {
	            return Direction.NE;
	        }
	        else if (this.getY() < ((yHi - yLo) / 2) && this.getY() >= yLo) {
	            return Direction.SE;
	        }
	    }
	
	    else if (this.getX() < ((xHi - yLo) / 2) && this.getX() >= xLo) {
	        
	        if (this.getY() > ((yHi - yLo) / 2) && this.getY() >= yLo) {
	            return Direction.NW;
	        }
	        else if (this.getY() <= ((yHi - yLo) / 2) && this.getY() >= yLo) {
	            return Direction.SW;
	        }
	        
	    }*/
	    
	    /**else if (this.getX() == ((xHi - yLo) / 2) && this.getY() == ((yHi-yLo) / 2)) {
	        return Direction.NE;
	    }
	 
	    else {
	    return Direction.NOQUADRANT;
	    }*/
	    // edge case: if at center
	    
	}
	
	// Returns true iff P lies in the specified region.
	public boolean   inBox(double xLo, double xHi, double yLo, double yHi) {
		
        // complete implementation!
		//return (this.getX() >= xLo && this.getX() <= xHi &&
		    //this.getY() >= yLo && this.getY() <= yHi);
	    
	    return (this.getX() <= xHi && this.getX() >= xLo && this.getY() >= yLo && this.getY() <= yHi);
	}
	
    // Returns a String representation of P.
	public String toString() {
		
		return new String("(" + xcoord + ", " + ycoord + ")");
	}
	
	// Returns true iff P and o specify the same point.
	public boolean equals(Object o) {
		
        // complete implementation!
		return false;
	}
}

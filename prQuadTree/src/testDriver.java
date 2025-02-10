/**  Data/Soln generator for CS 3114 J2 Spring 2021
 *   Modified  2/5/2021
 *   Changes
 *     -  command-line specification of number of data points
 *     -  set world size based on number of data points
 *     -  command-line switch for repeating with previous seed value
 * 
 *   Invocation:  java testDriver <# data points> [-repeat]
 *     -  minimum # data points is 10
 *     -  optional repeat switch has no effect unless seed.txt is present
 */
 
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Vector;
import java.util.Random;

import cs3114.J2.DS.prQuadTree;
import cs3114.J2.Client.Point;
import cs3114.J2.DS.Lewis;

public class testDriver {
	
	static int xMin = 0;
	static int xMax = 1 << 5;
	static int yMin = 0;
	static int yMax = 1 << 5;
	static int nDataPts = 10;
	static int nDivisions = 5;
	
	static Vector<Point> data;
	static Vector<Long> seps;     // holds subregion boundaries 
	static Long randSeed = -1L;
    
	public static void main(String[] args) throws IOException {
		
		if ( args.length == 0 ) {
			System.out.println("java testDriver <# data points> [-repeat]");
			return;
		}
		
		nDataPts = Integer.parseInt(args[0]);
		if ( nDataPts < 10 ) {
			System.out.println("Number of data points must be at least 10.");
			System.out.println("Will use 10 data points.");
			nDataPts = 10;
		}
		setWorldBoundaries();
		
		boolean preferRandomData = true;
		if ( args.length == 2 && args[1].equals("-repeat") ) {
			preferRandomData = false;
		}
		
		randSeed = 0L;
		if ( !preferRandomData ) {
            File sFile = new File("seed.txt");
            if ( sFile.exists() ) {
			    RandomAccessFile seedFile = new RandomAccessFile(sFile, "r");
			    randSeed = Long.parseLong(seedFile.readLine());
			    seedFile.close();
		    }
		    else {
				System.out.println("Seed file not found; generating new data.");
				preferRandomData = true;
			}
	    }
	    
		if ( preferRandomData ) {
		   randSeed = System.currentTimeMillis();
           FileWriter sfile = new FileWriter("seed.txt");
           sfile.write( randSeed.toString() + "\n" );
           sfile.close();
	   }
        
		seps = new Vector<Long>();
		computePartition();
		data = new Vector<Point>();
		generatePoints();
		
		//System.out.println("data: " + data.toString());
		//System.out.println("Min separation: " + checkScatterAll());
		
		Lewis robbie = new Lewis(xMin, xMax, yMin, yMax, data, randSeed, false);
		
		try {
			try {
			   checkTreeInitialization(robbie);
			   System.out.print(" Completed test of quadtree initialization.\n");			}
			catch ( Exception e ) {
			   System.out.print("Exception caught while testing tree initialization: " + e + "\n");
			   System.out.print("Aborting remaining tests.\n");
			   return;
			}

			try {
			   checkInsertion(robbie);
			   System.out.print(" Completed test of quadtree insertion.\n");
			}
			catch ( Exception e ) {
			   System.out.print("Exception caught while testing insertion: " + e + "\n");
			   System.out.print("Aborting remaining tests.\n");
			   return;
			}

			try {
			   checkRegionSearch(robbie);
			   System.out.print(" Completed test of quadtree region search.\n");
			}
			catch ( Exception e ) {
				System.out.print("Exception caught while testing region search: " + e + "\n");
			}
		} 
		catch (Exception e) {
			System.out.print("Exception caught in main: " + e.getMessage() + "\n");
		}
	}
	
	private static void checkTreeInitialization(Lewis robbie) throws IOException {
		
	    robbie.checkTreeInitialization();
	}
	
	private static void checkInsertion(Lewis robbie) throws IOException {
	
       robbie.checkInsertion();
	}
	
	private static void checkRegionSearch(Lewis robbie) throws IOException {
		
       robbie.checkRegionSearch();
	}

	private static void generatePoints() throws IOException {
		
		Random source = new Random( randSeed );
		
		int pt = 0;
		while ( pt < nDataPts ) {
			long x = Math.abs(source.nextInt()) % xMax;
			long y = Math.abs(source.nextInt()) % yMax;
			
			if ( seps.contains(x) ) {
				++x;
			}
			if ( seps.contains(y) ) {
				++y;
			}
			
			Point nxt = new Point(x, y);
			//System.out.println(nxt);
			if ( checkScatterOK( nxt, 4L) ) {
			   if ( !data.contains(nxt) ) {
				   ++pt;
			      data.add(nxt);
			   }
			}
		}
	}
	
	private static boolean checkScatterOK(Point A, long Min) {
		
		for (int i = 0; i < data.size(); i++) {
			Point N = data.get(i);
			if ( taxiDistance(A, N) < Min )
				return false;
		}
		return true;
	}
	
	private static long taxiDistance(Point A, Point B) {
		
		return Math.abs(A.getX() - B.getX()) + Math.abs(A.getY() - B.getY());
	}
	
	private static long checkScatterAll() {
		
		long minimumSeparation = (1 << 20);
		for (int i = 0; i < data.size(); i++) {
			Point A = data.get(i);
			for (int j = 0; j < data.size(); j++) {
				if ( j != i ) {
					Point B = data.get(j);
					long currSeparation = taxiDistance(A, B);
					if ( currSeparation < minimumSeparation )
						minimumSeparation = currSeparation;
				}
			}
		}
		return minimumSeparation;
	}
	
	private static void computePartition() {
		
		int numParts = 1 << nDivisions;
		int Step = (xMax - xMin) / numParts;
		for (int lvl = 0; lvl <= numParts; lvl++) {
			
			long x = xMin + lvl * Step;
			seps.add( x );
		}
	}
	
	private static void setWorldBoundaries() {
		
		if ( nDataPts <= 10 ) {          // world is 32 x 32
	        xMax = 1 << 5;
	        yMax = 1 << 5;
	        nDivisions = 5;
		}
		else if ( nDataPts <= 40 ) {     // world is 256 x 256
	        xMax = 1 << 8;
	        yMax = 1 << 8;
	        nDivisions = 8;
		}
		else {                           // world is 32768 x 32768
	        xMax = 1 << 15;
	        yMax = 1 << 15;
	        nDivisions = 15;
		}
	}
}

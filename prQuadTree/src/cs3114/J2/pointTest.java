/**
 * 
 */
package cs3114.J2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import cs3114.J2.Client.Point;
import cs3114.J2.DS.Direction;
import cs3114.J2.DS.prQuadTree;
import junit.framework.TestCase;

/**
 * @author A
 *
 */
public class pointTest extends TestCase {

    private Point p;
    private prQuadTree<Point> tree;
    
    public void test() {
        fail("Not yet implemented");
    }
    
    public pointTest() {
        
    }
    public void setUp() {
        
        p = new Point(67, 55);
        tree = new prQuadTree<Point>(0, 100, 0, 100);
    }
    
    public void testInQuadrantNE() {
        //tree.insert(p);
        Point x = new Point(187, 150);
        assertEquals(x.inQuadrant(0, 256, 0, 256), Direction.NE);
    }
    
    public void testInQuadrantSW() {
        Point x = new Point(2, 2);
        assertEquals(x.inQuadrant(0, 256, 0, 256), Direction.SW);
    }
    
    public void testInQuadrantSE() {
        Point y = new Point(207, 31);
        assertEquals(y.inQuadrant(0, 256, 0, 256), Direction.SE);
    }
    
    public void testInQuadrantNW() {
        Point nw = new Point(65,181);
        assertEquals(nw.inQuadrant(0, 256, 0, 256), Direction.NW);
    }
    
    public void testInQuadrantCenter() {
        Point mid = new Point(520, 50);
        assertEquals(mid.inQuadrant(0, 256, 0, 256), Direction.NOQUADRANT);
    }
    
    public void testNEboundary() {
        Point tst = new Point(130, 128);
        assertEquals(tst.inQuadrant(0, 256, 0, 256), Direction.NE);
    }
    
    public void testSY() {
        Point c = new Point (237, 129);
        assertEquals(Direction.SW, c.inQuadrant(0, 256, 0, 256));
    }
    
    public void testBoolean() {
        
        Point c = new Point (4, 7);
        Direction dir = c.inQuadrant(0, 10, 0, 10);
        boolean tr = (dir == Direction.NW);
        assertTrue(tr);
    }

}

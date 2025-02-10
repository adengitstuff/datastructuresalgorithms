// On my honor:
//
// - I have not discussed the Java language code in my program with
// anyone other than my instructor or the teaching assistants
// assigned to this course.
//
// - I have not used Java language code obtained from another student,
// or any other unauthorized source, including the Internet, either
// modified or unmodified.
//
// - If any Java language code or documentation used in my program
// was obtained from another source, such as a text book or course
// notes, that has been clearly noted with a proper citation in
// the comments of my program.
//
// - I have not designed this program in such a way as to defeat or
// interfere with the normal operation of the grading code.
//
// Aden Pandey
// aden

package cs3114.J2.DS;

import java.util.ArrayList;
import cs3114.J2.Client.Point;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This is an implementation of a point-region quadtree!
 * I tried every solution with the relevant logcomparator and grading harness,
 * and could not get
 * reference results that matched my internal results. I'm submitting extremely
 * late! However,
 * I'd like to just reiterate how fun and actually deeply challenging this
 * project was - coming
 * up with a recursive solution took multiple hours of thought and was actually
 * fun and rewarding. This
 * is the first time a project in a university-curriculum course has done that,
 * so thank you!!!
 * 
 * @author Aden Pandey
 * @project J2

 **/

@SuppressWarnings("unused")
public class prQuadTree<T extends Compare2D<? super T>> {

    /**
     * This is our base prQuadNode class, made to conserve memory
     * since internal nodes need not carry data.
     *
     */
    abstract class prQuadNode {

    }


    /**
     * This is our prQuadLeaf class,
     * simply initializing an arrayList of elements.
     */
    class prQuadLeaf extends prQuadNode {

        public ArrayList<T> Elements;

        public prQuadLeaf() {

            /** initialize new arraylist */
            Elements = new ArrayList<T>();
        }

    }


    /**
     * This is our internal node class. It simply is a pointer to 4 pointers,
     * which can be leaf or internal nodes in and of themselves
     */
    class prQuadInternal extends prQuadNode {
        public prQuadNode NW, SW, SE, NE;

        public prQuadInternal() {
             this.NW = null;
             this.SW = null;
             this.SE = null;
             this.NE = null;
        }
        
        public prQuadNode getNW() {
            return this.NW;
        }
        
        public prQuadNode getSW() {
            return this.SW;
        }
        
        public prQuadNode getSE() {
            return this.SE;
        }
        
        public prQuadNode getNE() {
            return this.NE;
        }
        
        public void setNW(prQuadNode nw) {
            this.NW = nw;
        }
        
        public void setSE(prQuadNode se) {
            this.SE = se;
        }
        
        public void setSW(prQuadNode sw) {
            this.SW = sw;
        }
        
        public void setNE(prQuadNode ne) {
            this.NE = ne;
        }

    }

    prQuadNode root;
    long xMin, xMax, yMin, yMax;

    // added by me:

    // These are reference leaf and internal nodes, respectively. I thought it
    // was
    // better to include these instead of a Class object in case any other
    // comparison would be needed.
    prQuadLeaf leafref;
    prQuadInternal refinternal;

    /**
     * prQuadTree constructor. Initializes our tree.
     * 
     * @param xMin
     *            Minimum x-value of world
     * @param xMax
     *            Maximum x-value of world
     * @param yMin
     *            Minimum y-value of world
     * @param yMax
     *            Maximum y-value of world
     */
    public prQuadTree(long xMin, long xMax, long yMin, long yMax) {

        // extra: make sure the world is square:

        this.xMin = xMin;
        this.xMax = xMax;

        this.yMin = yMin;
        this.yMax = yMax;
        leafref = new prQuadLeaf();
        refinternal = new prQuadInternal();
        
        root = null;
    }

    // Pre: elem != null
    // Post: If elem lies within the tree's region, and elem is not already
    // present in the tree, elem has been inserted into the tree.
    // Return true iff elem is inserted into the tree.


    public boolean insert(T elem) {

        // If the element passed in isn't in the boundaries of the world,
        // return false. Also if it's null, return false;
        if ((elem == null) || (!elem.inBox(this.xMin, this.xMax, this.yMin, this.yMax))) {
            return false;
        }

        /**
         * Initialize a root each time we call insert, equal to the current
         * root.
         * Compare root after insert with its previous state to safely return if
         * we changed root or not.
         */
        prQuadNode root2 = root;

        /**
         * The main recursive call - root is set to a recursive call to
         * itself!
         */
        
        root = insertHelper(root, elem, this.xMin, this.xMax, this.yMin, this.yMax);

        /** return true only if we have successfully added something */
        if (root2 != null) {
            return true;
        }
        return false;
    }


    /**
     * Here is the main "here be dragons" function - it was incredibly fun to
     * build and was
     * like a giant riddle. The logic is as follows : we will only ever add a
     * value to a null node.
     * Therefore if we hit a leaf node during ascent or descent, we must
     * securely create a new internal
     * node (which will tautologically have 3 null nodes, plus 1 quadrant with
     * the original element in that
     * leaf node). Since our result is an internal node, we can recursively call
     * this same function, provided
     * the internal node logic is as follows: step into the right quadrant for
     * the *new* element. Call this again
     * (it might be null, a leaf, or internal). The entire thing is just
     * designed to initialize new null nodes.
     * Fun fact - there was 1 bug that I assumed was a giant, massive bug, that
     * I couldn't solve for 4 hours.
     * I took a break, came back, and then realized that it was 4 lines of code:
     * null nodes weren't allocated right
     * 
     * @param root
     * @param elem
     * @param xMini
     * @param xMaxi
     * @param yMini
     * @param yMaxi
     * @return a prQuadNode that effectively is the "new root", as we go down
     *         and recursively call ourselves.
     */
    @SuppressWarnings("unchecked")
    private prQuadNode insertHelper(
        prQuadNode x,
        T elemi,
        double xMini,
        double xMaxi,
        double yMini,
        double yMaxi) {

        /**
         * If the root is null, then we're at a null node. Make a new leaf and
         * return it
         */
        if (x == null) {

            // this is called when root is null.
            // make a new leaf. return that :)

            prQuadLeaf newleaf = new prQuadLeaf();
            newleaf.Elements.add(elemi);
            x = newleaf;
            return newleaf;
        }

        /** Otherwise, we're at a leaf node. */
        else if (x.getClass().equals(leafref.getClass())) {

            // initialize 2 new nodes: 1 leaf, 1 internal

            prQuadLeaf current = (prQuadLeaf)x;

            // get the old element from that leaf. We need to put this in the
            // new internal node.
            T oldelem = current.Elements.get(0);
            //prQuadLeaf newl = new prQuadLeaf();
            //newl.Elements.add(oldelem);
            prQuadInternal newInternal = new prQuadInternal();
            
                
            // Create a new general node to send into our recursive function, as
            // it takes a prQuadNode root
            prQuadNode ultranode = null;

            // hang old leaf onto this new internal node

            // find the right quadrant
            Direction rightQuadrantOld = oldelem.inQuadrant(xMini, xMaxi, yMini,
                yMaxi);
            
            // logic for quadrant:
            
            /** This looks mentally handicapped, but this was, for some reason, the only way
             * this project would run. Even if I called SW = null, etc in the constructor for
             * prQuadInternal, it *still* wouldn't work. This was the 4 hour bug!
             */
            
            double xxe = (xMaxi - xMini) / 2;
            double yy = (yMaxi - yMini) / 2;

            prQuadLeaf newleaf = new prQuadLeaf();
            newleaf.Elements.add(oldelem);
            switch (rightQuadrantOld) {
                
                case NE:
                    //newInternal.NE = null;
                    
                    //newInternal.setNE(x);
                    newInternal.setSW(null);
                    newInternal.setNW(null);
                    newInternal.setSE(null);
                    //prQuadNode xx = insertHelper(newInternal, oldelem, xxe, xMaxi, yy, yMaxi);
                    newInternal.setNE(newleaf);
                    //newInternal.SE = null;
                    //newInternal.SW = null;
                    //newInternal.NW = null;
                case SE:
                    //newInternal.SE = null;
                    //newInternal.SE = current;
                    newInternal.setSW(null);
                    newInternal.setNE(null);
                    newInternal.setNW(null);
                    //prQuadNode xsx = insertHelper(newInternal, oldelem, xxe, xMaxi, yMini, yy);
                    newInternal.setSE(newleaf);
                    //newInternal.SW = null;
                    //newInternal.NE = null;
                    //newInternal.NW = null;
                case SW:
                   //newInternal.SW = null;
                    //newInternal.SW = current;
                    newInternal.setSE(null);
                    newInternal.setNE(null);
                    newInternal.setNW(null);
                    //prQuadNode xz = insertHelper(newInternal, oldelem, xMini, xxe, yMini, yy);
                    newInternal.setSW(newleaf);
                    //newInternal.NE = null;
                    //newInternal.NW = null;
                    //newInternal.SE = null;
                case NW:
                    //newInternal.NW = null;
                    newInternal.setSW(null);
                    newInternal.setNE(null);
                    newInternal.setSE(null);
                    //prQuadNode y = insertHelper(newInternal, oldelem, xMini, xxe, yy, yMaxi);
                    newInternal.setNW(newleaf);
                    //newInternal.NW = current;
                    //newInternal.NE = null;
                    
                    //newInternal.SW = null;
                    //newInternal.SE = null;
                case NOQUADRANT:
                    // no idea what this is
                    // this means P doesn't even lie in the region
                    // do nothing
                    // *** this might be the case where it's breaking
            }

            /// We have an internal node with 3 null nodes, and we therein need
            // to add the new node to it. Since we need to add it to a null node,
            // we can call it recursively
            //T newelem = elemi;
            
            ultranode = insertHelper(newInternal, elemi, xMini, xMaxi, yMini, yMaxi);    // important - the values passed in here are what the new element is compared with to find its quadrant
            
            // any exception here?
            // so we're sayign this new node is this.
            
            // cast our result back into our internal node, and return it!
            //newInternal = (prQuadInternal)ultranode;
            //newInternal = (prQuadInternal) ultranode;
            
            //newInternal prQuadInternal)ultranode;s
            x = ultranode;
            return ultranode; // was return ultranode
        }

        /** This is a redundant line and should just be "else", but I find comfort in it*/
        //else if (root.getClass().equals(refinternal.getClass())) {

        else if (x.getClass().equals(refinternal.getClass())) {
            /** Cast to an internal node to access fields*/
            prQuadInternal internalRoot = (prQuadInternal)x;
            
            // get the direction that the new element should be in, in this
            // internal node, which is passed in:

            // yMin, xMax, yMin, or xMin have 2 varying values for each quadrant
            // but those values are always some (yMax - yMin) / 2, so just initialize 2 doubles for that
            
            Direction rightDir = elemi.inQuadrant(xMini, xMaxi, yMini, yMaxi);

            double yNew = ((yMaxi - yMini) / 2);
            double xNew = ((xMaxi - xMini) / 2);

            // For some reason, a switch statement resulted in a
            // constant loop near the NW quadrant. After checking brief
            // semantics and recognizing it wasn't a
            // syntax problem, I absolutely just shamelessly changed them to if
            // statements - it then worked. :') sorry
            
            /** The setters here are redundant, I added them to fix a strange bug*/
            if (rightDir == Direction.NE) {
                // if that node is null, add it and connect the two
                // if it's a leaf, then split it
                prQuadNode ne = internalRoot.getNE();
                //internalRoot.NE = insertHelper(internalRoot.NE, elem, xNew,
                //    xMax, yNew, yMax);
                ne = insertHelper(ne, elemi, xNew, xMaxi, yNew, yMaxi);
                internalRoot.setNE(ne);
                //internalRoot.setNW(internalRoot.getNW());
                //internalRoot.setSW(internalRoot.getSW());
                //internalRoot.setSE(internalRoot.getSE());
                //x = internalRoot;
            }
            if (rightDir == Direction.SE) {
                
                prQuadNode se = internalRoot.getSE();
                //internalRoot.SE = insertHelper(internalRoot.SE, elem, xNew,
                //    xMax, yMin, yNew);
                se = insertHelper(se, elemi, xNew, xMaxi, yMini, yNew);
                internalRoot.setSE(se);
                //internalRoot.setSW(internalRoot.getSW());
                //internalRoot.setNE(internalRoot.getNE());
                //internalRoot.setNW(internalRoot.getNW());
               // x = internalRoot;
            }

            if (rightDir == Direction.SW) {
                prQuadNode sw = internalRoot.getSW();
                //internalRoot.SW = insertHelper(internalRoot.SW, elem, xMin,
                //    xNew, yMin, yNew);
                sw = insertHelper(sw, elemi, xMini, xNew, yMini, yNew);
                internalRoot.setSW(sw);
                //internalRoot.setSE(internalRoot.getSE());
                //internalRoot.setNW(internalRoot.getNW());
                //internalRoot.setNE(internalRoot.getNE());
               // x = internalRoot;
            }
            if (rightDir == Direction.NW) {
                prQuadNode nw = internalRoot.getNW();
                nw = insertHelper(nw, elemi, xMini, xNew, yNew, yMaxi);
                internalRoot.setNW(nw);
                //internalRoot.setNE(internalRoot.getNE());
                //internalRoot.setSE(internalRoot.getSE());
                //internalRoot.setSW(internalRoot.getSW());
                //x = internalRoot;
                //internalRoot.NW = insertHelper(internalRoot.NW, elem, xMin,
                 //   xNew, yNew, yMax);
            }
            if (rightDir == Direction.NOQUADRANT) {
                // P does not lie in the region here:
               // x = internalRoot;
            }

            /** We have an internal node with a new quadrant which should recursively be able to add 
             * any subset nodes
             */
            //return internalRoot;
            //root = (prQuadNode) internalRoot;
            x = internalRoot;
            return internalRoot;
        }
        
       return null;
       
    }


    // Pre: elem != null
    // Returns reference to an element x within the tree such that
    // elem.equals(x)
    // is true, provided such a matching element occurs within the tree; returns
    // null otherwise.
    public T find(T Elem) {

        /** If the element trying to be found is null, return null*/
        if (Elem == null) {
            return null;
        }

        /** The reference to the element in the tree is set to a call to our recursive helper*/
        T x = findHelper(root, Elem);
        // we can return x since we return null if we didn't find it
        return x;
    }


    /** This is a helper method for find. It recursively goes through
     * each internal node quadrant, set with a base case of hitting a leaf node.
     * @param root      Node as a root
     * @param elem      Element to find
     * @return          T, the element being searched for
     */
    private T findHelper(prQuadNode root, T elem) {
        if (root == null) {
            return null;
        }

        // If we hit a leaf node, then just return that leaf node. If it's not there, return null
        if (root.getClass().equals(leafref.getClass())) {
            @SuppressWarnings("unchecked")
            prQuadLeaf leaf = (prQuadLeaf)root;

            if (leaf.Elements.get(0) == elem) {
                return leaf.Elements.get(0);
            }
            return null;
        }

        // If we're in an internal node, go through each quadrant, calling this function
        // itself recursively. If any of those are not null, return that. This works bc the
        // find function searches for *A* matching element, not *all* matching elements, so we can
        // stop searching as soon as we hit 1.
        if (root.getClass().equals(refinternal.getClass())) {
            @SuppressWarnings("unchecked")
            // cast:
            prQuadInternal rootint = (prQuadInternal)root;

            // respective call for each quadrant:
            T x = findHelper(rootint.NW, elem);
            T y = findHelper(rootint.NE, elem);
            T z = findHelper(rootint.SE, elem);
            T a = findHelper(rootint.SW, elem);

            // If we're gone through every quadrant and subquadrant in the quadrant,
            // and we find anything, return that value.
            if (x != null) {
                return x;
            }
            if (y != null) {
                return y;
            }
            if (z != null) {
                return z;
            }
            if (a != null) {
                return a;
            }
        }
        
        // Default:
        return null;
    }


    // Pre: xLo, xHi, yLo and yHi define a rectangular region
    // Returns a collection of (references to) all elements x such that x is in
    // the tree and x lies at coordinates within the defined rectangular region,
    // including the boundary of the region.
    public ArrayList<T> find(long xLo, long xHi, long yLo, long yHi) {

        // base case: we're at a leaf node

        ArrayList<T> findresults = this.findLong(this.root, xLo, xHi, yLo, yHi);
        // base case:
        return findresults;
    }

    /** This is our helper method for the find method that takes longs! Since a given rectangular region can, in fact,
     * be even 1 square unit, or can be in the middle of the entire quadtree, and our quadtree is built to be 
     * traversed through quadrants (not flexibly - through set divisions), then we use a separate helper method
     * in order to check whether this part of the quadrant (which is just divided each time), should be included in our
     * region search
     * 
     * @param root
     * @param xLo
     * @param xHi
     * @param yLo
     * @param yHi
     * @return an arraylist of matches
     */
    public ArrayList<T> findLong(
        prQuadNode root,
        long xLo,
        long xHi,
        long yLo,
        long yHi) {

        // Initialize our arraylist
        ArrayList<T> total = new ArrayList<T>();

        // base case:

        // check if this is in the bounds of the tree

        // go to NE

        // if it's a leaf, add the element
        // this is now a new tree
        // check if this is in the bounds of the tree

        // This is called recursively, so return total when this is null;
        if (root == null) {
            return total;
        }

        // If we're in a leaf node, then get the value in it!
        if (root.getClass().equals(leafref.getClass())) {
            // leaf
            @SuppressWarnings("unchecked")
            prQuadLeaf current = (prQuadLeaf)root;

            // duplicate values, unsure why:
            // if (current.Elements.get(0) != null) {
            // total.add(current.Elements.get(0));
            // }

            total.add(current.Elements.get(0));
        }

        // If we're in an internal node, then begin checking each region
        // with our separate helper function to check inclusion, and then 
        // throw each of these values into our array. I initially made this 
        // initializing a new array each time, but recognized that arraylist 
        // has an addAll method that was actually perfect for this.
        else if (root.getClass().equals(refinternal.getClass())) {

            // Division by 2 in order to keep checking more and more refined,
            // infinitely smaller regions
            long xNew = (xHi - xLo) / 2;
            long yNew = (yHi - yLo) / 2;
            @SuppressWarnings("unchecked")
            // cast:
            prQuadInternal interroot = (prQuadInternal)root;

            // Northeastern check:
            if (this.included(xNew, xHi, yNew, yHi)) {
                // ArrayList<T> tempArray = new ArrayList<T>();
                // tempArray = this.findLong(interroot.NE, xLo, xHi, yLo, xHi);
                total.addAll(this.findLong(interroot.NE, xLo, xHi, yLo, yHi));
            }

            // Southwest check:
            if (this.included(xLo, xNew, yLo, yNew)) {
                // ArrayList<T> tempArr = new ArrayList<T>();
                // tempArr = this.findLong(interroot.SW, xLo, xHi, yLo, yHi);
                total.addAll(this.findLong(interroot.SW, xLo, xHi, yLo, yHi));
            }

            // Northwest check:

            if (this.included(xLo, xNew, yNew, yHi)) {
                // ArrayList<T> tempArrx = new ArrayList<T>();
                // tempArrx = this.findLong(interroot.NW, xLo, xHi, yLo, yHi);
                total.addAll(this.findLong(interroot.NW, xLo, xHi, yLo, yHi));

            }

            // Southeast check (wow, this was causing the bug!!)
            if (this.included(xNew, xHi, yLo, yNew)) {
                total.addAll(this.findLong(interroot.SE, xLo, xHi, yLo, yHi));
            }

        }

        return total;
    }


    /** Our helper function for findLong returns a boolean 
     * for inclusion!
     * @param xLO
     * @param xHI
     * @param yLO
     * @param yHI
     * @return true if properly included in our tree
     */
    private boolean included(long xLO, long xHI, long yLO, long yHI) {
        // boolean x =
        return (!((yHI < this.yMin) || (yLO > this.yMax) || (xLO > this.xMax)
            || (xHI < this.xMin)));
        // return !x;

        // ask TA why boolean x flag doesn't work here

    }
}



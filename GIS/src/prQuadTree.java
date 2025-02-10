
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import java.awt.geom.Rectangle2D;
import java.io.FileWriter;
import java.io.IOException;

/** This is our recursive PR Quad Tree. This tree holds coordinate objects. 
 * The difference between this and J2 is that this is a true quadtree - 
 * each leaf can hold up to 4 entries. These data structures are so interesting!
 * 
 * 
 * @author Aden P
 * @project  GIS
 * @date 7.18.2022
 **/

@SuppressWarnings("unused")
public class prQuadTree<T extends Compare2D<? super T>> {

    /**
     * This is our base prQuadNode class, made to conserve memory
     * since internal nodes need not carry data. @10.17 SAAAAH DUDDDD KONG
     *
     */
    abstract class prQuadNode {
        
        public prQuadNode() {
            /* Added this to help debug. Constructor is never called since it's an abstract type*/
        }
    }

    /* Delete this! Just use it for testing a class here */
    class prQuadNull extends prQuadNode {
        public prQuadNull() {
                                                          
        }
    }
    /**
     * This is our prQuadLeaf class,
     * simply initializing an arrayList of elements.
     */
    class prQuadLeaf extends prQuadNode {

        public ArrayList<T> bucket;
        private final int max = 4;

        public prQuadLeaf() {

            /** initialize new arraylist */
            bucket = new ArrayList<T>(max);
        }
        
        public prQuadLeaf(T elem) {
            bucket = new ArrayList<T>(max);
            bucket.add(elem);
        }
        
        public void add(T elem) {
            bucket.add(elem);
            /* Add full case. */
        }
        
        public void resize() {
            
        }

    }


    /**
     * This is our internal node class. It simply is a pointer to 4 pointers,
     * which can be leaf or internal nodes in and of themselves
     */
    class prQuadInternal extends prQuadNode {
        public prQuadTree<T>.prQuadNode NW;
        public prQuadTree<T>.prQuadNode SE;
        public prQuadTree<T>.prQuadNode SW;
        public prQuadTree<T>.prQuadNode NE;

        public prQuadInternal() {
            /* Avoided assigning to null since that might count as an allocation?*/
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
    
    
                                                            /*      PR Quad Tree!    */
    
    
    
     
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

    prQuadNode root;
    long xMin;
    long yMin;
    long xMax;
    long yMax;
    prQuadLeaf leafclass = new prQuadLeaf();
    prQuadInternal quadclass = new prQuadInternal();
    private final int bucketSize = 4;
    public prQuadTree(long xMini, long yMini, long xMaxa, long yMaxa) {

        // extra: make sure the world is square:
        this.xMin = xMini;
        this.yMin = yMini;
        this.xMax = xMaxa;
        this.yMax = yMaxa;
        this.root = null;
        
        // root?
        
        
        
    }
    
    
    

    // Pre: elem != null
    // Post: If elem lies within the tree's region, and elem is not already
    // present in the tree, elem has been inserted into the tree.
    // Return true iff elem is inserted into the tree.

    public boolean insert(T elem) {
       
      //  System.out.println(" in insert :))))))))))))))))))");
       if (elem == null) {
           return false;
       }
       
       long xval = elem.getX();
       long yval = elem.getY();
       
       if (xval < this.xMin || yval < this.yMin || xval > this.xMax || yval > this.yMax) {
       //    System.out.println("the checks didn't go thru. returning false!");
       //    System.out.println(" xval is: " + xval + "   " + "yval : " + yval + "compared to: " + elem.getX() + " " + elem.getY());
      //     System.out.println("and our world boundaries: x min" + this.xMin + " y min : " + this.yMin + "  x max : " + this.xMax + " y max " + this.yMax);
           return false;
       }
       //System.out.println( " AAAAAAAAAAAAAAAAAAYYYYYYYYYYYE. we're in pr quad tree INSERRRT");
       prQuadNode node = insertRecursive(this.root, (double)this.xMin, (double)this.yMin, (double)this.xMax, (double)this.yMax, elem);
       /* Past the "node" in insert*/
       if (node == null) {
           return false;
       }
       this.root = node;
       return true;
    }
    
    
    /** Re-do of the recursive function!
     * 
     * This was the most incredibly fun thing in any CS course I've ever done. It was 
     * a joy to be new to this in 2, and to build this for this project!
     *  It's interesting to go from small-size to mid-size programs! 
     * 
     * This QuadTree just recursively calls itself for every insert. Many embarrassing
     * hours were spent over infinitesimal problems (prQuadTree<T>.prQuadNode, rip)
     * This just goes through internal nodes and adds it if it's a leaf, in essence.
     * 
     * 
     * @param xmn, ymn, xmx, ymx     - xMin, yMin, xMax, yMax values. In that order
     * @param T elem                 - The element to be inserted!
     * 
     * */
    @SuppressWarnings("unchecked")
    public prQuadNode insertRecursive(prQuadNode rootx, double xmn, double ymn, double xmx, double ymx, T elem) {
        
        /* First, handle duplicates. Find? */
       
       // System.out.println(" @@@@@@@@@@@@@@@$$$$$ Wre're in Recursive!!!. We got passed in + " + xmn + " " + ymn + " " + xmx + " " + ymx + " and " + elem.getX() + " " + elem.getY());
        
        if (rootx == null) {
            prQuadLeaf leafx = new prQuadLeaf();
            leafx.add(elem);
            //System.out.println("We are in the null part of the rootx! :) ");
          //  System.out.println("We just added elem " + leafx.bucket.get(0).getX() + " , " + leafx.bucket.get(0).getY());
        //    System.out.println(" ^ those are printed from the bucket itself, which we're returning");
            return leafx;
        }
        
        prQuadInternal printernal = null;
        /* Leaf node case : */
        if (rootx.getClass().equals(leafclass.getClass())) {
            
            prQuadLeaf leaf = (prQuadLeaf) rootx;
            
            /* if it contains elem already?*/
            if (leaf.bucket.contains(elem)) {
                //System.out.println(" We're in 'if leaf contains elem!!");
                
                /* find the element that has the same x and y coordinates */
                
                for (T element : leaf.bucket) {
                    if (element.equals(elem)) {
                        /* equals returns true if their x and y coordinates are the same. now we have the element with the same x and y coordinate
                         */
                        // cast in order to access offsets
                        coordinate elementc = (coordinate) element;
                        // such wasteful memory use, but i could see no way around it. cast in order to get offset
                        coordinate elemc = (coordinate) elem;
                        
                        /* loop thru each offset in elementc. if they're not the same elem's offset, add elem's offset to the list of offsets*/
                        if (elementc.offsets().contains(elemc.offsets().get(0))) {
                            return rootx;
                        }
                        else {
                            elementc.offsets().add(elemc.offsets().get(0));
                            return rootx;
                        }
                    }
                }
                
                return rootx;
                
            }
            
            /* if it's not full:*/
            if (leaf.bucket.size() < bucketSize) {
                //.out.println(" we're in the < bucketsize part. we will add it");
                leaf.add(elem);
                return rootx;
            }
            
            else if (leaf.bucket.size() >= bucketSize) {
                /* Here be dragons!*/
                
                prQuadInternal internal = new prQuadInternal();
                                                
                /* For each element in the leaf bucket so far, go through and add it into the new internal node. Be careful*/
                /* Since it MUST go into a new leaf node, we can avoid all the compiler issues (omg, so many) by just hard-coding and forcing a new leaf node. Idk if this is the right
                 * solution
                 */
                for (T element : leaf.bucket) {
                    Direction rightDirection = element.inQuadrant(xmn, xmx, ymn, ymx);
                    
                    /* I had to do this, but to me this is sad! It should be recursive fully. The recursive function should handle the null cases, etc*/
                    switch (rightDirection) {
                        case NE:
                            if (internal.NE == null) {
                                internal.NE = new prQuadLeaf(element);
                                break;
                            }
                            else {
                            prQuadLeaf neleaf = (prQuadLeaf) internal.NE;
                            neleaf.add(element);
                            break;
                            }
                            /* This CAN'T be full. but it can either be a null or leaf node. here's the case wher eit's a leaf node :*/
                            /* but what if it's full?*/
                            /* actually there can only be 4, so this should work?*/
                        case NW:
                            if (internal.NW == null) {
                                internal.NW = new prQuadLeaf(element);
                                break;
                            }
                            else {
                            prQuadLeaf nwleaf = (prQuadLeaf) internal.NW;
                            nwleaf.add(element);
                            break;
                            }
                        case SE:
                            if (internal.SE == null) {
                                internal.SE = new prQuadLeaf(element);
                                break;
                            }
                            else {
                            prQuadLeaf seleaf = (prQuadLeaf) internal.SE;
                            seleaf.add(element);
                            break;
                            }
                        case SW:
                            if (internal.SW == null) {
                                internal.SW = new prQuadLeaf(element);
                                break;
                            }
                            else {
                            prQuadLeaf swleaf = (prQuadLeaf) internal.SW;
                            swleaf.add(element);
                            break;
                            }
                        case NOQUADRANT:
                            //System.out.println("We're in the NO-  QUADRANT logic. @@ Yikes!!");
                            //.out.println("We'll avoid exiting, but unsure. the point fits into the world!!!!");
                            //System.exit(200);
                            break;
                    }
                    printernal = internal;
                    rootx = printernal;
                    
                                /*  @@@@@@@@@@@@@@@@@@@ IF THERE'S A PROBLEM HERE, USE ANOTHER INTERNAL NODE TO INITIALIZE, THEN SET THEM*/
                 
                } /* End for loop!*/

                /* Now, we have an internal node with all previous 4 elements attached. Since there can only be 4, (thank the Lord God in Heaven), 
                 * this can't overflow or need to be partitioned more. It's impossible for 1 to be full. This simplifies things somewhat. So now, we 
                 * still have the element we want to add to add. But we have an up-and-ready internal node to recursively call, so:
                 */

                /* for the new element: */
                    Direction rightPath = elem.inQuadrant(xmn, xmx, ymn, ymx);
                    
                                    /* For extra extra clarity: We intend to add a new element. The leaf bucket size was full. We have a fresh internal node
                                     * with 4 of the previous elements now added in. We intend to add the NEW element onto this internal node.
                                     * 
                                     * Things that can go wrong:
                                     *                  - all 4 elements could've been added into 1 quadrant, so it needs re-partitioning
                                     *                  - some dumb mistake
                                     *                  - 
                                     */
                    
                    double xnew = (xmn + xmx) / 2.0;
                    double ynew = (ymn + ymx) / 2.0;
                    
                    switch (rightPath) {
                                    /*NE*/               
                    case NE:
                    /* There are only 3 options here. It is either: 
                    * - a null node
                    * - a ready leaf node
                    * - a full leaf node (ugh)
                    */
                //        System.out.println(" @@@@@ NE!!!!!! (leaf");
                    if (internal.NE == null) {
                    internal.NE = new prQuadLeaf(elem);
                    }
                    else {
                    prQuadLeaf neleafx = (prQuadLeaf) internal.NE;
                    if (neleafx.bucket.size() < bucketSize) {
                        /* This is the case where it's not full. yaaaay*/
                        neleafx.add(elem);
                        break;
                    }
                    else /* if neleaf.bucket.size() >= bucketSize!!?)*/ {
                        // this is the main recursive step. it's full, so just call it recursively:
                        internal.NE = insertRecursive(internal.NE, xnew, ynew, xmx, ymx, elem);
                        break;
                    }
                    }
                    
                                /* SE : */
                    case SE:
                 //       System.out.println(" @@@@@ SE!!!!!! (leaf");
                    if (internal.SE == null) {
                    internal.SE = new prQuadLeaf(elem);
                    }
                    else {
                    prQuadLeaf seleafx = (prQuadLeaf) internal.SE;
                    if (seleafx.bucket.size() < bucketSize) {
                        /* This is the case where it's not full. yaaaay*/
                        seleafx.add(elem);
                        break;
                    }
                    else /* if neleaf.bucket.size() >= bucketSize!!?)*/ {
                        // this is the main recursive step. it's full, so just call it recursively:
                        internal.SE = insertRecursive(internal.SE, xnew, ymn, xmx, ynew, elem);
                        break;
                    }
                    }
                    
                            /* NW case: new elem*/
                    case NW:
                //        System.out.println(" @@@@@ NW!!!!!! (leaf");
                    if (internal.NW == null) {
                    internal.NW = new prQuadLeaf(elem);
                    }
                    else {
                    prQuadLeaf nwleafx = (prQuadLeaf) internal.NW;
                    if (nwleafx.bucket.size() < bucketSize) {
                        /* This is the case where it's not full. yaaaay*/
                        nwleafx.add(elem);
                        break;
                    }
                    else /* if neleaf.bucket.size() >= bucketSize!!?)*/ {
                        // this is the main recursive step. it's full, so just call it recursively:
                        internal.NW = insertRecursive(internal.NW, xmn, ynew, xnew, ymx, elem);
                        break;
                    }
                    }
                    
                          /* SW case: new elem!*/
                    case SW:
                       // System.out.println(" @@@@@ SW!!!!!! (leaf");
                    if (internal.SW == null) {
                    internal.SW = new prQuadLeaf(elem);
                    }
                    else {
                    prQuadLeaf swleafx = (prQuadLeaf) internal.SW;
                    if (swleafx.bucket.size() < bucketSize) {
                        /* This is the case where it's not full. yaaaay*/
                        swleafx.add(elem);
                        break;
                    }
                    else /* if neleaf.bucket.size() >= bucketSize!!?)*/ {
                        // this is the main recursive step. it's full, so just call it recursively:
                        internal.SW = insertRecursive(internal.SW, xmn, ymn, xnew, ynew, elem);
                        break;
                    }
                    }
                    case NOQUADRANT:
              //      System.out.println("Help - I'm in the no quadrant 2nd loop!!");
           //         System.out.println("Not sure what to do here!!!!!");
                    //System.exit(100);
                    
                    } /* End switch statement*/
                    
                    return internal;
                
            } /* End else-if (full bucket case!*/
            
        } /* End leaf-class case!*/
        
                                                                
                                                        
                                                                
                                                                /* Internal node logic!*/
                                                        /* This should be pretty simple - just recursive calls if we're in an internal node*/
                                                        
                                                        
        if (rootx.getClass().equals(quadclass.getClass())) {
            prQuadInternal inter = (prQuadInternal) rootx;
            Direction elemadd = elem.inQuadrant(xmn, xmx, ymn, ymx);
            
            double xnew = (xmn + xmx) / 2.0D;
            double ynew = (ymn + ymx) / 2.0D;
            
            switch (elemadd) {
                case NE:
                    /* We're in an internal node, and the element belongs in the NE quadrant. So just call this recursively*/
                    inter.NE = insertRecursive(inter.NE, xnew, ynew, xmx, ymx, elem);
               //     System.out.println("internal case. NE");
                    break;
                case SE:
                    inter.SE = insertRecursive(inter.SE, xnew, ymn, xmx, ynew, elem);
              //      System.out.println("internal case. SE");
                    break;
                case NW:
                    inter.NW = insertRecursive(inter.NW, xmn, ynew, xnew, ymx, elem);
              //      System.out.println("internal case. NW");
                    break;
                case SW:
                    inter.SW = insertRecursive(inter.SW, xmn, ymn, xnew, ynew, elem);
              //      System.out.println("internal case. SW");
                    break;
                case NOQUADRANT:
                    System.exit(3);
                    break;
            }
            
            return rootx; /* <--- @@@ ?*/
            
        }
        
        
            return printernal;
        
        
        
        
    } /* End insert recursive */
    
    
    
    
    /** Find simply passes an element into a recursive find method. I learned that quadtrees are 
     * surprisingly helpful for searching for objects, given that internal nodes don't hold data.
     * In effect, it's like adding quadrant logic to pointers. 1 thing points to 4 possible, related
     * variations of a thing. Therein, I wonder if octatrees are a thing.
     * 
     * @elem    Element to find
     */
    public T find(T elem) {

        /** If the element trying to be found is null, return null*/
        if (elem == null) {
            System.out.println(" you passed in a null entry to T find(T elem) in prQuadTree");
            return null;
        }
        long x = elem.getX();
        long y = elem.getY();
//        System.out.println(" elem's x and y : " + x + " " + y);
//        System.out.println(" It might be bad xmin's and xmaxs. Check xmin, xmax, ymin, ymax : " + xMin + " " + xMax + " " + yMin + " " + yMax);
        if (x < this.xMin || x > this.xMax || y < this.yMin || y > this.yMax) {
            return null;
        }
        return findRecursive(this.root, elem, this.xMin, this.yMin, this.xMax, this.yMax);
        
    }
    
    
    /** Recursive finder method for our quadtree. This was fun to figure out in both 
     * projects. This takes an element, and then recursively figures out what quadrant it should
     * be in. Then, if it's at an internal node, it still figures out what quadrant it should be in.
     * It's like a hashtable hashing an entry to find. Then, given that we're at the proper spot (a leaf node),
     * it just loops through every element and checks for a match. Here, "match" means that
     * x and y coords are the same - not offsets
     * 
     * @param rootz                         The root of the tree
     * @param xmn, ymn, xmx, ymx            xMin, yMin, xMax, yMax, in that order 
     * @param T                             Element to add
     * 
     */
    public T findRecursive(prQuadNode rootz, T elem, double xmn, double ymn, double xmx, double ymx) {
        
        /* This is a simpler version of insertion, basically! 3 cases: null, internal, and leaf:*/
        
        if (rootz == null) {
            return null;
        }
        else if (rootz.getClass().equals(leafclass.getClass())) {
            @SuppressWarnings("unchecked")
            prQuadLeaf leafie = (prQuadLeaf) rootz;
            
            for (T element : leafie.bucket) {
                  if (element.equals(elem)) {
                      return element;
                  }
            }
            
        }
        if (rootz.getClass().equals(quadclass.getClass())) {
            double xnew = (xmn + xmx) / 2.0;
            double ynew = (ymn + ymx) / 2.0;
           // System.out.println("I'm about to run a direction check on : " + xmn + " " + ymn + " " + xmx + " " + ymx);
           // System.out.println("Elem is in : " + elem.getX() + " " + elem.getY());
            Direction rightDir = elem.inQuadrant(xmn, xmx, ymn, ymx);
            @SuppressWarnings("unchecked")
            prQuadInternal rootint = (prQuadInternal) rootz;
            
            switch (rightDir) {
                case NE:
                    //System.out.println("got NE...");
                    return findRecursive(rootint.NE, elem, xnew, ynew, xmx, ymx);
                case SE:
                    //System.out.println("got SE");
                    return findRecursive(rootint.SE, elem, xnew, ymn, xmx, ynew);
                case SW:
                    //System.out.println("Got SW");
                    return findRecursive(rootint.SW, elem, xmn, ymn, xnew, ynew);
                case NW:
                    //System.out.println("Got nw...");
                    return findRecursive(rootint.NW, elem,  xmn, ynew, xnew, ymx);
                case NOQUADRANT:
                    System.out.println( " @ NoQUADRANT case ! + in findRecursive, internal <3");
                    break;
            }
        }
        return elem;
    }
        
    
    /** Our range finder method! We call the recursive function to go through every possible leaf
     * in the tree, checking for inquadrant.
     * 
     * @param xmn, ymn, xmx, ymx        -       xMin,  yMin, xMax, yMax - in that order
     * @return                                  All objects!
     */
    public Vector<T> find(long xmn, long ymn, long xmx, long ymx) {
        Vector<T> vec = new Vector<T>();
//        System.out.println(" I'm in find. vec is : " + vec + " and coords are : " + xmn + " " + ymn + " " + xmx + " " + ymx);
//        System.out.println(" this root and world values are : " + this.root + this.xMin + " " + this.yMin + " " + this.xMax + " " + this.yMax);
        findRange(this.root, vec, xmn, ymn, xmx, ymx, this.xMin, this.xMax, this.yMin, this.yMax);
        
        return vec;
    }
    
    @SuppressWarnings("unchecked")
    /** findRange starts at the root, and goes through each quadrant. There is another way to do this wherein one
     * consistently checks if regions overlap, but I found that that method was not particularly faster than this one;
     * this root of this method (pun intended) is a check to inBox, essentially. I did not make use of the Rectangle class,
     * and in hindsight should have looked into it
     * 
     * @param rooty                                                 Root to start in, and for all recursive calls   
     * @param vectra                                                Vector to add objects to!
     * @param xmn, ymn, xmx, ymx                                    THE RANGE WE'RE SEARCHING FOR: xMin, yMin, xMax, yMax. IN that order
     * @param thisxmin, thisxmax, thisymin, thisymax                THE RANGE OF "THIS" CURRENT BOX: xMin, xMax, yMin, yMax. In that order
     */
    public void findRange(prQuadNode rooty, Vector<T> vectra, long xmn, long ymn, long xmx, long ymx, long thisxmin, long thisxmax, long thisymin, long thisymax) {
        /* There are still the same 3 cases here, which is mind-boggling.
         * Range search was hard to wrap my head around at first, it's crazy
         * how beautiful and logically sound recursion is.
         */
       
        if (rooty == null) {
            return;
        }
        
        if (rooty.getClass().equals(leafclass.getClass())) {
            prQuadLeaf leaf = (prQuadLeaf) rooty;
            
            /* Go through each element!*/
            
            /* Trying a different for loop, for element : bucket didn't work?
             * 
             */
            
            for (int i = 0; i < leaf.bucket.size(); i++) {
                //System.out.println("checking element: " + leaf.bucket.get(i) + "or " + leaf.bucket.get(i).getX() + " " + leaf.bucket.get(i).getY());
                if (leaf.bucket.get(i).inBox(xmn, xmx, ymn, ymx)) {
                    //System.out.println("in element: " + i);
                    vectra.addElement(leaf.bucket.get(i));
                  
                }
                else {
                    // Nohting to do here - it failed the inBox test.
                }
            }
            return;
        }
        
        if (rooty.getClass().equals(quadclass.getClass())) {
                        /* NE block : this logic is tricky*/
            /* Basically for each quadrant, call this. internal nodes are annoying
             * for insert but great for serach
             */
            long xNew = (thisxmin + thisxmax) / 2L;
            long yNew = (thisymin + thisymax) / 2L;
            prQuadInternal q = (prQuadInternal) rooty;
            findRange(q.NE, vectra, xmn, ymn, xmx, ymx, xNew, thisxmax, yNew, thisymax);
            
                    /* SE case:*/
            findRange(q.SE, vectra, xmn, ymn, xmx, ymx, thisxmin, xNew, thisymin, yNew);
            
                    /* NW case!*/
            findRange(q.NW, vectra, xmn, ymn, xmx, ymx, thisxmin, xNew, yNew, thisymax);
                    /* SW case :*/
            findRange(q.SW, vectra, xmn, ymn, xmx, ymx, thisxmin, xNew, thisymin, yNew);
        }
    }
       
    
    /** Getter for the root object!
     * 
     * @return the root of this tree
     */
    public prQuadTree<T>.prQuadNode rootget() {
        return this.root;
    }


    
    

}



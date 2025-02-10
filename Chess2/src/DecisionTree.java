/**
 *
 */

/** This is our basic DecisionTere class. Its "nodes" are State objects, 
 * which contain 
 * moves and children - however, for
 * print and DFS, they are rarely instantiated and used. Node counts are fields!
 * 
 * @author Josh Pandey
 * @version 8-2-2021
 *
 */
public class DecisionTree {

    private State root;
    private int totalNodesVisitedDFS;
    private boolean dfsFound;
    private long dfsEndTime;
    private long bfsNodeCount;
   
    /** DecisionTree constructor. Pass in a board name and it creates a new 
     * state as the root
     * 
     * @param boardroot             The board name to use
     */
    public DecisionTree(String boardroot) {
        State firstState = new State(boardroot);
        this.root = firstState;
    }
   
    /** Getting the root ID!
     * 
     * @return  Root id, so the "name" of the board
     */
    public String getRootId() {
        System.out.println("root ID :" + root.getId());
        return root.getId();
    }
   
    /** The starter method for print!
     * 
     * @param depth     The depth limit of the rpint
     * @param s         The print!
     */
    public void startPrint(int depth, String s) {
        System.out.println(s + " fitness: 0");
        this.recursivePrint(depth, root, null, 0);
    }
   
    /** Starter method of our DFS!
     * 
     * @param depth         The depth limit!
     * @param target        The target of the search
     */
    public void startDFS(int depth, String target) {
       
        /* Get rid of any extra spaces etc*/
       
        System.out.println("Search from " + root.getId() + " to " 
            + target + "");
        /* reset nodes visited ever ytime search is called*/
        this.totalNodesVisitedDFS = 0;
        this.dfsFound = false;
        long startTime = System.currentTimeMillis();
        String c = this.recursiveDFS(depth, root, null, 0, target, startTime);
        long endTime = System.currentTimeMillis();
        if (this.dfsFound) {
           
        }
        else {
            System.out.println("Moves to target: Unknown");
            System.out.println("Target fitness: Unknown");
            System.out.println("Nodes visited: " 
                + (this.totalNodesVisitedDFS + 1));
            System.out.println("Duration : " + (endTime - startTime));
        }
       
    }
   
   
    /** Starter method for our BFS!
     * 
     */
    public void realStartBFS() {
       
        System.out.println("Playing to win from : " + root.getId());
        Queue empt = new Queue();
        empt.enqueue(root);
        /* we now have a queue we knwo is right. now pass it recursively down*/
        long startTime = System.currentTimeMillis();
        this.bfs(empt);
        long endTime = System.currentTimeMillis();
        System.out.println("Duration : " + (endTime - startTime));
    }
   
    /** This is the main breadth-first search method. I misunderstood the Project Spec, thinking that
     *  "there's no easy way to do this recursively" was a challenge! I tried
     * for 1 full extra day to fix a stackoverflow error :)
     * 
     * This method is just passed in a queue. The end state is when the queue is empty. It was so cool to 
     * use multiple datastructures within 1 assignment. The method just
     * checks if fitness is >= 200, and if it's not, it just generates children and adds them onto the queue. 
     * Solutions like using a queue to traverse the breadth of a tree
     * are answering the "why are data structures important at all" question that is still resonant in my
     *  mind as a novice programmer - they're useful because they are tools
     * to be able to do new things that aren't pre-packaged into a programming language!
     * 
     * * NOTE: there were no instructions for when a game doesn't have 200+ fitness as a potential state 
     * (stalemate games etc) so I don't output anything. Let me know if this is right!
     * 
     * @param q         The queue (start with a queue with just the root)
     * @return          True if we found it, false if we did not
     */
    public boolean bfs(Queue q) {
       
        while (! q.isEmpty()) {
            State c = (State)q.dequeue();
            this.bfsNodeCount++;

           
            // just check its fitness
            if (c.getFitness() >= 200) {
                
                //System.out.println(" NODE COUNT : " + this.BFSNodeCount);
                System.out.println("Win state: " + c.getId());
                System.out.println("Moves to target: " 
                    + c.getMoves().substring(3));
                System.out.println("Target fitness: " + c.getFitness());
                System.out.println("Nodes visited: " + this.bfsNodeCount);
                return true;
            }
           
            else {
                String[] nextmoves = ChessFaker.getNextMoves(c.getId());
               
               
                for (String m : nextmoves) {
                    
                    int fitness = 
                        (ChessFaker.getFitnessChange(c.getId(), m)) + c.getFitness();
                    State cc = new State(ChessFaker.getNextBoard(c.getId(), m));
                    cc.appendMoves(c.getMoves() + " + " + m);
                    cc.addFitness(fitness);
                    q.enqueue(cc);
                }
            }
        }
       
        return q.isEmpty();
    }
   
    
   /** In the interest of saving memory, this DFS still doesn't actually make a tree!
    *  This is our recursive DFS method.
    * It just intakes parameters that serve as the last move, board, fitness, etc, 
    * calculating everything on the fly.
    * This was (I hope) an efficient way of running this DFS - imagine a board with 
    * multiple players and the added variable
    * of an opponent! It would be annoying to have to use up so much memory each time!
    * 
    * @param depth              The depth limit of the DFS
    * @param lastBoard          The last board (start with the root)
    * @param lastMove           The last move!
    * @param lastFitness        The last fitness of the last board state. Do this 
    *                               to add it in
    * @param target             The target board to reach
    * @param startTime          Start time, in order to calculate duration
    * @return
    */
    public String recursiveDFS(int depth, State lastBoard, String lastMove, int lastFitness, String target, long startTime) {
        depth--;
       
        /* if we find it:*/
        if (lastBoard.getId().contentEquals(target)) {

            this.dfsEndTime = System.currentTimeMillis();
            System.out.println("Moves to target: " + lastMove.substring(3));
            System.out.println("Target fitness: " + lastFitness);
            System.out.println("Nodes visited: " 
                + (this.totalNodesVisitedDFS + 1));
            System.out.println("Duration: " + (dfsEndTime - startTime) + "");
            this.dfsFound = true;
            return "";
        }
       
        if (depth == 0) {            
            return null;
        }


        String[] moveArr = ChessFaker.getNextMoves(lastBoard.getId());
       
        for (String c : moveArr) {
            if (lastMove == null) {
                lastMove = "";
            }

            totalNodesVisitedDFS++;
            // get the next baord
            String nextBoard = ChessFaker.getNextBoard(lastBoard.getId(), c);
            State nextState = new State(nextBoard);
            int currFitness = ChessFaker.getFitnessChange(lastBoard.getId(), c);
            String currMovesTotal = lastMove + " + " + c;
           
            String x = this.recursiveDFS(depth, nextState, currMovesTotal, 
                (ChessFaker.getFitnessChange(lastBoard.getId(), c) + lastFitness), target, startTime);
        }
        return "0";
    }
   
   
    /** This was so much fun to write - this was a simple pre-order traversal of a decision tree, 
     * but it was a nice recursive puzzle. This method just
     * generates the next move if we're not at the desired depth, decrementing depth each time. 
     * We check if we're on the first of a given potential of moves
     * (the original getNextMoves for the original board state) by checking if the lastMoves exists or not. 
     * If it does not, we know we're at an "original" node - we
     * print it. If the last move *does* exist, then we know that we are at a series of moves, so we print 
     * the last moves. This was how I abstracted it - if I have time
     * I'll go in and fix the obvious logical fallacy (lastMove can just be null or empty, 
     * and thus we can avoid the if/else altogether!). 
     * 
     * ** I hope this was okay - I didn't actually make a tree!
     *
     *
     * @param depth                 The desired depth of the board
     * @param lastBoard             The last State (which represents a board). Start by passing in the root itself of this tere for now.
     * @param lastMove              The last Move that we made.
     * @param lastFitness           The fitness of the last board, to add to this one.
     * @return
     */
    public String recursivePrint(int depth, State lastBoard, String lastMove, int lastFitness) {
        depth--;
       
        if (depth == 0) {
            return null;
        }
       
        String [] moveArr = ChessFaker.getNextMoves(lastBoard.getId());
       
        for (String c : moveArr) {
            /* for each strnig c in moves array*/
            if (lastMove == null) {
                System.out.print(" + " + c);
                System.out.print(" = " 
                    + ChessFaker.getNextBoard(lastBoard.getId(), c));
                System.out.println(" fitness: " 
                    + ChessFaker.getFitnessChange(lastBoard.getId(), c));

                String cc = this.recursivePrint(depth, 
                    new State(ChessFaker.getNextBoard(lastBoard.getId(), c)), c, 
                        ChessFaker.getFitnessChange(lastBoard.getId(), c));
               
               
                // if it's in the first batch...
            }
            else {
            System.out.print(" + " + lastMove);
            String conglomerateMoves = lastMove + " + " + c;
            System.out.print(" + " + c);
            System.out.print(" = " + ChessFaker.getNextBoard(lastBoard.getId(), c));
            /* actually make the move*/
            System.out.println(" fitness: " + (ChessFaker.getFitnessChange(lastBoard.getId(), c) + lastFitness));
            //System.out.println("fitness : ===>" + lastFitness);

            String cc = this.recursivePrint(depth, 
                new State(ChessFaker.getNextBoard(lastBoard.getId(), c)), 
                    conglomerateMoves, (ChessFaker.getFitnessChange(lastBoard.getId(), c)
                        + lastFitness));
            }
        }
        return null;
       
    }
   
    /** Getter method for the root
     * 
     * @return the Root
     */
    public State getRoot() {
        return this.root;
    }
}

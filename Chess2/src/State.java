/**
 * 
 */

/** This is our state class. It contains fields that are rarely used,
 * but used in BFS: a children array, an int for fitness, and 1 String of moves.
 * String[] is not used for moves; instead I chose to just append to 1 string
 * each time, in order to try to save memory (though I'm unsure if this actually 
 * saves memory - please let me know!)
 * 
 * @author Josh Pandey
 * @version 8-2-2021
 *
 */
public class State {

    /* This state class is the staet of th board at any given moment. So a staet has an ID (a string like : kasodjlaskda) and
     * children, in an array
     */
    
    private String id;
    private String[] children;
    private int fitness;
    public String moves; 
    
    /** The State constructor
     * 
     * @param boardname         The board name of the State - like "stonewall"
     */
    public State(String boardname) {
        this.id = boardname;
        children = new String[12];
        moves = "";
        this.fitness = 0;
    }
    
    /**
     * A second constructor to initialize a fitness as well
     * @param boardname         The board name
     * @param fit               The fitness
     */
    public State(String boardname, int fit) {
        this.id = boardname;
        this.fitness = fit;
    }
    
    /**
     * Get an ID
     * @return      Return the board's "name"
     */
    public String getId() {
        return this.id;
    }
    
    /**
     * Setter method for moves
     * @param s             String of moves, meant to be concatinated series of moves
     * @return              True if set
     */
    public boolean setMoves(String s ) {
        this.moves = s;
        return true;
    }
    
    /**
     * Setter method for fitness. 
     * @param z             The fitness to set to
     * @return              True if set
     */
    public boolean setFitness(int z) {
        this.fitness = z;
        return true;
    }
    
    /**
     *  Getter method for children.
     * @return              The children array
     */
    public String[] getChildren() {
        return this.children;
    }
    
    /**
     * Getter method for fitness
     * @return          The board's fitness
     */
    public int getFitness() {
        return this.fitness;
    }
    
    /**
     * Append method for moves. Just call this to "add" a move.
     * @param s         The move to append - e.g. "Ne4"
     * @return          True if appended
     */
    public boolean appendMoves(String s) {
        //this.moves = moves + " + " + s;
        StringBuilder stringbuild = new StringBuilder(this.moves);
        //stringbuild.append(" + ");
        stringbuild.append(s);
        this.moves = stringbuild.toString();
        return true;
    }
    
    /**
     * Fitness to ADD to this state.
     * @param fit               Fitness to add - the change in fitness, basically
     * @return                  True if updated fitness
     */
    public boolean addFitness(int fit) {
        this.fitness = this.fitness + fit;            // does thsi work with negatievs
        return true;
    }
    
    /**
     * Getter method for moves
     * @return          moves
     */
    public String getMoves() {
        return this.moves;
    }
    
    
}

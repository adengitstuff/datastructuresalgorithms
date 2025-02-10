/**
 * 
 */
package towerofhanoi;

/**
 * @author A
 *
 */
public class ProjectRunner {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        int numberDiscs = 5;
        if (args.length == 1)
        {
            numberDiscs = Integer.parseInt(args[0]);
        }
        HanoiSolver hanoi = new HanoiSolver(numberDiscs);
        PuzzleWindow puzzle = new PuzzleWindow(hanoi);
    }

}

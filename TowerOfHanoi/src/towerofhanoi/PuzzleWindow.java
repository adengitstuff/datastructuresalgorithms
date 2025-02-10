/**
 * 
 */
package towerofhanoi;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import CS2114.Button;
import CS2114.Shape;
import CS2114.Window;
import CS2114.WindowSide;

/** The PuzzleWindow class is the 'observer' in this program - 
 * it's the 'front-end', or what the user will see. We use windows and shapes
 * in order to add pixels to a screen which will represent the movement among
 * our LinkedStack towers. The PuzzleWindow will then read the changes which
 * have ****already**** happened, and then move the shapes onto the corresponding tower.
 * 
 * It's important to note that this puzzlewindow is not changing anything in any
 * Tower or Stack - it is not removing or adding any items. It's simply changing the 
 * pixels on the screen such that they represent movements in linked stacks.
 * 
 * @author Aden Pandey 
 *
 *
 */
public class PuzzleWindow implements Observer {
    
    /** Edit: switched constructor position from after moveDisk/move to in front **/
    
    private HanoiSolver game;
    private Shape leftTower;
    private Shape midTower;
    private Shape rightTower;
    private Window window;
    public static final int WIDTH_FACTOR = 5;
    public static final int DISK_GAP = 5;
    public static final int DISK_HEIGHT = 7;

    /** Pause the execution of a current thread in 500
     * milliseconds. This is mainly used for pacing, rather than 
     * freeing up resources.
     * 
     */
    private void sleep() {
        try {
            Thread.sleep(500);
        }
        catch (Exception e) {
        }
    }


    /** clickedSolve will be called when a user clicks a 
     * button, and will start the runtime of our program!
     * 
     * @param button        - The button to represent 'Solve'.
     */
    public void clickedSolve(Button button) {
        button.disable();
        new Thread() {
            public void run() {
                game.solve();
            }
        }.start();
    }
    
    /** The PuzzleWindow will create and manage all of the right
     * shapes, as well as instantiate a window. LinkedStack Towers
     * are added in as long, rectangular shapes, and disks are added
     * finally onto the window. PuzzleWindow will intake a game parameter
     * (a HanoiSolver), which is the 'back-end' of the program. It will
     * simply move pixels such that the shapes represented are 'moved'.
     * 
     * @param game      The HanoiSolver class, which contains a solution
     *                  for the Towers of Hanoi game!
     */
    @SuppressWarnings("deprecation")
    public PuzzleWindow(HanoiSolver game)
    {
        this.game = game;
        game.addObserver((Observer)this);
        
        window = new Window("Tower of Hanoi");
        int graphHeight = window.getGraphPanelHeight();
        
        leftTower = new Shape(100, graphHeight - 150, 5, 100, Color.LIGHT_GRAY);
        
        midTower = new Shape(leftTower.getX() + 150, graphHeight - 150, 5, 100, Color.LIGHT_GRAY);
        
        rightTower = new Shape(midTower.getX() + 150, graphHeight - 150, 5, 100, Color.LIGHT_GRAY);
        
        Shape towerBaseL = new Shape(leftTower.getX() - 20, graphHeight - 50, 45, 5, Color.LIGHT_GRAY);
        
        Shape towerBaseM = new Shape(midTower.getX() - 20, graphHeight - 50, 45, 5, Color.LIGHT_GRAY);
        
        Shape towerBaseR = new Shape(rightTower.getX() - 20, graphHeight - 50, 45, 5, Color.LIGHT_GRAY);
        
        for (int i = game.disks(); i > 0; i--)
        {
            /** should be new Disk(i * 5)*/
            Disk newDisc = new Disk(i * 5);
            window.addShape(newDisc);
            game.getTower(Position.RIGHT).push(newDisc);
            moveDisk(Position.RIGHT);
        }
        
        window.addShape(leftTower);
        window.addShape(midTower);
        window.addShape(rightTower);
        window.addShape(towerBaseL);
        window.addShape(towerBaseM);
        window.addShape(towerBaseR);
        
        Button solveButton = new Button("Solve");
        window.addButton(solveButton, WindowSide.SOUTH);
        solveButton.onClick(this, "clickedSolve");
        /**
         * Now:         initialize shape fields for the towers!
         * set them to getGraphX
         */
    }

    /** MoveDisk simply instantiates representative  
     * disks and poles of the last movement to occur in a
     * Tower, and then calls moveTo() on those representative
     * variables.
     * @param position      - The Position of a Tower (right, middle, left).
     *                      Note that this is specified by an enum. So 
     *                      the param would be, e.g., Position.RIGHT.
     */
    public void moveDisk(Position position)
    {
        //Disk currentDisk;
        //Position px = position;
        Shape currentPole = null;
        //Disk currentDisk = new Disk(game.getTower(position).peek());
        
        Disk currentDisk = null;
        
        currentDisk = game.getTower(position).peek();      
        
        switch(position)
        {
           case LEFT:
                currentPole = leftTower;
                break;
            case RIGHT:
                currentPole = rightTower;
                break;
            case MIDDLE:
                currentPole = midTower;
                break;
            default:
                currentPole = midTower;
                break;
        }
        int startHeight = (currentPole.getY() + (currentPole.getHeight() - 4));
        int discHeight = game.getTower(position).size() * 3;
        
        currentDisk.moveTo(currentPole.getX() - ((currentDisk.getWidth() / 2) - 2), startHeight - discHeight);
        
        
        
    }

    /** Because PuzzleWindow is an observer, it must update the window
     * upon observing a game. We set an object as changed and notify
     * observers in HanoiSolver's move() method - this reads that move()
     * method, and then ensures the Position class is called. 
     */
    public void update(@SuppressWarnings("deprecation") Observable o, Object arg)
    {
      
        if (arg.getClass() == Position.class)
        {
            Position positi = (Position)arg;
            moveDisk(positi);
            this.sleep();
        }
        
        
        
    }
    
   
}

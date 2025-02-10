// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Josh Pandey (josh1)

package spacecolonies;

import java.awt.Color;
import CS2114.Button;
import CS2114.CircleShape;
import CS2114.SquareShape;
import CS2114.TextShape;
import CS2114.Window;
import CS2114.WindowSide;
import list.AList;

/** SpaceWindow is the GUI that will create the visual
 * representations of our backend objects.
 * @author Aden P
 *
 *
 */
public class SpaceWindow {

    private Window window;
    private ColonyCalculator calculator;
    private Button accept;
    //private Button reject;
    private AList<CircleShape> personCircles;
    public static final int CIRCLE_SIZE = 10;
    public ArrayQueue<Person> Q;
    private Planet[] planets;
    private TextShape nextPersonText;
    private String topText;
    Color p1Color;
    Color p2Color;
    Color p3Color;
    
    /** Create and instantiate our SpaceWindow!
     * 
     * @param calc
     */
    public SpaceWindow(ColonyCalculator calc) {

        ArrayQueue<Person> tempQ = calc.getQueue();
        Planet[] tempPlanet = calc.getPlanets();
        
        calculator = new ColonyCalculator(tempQ, tempPlanet);
        
        window = new Window("Space Colony Placement");
        accept = new Button("Accept");
        Button reject = new Button("Reject");
        
        
        Q = calculator.getQueue();
        
        //appQ.dequeue();
        /// ***** maybe put all the below in a new method: "updateWindow"?
        
        window.addButton(accept, WindowSide.SOUTH);
        window.addButton(reject, WindowSide.SOUTH);
        
                
        planets = calculator.getPlanets();
        //appQ.dequeue();
        this.updateScreen();
        
        // the reject button is always enabled!
        accept.onClick(this, "clickedAccept");
        reject.onClick(this, "clickedReject");                
        // *** idk if that's right
        
    }
    
    /** Update our screen.
     * 
     */
    public void updateScreen() {
        
        if (! (Q.isEmpty())) {
        topText = Q.getFront().toString();
        nextPersonText = new TextShape(10, 10, topText);
        nextPersonText.setBackgroundColor(Color.WHITE);
        
        p1Color = new Color(173, 147, 189);
        p2Color = new Color(102, 176, 174);
        p3Color = new Color(112, 148, 180);
        SquareShape p1 = new SquareShape(55, 165, 100, p1Color);
        TextShape textp1 = new TextShape(p1.getX(), p1.getY() + p1.getWidth() + 20, planets[0].toString());
        SquareShape p2 = new SquareShape(p1.getX() + p1.getWidth() * 2, 165, 100, p2Color);
        //TextShape textp2 = new TextShape(p2.getX(), p2.getY() + p2.getWidth(), skills.toString());
        SquareShape p3 = new SquareShape(p2.getX() + p2.getWidth() * 2, 165, 100, p3Color);
        
        //window.addShape(circle);
        window.addShape(p1);
        window.addShape(p2);
        window.addShape(p3);
        window.addShape(textp1);
        window.addShape(nextPersonText);
        
        /**int circleSpace = 55;
        for (int i = 0; i < appQ.getSize(); i++) {
            CircleShape circley = new CircleShape(circleSpace, 55, Color.BLUE);
            circleSpace = circleSpace + circley.getWidth() + 15;
            window.addShape(circley);
        }**/
        this.addCircles(Q.getSize());
        }
        else {
            
        }
    }
    

    /** A helper method to add a number of circles onto the 
     * screen.
     * @param num       The number of people in our Queue.
     */
    public void addCircles(int num) {
        // create a new arrayqueue
        if (! Q.isEmpty()) {
            ArrayQueue<Person> tempQ = Q;
            Color planetColor = null;
            Color noPreference = new Color(165, 209, 232);
            int circleSpace = 55;
            Person next;
            
            //************* doesn't account for less than 3 planets
            String name1 = planets[0].getName();
            String name2 = planets[1].getName();
            String name3 = planets[2].getName();
            
            
            for (int i = 0; i < num; i++) {
                Person nextP = tempQ.getFront();
                
                if (nextP != null) {
                String prefP = nextP.getPlanetName();
                
                if (prefP.equals(planets[0].getName())) {
                    planetColor = p1Color;
                }
                else if (prefP.equals(planets[1].getName())) {
                    planetColor = p2Color;
                }
                else if (prefP.equals(planets[2].getName())) {
                    planetColor = p3Color;
                }
                else {
                    // the no preference color:
                    planetColor = noPreference;
                }
                
                CircleShape circleX = new CircleShape(circleSpace, 55, planetColor);
                circleSpace = circleSpace + circleX.getWidth() + 15;
                window.addShape(circleX);
                // **** this should work for BOTH, (tempQ and appq), but we'll try tempQ first:                
                tempQ.dequeue();
            }            
            }
            
            //String nextString = appQ.getFront().toString();
            //topText = new TextShape(10, 10, nextString);
            
        }
        // getFront(), getPlanetPreference
        // switch statement: if planetPreference.getName() equals planets[0]
        //              then color = p1Color
    }
    
    /** Our clickedAccept method, which should pass a person into
     * a planet, and then update the screen.
     * @param b The button the client will press.
     */
    public void clickedAccept(Button b) {
        
        // first check to ensure that there's a valid, nonempty queue of
        // riders
         
        if (! (Q.isEmpty())) {
            
            // ********* this is a try to do: " the accept button should be disabled if the
            // coloner can't beaccepted:                
                calculator.accept();
                window.removeAllShapes();
                topText = Q.getFront().toString();
                window.addShape(new TextShape(10, 10, topText));
                //this.updateScreen();
        }
            else {
                window.removeAllShapes();
                b.disable();
            }
            // window should update to represent this
        
    }
    
    /** Set the next text to display.
     * 
     * @return String of text to display.
     */
    public String nextDisplayText() { 
        String textDisplay = Q.getFront().toString();
        return textDisplay;
    }
    
    /** Button representing the client clicked "reject".
     * Should send a Person object to a "Reject bus", 
     * and update the screen.
     * @param b The button client will press.
     */
    public void clickedReject(Button b) {
        calculator.reject();
        accept.enable();
        this.updateScreen();
        
        // visuals updated to represent the person's absence
    }
}

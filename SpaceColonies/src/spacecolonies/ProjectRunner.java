// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Josh Pandey (josh1)

package spacecolonies;

import java.io.FileNotFoundException;
import java.text.ParseException;

/** Our ProjectRunner will run the project!
 * @author Aden P
 *
 *
 */
public class ProjectRunner {

    /** Create colonyReader and calculator objects, to create our SpaceWindow. If argument
     * inputs are provided, use those, and if not, use the default "input" and "planets" files.
     * @param args The file names to try to use.
     * @throws ParseException 
     * @throws SpaceColonyDataException 
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException, SpaceColonyDataException, ParseException {
        if (args.length == 2) {
            String input1 = args[0];
            String input2 = args[1];
            ColonyReader colonyReader = new ColonyReader(input1, input2);
            ColonyCalculator calc = new ColonyCalculator(colonyReader.readQueueFile(input2), colonyReader.readPlanetFile(input2));
            SpaceWindow window = new SpaceWindow(calc);
        }
        else {
            ColonyReader colonyReaderX = new ColonyReader("input.txt", "planets.txt");
            ColonyCalculator calc = new ColonyCalculator(colonyReaderX.readQueueFile("input.txt"), colonyReaderX.readPlanetFile("planets.txt"));
            SpaceWindow window = new SpaceWindow(calc);
        }

    }

}

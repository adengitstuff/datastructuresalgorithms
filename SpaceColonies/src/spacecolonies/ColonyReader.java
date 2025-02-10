/**
 * 
 */
package spacecolonies;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

/** ColonyReader is responsible for reading the inputs provided - 
 * raw text files are provided, and then queues and planet arrays are made.
 * 
 * @author Aden Pandey
 *
 */
public class ColonyReader {
    
    private Planet[] planets;
    private ArrayQueue<Person> queue;

    /** Construct our ColonyReader, setting our field variables
     * to the provided inputs.
     * @param applicant     The String of the applicant.
     * @param planet        The Planet of the applicant.
     * @throws SpaceColonyDataException
     * @throws FileNotFoundException
     * @throws ParseException
     */
    public ColonyReader(String applicant, String planet) throws SpaceColonyDataException, FileNotFoundException, ParseException {
        this.readQueueFile(applicant);
        this.readPlanetFile(planet);
        //SpaceWindow space = new SpaceWindow(new ColonyCalculator(queue, planets));
    }
    
    /** This method is the "backbone" to the class - this will read
     * an input file and construct a planet array with the provided
     * planets.
     * @param fileName      The filename to scan. ".txt" is required for text files.
     * @return              An array of planets the file provides, up to 3 planets.
     * @throws FileNotFoundException
     * @throws ParseException
     * @throws SpaceColonyDataException
     */
    public Planet[] readPlanetFile(String fileName) throws FileNotFoundException, ParseException, SpaceColonyDataException {
        Planet[] newPlanets = new Planet[ColonyCalculator.NUM_PLANETS];
        int index = 0;
        
        @SuppressWarnings("resource")
        Scanner file = new Scanner(new File(fileName));
        
        // if the filename was correct...
        
        while (file.hasNextLine() && index < 3) {
            String fullLine = file.nextLine();
            
            String[] values = fullLine.split(", *");
            
            if (values.length != 5) {
                // idk what params it takes
                throw new ParseException(fullLine, 0);
            }
            else {
                String pName = values[0];
                int agri = Integer.valueOf(values[1]);
                int medi = Integer.valueOf(values[2]);
                int tech = Integer.valueOf(values[3]);
                int popCap = Integer.valueOf(values[4]);
                
                if (! (this.isInSkillRange(agri, medi, tech))) {
                    throw new SpaceColonyDataException("skills not in range!");
                }
                else {
                    Planet tempPlan = new Planet(pName, agri, medi, tech, popCap);
                    newPlanets[index] = tempPlan;
                    index++;
                }
            }
        }
        
        planets = newPlanets;
        return planets;
    }
    
    /** Our readQueueFile method will do the same thing as the above method,
     * but will do it for Queues. A string of applicants is provided in, and a queue is made and
     * constructed. This queue is what will be manipulated later and read from.
     * 
     * @param qFile     The file name of the string, with ".txt" at the end (for text files).
     * @return          The ArrayQueue of person objects from the file.
     * @throws FileNotFoundException
     * @throws ParseException
     * @throws SpaceColonyDataException
     */
    public ArrayQueue<Person> readQueueFile(String qFile) throws FileNotFoundException, ParseException, SpaceColonyDataException {
        
        ArrayQueue<Person> people = new ArrayQueue<Person>();
        
        Scanner inFile = new Scanner(new File(qFile));
        
        while (inFile.hasNextLine()) {
            
            String fullLine = inFile.nextLine();
            String[] values = fullLine.split(", *");
            String planetPref = " ";
            //if (values.length != 5) {
            //    throw new ParseException(fullLine, 0);
            //}
            //else {
                String pName = values[0];
                int a = Integer.valueOf(values[1]);
                int m = Integer.valueOf(values[2]);
                int t = Integer.valueOf(values[3]);
                
                //******* NOTE: this can be "if values.length == 4 too.
                // this is also a bad heuristic, though.
                
                if (values.length == 5) {
                    planetPref = values[4];
                }
                if (! (this.isInSkillRange(a, m, t))) {
                    throw new SpaceColonyDataException("A person's skills must be between 1 and 5!");
                }
                
                Person newPerson = new Person(pName, a, m, t, planetPref);
                
                people.enqueue(newPerson);
            //}
        }
        
        return people;
    }
    
    
    /** We avoid using the tempting abstraction: x * y * z < 0 || x > 5, etc simply because
     *  this wouldn't account for harder edge cases (2 negative values, for example).
     * @param x     The 1st skill of the person.
     * @param y     The 2nd skill of the person.
     * @param z     The 3rd skill of the person.
     * @return true if inSkillRange
     */
    
    private boolean isInSkillRange(int x, int y, int z) {

        // originally tried x * y * z < 0 || x > 5 || y > 5 || z > 5)
        //                          return false
        // but this doesn't account for 2 values being negative
        
        if ((x >= ColonyCalculator.MIN_SKILL_LEVEL && x <= ColonyCalculator.MAX_SKILL_LEVEL) && 
            (y >= ColonyCalculator.MIN_SKILL_LEVEL && y <= ColonyCalculator.MAX_SKILL_LEVEL) && 
            (z >= ColonyCalculator.MIN_SKILL_LEVEL && z <= ColonyCalculator.MAX_SKILL_LEVEL)) {
            return true;
        }
        
        else {
            return false;
        }
    }
}

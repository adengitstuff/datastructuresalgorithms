// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Josh Pandey (josh1)
package spacecolonies;

import java.util.Arrays;
import list.AList;
import queue.EmptyQueueException;

/** ColonyCalculator will do most of the back-end calculation of our 
 * queues and array functions, adding people into planets based on minimum
 * thresholds and availability. 
 * @author Aden Pandey 
 *
 */
public class ColonyCalculator {

    public static final int NUM_PLANETS = 3;
    public static final int MIN_SKILL_LEVEL = 1;
    public static final int MAX_SKILL_LEVEL = 5;
    
    private ArrayQueue<Person> appQ;
    private AList<Person> rejectBus;
    // removed private Static Planet[] 
    private Planet[] planets;
    
    /** Construct a ColonyCalculator object, passing in an ArrayQueue and a planet array.
     * 
     * @param people    The arrayqueue of people.
     * @param planets   The array of planets.
     */
    public ColonyCalculator(ArrayQueue<Person> people, Planet[] planets) {
        
        if (! (people == null)) {
            
            appQ = people;
            this.planets = planets;
            planets = new Planet[NUM_PLANETS + 1];
            rejectBus = new AList<Person>();
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    
    /** Return our ArrayQueue of people.
     * 
     * @return The ArrayQueue of person objects.
     */
    public ArrayQueue<Person> getQueue() {
        
        return this.appQ;
    }
    
    /** Return our array of planets.
     * 
     * @return  Planet object array.
     */
    
    public Planet[] getPlanets() {
        return ColonyCalculator.planets;
    }
    
    /** This gets the planet for a person, using their preferred
     * planet first, checking if they meet the eligibility requirements,
     * making sure the planet isn't full, and then passing it in.
     * @param nextPerson        The person to add in.
     * @return                  The planet for the person to be accepted into.
     */
    public Planet getPlanetForPerson(Person nextPerson) {
        // first, assure it's not empty.
        Planet temp = null;
        
        if (nextPerson == null) {
            //return null;
            temp = null;
        }
        else {
            if (! appQ.isEmpty()) {
                
                String planetName = nextPerson.getPlanetName();
                
                //******* was nextPerson.getPlanetName() != ""
                if (planetName != "") {
                    // check it's not full
                    /**
                    int planetI = this.getPlanetIndex(nextPerson.getPlanetName());
                    
                    //*** planetI - 1, since it returns 0 if null. and 1, 2, 3, etc.
                    Planet needPlanet = planets[planetI - 1];**/
                    
                    Planet prefPlanet = this.getPreferredPlanet(nextPerson);
                    
                    if (this.isEligible(nextPerson, prefPlanet)) {
                        temp = prefPlanet;
                    }
                    
                    else {
                        temp = null;
                    }
                }
                else {
                    //they have no preferred planet, so try to place
                    // in order of availability:
                    Planet[] available = this.getMostAvailablePlanet();
                    
                    for (int i = 0; i < 3; i++) {
                        if (this.isEligible(nextPerson, available[i])) {
                            temp = available[i];
                        }
                    }
                    
                }
            }
        else {
            throw new EmptyQueueException();
        }
        }
        
        return temp;
        
    }
    
    /** This calculates if a person is eligible for a planet. The planet
     * should be not full, and the PErson should meet the minimum skill 
     * requirements. In this case, we use "isBelow" in Skills to make sure
     * the planet is "above or lower" than the person's Skill fields.
     * @param person    The person to add in.
     * @param planet    The planet the person will be added into.
     * @return
     */
    public boolean isEligible(Person person, Planet planet) {
        return (planet.getSkills().isBelow(person.getSkills()) && (!planet.isFull())); 
        /**
        if (planet.isFull()) {
            return false;
        }
        else if (planet.getSkills().isBelow(person.getSkills())) {
            return true;
        }
        else {
            return false;
        }**/
    }
    
    /** This will accept a person into a planet, returning true if they are
     * accepted in!
     * @return  True if a person is accepted into their planet.
     */
    public boolean accept() {
        if (! (appQ.isEmpty())) {
            Person next = appQ.dequeue();
            Planet nextPlanet = this.getPlanetForPerson(next);
            
            //********* added nullCheck
            if (next != null && nextPlanet != null) {
                nextPlanet.addPerson(next);
            // dequeue from appQ;
                //appQ.dequeue();
            }
            return true;
        }
        else {
            //throw new EmptyQueueException();
            return false;
        }
    }
    
    /** This "Rejects" an applicant - it adds a person
     * to the "Reject bus", deqeueing the Queue in the process.
     */
    public void reject() {
        if (! appQ.isEmpty()) {
            Person toTheBus = appQ.dequeue();
            if (toTheBus != null) {
            rejectBus.add(toTheBus);
            }
        }
    }
    
    /** Return the Alist of people
     * rejected from planets.
     * @return AList of Person objects.
     */
    public AList<Person> getRejectBus() {
        return rejectBus;
    }
    
    /** Gets the preferred planet of a person.
     * 
     * @param nextPerson    The person to get the name for.
     * @return        The planet object the person prefers.
     */
    public Planet getPreferredPlanet(Person nextPerson) {
        
        int planetI = this.getPlanetIndex(nextPerson.getPlanetName());
        
        //*** planetI - 1, since it returns 0 if null. and 1, 2, 3, etc.
        Planet needPlanet = planets[planetI - 1];
        
        return needPlanet;
    }
    
    /** Gets the most available planet, using
     * the comparable compareTo override in Planet.
     * @return  Planet array sorted by availability.
     */
    public Planet[] getMostAvailablePlanet() {
        Planet[] tempP = planets;
        Arrays.sort(tempP);
        return tempP;        
    }
    
    /** Returns a planet by a number passed in (1, 
     * 2, or 3).
     * 
     * @param planet    The number representing the planet.
     * @return
     */
    public Planet planetByNumber(int planet) {
        if (planet < 4 && planet > 0) {
            return planets[planet - 1];
        }
        else {
            return null;
        }
    }
    
    /** Get a planet by its index (The string 
     * representation of it).
     * @param planet    The planet's String name.
     * @return          The planet object the string represents.
     */
    public int getPlanetIndex(String planet) {
        int planetIndex = 0;
        
        for (int i = 0; i < NUM_PLANETS; i++) {
            if (planets[i].getName().equals(planet)) {
                planetIndex = i + 1;
            }            
        }
        
        return planetIndex;
    }
    
    
}

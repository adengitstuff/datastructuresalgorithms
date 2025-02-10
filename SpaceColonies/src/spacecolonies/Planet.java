// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Josh Pandey (josh1)

package spacecolonies;

/** Our Planets class will contain fields for the "threshold" for
 * Planets, as well as population data and availability.
 * @author Aden P
 *
 *
 */
public class Planet implements Comparable<Planet> {
    
    private String name;
    private Skills minSkills;
    private Person[] population;
    private int popNumber;
    private int popCap;
    
    /** Instantiate our fields and create the array that will
     * hold our planet's population.
     * @param pName The name of the planet!
     * @param pAgri The minimum agriculture score.
     * @param pMedi The minimum medicine score.
     * @param pTech The minimum tech score.
     * @param popCap The population cap of the planet!
     */
    public Planet(String pName, int pAgri, int pMedi, int pTech, int popCap)
    {
        this.name = pName;
        this.popCap = popCap;
        this.minSkills = new Skills(pAgri, pMedi, pTech);
        // ************* this might need to be popCap - 1. the length is popcap, so...
        this.population = new Person[popCap];
    }
    
    /** Set the name of our planet!
     * 
     * @param setTheName The name to set the planet's name to.
     */
    public void setName(String setTheName) {      
        this.name = setTheName;
    }
    
    /** Returns the planet's name.
     * 
     * @return The name of our planet.
     */
    public String getName() {
        return this.name;
    }
    
    /** Return our skills for the planet.
     * 
     * @return Skills object with the minimum skills for the planet.
     */
    public Skills getSkills() {
        return this.minSkills;
    }
    
    /** Returns the population of the planet - an array
     * of Person objects.
     * @return Array of person objects, that represent the people "in"
     *             the planet.
     */
    public Person[] getPopulation() {
        return population;
    }
    
    /** Return our population size.
     * 
     * @return The population size.
     */
    public int getPopulationSize() {
        return popNumber;
    }
    
    /** Return the capacity this planet can hold.
     * 
     * @return   Capacity of the planet
     */
    public int getCapacity() {
        return popCap;
    }
    
    /** Return the availablity of the planet.
     * 
     * @return Available spots on planet
     */
    public int getAvailability() {
        return (popCap - popNumber);
    }
    
    /** Return if planet is full or not.
     * 
     * @return  True if full. False if not.
     */
    public boolean isFull() {
        return (popNumber == popCap);
    }
    
    //public boolean addPerson(Person newPerson) {
        
        // 1: check if planet has spce
        
        // 2: check if the person is qualified
            // get the planet's minimum requirements
            // get the person's skills
            // use the method in Skills. if person is below in any
            // return false
        
        // 3: increment size++
        
        //popNumber++;
    //}
    
    /** The toString method will convert this planet into a string
     * using stringbuilder.
     * 
     * @return String representing the planet's information.
     */
    public String toString() {
        
        StringBuilder stringbuilder = new StringBuilder();
        
        stringbuilder.append(this.getName() + ", ");
        stringbuilder.append("population " + this.popNumber);
        stringbuilder.append(" (cap:" + this.popCap);
        stringbuilder.append(", Requires: ");
        stringbuilder.append("A >= " + this.getSkills().getAgriculture());
        stringbuilder.append(", M >= " + this.getSkills().getMedicine());
        stringbuilder.append(", T >= " + this.getSkills().getTechnology());
        
        // do i close a stringbuilde or something? like i'd close a scanner?
        
        return stringbuilder.toString();
    }
    
    /** The equals method will compare two planet objects
     * and return true if their fields are equal.
     * 
     * @return      True if fields are equal.
     */
    public boolean equals(Object obj) {
        
        if (obj == null) {
            return false;
        }
        
        else if (obj.getClass() != Planet.class) {
            return false;
        }
        else {
        return (this.getSkills().equals(((Planet)obj).getSkills()) && 
                this.getName().equals(((Planet)obj).getName()) && 
                this.getCapacity() == ((Planet)obj).getCapacity() && 
                this.getPopulationSize() == ((Planet)obj).getPopulationSize());
        }
    }
    
    /** Planet implements comparable, and therein we can compare
     * two planets based on availability. This will be used later
     * to sort planets by availability, in the case a Person object
     * has no "preference" for a planet.
     * 
     * @return An int value of the comparison in availability.
     */
    public int compareTo(Planet other) { 
        
        // I have no idea what these instructions are saying. 
        
        return (this.getAvailability() - other.getAvailability());
    }
    
    
    /** AddPerson method will add a person to a planet!
     * 
     * @param nextPerson        The person to add in.
     * @return                  True if added in successfully.
     */
    public boolean addPerson(Person nextPerson) {
        
        //*************** we didn't check if applicant is qualified
        // but maybe a double check is in order?
        if (! (this.isFull())) {
            population[popNumber] = nextPerson;
            popNumber++;
            return true;
        }
        else {
            
            return false;
        }
    }

}

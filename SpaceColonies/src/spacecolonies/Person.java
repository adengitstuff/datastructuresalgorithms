/**
 * 
 */
package spacecolonies;

/** The Person class, for Project 4. The first Project
 * I've ever started early.
 * 
 * @author Aden P
 * @version 2019.11.3
 *
 */
public class Person {
    
    private String name;
    private Skills skills;
    private String planetPreference;
    
    public Person(String name, int agri, int medi, int tech, String planet) {
        
        this.name = name;
        this.planetPreference = planet;
        skills = new Skills(agri, medi, tech);
    }
    
    public String getName() {        
        return this.name;
    }
    
    public Skills getSkills() {
        return skills;
    }
    
    public String getPlanetName()
    {
        return this.planetPreference;
    }
    
    public String toString() {
        
        StringBuilder stringbuilder = new StringBuilder();
        
        stringbuilder.append(name + " ");
        stringbuilder.append("A:" + skills.getAgriculture() + " ");
        stringbuilder.append("M:" + skills.getMedicine() + " ");
        stringbuilder.append("T:" + skills.getTechnology() + " ");
        
        if (this.getPlanetName().length() > 0) {
            stringbuilder.append("Wants: " + this.getPlanetName());
        }
        
        //**** - returned a Stringbuilder as a toString. Not sure
        //      if this is right!
        
        return stringbuilder.toString();
            
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (obj.getClass() != Person.class) {
            return false;
        }
        else if (obj == this) {
            return true;
        }
        //**** note: casts are probably not necessary here.
        else {
            return (this.getName().equals(((Person)obj).getName()) &&
                    this.getPlanetName().equals(((Person)obj).getPlanetName()) &&
                    this.getSkills().equals(((Person)obj).getSkills()));
        }
    }

}

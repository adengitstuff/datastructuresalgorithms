// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Josh Pandey (josh1)
package spacecolonies;

/** The skills class will represent the "skills" of colony applicants -
 * their scores in various fields. This is a relatively simple class
 * that simply returns values and performs simple comparisons.
 * @author A
 *
 */
public class Skills {

    private int agriculture;
    private int medicine;
    private int technology;
    
    /** Instantiate our Skills class.
     * 
     * @param ag        The agriculture score.  
     * @param med       The medicine score. 
     * @param tech      The tech score.
     */
    public Skills(int ag, int med, int tech) {
        
        this.agriculture = ag;
        this.medicine = med;
        this.technology = tech;
    }
    
    /** If colony applicant is below the threshold for a planet,
     * then this method should be used.
     * @param otherSkill    The other Skills object to compare.
     * @return              True if this "is below"!
     */
    public boolean isBelow(Skills otherSkill) {
        
        return (this.agriculture <= otherSkill.getAgriculture() && 
            this.medicine <= otherSkill.getMedicine() && 
            this.technology <= otherSkill.getTechnology());       
        
    }
    
    /** Return agriculture score.
     * 
     * @return  Agriculture score.
     */
    public int getAgriculture() {
        return this.agriculture;
    }
    
    /** Return medicine score.
     * 
     * @return  Medicine score.
     */
    public int getMedicine() {
        return this.medicine;
    }
    
    /** Return technology score.
     * 
     * @return Technology score.
     */
    public int getTechnology() {
        return this.technology;
    }
    
    /** This is a simple equals method for our
     * Skills object.
     * 
     * @return true if 2 Skills objects have the same scores.
     */
    public boolean equals(Object obj) {
                
        if (this == obj)
        {
            return true;
        }
        else if (this.getClass() != obj.getClass()) {
            return false;
        }
        else {
            return (this.technology == ((Skills)obj).getTechnology() && 
                this.medicine == ((Skills)obj).getMedicine() && 
                this.agriculture == ((Skills)obj).getAgriculture());
        }
    }
    
    
    
    
}

import java.util.ArrayList;

/**
 * 
 */

/**
 * @author A
 *
 */
public class nameEntry implements Hashable<nameEntry>{

    String key = null;
    ArrayList<Long> locations;
    
    public nameEntry(String name, Long offset) {
        this.key = name;
        locations = new ArrayList<Long>();
        locations.add(offset);
    }
    
    public int Hash() {
        int hashValue = key.length();

        for (int i = 0; i < key.length(); i++) {
            
            hashValue = ((hashValue << 5) ^ (hashValue >> 27)) ^ key.charAt(i);
        }

        return ( hashValue & 0x0FFFFFFF );
    }
    
    public String key() {
        return this.key;
    }
    
    public ArrayList<Long> locations() {
        return this.locations;
    }
    

    /** Add a location to the arraylist of long vals of offsets.
     *
     * @param offset        Offset to be added
     * @return
     */
    public boolean addLocation(Long offset) {

        if (offset != null) {
            return (this.locations.add(offset));
        }
        return false;
        // try block? what if it's not added in, or a null object?
    }

    
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            return false;
        }
        nameEntry o = (nameEntry) other;
        
        return (o.key().contentEquals(this.key()));
    }
    
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            return false;
        }
    }

    /** Tostring method to display our nameEntry
     *
     * @return  nameEntry as a string
     */
    public String toString() {
        return ( "[" + this.key + ", " + this.locations.toString() + "]" );
    }
}

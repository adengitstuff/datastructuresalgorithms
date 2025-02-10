jgu
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;calling out father, prepaer
import java.util.LinkedList;

/** This is our main hashTable class. This is super important in all coding interviews
 * so I'm happy to have projects that work on this - thank you!
 * @author Josh Pandey
 * @date 03.24.2021
 */

public class hashTable<T extends Hashable<T>> {

    private ArrayList<LinkedList<T> > table;
    private Integer numElements = 0;
    private Double loadLimit = 0.7;
    private final Integer defaultTableSize = 256;
    private int sizie;
    private int maxSlots;
    private nameEntry nameClass = new nameEntry("tst", 0L);

    /** Constructor, specifically setting to default sizes if null is passed in.hhjujijiif thsi is to end in fiejkif this is to end ni fire, then we shoul
     *
     * @param size          size of table initially (it can resize)
     * @param idLimit       load limit
     */
    public hashTable(Integer size, Double idLimit) {    // <----- originally had a double idLim as well


        if (size == null) {
            table = new ArrayList<LinkedList<T>>(defaultTableSize);
            this.sizie = defaultTableSize;
        }
        else {
            table = new ArrayList<LinkedList<T>>(size);
            this.sizie = size;
        }
        if (idLimit == null) {
            this.loadLimit = 0.7;
        }
        else {
            this.loadLimit = idLimit;
        }
        this.maxSlots = 4;
        /* Initialize to null, because ArrayLists are ArrayLists
         */
        for (int i = 0; i < this.sizie; i++) {
            table.add(i, null);
        }

    }

    /** Simple getter.
     *
     * @return  our table - an ArrayList of Linked Lists
     */
    public ArrayList<LinkedList<T>> table() {
        return this.table;
    }

    /** Simple setter
     *
     * @return the size of our table
     */
    public int sizie() {
        return this.sizie;
    }

    /** Simple overload check - check if our load limit is passed.
     *
     * @return      true if we are past our load limit
     */
    public boolean overloadCheck() {
        return ( (this.numElements/this.sizie()) >= this.loadLimit);
    }

    /** Simple getter
     *
     * @return  the load limit
     */
    public double loadLimit() {
        return this.loadLimit;
    }

    /** Resize function simply creates a new function, and then iterates through our
     * table, inserting each entry. This could have been done recursively in insert,
     * but I thought that in any real/professional setting, I would earnestly make
     * a separate function. I'm inexperienced, so I'm not sure if this is the best option.
     *
     * @throws IOException
     */
    public void resize() throws IOException {

        try {
            hashTable<T> newt = new hashTable<T>(this.sizie() * 2, this.loadLimit());

            for (LinkedList<T> link : this.table()) {
                // get each entry and put it in
                if (link != null) {
                    for (T n : link) {
                        newt.insert2(n);
                    }
                }

            }
            this.sizie = this.sizie * 2;
           // newt.displayx();
            this.table = newt.table();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** This is our main insert method! This is the meat of indexing and inserting entries. We check
     * if we're overloaded from the very beginning, resizing if necessary. Then we just go to the
     * index that this object being passed in would be in. If it's null, we create a new entry; if it's
     * not, we iterate through the linked list at that index, comparing keys and making sure to insert it in
     * the right place.
     *
     * @param elem      Element to be inserted
     * @return          TRue if inserted
     * @throws IOException
     */
    public boolean insert2(T elem) throws IOException {

        if (this.overloadCheck()){
           this.resize();
        }

        nameEntry newn = (nameEntry) elem;
        int hashn = newn.Hash();


        int tindex = hashn % table.size();

        LinkedList<T> linkt = table.get(tindex);
        if (elem == null) {
            return false;
        }
        /* If we're at a null spot, then it's an 'empty' slot. Just put it in.

         */
        if (linkt == null) {
            LinkedList<T> newlist = new LinkedList<T>();
            newlist.add(elem);
            table.set(tindex, newlist);
            this.numElements++;
            return true;
        }
        /* If we're not at a null slot, then we need to do some work. First compare names,
        and if the names are the same then add it to that name's arraylist of locations. If the
        names aren't the same, then we need to just append this entry onto that linkedlist
        in that index in our table.

         */
        else {

            for (T item: linkt) {
                nameEntry curr = (nameEntry) item;
                if (curr.key().equals(newn.key())) {
                    // The 2 keys are equal. Grab the linked list of the current newn
                    ArrayList<Long> local = curr.locations();

                    // go through each location, check if it's the same:
                    for (long l : local) {
                        long newloc = newn.locations().get(0);
                        if (l == newloc) {
                            return false;
                        }
                    }
                    local.add(newn.locations().get(0)); // <-- take the elem's location, append it to this location?
                    //this.numElements++;
                    return true;
                }

                // we never found a name that is the same here, and we went through the whole linked list. so we need to
                    // add it:
                // but we can't do it in this loop
            }
            linkt.add(elem);
            this.numElements++;

            return true;

        }

    }

    // changed from T elem to linkedlist<T>, or even nameentry
/**
    public boolean insert(T elem) {

        // 2 possible frames:

        // everything is a linked list at first. every slot.

        // everything is null at first. linked list added. this is that frame.

        nameEntry newentry = (nameEntry) elem;
        int t = Math.abs(newentry.Hash() % table.size());
        System.out.println(" Now in insert() method. I am inserting" + newentry.key() + "at " + t);
        if (elem == null) {
            return false;
        }
        if (table.get(t) == null) {
            System.out.println("I'm in the null loop!!!");
            LinkedList<T> linked = new LinkedList<T>();
            linked.add(elem); // **** might need to be newentry?
            System.out.println("This is a linked list now. It contains" + elem + " . Now running table.add");
            this.table().add(t, linked);
            System.out.println("table add ran. Now, at" + t + " is the element" + this.table().get(t));
            this.numElements++;
            return true;
        }
        else {
            LinkedList<T> curr = table.get(t);
            System.out.println("I'm in the else loop. I just got" + this.table().get(t) + "at " + t);
            for (T n : curr) {           // *****************************
                nameEntry tst = (nameEntry) n;
                System.out.println(" The value here that I'm looping through is" + n);
                if (newentry.equals(tst)) {             // ****<<<<=-----**** problem
                    // add this location offset to that location thing
                    System.out.println("I'm in the 'if' loop inside the for loop in the else loop" + newentry.key() + "equals" + tst.key());
                    tst.addLocation(newentry.locations().get(0));        //**** getting 0 for the newentry.
                    this.numElements++;
                    return true;
                }
                curr.add(elem);
            }

            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!@@@@@@@@@@@@@@@@@@@ ");
            System.out.println("At 122 is" + this.table().get(122) + "and at 70 is" + this.table().get(70));

            //this.numElements++;
            return true;
        }

        // needs a block for duplicates??
    }*/

    /** Print all entries. Never used in the project, provided from the project spec!
     *
     */
    public void printAll() {
        for (int i = 0; i < this.sizie; i++) {
            LinkedList<T> linker = new LinkedList<>();
            linker = table.get(i);
            if (linker != null) {
                nameEntry x = (nameEntry) linker.get(0);
                System.out.println("EEEEEEEEEEEEEEEEEE" +  "    " + x.key() + "   " + x.locations().get(0) + " " + i);

            }
        }
    }

    /** Finds an element in our table
     *
     * @param elem      Element to be found
     * @return          reference to element
     */
    public T find (T elem) {


        int tele = elem.Hash();
        int telem = Math.abs(tele % table.size()); //*********

        LinkedList<T> linktest = table.get(telem);

        if (linktest == null) {
            return null;
        }
        else {
            for(T item : linktest) {
                nameEntry itm = (nameEntry) item;
                nameEntry elemn = (nameEntry) elem;

                String elems = elemn.key().trim();
                String itms = itm.key().trim();
            }

        }
        return null;

    }

    /** Display method used during building, just keeping it in here.
     *
     * @throws IOException
     */
    public void displayx() throws IOException {
        System.out.println("Number of elements: " + numElements + "\n");
        System.out.println("Number of slots: " + table.size() + "\n");
        System.out.println("Maximum elements in a slot: " + maxSlots + "\n"); // was maxSlotLength + n
        System.out.println("Load limit: " + loadLimit + "\n");
        System.out.println("\n");

        System.out.println("Slot Contents\n");
        for (int idx = 0; idx < table.size(); idx++) {

            LinkedList<T> curr = table.get(idx);
            
            if ( curr != null && !curr.isEmpty() ) {

                System.out.println(String.format("%5d: %s\n", idx, curr.toString()));
            }
        }
        // Implementation above conforms to the suggested hash table design; if
        // you make changes to the class design above, you must supply a display
        // function that writes the same information as this one.
    }
    
    /** Display method that writes to a file. Used for "debug" command
    *
    * @throws IOException
    */
   public void displayHashWrite(FileWriter out) throws IOException {
       out.write("Number of elements: " + numElements + "\n");
       out.write("Number of slots: " + table.size() + "\n");
       out.write("Maximum elements in a slot: " + maxSlots + "\n"); // was maxSlotLength + n
       out.write("Load limit: " + loadLimit + "\n");
       out.write("\n");

       out.write("Slot Contents\n");
       for (int idx = 0; idx < table.size(); idx++) {

           LinkedList<T> curr = table.get(idx);
           
           if ( curr != null && !curr.isEmpty() ) {

               out.write(String.format("%5d: %s\n", idx, curr.toString()));
           }
       }
       // Implementation above conforms to the suggested hash table design; if
       // you make changes to the class design above, you must supply a display
       // function that writes the same information as this one.
   }

    // Getter method
    public int numElements() {
        return this.numElements;
    }

    /** Display method used in our debug process
     *
     * @param fw        FileWriter to write to
     * @throws IOException
     */
    public void display(FileWriter fw) throws IOException {
        // Originally took a FileWriter fw and just did: "fw.write". for each thing here.
        // Changing to System.out.print
        fw.write("Number of elements: " + numElements + "\n");
        fw.write("Number of slots: " + table.size() + "\n");
        fw.write("Maximum elements in a slot: " + maxSlots + "\n"); // was maxSlotLength + n
        fw.write("Load limit: " + loadLimit + "\n");
        fw.write("\n");

        fw.write("Slot Contents\n");
        for (int idx = 0; idx < table.size(); idx++) {

            LinkedList<T> curr = table.get(idx);

            if ( curr != null && !curr.isEmpty() ) {

                fw.write(String.format("%5d: %s\n", idx, curr.toString()));
            }
        }

        fw.write("-----------------------------------------------------------------" + "\n");
        // Implementation above conforms to the suggested hash table design; if
        // you make changes to the class design above, you must supply a display
        // function that writes the same information as this one.
    }
}

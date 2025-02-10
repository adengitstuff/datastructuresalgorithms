import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Scanner;

// On my honor:
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project
// with anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

// - Note: The sole exceptions are the LStack and Link classes,
// provided by OpenDSA

//

/** Our main method just takes in args and passes them to printCalculations!
 * 
 * @author Aden Pandey
 * @version 07-15-2021
 *
 */
public class BigNumArithmetic {

    LStack<LinkedList> lstack;

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            throw new IllegalArgumentException(
                "Exactly one argument needed: file-input");
        }

        /* Check if the file actually exists */
        try {
            File temp = new File(args[0]);
            if (temp.exists()) {

                /*
                 * If the file exists, then pass args to our method.
                 *
                 * then, rememebr to close the file! (yikes!)
                 */

                // pasting given code:

                // String inputFilePath = args[0];

                // We could do all this in a static way, but then we would
                // need to reset our static variables for every test!
                // Let's avoid that by using the object approach like so:

                BigNumArithmetic superCalc = new BigNumArithmetic();

                // superCalc.printCalculations(args[0]);

                // make a new linked list and see if it works

                LinkedList linkie = new LinkedList();


                // LinkedList yes = superCalc.recursiveAdd(linkie.head(),
                // linkie2.head(), 0, new LinkedList(), 0,
                // superCalc.findWhichIsBigger(linkie, linkie2).listSize(),
                // linkie, linkie2); // LinkedList n =
                // superCalc.exponents(linkie, 4);
                // LinkedList n = superCalc.nent(linkie, 5);
                // System.out.println("Now recursive add try!!!!");
                // LinkedList yes = superCalc.rowGetter(linkie.head(),
                // linkie2.head().next().element(), 0, new LinkedList(), 1,
                // linkie);
//                LinkedList yes = superCalc.tryMultiply(linkie, linkie2.head(),
//                    linkie, new LinkedList(), 1);
                // LinkedList yes = superCalc.exponentTest(linkie, 2);
                // LinkedList yes = superCalc.exponents(linkie, 4);

                // int z = linkie.toInt();
                // System.out.println(" =========================" + z);

                // LinkedList yes = superCalc.squares(linkie);
                // LinkedList yes = superCalc.exponentia(linkie, 9);
                // LinkedList yes = superCalc.evenExponent(linkie, 4);
                // LinkedList yes = superCalc.exponents(linkie, 6);
                // linkie.isEven();
               
                superCalc.printCalculations(args[0]);

                // System.out.println(" $$$ BIGGERSIZE IS : " +
                // superCalc.biggerSize(linkie.listSize(), linkie2.listSize()));
                // LinkedList yaa = superCalc.recursiveAdd(linkie.head(),
                // linkie2.head(), 0, new LinkedList(), 0,
                // superCalc.biggerSize(linkie.listSize(), linkie2.listSize()),
                // linkie, linkie2);
         }
            else {
                System.out.println("Can't find the input file you specified");
                System.out.println(
                    "Check the name and directories. Exiting program!");
                System.exit(0);
                ;
            }

        }
        catch (Exception e) {
            System.out.println("Something went wrong - printing stacktrace");
            e.printStackTrace();
        }

    }


    /** Constructor for our program! 
     * 
     */
    public BigNumArithmetic() {
        // If I had any fields, I'd initialize them here
        lstack = new LStack<LinkedList>(100);
    }


    /** Our exponents method. This just does simple 0 and 1
     * checks, and then just follows the exponentiation by
     * squaring algorithm. Squaring is just done in a 
     * helper method.
     * 
     * @param a         The number to exponentiate
     * @param n         The power to raise it to
     * @return
     */
    public LinkedList exponentia(LinkedList a, int n) {

        // x^0 = 1;
        if (n == 0) {
            LinkedList one = new LinkedList();
            one.add(1);
            return one;
        }
        
        // x^1 = x;
        if (n == 1) {
            return a;
        }
        
        // if n is even case.
        if (n % 2 == 0) {
            int c = n / 2;
            LinkedList sqr = this.squares(a);
            LinkedList sqrTemp = sqr;
            for (int i = 1; i < c; i++) {
                LinkedList temp = this.tryMultiply(sqr, sqrTemp.head(), sqrTemp,
                    new LinkedList(), 1);
                sqr = temp;
            }
            return sqr;

        }
        
        // if n is odd case!
        else {
            LinkedList sqr = this.squares(a);
            LinkedList sqrTemp = sqr;
            for (int i = 1; i < ((n - 1) / 2); i++) {
                LinkedList temp = this.tryMultiply(sqr, sqrTemp.head(), sqrTemp,
                    new LinkedList(), 1);
                sqr = temp;
            }

            LinkedList ultra = this.tryMultiply(a, sqr.head(), sqr,
                new LinkedList(), 1);
            return ultra;
        }
    }


    /** This is a simple helper method that I use to
     * stop recursive add at the right pass number,
     * so that 1 is appended onto the number instead of
     * carried forward.
     * 
     * @param x     The number to add
     * @param y     The 2nd number to add
     * @return      The result of which is bigger
     */
    public LinkedList findWhichIsBigger(LinkedList x, LinkedList y) {
        if (x.listSize() > y.listSize()) {
            return x;
        }
        else {
            return y;
        }
    }


    /** This is the recursive multiply method. This was so fun
     * to figure out, write, and make. This helped answer
     * the fundamental question for me, of why data structures are 
     * useful at all. This method uses a helper method, rowgeter,
     * to get the value of 1 int multipled by a series of ints.
     * Then, it just adds the right amount of zeroes, and
     * passes results to recursiveAdd. Since addition is by
     * nature cumulative, we can do this in just one recursive 
     * method by adding results over and over again!
     * 
     * Note: sorry the params are long, I just ended up writing
     * them this way. This can be smiplified as a method.
     * 
     * @param a     The first number to multiply
     * @param y     The node of the 2nd number to multiply
     * @param b     The second number to multiply
     * @param prev  The "previous" additive result (empty at first)
     * @param numPasses     The number of passes thru this method
     * @return      A linkedlist representing the multiplied value
     */
    public LinkedList tryMultiply(
        LinkedList a,
        Node y,
        LinkedList b,
        LinkedList prev,
        int numPasses) {

        // base case! we reached the last node
        // in y, so return the cumulative
        // addition so far
        if (y.next() == null) {
            return prev;
        }

        // if either value is null
        if (a == null || b == null) {
            return null;
        }

        // initialize a field, numPasses
        int numPass = numPasses;
        
        // we initialize a linkedlist with
        // a "0" as the head, so this ensures
        // that if we're passed in the head,
        // we dont' include it in our multiplications
        if (b.head() == y) {
            y = y.next();
        }
        
        // get value of a row
        LinkedList row = this.rowGetter(a.head(), y.element(), 0,
            new LinkedList(), 0, a);

        // Add the right amount of zeroes. Tricky
        // to figure out!
        for (int z = 1; z < numPasses; z++) {
            // System.out.println("@
            // add front zeros being called");
            row.realAddFront(0);
        }
        
        // now, add this row to teh previous row
        LinkedList add = this.recursiveAdd(row.head(), prev.head(), 0,
            new LinkedList(), -1, this.findWhichIsBigger(row, prev).listSize(),
            row, prev);

        // iterate numPass
        numPass++;
        
        // if y isn't null, then iterate y
        // and pass it into this method agani
        if (y.next() != null) {
            y = y.next();
            return this.tryMultiply(a, y, b, add, numPass);
        }
        // System.out.println("@@@@@@@");
        return null;

    }



    /** This method just squares stuff! It
     * just calls multiply on itself.
     * 
     * @param a     The number to square
     * @return      The squared number
     */
    public LinkedList squares(LinkedList a) {

        return this.tryMultiply(a, a.head(), a, new LinkedList(), 1);
    }


    /** This is the (probably too complicated)
     * helper method for our recursive multiply method.
     * This could have probably been smiplified, but it's
     * how I abstracted it at first
     * 
     * @param x     The node of the 1st row
     * @param y     The int value to multiply row b
     * @param carry  Carry value    
     * @param results   Results so far
     * @param numPasses Number of passes
     * @param a     The linkedlist row
     * @return
     */
    public LinkedList rowGetter(
        Node x,
        int y,
        int carry,
        LinkedList results,
        int numPasses,
        LinkedList a) {

        // base cases - uor numPasses
        // is above lstsize
        if (numPasses > a.listSize()) {
            return results;
        }
        
        // initialized values. not necessary
        // but wrote it this way
        int numPass = numPasses;
        int carryx = 0;

        // if we're passed in the head, 
        // sip it
        if (numPass == 0) {
            x = x.next();
        }
        /* way to stop at tail */
        // do multiplication stuff
        int resultingnumber = (x.element() * y) + carry;

        // base cases for above 9 an lower tha n9
        if (resultingnumber > 9) {
            // take the modulo, add it rn
            // System.out.println(
            // (resultingnumber % 10));

            int basenumber = (resultingnumber - (resultingnumber % 10)) / 10;

            if (x.next() == null || numPasses == a.listSize()) {
                // System.out.println("*
                // basenumber);

                results.addFront(basenumber);
            }
            results.addFront(resultingnumber % 10);
            carryx = basenumber;
        }
        else {
            // System.out.println(" ********* resultnignumber :) ");
            results.addFront(resultingnumber);

            Node yx = results.head();
            // System.out.println(" head.next is ==>" + yx.next().element());
        }
        numPass++;
        return this.rowGetter(x.next(), y, carryx, results, numPass, a);

    }

    /** Recursive add was fun to wrie! I use way too
     * many params, but it's just a simple recursive
     * add method that keeps track of its number of
     * passes, the size of the biggest list,
     * and just simply adds and carreis recursively.
     * 
     * @param x         Head of first list
     * @param y         Head of second list
     * @param carry     carry value (0 at first)
     * @param results   results so far
     * @param numPasses number of passes
     * @param biggestSize  the size of the bigger list
     * @param a         first list
     * @param b         second list
     * @return
     */
    public LinkedList recursiveAdd(
        Node x,
        Node y,
        int carry,
        LinkedList results,
        int numPasses,
        int biggestSize,
        LinkedList a,
        LinkedList b) {

        int carryx = 0; // changed /* declare carry here? confused abotu static
                        // cnotext etc*/
        int numPass = numPasses;
        // System.out.println(" In recursive add, on pass number: " + numPass);
        if (y.next() == null && x.next() == null) { /* >=, ==, or >? */
            // System.out.println("Reached end case on recursive add
            // .Printing:");
            Node curr = results.head();
            while (curr.next() != null) {
                // System.out.println(" node is: " + curr.element() + " ---> ");
                curr = curr.next();
            }

            return results;
        }

        else {
            /*
             * I thought i'd need the following, btu this works because a null
             * node cuonts as a 0 for some reason internally!
             */
            // int xelem = 0;
            // int yelem = 0;

            if (y == b.head()) {
                y = y.next();
            }
            if (x == a.head()) {
                x = x.next();
            }

            int resultNum = x.element() + y.element() + carry;
            // System.out.println("%%%%%%%%%%%%%%%%%%%%%%% x is : " +
            // x.element() + " y is : " + y.element() + "on pass number: " +
            // numPass);

            /* If greater than 9 */

            if (resultNum > 9) {

                // System.out.println("~~~~~~~~~~~~~ in ersultNum > 9. Adding :
                // " + resultNum % 10);
                results.append(resultNum % 10);
                carryx = 1;

                if (numPasses + 1 == biggestSize) {
                    // System.out.println(" ~~~~~~~~~ in numPasses ==
                    // biggestSize. Adding 1");
                    results.append(1);

                }

            }
            else {

                // System.out.println("+++++++++++++ in else (resultNum less
                // than 9.). so adding : " + resultNum);

                results.append(resultNum);
                carryx = 0;
                // }
            }

            /*
             * This was beautiful to me! If y is a null list or anything, it
             * just never iterates and teh digit is always stuck at 0
             */
            if (x.next() != null) {
                x = x.next();
            }

            if (y.next() != null) {
                y = y.next();
            }

            numPass++;

            return this.recursiveAdd(x, y, carryx, results, numPass,
                biggestSize, a, b);
        }

    }

    /** Finally, print calculations just uses all of the above method,
     * raeding our input file and calling methods as they come. It prints
     * out the line if it's not a null, /t, /n, or just empty line ,and
     * then performs calculations and prints the result. Fun project!!
     * 
     * @param filepath
     * @throws FileNotFoundException
     */
    public void printCalculations(String filepath)
        throws FileNotFoundException {
        Scanner scanIn = new Scanner(new File(filepath));
        boolean skipLine = false;

        while (scanIn.hasNextLine()) {
            String line = scanIn.nextLine();

            if (line.isBlank() || line.length() == 1 || line.contentEquals("\n")
                || line.isEmpty()) {
                continue;
            }
            // System.out.println("Input Line: " + line);
            // if (line.isBlank()) {
            // if (scanIn.hasNextLine()) {

            String[] words = line.split("\\s+"); /* + conuts continuous spaces as 1 regex*/
            for (int i = 0; i < words.length; i++) {

                /* If it's not a tab character and it's not a space */
                if ((!(words[i].contentEquals("\t"))) && words[i].length() > 0
                    && !words[i].isBlank()) {

                    if (words[i].length() > 0) {
                        System.out.print(words[i] + " ");
                    }
                    // System.out.println("Input word: " + words[i]);
                    /* If we come across an expression... */
                    if (words[i].contentEquals("+") || words[i].contentEquals(
                        "*") || words[i].contentEquals("^")) {

                        if (words[i].contentEquals("+")) {

                            // pop 2 linkedlists out, perform addition, push it
                            // onto stack?
                            // System.out.println("Stack topvalue =>" +
                            // lstack.topValue() + "adn size..." +
                            // lstack.length());
                            LinkedList a = lstack.pop();

                            LinkedList b = lstack.pop();
                            if (a == b) {
                                // System.out.println("==================================
                                // SAME THING");
                            }

                            LinkedList results = this.recursiveAdd(a.head(), b
                                .head(), 0, new LinkedList(), 0, this
                                    .findWhichIsBigger(a, b).listSize(), a, b);

                            lstack.push(results);
                            // System.out.println("!!!!!!!!!!!!!!!!! Got
                            // addition results =>");

                        }
                        else if (words[i].contentEquals("*")) {
                            // System.out.println("*");
                            if (lstack.length() < 2) {
                                // System.out.println(" reached * case where
                                // stacklength less than 2");
                                skipLine = true;
                                continue;
                            }
                            LinkedList x = lstack.pop();
                            LinkedList y = lstack.pop();

                            LinkedList results = this.tryMultiply(y, x.head(),
                                x, new LinkedList(), 1);
                            lstack.push(results);

                        }
                        else {
                            // System.out.println("^");
                            if (lstack.length() < 2) {
                                // System.out.println(" reached * case where
                                // stacklength less than 2");
                                skipLine = true;
                                continue;
                            }
                            LinkedList x = lstack.pop();
                            LinkedList y = lstack.pop();
                            int c = x.toInt();
                            LinkedList results = this.exponentia(y, c);
                            lstack.push(results);
                        }
                    }
                    /* If we come across a number... */
                    else {
                        /* turn it into a linked list */
                        // System.out.println("Turning itno linked list!");

                        LinkedList res = this.turnIntoLinkedList(words[i]);
                        // System.out.println("lstack should be empty =>" +
                        // lstack.length());
                        lstack.push(res);
                        // System.out.println("lstack shoudl now be full: " +
                        // lstack.length());

                    }
                    // Something's not right here when the input has some tabs
                    // ...
                    // Maybe we should change the Regular Expression that's used
                    // for splitting the line into words?
                    // And what happens when I have a blank line?
                }
                else {
                    // System.out.println("tab character");

                }

                /*
                 * We now have ana rray with no blank lines if we need it
                 *
                 * If we want blank lines, we remove " && words[i].length() > 0)
                 */

            }
            // System.out.println(" ~~~ Reached end of line!");

            /*
             * Here, we reached teh end of the line ,but not the end of the
             * while loop. We st lread the next line if there's more
             */
            if (lstack.length() != 1) {
                // if the length isn't exactly 1
                // skip a line and move on to the next one
                // System.out.println("not enough operators");
                
                lstack.clear();
                skipLine = true;
                //continue;
            }
            if (skipLine == true) {
//                System.out.println(" = ");
                System.out.println(" = ");         
                lstack.clear();                
            }
            else {
                // System.out.println("Continuing");
                LinkedList oneone = lstack.pop();
                System.out.print(" = ");
                System.out.print(this.recursivePrint(oneone));
                System.out.println("");
                lstack.clear();
            }
            // LinkedList e = stack.pop();
            // System.out.println(e.toInt());
            skipLine = false;
            lstack.clear();

        } /** End while loop for scanin */
        scanIn.close();

    }

    /** Recursive print just recursively prints a linked
     * list, using a helper function.
     * 
     * @param a     The number to print
     * @return      Return a string, for system.out
     */
    public String recursivePrint(LinkedList a) {
        
        // th program created lots of 0's at the tail
        // so I took the easy way out and just trimmed them off,
        // since they were always at the tail (front of the number)
        // and thus tautologically could not be useful
        a.trimZeroes();
        Node z = a.head();

        this.recursivePrintHelper(z.next(), 0, a.listSize());
        return "";
    }

    /** Helper fnuction for recursive print. Take in
     * a node and a list size, and print its values!
     * 
     * @param x     The head of a list
     * @param numPass   Number of passes (start at 0)
     * @param listSize  Size of the list
     * @return
     */
    public int recursivePrintHelper(Node x, int numPass, int listSize) {

        /** Base case.. */
        if (x == null) {
            return 0;
        }
        // iterate - i don't nkow why i
        // do it like this, sorry!
        int nums = numPass + 1;

        /** Don't print the tail node, 0 */
        // if (x.next() == null && x.element() == 0) {
        // return 0;
        // }
        this.recursivePrintHelper(x.next(), nums, listSize);
        System.out.print(x.element());
        
        return 1;
    }

    /** Turn a string into a linked list. Helper method,
     * used when we encounter a string in our input file
     * and want to turn it into a series of ints, to
     * pass into a linked list. 
     * 
     * @param x     String number (e.g. 21424)
     * @return      Linked list of that number
     */
    public LinkedList turnIntoLinkedList(String x) {
        // turn string into char array
        char[] ch = x.toCharArray();
        boolean reachedFirstNumber = false;
        LinkedList results = new LinkedList();
        for (char c : ch) {
            int z = Character.getNumericValue(c);

            // if the word length is graeter tahn 1

            if (x.length() > 1) {
                if (c == '0') {
                    if (reachedFirstNumber) {
                        results.add(z);
                    }
                    else {
                        // ignore it - it's a leading whitespace
                    }
                }
                else {
                    results.add(z);
                    reachedFirstNumber = true;
                }
            }
            else {
                // the length is 1, so even if it's a zero i's valid
                results.add(z);
            }
        }
        
        return results;
    }

}

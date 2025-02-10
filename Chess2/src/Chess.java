 // On my honor:
 // - I have not used source code obtained from another student,
 //   or any other unauthorized source, either modified or
 //   unmodified.
 //
 // - All source code and documentation used in my program is
 //   either my original work, or was derived by me from the
 //   source code published in the textbook for this course.
 //
 // - I have not discussed coding details about this project
 //   with anyone other than my partner (in the case of a joint
 //   submission), instructor, ACM/UPE tutors or the TAs assigned
 //   to this course. I understand that I may discuss the concepts
 //   of this program with other students, and that another student
 //   may help me debug my program so long as neither of us writes
 //   anything during the discussion or modifies any computer file
 //   during the discussion. I have violated neither the spirit nor
 //   letter of this restriction.


/**
 * Our main method for our Chess program!
 * 
 * @author Josh Pandey
 * @version 8-2-2021
 *
 */
public class Chess {

    /** This is our main method.
     * @param args  3 possible arguments - 2 for print tree, 3 for DFS,
     *  and 1 for BFS!
     */
   
    public static void main(String[] args) {
       
        if (args.length == 2) {
           
            DecisionTree treex = new DecisionTree(args[0]);
            treex.startPrint((Integer.parseInt(args[1]) + 1), args[0]);
           
        }
        if (args.length == 3) {
            DecisionTree treex = new DecisionTree(args[0]);
            int arg3 = Integer.parseInt(args[2]) + 1;
            treex.startDFS(arg3, args[1]);
        }
       
        if (args.length == 1) {
            DecisionTree treex = new DecisionTree(args[0]);
            treex.realStartBFS();
        }
       
        /* extra checks*/
    }

}

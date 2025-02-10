/**
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Josh Pandey (josh1)
 * 
 */
package spacecolonies;

/** This is a special data exception that extends
 * Exception. A static, final serialVersion is required
 * but isn't used.
 * @author Aden P
 *
 *
 */
public class SpaceColonyDataException extends Exception {

    /** Serial ID simply auto-generated to avoid
     * compiler warning.
     */
    private static final long serialVersionUID = 1L;

    /** Set up and pass in our String for the exception!
     * 
     * @param string    The string that the exception will display.
     */
    public SpaceColonyDataException(String string) {
        super(string);
    }
}


package hw1;

/**
 * This class is an exception used for invalid dates
 * 
 * @author Judah Ben-Eliezer
 *
 */
public class InvalidDateException extends Exception {

	/**
	 * This is the constructor for an InvalidDateException object
	 *
	 * @param s
	 * 		the error message
	 */
	public InvalidDateException(String s) {
		super(s);
	}
}

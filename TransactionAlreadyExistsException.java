package hw1;

/**
 * This class is an exception thrown when an attempt to add a duplicate transaction is made
 *
 * @author Judah Ben-Eliezer
 *
 */
public class TransactionAlreadyExistsException extends Exception {
	
	/**
	 * This is a constructor used to initialize a TransactionAlreadyExistsException
	 *
	 * @param s
	 * 		the error message
	 */
	public TransactionAlreadyExistsException(String s) {
		super(s);
	}
}

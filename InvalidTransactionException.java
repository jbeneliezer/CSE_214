package hw1;

/**
 * This class is an exception thrown when an attempt to add an invalid transaction to a ledger is made
 *
 * @author Judah Ben-Eliezer
 *
 */
public class InvalidTransactionException extends Exception {
	
	/**
	 * this is a constructor used to initialize an InvalidTransactionException object
	 * @param s
	 * 		the error message
	 */
	public InvalidTransactionException(String s) {
		super(s);
	}
}

package hw1;

/**
 * This class is an exception used when an attempt to access an invalid ledger position is made
 * 
 * @author Judah Ben-Eliezer
 *
 */
public class InvalidLedgerPositionException extends Exception {
	
	/**
	 * This is a constructor used to initialize an InvalidLedgerPositionException object
	 * 
	 * @param s
	 * 		the error message
	 */
	public InvalidLedgerPositionException(String s) {
		super(s);
	}
}

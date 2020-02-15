package hw1;

/**
 * This class is an exception thrown when an attempt to add to a full ledger is made
 * 
 * @author Judah Ben-Eliezer
 *
 */
public class FullGeneralLedgerException extends Exception {
	
	/**
	 * This method is a constructor for a FullGeneralLedgerException object
	 * 
	 * @param s
	 * 		the error message
	 */
	public FullGeneralLedgerException(String s) {
		super(s);
	}
}

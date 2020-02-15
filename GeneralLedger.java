package hw1;

import java.lang.String;;

/**
 * This class represents a ledger of Transaction objects
 * 
 * @author Judah Ben-Eliezer
 * 
 * @param MAX_TRANSACTIONS
 * 		the maximum number of transactions in a ledger
 * @param ledger
 * 		the ledger of Transaction objects
 * @param totalDebitAmount
 * 		the sum of all positive transactions
 * @param totalCreditAmount
 * 		the sum of all negative transactions
 *
 */
public class GeneralLedger {
	private static final int MAX_TRANSACTIONS = 50;
	private Transaction[] ledger;
	private double totalDebitAmount;
	private double totalCreditAmount;
	
	/**
	 * This is a constructor used to create new GeneralLedger objects
	 */
	public GeneralLedger() {
		ledger = new Transaction[MAX_TRANSACTIONS];
		totalDebitAmount = totalCreditAmount = 0;
	}
	
	/**
	 * This is an accessor for the ledger
	 *
	 * @return
	 * 		the ledger
	 */
	public Transaction[] getLedger() {
		return ledger;
	}
	
	/**
	 * This is an accessor for the totalDebitAmount
	 *
	 * @return
	 * 		the total of all positive transactions
	 */
	public double getTotalDebitAmount() {
		return totalDebitAmount;
	}
	
	/**
	 * This is an accessor for the totalCreditAmount
	 *
	 * @return
	 * 		the total of all negative transactions
	 */
	public double getTotalCreditAmount() {
		return totalCreditAmount;
	}
	
	/**
	 * This method adds a new Transaction object to the ledger
	 * 
	 * @param newTransaction
	 * 		the new transaction to be added to the ledger
	 * @throws FullGeneralLedgerException
	 * 		thrown when the ledger is full
	 * @throws InvalidTransactionException
	 * 		thrown for transactions that are not valid
	 * @throws TransactionAlreadyExistsException
	 * 		thrown when the addition of a duplicate transaction is attempted
	 */
	public void addTransaction(Transaction newTransaction) throws FullGeneralLedgerException, InvalidTransactionException, TransactionAlreadyExistsException {
		int position = 0;
		while (ledger[position] != null && newTransaction.getYear() > ledger[position].getYear()) ++position;
		while (ledger[position] != null && newTransaction.getMonth() > ledger[position].getMonth()) ++position;
		while (ledger[position] != null && newTransaction.getDay() > ledger[position].getDay()) ++position;
				
		if (position >= MAX_TRANSACTIONS - 1 || ledger[MAX_TRANSACTIONS - 1] != null) throw new FullGeneralLedgerException("Ledger is full");
		
		for (int i = MAX_TRANSACTIONS - 1; i > position; --i) {
			ledger[i] = ledger[i - 1];
		}
		ledger[position] = newTransaction;
		
		if (newTransaction.getAmount() > 0) {
			totalDebitAmount += newTransaction.getAmount();
		} else {
			totalCreditAmount -= newTransaction.getAmount();
		}
		
		System.out.println("Transaction added successfully");
	}
	
	/**
	 * This method removes a transaction from the ledger
	 * 
	 * @param position
	 * 		position on the list where the transaction is to be removed - 1 based
	 * 
	 * @throws InvalidLedgerPositionException
	 * 		thrown when position parameter is out of bounds
	 */
	public void removeTransaction(int position) throws InvalidLedgerPositionException {
		if (position < 1 || position > MAX_TRANSACTIONS) {
			throw new InvalidLedgerPositionException("Position out of bounds");
		}
		
		for (int i = position - 1; i < MAX_TRANSACTIONS - 2; ++i) {
			ledger[i] = ledger[i + 1];
		}
		
		System.out.println("Transaction removed successfully");
	}
	
	/**
	 * This method returns a transaction from the ledger at a specified position
	 * 
	 * @param position
	 * 		the position at which the transaction is retrieved - 1 based
	 * 
	 * @return
	 * 		the transaction at the specified position
	 * 
	 * @throws InvalidLedgerPositionException
	 * 		thrown when position parameter is out of bounds
	 */
	public Transaction getTransaction(int position) throws InvalidLedgerPositionException {
		if (position < 1 || position > MAX_TRANSACTIONS) {
			throw new InvalidLedgerPositionException("Position out of bounds");
		}
		return ledger[position - 1];
	}
	
	/**
	 * This method displays all transactions from a specified date
	 * 
	 * @param generalLedger
	 * 		the ledger from which the specified transactions are retrieved
	 * @param date
	 * 		the date at which the transactions are retrieved
	 * @throws InvalidDateException
	 * 		for invalid date entries
	 */
	public static void filter(GeneralLedger generalLedger, String date) throws InvalidDateException {
		System.out.println(String.format("%-15s%-15s%-15s%-15s%-15s", "No.", "Date", "Debit", "Credit", "Description"));
		System.out.println("-------------------------------------------------------------------------------------\n");
		int i = 0;
		for (Transaction t: generalLedger.getLedger()) {
			Transaction.checkDate(date);
			if (t == null) return;
			else if (date.equals(t.getDate())) System.out.print(t.toString(i++));
		}
	}
	
	/**
	 * This method creates a new deep copy of a GeneralLedger object
	 * 
	 * @return
	 * 		the deep copy of the GeneralLedger object
	 */
	public Object clone() {
		GeneralLedger newLedger = new GeneralLedger();
		for (Transaction t: ledger) {
			try {
				if (t == null) return newLedger;
				newLedger.addTransaction((Transaction) t.clone());
				System.out.println("backup successful");
			} catch (FullGeneralLedgerException | InvalidTransactionException | TransactionAlreadyExistsException e) {
				System.out.println(e.getMessage());
			}
		}
		
		return newLedger;
	}
	
	/**
	 * This method is used to check whether a specific transaction exists in the ledger
	 * 
	 * @param transaction
	 * 		the transaction to be searched for in the ledger
	 * @return
	 * 		true or false dependent on whether the transaction exists in the ledger
	 * @throws IllegalArgumentException
	 * 		thrown when method is passed a non-Transaction object
	 */
	public boolean exists(Transaction transaction) throws IllegalArgumentException {
		if (!(transaction instanceof Transaction)) {
			throw new IllegalArgumentException();
		} else {
			for (Transaction t: ledger) {
				if (t.equals(transaction)) return true;
			}
			return false;
		}
	}
	
	/**
	 * This method returns the size of the ledger, ie. the number of transactions
	 * 
	 * @return
	 * 		the number of transactions in the ledger
	 */
	public int size() {
		for (int i = 0; i < MAX_TRANSACTIONS; ++i) {
			if (ledger[i] == null) return i;
		}
		return MAX_TRANSACTIONS;
	}
	
	/**
	 * This method prints a table of all the transactions in the ledger
	 */
	public void printAllTransactions() {
		System.out.println(this.toString());
	}
	
	/**
	 * This method produces and returns a neat table that summarizes the ledger
	 * 
	 * @return
	 * 		the table of transactions
	 */
	public String toString() {
		String str = String.format("%-15s%-15s%-15s%-15s%-15s\n", "No.", "Date", "Debit", "Credit", "Description");
		str += "-------------------------------------------------------------------------------------\n";
		int position = 0;
		while (ledger[position] != null) {
			str += ledger[position].toString(position++);
		}
		
		return str;
	}
}

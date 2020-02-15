
package hw1;

import java.util.Scanner;

/**
 * This is the driver class for using the general ledger
 * 
 * @author Judah Ben-Eliezer
 *
 */
public class GeneralLedgerManager {

	/**
	 * This is the main method for the manager
	 * 
	 * @param args
	 * 		in this program no command line arguments are used
	 */
	
	public static void main(String[] args) {
		String command, date, description;
		double amount;
		
		Scanner stdin = new Scanner(System.in);
		
		GeneralLedger ledger = new GeneralLedger();
		GeneralLedger backup = null;
		
		while (true) {
			System.out.print("\nEnter command to be executed:\n"+
					"(A) Add Transaction\n" + 
					"(G) Get Transaction\n" + 
					"(R) Remove Transaction\n" + 
					"(P) Print Transactions in General Ledger\n" + 
					"(F) Filter by Date\n" + 
					"(L) Look for Transaction\n" + 
					"(S) Size\n" + 
					"(B) Backup\n" + 
					"(PB) Print Transactions in Backup\n" + 
					"(RB) Revert to Backup\n" + 
					"(CB) Compare Backup with Current\n" + 
					"(PF) Print Financial Information\n" + 
					"(Q) Quit\n: ");
			
			command = stdin.next().toLowerCase();
			
			switch(command) {
			case "a":
				
				System.out.print("Enter date: ");
				date = stdin.next();
				stdin.nextLine();
				System.out.print("Enter amount: ");
				amount = stdin.nextDouble();
				stdin.nextLine();
				System.out.print("Enter description(100 characters or less): ");
				description = stdin.nextLine();
				
				try {
					ledger.addTransaction(new Transaction(date, amount, description));
				} catch (InvalidDateException | FullGeneralLedgerException | InvalidTransactionException | TransactionAlreadyExistsException e) {
					System.out.println(e.getMessage());
				}
				
				break;
			case "g":
				int position;
				
				System.out.print("Enter position: ");
				position = stdin.nextInt();
				
				try {
					Transaction t = ledger.getTransaction(position);
					System.out.println(t.getDate() + "\t" + t.getAmount() + "\t" + t.getDescription());
				} catch (InvalidLedgerPositionException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "r":
				
				System.out.println("Enter position: ");
				position = stdin.nextInt();
				
				try {
					ledger.removeTransaction(position);
				} catch (InvalidLedgerPositionException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "p":
				ledger.printAllTransactions();
				break;
			case "f":
				System.out.print("Enter date: ");
				date = stdin.next();
				
				try {
					GeneralLedger.filter(ledger, date);
				} catch (InvalidDateException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "l":
				System.out.print("Enter date: ");
				date = stdin.next();
				System.out.print("Enter amount: ");
				amount = stdin.nextDouble();
				System.out.print("Enter description(100 characters or less)");
				description = stdin.nextLine();
				
				try {
					if (ledger.exists(new Transaction(date, amount, description))) {
						System.out.println("Transaction exists in ledger");
					} else {
						System.out.println("Transaction does not exist in ledger");
					}
				} catch (IllegalArgumentException | InvalidDateException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "s":
				System.out.println(ledger.size());
				break;
			case "b":
				backup = (GeneralLedger) ledger.clone();
				break;
			case "pb":
				backup.printAllTransactions();
				break;
			case "rb":
				if (backup != null) {
					ledger = backup;
					System.out.println("ledger reverted");
				} else {
					System.out.println("no backup saved");
				}
				break;
			case "cb":
				if (backup.equals(ledger)) System.out.println("backup equal to ledger");
				else System.out.println("backup not equal to ledger");
				break;
			case "pf":
				System.out.println("debit\tcredit\tnet worth");
				double debit = ledger.getTotalDebitAmount();
				double credit = ledger.getTotalCreditAmount();
				System.out.println(debit + "\t" + credit + "\t" + (debit - credit));
				break;
			case "q":
				stdin.close();
				System.out.println("Terminating");
				System.exit(0);
			default:
				System.out.println("Invalid command");
				break;
			}
			
		}
		
	}

}

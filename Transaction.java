package hw1;

/**
 * This class represents a transaction with a date, amount, and description
 * 
 * @author Judah Ben-Eliezer
 * 
 * @param date
 * 		date of the transaction
 * @param amount
 * 		the amount in the transaction
 * @param description
 * 		the description of the transaction
 *
 */
public class Transaction {

    private String date;
    private double amount;
    private String description;

    /**
     * This method is the default constructor for a Transaction object
     */
    public Transaction() {
        date = "";
        amount = 0;
        description = "";
    }

    /**
     * This method is the parameterized constructor for a Transaction object
     * 
     * @param da
     * 		used to initialize the date
     * @param a
     * 		used to initialize the amount
     * @param de
     * 		used to initialize the description
     * 
     * @throws InvalidDateException
     * 		for invalid dates from checkDate()
     */
    public Transaction(String da, double a, String de) throws InvalidDateException {
    	
    	checkDate(da);
    	
        date = da;
        amount = a;
        description = de;
    }

    /**
     * This is an accessor for the date in string form
     *
     * @return
     * 		the date
     */
    public String getDate() {
        return date;
    }

    /**
     * This is an accessor for the amount in a transaction
     *
     * @return
     * 		the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * This is an accessor for the description
     *
     * @return
     * 		String description of the transaction
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * This is an accessor for the year of a transaction, use checkDate() before attempting to access the year to ensure date is valid
     *
     * @return
     * 		the year as an integer
     */
    public int getYear() {
    	return Integer.parseInt(date.substring(0, 4));
    }
    
    /**
    * This is an accessor for the month of a transaction, use checkDate() before attempting to access the year to ensure date is valid
    *
    * @return
    * 		the month as an integer
    */
    public int getMonth() {
    	return Integer.parseInt(date.substring(5, 7));
    }
    
    /**
     * This is an accessor for the day of a transaction, use checkDate() before attempting to access the year to ensure date is valid
     *
     * @return
     * 		the day as an integer
     */
    public int getDay() {
    	return Integer.parseInt(date.substring(8));
    }

    /**
     * This method creates a new deep copy of a Transaction object
     * 
     * @return
     * 		the new Transaction object
     */
    public Object clone() {
        try {
			return new Transaction(date, amount, description);
		} catch (InvalidDateException e) {
			System.out.println(e.getMessage());
			return null;
		}
    }

    /**
     * This is an equals method used to compare two Transaction objects
     * 
     * @param obj
     * 		the object to be compared with
     * 
     * @return
     * 		true or false determined by whether the two objects are the same
     */
	public boolean equals(Object obj) {
		if (obj instanceof Transaction) {
			Transaction tranObject = (Transaction) obj;
			return (tranObject.getDate() == date) && (tranObject.getAmount() == amount) && (tranObject.getDescription() == description);
		} else {
			return false;
		}
	}
	
	/**
	 * This is a method to neatly print a transaction
	 * 
	 * @param position
	 * 		used when printing multiple transactions in order to keep a neat list
	 * 
	 * @return
	 * 		the formatted string
	 */
	public String toString(int position) {
		
		return String.format("%-15d%-15s%-15.2f%-15.2f%-15s\n", (position + 1), getDate(),
				(getAmount() > 0) ? getAmount() : 0, (getAmount() > 0) ? 0 : -getAmount(), getDescription());
	}
	
	/**
	 * This method is used to check that dates are formatted yyyy/mm/dd
	 *
	 * @param date
	 * 		the date to be checked
	 * 
	 * @throws InvalidDateException
	 * 		thrown for invalid date formatting
	 */
	public static void checkDate(String date) throws InvalidDateException {
		try {
    		int year = Integer.parseInt(date.substring(0, 4));
        	int month = Integer.parseInt(date.substring(5, 7));
        	int day = Integer.parseInt(date.substring(8));
        	
        	if (year < 1900 || year > 2050 || month < 1 || month > 12 || day < 1 || day > 30 || date.charAt(4) != '/' || date.charAt(7) != '/') {
            	throw new InvalidDateException("Invalid date");
            }
    	} catch (Exception e) {
    		throw new InvalidDateException("Invalid date");
    	}
	}
}
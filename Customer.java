
/**
 * This class represents a Customer at a restaurant
 * 
 * @author Judah Ben-Eliezer
 *
 */
public class Customer {

	private static int totalCustomers;
	private int orderNumber;
	private String food;
	private int priceOfFood;
	private int timeArrived;
	private int timeToServe;
	
	/**
	 * default constructor for a Customer object
	 * 
	 */
	public Customer() {
		
	}
	
	/**
	 * Parameterized constructor for a Customer object
	 *
	 * @param number
	 * 		orderNumber
	 * @param f
	 * 		food
	 * @param time
	 * 		arrival time
	 */
	public Customer(int number, String f, int time) {
		orderNumber = number;
		food = f;
		timeArrived = time;
		
	}
	
	/**
	 * 
	 *
	 * @return
	 * 		orderNumber
	 */
	public int getOrderNumber() {
		return orderNumber;
	}
	
	/**
	 * 
	 *
	 * @return
	 * 		food
	 */
	public String getFood() {
		return food;
	}
	
	/**
	 * This method returns the food in abbreviated form
	 *
	 * @return
	 * 		abbreviated food
	 */
	public String getFoodAbbreviated() {
		switch(food.toLowerCase()) {
		case "cheeseburger":
			return "C";
		case "steak":
			return "S";
		case "grilled cheese":
			return "GC";
		case "chicken tenders":
			return "CT";
		case "chicken wings":
			return "CW";
		default:
			return food;
		}
	}
	
	/**
	 * Sets time to serve
	 *
	 * @param t
	 * 		timeToServe
	 */
	public void setTimeToServe(int t) {
		timeToServe = t;
	}
	
	/**
	 * 
	 *
	 * @return
	 * 		timeToServe
	 */
	public int getTimeToServe() {
		return timeToServe;
	}

	
}

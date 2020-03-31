
import java.util.LinkedList;

/**
 * This Class represents a restaurant and implements a Queue structure
 *
 * @author Judah Ben-Eliezer
 *
 */
public class Restaurant extends LinkedList<Customer> {

	/**
	 * default constructor
	 */
	public Restaurant() {
		super();
	}
	
	/**
	 * adds new Customer to front of queue
	 *
	 * @param c
	 * 		customer
	 */
	public void enqueue(Customer c) {
		super.addFirst(c);
	}
	
	/**
	 * removes customer from end of queue
	 *
	 * @return
	 * 		the removed customer
	 */
	public Customer dequeue() {
		return super.removeLast();
	}
	
	/**
	 * returns customer at end of queue
	 * 
	 * @return customer at end of queue
	 */
	public Customer peek() {
		return super.peekLast();
	}
	
	/**
	 * returns size of queue
	 * 
	 * @return
	 * 		size
	 */
	public int size() {
		return super.size();
	}
	
	/**
	 * true or false depending on if queue is empty
	 * 
	 * @return
	 * 		true or false
	 */
	public boolean isEmpty() {
		return super.isEmpty();
	}
	
	/**
	 * returns a abbreviated string representation of all the customers with their attributes in a restaurant
	 * 
	 * @return
	 * 		the string representation of a restaurant
	 */
	public String toString() {
		Restaurant tmp = new Restaurant();
		String str = "{";
		while (!isEmpty()) {
			tmp.enqueue(dequeue());
		}
		while (!tmp.isEmpty()) {
			str += "[#" + tmp.peek().getOrderNumber() + ", " + tmp.peek().getFoodAbbreviated() + ", " + tmp.peek().getTimeToServe() + " min.]";
			enqueue(tmp.dequeue());
			if (!tmp.isEmpty()) {
				str += ", ";
			}
		}
		str += "}";
		return str;
	}
}

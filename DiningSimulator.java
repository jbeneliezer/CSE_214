import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This Class acts as a simulator for a chain of restaurants
 *
 * @author Judah Ben-Eliezer
 *
 */
public class DiningSimulator {

	private ArrayList<Restaurant> restaurants;
	private int chefs;
	private int duration;
	private double arrivalProb;
	private int maxCustomerSize;
	private int numRestaurants;
	private int customersLost;
	private int totalServiceTime;
	private int customersServed;
	private int profit;
	
	/**
	 * default constructor for DininSimulator
	 */
	public DiningSimulator() {
		restaurants = new ArrayList<Restaurant>();
		chefs = 0;
		duration = 0;
		arrivalProb = 0;
		maxCustomerSize = 0;
		numRestaurants = 0;
		customersLost = 0;
		totalServiceTime = 0;
		customersServed = 0;
		profit = 0;
	}
	
	/**
	 * Main method that controls the simulator
	 * 
	 * @param args
	 * 		no command line arguments are used
	 */
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		DiningSimulator simulator = new DiningSimulator();
		String answer;
		
		do {
			System.out.println("Starting simulator...\n");
			System.out.print("Enter the number of restaurants: ");
			simulator.numRestaurants = stdin.nextInt();
			stdin.nextLine();
			System.out.print("Enter the maximum number of customers a restaurant can seat: ");
			simulator.maxCustomerSize = stdin.nextInt();
			stdin.nextLine();
			System.out.print("Enter the probability of a customer: ");
			simulator.arrivalProb = stdin.nextDouble();
			stdin.nextLine();
			System.out.print("Enter the number of chefs: ");
			simulator.chefs = stdin.nextInt();
			stdin.nextLine();
			System.out.print("Enter the number of simulation units: ");
			simulator.duration = stdin.nextInt();
			stdin.nextLine();
		
			double ave;
			try {
				ave = simulator.simulate();
				
				System.out.println("\nTotal customer time: " + simulator.totalServiceTime + " minutes.");
				System.out.println("Total customers served: " + simulator.customersServed + ".");
				System.out.println("Average customer time lapse: " + ave + " minutes per order.");
				System.out.println("Total profit: $" + simulator.profit + ".");
				System.out.println("Customers that left: " + simulator.customersLost + ".");
				
			} catch (TooFewChefsException e) {
				System.out.println(e.getMessage());
			}
			
			do {
				System.out.println("Would you like to run another simulation? (y/n): ");
				answer = stdin.next().toLowerCase();
				stdin.nextLine();
			} while (!answer.equals("y") && !answer.equals("n"));
		} while (answer.equals("y"));
		
			
		System.out.println("\nprogram terminating normally");
		
	}
	
	/**
	 * This method is the simulator, it gives the restaurant chain a virtual test or random customers and prints the results
	 *
	 * @return
	 * 		average time spent by a customer at a restaurant
	 * @throws TooFewChefsException
	 * 		thrown for less than three chefs
	 */
	public double simulate() throws TooFewChefsException {
		String [] menu = new String[] {"cheeseburger", "steak", "grilled cheese", "chicken tenders", "chicken wings"};
		int customerCount = 0;
		
		for (int i = 0; i < this.numRestaurants; ++i) {
			restaurants.add(new Restaurant());
		}
		
		for (int i = 1; i <= duration; ++i) {
			System.out.println("\nTime: " + i);
			for (Restaurant j: restaurants) {
				for (Customer k: j) {
					k.setTimeToServe(k.getTimeToServe() - 5);
					totalServiceTime += 5;
				}
			}
			
			for (int j = 0; j < numRestaurants; ++j) {
				for (int k = 0; k < 3; ++k) {
					if (Math.random() * 10 <= (arrivalProb * 10)/3.0) {
						System.out.println("Customer #" + ++customerCount + " has entered Restaurant " + (j + 1));
						Customer c = new Customer(customerCount, menu[randInt(0, 4)], i);
						if (restaurants.get(j).size() < maxCustomerSize) {
							
							if (c.getFood().equals("cheeseburger")) {
								c.setTimeToServe(40 + chefCorrection());
							} else if (c.getFood().equals("steak")) {
								c.setTimeToServe(45 + chefCorrection());
							} else if (c.getFood().equals("grilled cheese")) {
								c.setTimeToServe(30 + chefCorrection());
							} else if (c.getFood().equals("chicken tenders")) {
								c.setTimeToServe(40 + chefCorrection());
							} else if (c.getFood().equals("chicken wings")) {
								c.setTimeToServe(45 + chefCorrection());
							}
							
							restaurants.get(j).enqueue(c);
							System.out.println("Customer #" + customerCount + " has been seated with order \"" + c.getFood() + "\"");
							
							
							
						} else {
							System.out.println("Customer #" + customerCount + " cannot be seated!  They have left the restaurant.");
							++customersLost;
						}
					}
				}
				
			}
			for (Restaurant j: restaurants) {
				Restaurant tmp = new Restaurant();
				while (!j.isEmpty()) {
					tmp.enqueue(j.dequeue());
				}
				while (!tmp.isEmpty()) {
					if (tmp.peek().getTimeToServe() == 0) {
						int tmpProfit = 0;
						if (tmp.peek().getFood().equals("cheeseburger")) {
							tmpProfit = 15;
						} else if (tmp.peek().getFood().equals("steak")) {
							tmpProfit = 25;
						} else if (tmp.peek().getFood().equals("grilled cheese")) {
							tmpProfit = 10;
						} else if (tmp.peek().getFood().equals("chicken tenders")) {
							tmpProfit = 10;
						} else if (tmp.peek().getFood().equals("chicken wings")) {
							tmpProfit = 20;
						}
						++customersServed;
						System.out.println("Customer #" + tmp.peek().getOrderNumber() + " has enjoyed their food! $" + tmpProfit + " profit.");
						profit += tmpProfit;
						tmp.dequeue();
					} else {
						j.enqueue(tmp.dequeue());
					}
					
				}
			}
			for (int j = 0; j < numRestaurants; ++j) {
				System.out.println("R" + (j + 1) + " " + restaurants.get(j).toString());

			}
			
		}
		
		return totalServiceTime/customersServed;
		
	}
	
	/**
	 * This method returns a random integer between specified boundaries
	 *
	 * @param minVal
	 * 		minimum possible value inclusive
	 * @param maxVal
	 * 		maximum possible value inclusive
	 * @return
	 * 		the random integer
	 */
	public int randInt(int minVal, int maxVal) {
		return minVal + (new Random()).nextInt(maxVal - minVal);
	}
	
	public int chefCorrection() throws TooFewChefsException {
		if (chefs < 1 ) {
			throw new TooFewChefsException("Error: must have at least one chef.");
		}
		int correction = 10;
		for (int i = 1; i < chefs; ++i) {
			correction -= 5;
			if (correction == -10)
				break;
		}
		return correction;
	}
	
}

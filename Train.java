
package hw2;

/**
 * This class represents a train object
 * 
 * @author Judah Ben-Eliezer
 *
 */
public class Train {
	
	private Train next;
	private Train prev;
	private int trainNumber;
	private String destination;
	private int arrivalTime;
	private int transferTime;
	
	/**
	 * This is the default constructor for a train object
	 */
	public Train() {
		next = null;
		prev = null;
	}
	
	/**
	 * This is the parameterized constructor for a train object
	 *
	 * @param num
	 * 		trainNumber
	 * @param dest
	 * 		destination
	 * @param arrival
	 * 		arrivalTime
	 * @param transfer
	 * 		transferTime
	 */
	public Train(int num, String dest, int arrival, int transfer) {
		next = null;
		prev = null;
		trainNumber = num;
		destination = dest;
		arrivalTime = arrival;
		transferTime = transfer;
		
	}
	
	/**
	 * This is an accessor for trainNumber
	 * 
	 * @return
	 * 		trainNumber
	 */
	public int getTrainNumber() {
		return trainNumber;
	}
	
	/**
	 * This is an accessor for destination
	 * 
	 * @return
	 * 		destination
	 */
	public String getDestination() {
		return destination;
	}
	
	
	/**
	 * This is an accessor for transferTime
	 * 
	 * @return
	 * 		transferTime
	 */
	public int getTransferTime() {
		return transferTime;
	}
	
	/**
	 * This is an accessor for arrivalTime
	 * 
	 * @return
	 * 		arrivalTime
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	public int getDepartureTime() {
		return ((((arrivalTime + transferTime) % 100) % 60) + (arrivalTime/ 100 + (arrivalTime % 100 + transferTime) / 60) * 100) % 2400;
	}
	
	/**
	 * This method sets the next train in the list
	 *
	 * @param nextTrain
	 * 		the train object to be set
	 */
	public void setNext(Train nextTrain) {
		next = nextTrain;
	}
	
	/**
	 * This method sets the previous train in the list
	 *
	 * @param prevTrain
	 * 		the train object to be set
	 */
	public void setPrev(Train prevTrain) {
		prev = prevTrain;
	}
	
	/**
	 * This method gets the next train in the list
	 *
	 * @return
	 * 		the next train
	 */
	public Train getNext() {
		return next;
	}
	
	/**
	 * This method gets the previous train in the list
	 *
	 * @return
	 * 		the previous train
	 */
	public Train getPrev() {
		return prev;
	}
	
	/**
	 * This is an equals method used to compare two Trains
	 * 
	 * @return
	 * 		true or false depending on equality
	 */
	public boolean equals(Object o) {
		if (o instanceof Train) {
			Train t = (Train) o;
			return (trainNumber == t.getTrainNumber());
		} else return false;
	}


	/**
	 * This method makes a neat textual summary of the information about a train
	 * 
	 * @return
	 * 		the string summary of the train's attributes
	 */
	public String toString() {
		return "Selected Train:\n" + 
				"    Train Number: " + trainNumber + "\n" + 
				"    Train Destination: " + destination + "\n" + 
				"    Arrival Time: " + arrivalTime + "\n" + 
				"    Departure Time: " + (arrivalTime + transferTime) + "\n";
	}
	
}

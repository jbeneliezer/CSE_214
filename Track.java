
package hw2;

/**
 * This class represents a container for train objects
 * 
 * @author Judah Ben-Eliezer
 *
 */
public class Track {

	private Train head;
	private Train tail;
	private Train cursor;
	private Track next;
	private Track prev;
	private double utilizationRate;
	private int trackNumber;
	private int trainCount;
	
	/**
	 * This is the default constructor for a Track object
	 * 
	 */
	public Track() {
		head = null;
		tail = null;
		cursor = null;
		next = null;
		prev = null;
		utilizationRate = 0;
		trackNumber = 0;
		trainCount = 0;
	}
	
	/**
	 * This is the parameterized constructor for a Track object
	 *
	 * @param t
	 * 		trackNumber
	 */
	public Track(int t) {
		head = null;
		tail = null;
		cursor = null;
		next = null;
		prev = null;
		utilizationRate = 0;
		trackNumber = t;
		trainCount = 0;
	}
	
	/**
	 * getter for trackNumber
	 *
	 * @return
	 * 		trackNumber
	 */
	public int getTrackNumber() {
		return trackNumber;
	}
	
	/**
	 * getter for next train
	 *
	 * @return
	 * 		next
	 */
	public Track getNext() {
		return next;
	}
	
	/**
	 * getter for previous train
	 *
	 * @return
	 * 		prev
	 */
	public Track getPrev() {
		return prev;
	}
	
	/**
	 * getter for cursor
	 *
	 * @return
	 * 		cursor
	 */
	public Train getCursor() {
		return cursor;
	}
	
	/** 
	 * getter for trainCount
	 *
	 * @return
	 * 		trainCount
	 */
	public int getTrainCount() {
		
		return trainCount;
	}

	/**
	 * getter for utilizationRate
	 *
	 * @return
	 * 		utilizationRate
	 */
	public double getUtilizationRate() {
		
		return utilizationRate;
	}
	
	/**
	 * setter for next track
	 *
	 * @param nextTrack
	 * 		next track to be set
	 */
	public void setNext(Track nextTrack) {
		next = nextTrack;
	}
	
	/**
	 * setter for previous track
	 *
	 * @param prevTrack
	 * 		previous track to be set
	 */
	public void setPrev(Track prevTrack) {
		prev = prevTrack;
	}
	
	public void addTrain(Train newTrain) throws ConflictingTimeError {
		if (head == null) {
			head = tail = cursor = newTrain;
			utilizationRate += (newTrain.getTransferTime() * 100)/ 1440;
			++trainCount;
			return;
		} else {
			cursor = head;
		}
		
		while (newTrain.getArrivalTime() > cursor.getArrivalTime()) {
			if (cursor.getNext() == null) {
				if (cursor.getDepartureTime() > newTrain.getArrivalTime()) {
					throw new ConflictingTimeError("Cannot add train to track, time overlap");
				} else {
					cursor.setNext(newTrain);
					newTrain.setPrev(newTrain);
					newTrain = tail;
					cursor = newTrain;
					return;
				}
			} else {
				cursor = cursor.getNext();
			}
		}
		
		if (cursor.getPrev().getDepartureTime() > newTrain.getArrivalTime() || newTrain.getDepartureTime() > cursor.getArrivalTime()) {
			throw new ConflictingTimeError("Cannot add train to track, time overlap");
		}
		
		cursor.getPrev().setNext(newTrain);
		cursor.setPrev(newTrain);
		newTrain.setPrev(cursor.getPrev());
		newTrain.setNext(cursor);
		
		utilizationRate += (newTrain.getTransferTime() * 100)/ 1440;
		
		cursor = newTrain;
		
		++trainCount;
	}
	
	public void printSelectedTrain() {
		System.out.println(cursor.toString());
	}
	
	public Train removeSelectedTrain() {
		cursor.getPrev().setNext(cursor.getNext());
		cursor.getNext().setPrev(cursor.getPrev());
		return cursor;
	}
	public boolean selectNextTrain() {
		if (cursor.getNext() != null) {
			cursor = cursor.getNext();
			return true;
		} else {
			return false;
		}
	}
	public boolean selectPrevTrain() {
		if (cursor.getPrev() != null) {
			cursor = cursor.getPrev();
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		String str = "Track " + trackNumber + " (" + utilizationRate + "% utilization rate):\n";
		str += String.format("%-20s%-20s%-20s%-20s%-20s\n", "Selected", "Train Number", "Train Destination", "Arrival Time", "Departure Time");
		str += "-------------------------------------------------------------------------------------\n";
		Train newCursor = head;
		while (newCursor != null) {
			str += String.format("%-20s%-20d%-20s%-20d%-20d\n", newCursor.equals(cursor) ? "*": " ", newCursor.getTrainNumber(),
				newCursor.getDestination(), newCursor.getArrivalTime(), newCursor.getDepartureTime());
			newCursor = newCursor.getNext();
		}
		return str + "\n";
	}

	

	
}

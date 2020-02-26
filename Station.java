
package hw2;

import java.util.Scanner;

/**
 * This class represents a train station and includes the main method for managing the railroad
 * 
 * @author Judah Ben-Eliezer
 *
 */
public class Station {
	
	private Track head;
	private Track tail;
	private Track cursor;
	private int trackCount;
	
	/**
	 * This is the default constructor for a Station object
	 */
	public Station() {
		head = tail = cursor = null;
		trackCount = 0;
	}
	
	/**
	 * This method adds a new track to the station
	 *
	 * @param newTrack
	 * 		the track to be added
	 * 
	 * @throws IdenticalTracksError
	 * 		thrown when attempt to add track with same trackNumber is made
	 */
	public void addTrack(Track newTrack) throws IdenticalTracksError {
		if (head == null) {
			head = tail = cursor = newTrack;
			++trackCount;
			return;
		}
		
		cursor = head;
		
		while (newTrack.getTrackNumber() > cursor.getTrackNumber()) {
			if (cursor.getNext() == null) {
				if (newTrack.getTrackNumber() == cursor.getTrackNumber()) {
					throw new IdenticalTracksError("Cannot have two tracks with same track number");
				} else {
					cursor.setNext(newTrack);
					newTrack.setPrev(cursor);
					++trackCount;
					cursor = newTrack;
					return;
				}
			}
			cursor = cursor.getNext();
		}
		
		if (newTrack.getTrackNumber() == cursor.getTrackNumber()) {
			throw new IdenticalTracksError("Cannot have two tracks with same track number");
		} else {
			cursor.getPrev().setNext(newTrack);
			cursor.setPrev(newTrack);
			newTrack.setPrev(cursor.getPrev());
			newTrack.setNext(cursor);
			++trackCount;
			cursor = newTrack;
		}
	}
	
	/**
	 * This method removes and returns track at cursor position
	 *
	 * @return
	 * 		the removed track
	 */
	public Track removeSelectedTrack() {
		if (cursor.getPrev() != null) {
			if (cursor.getNext() != null) {
				cursor.getPrev().setNext(cursor.getNext());
				cursor.getNext().setPrev(cursor.getPrev());
			} else {
				cursor.getPrev().setNext(null);
			}
		} else {
			if (cursor.getNext() != null) {
				cursor.getNext().setPrev(null);
			} else {
				cursor = null;
			}
		}
		
		
		return cursor;
	}
	
	/**
	 * This method prints out a neat summary of the track at cursor position's attributes
	 */
	public void printSelectedTrack() {
		System.out.println(cursor.toString());
	}
	
	/**
	 * This method prints out all the tracks in the station
	 */
	public void printAllTracks() {
		System.out.println(toString());
	}
	
	/**
	 * This method selects track by track number if it exists
	 *
	 * @param trackToSelect
	 * 		trackNumber of track to select
	 * 
	 * @return
	 * 		true or false depending on if track exists
	 */
	public boolean selectTrack(int trackToSelect) {
		if (head == null) return false;
		Track tempCursor = cursor;
		cursor = head;
		while (trackToSelect != cursor.getTrackNumber()) {
			if (cursor.getNext() != null) {
				cursor = cursor.getNext();
			} else {
				cursor = tempCursor;
				return false;
			}
		}
		cursor = tempCursor;
		return true;
	}
	
	/**
	 * this method returns a String summary of a track's attributes
	 * 
	 * @return
	 * 		the neatly formatted summary
	 */
	public String toString() {
		String str = null;
		Track tempCursor = cursor;
		cursor = head;
		while (cursor != null) {
			str += cursor.toString();
			cursor = cursor.getNext();
		}
		cursor = tempCursor;
		return str;
	}
	
	/**
	 * This is the main method for the station; allows the user to create and modify tracks and trains
	 *
	 * @param args
	 * 		no command line arguments are used
	 */
	public static void main(String[] args) {
		int aTime, tTime, trackNum = 0, trainNum;
		Station station = new Station();
		
		String command, destination;
		Scanner stdin = new Scanner(System.in);
		
		do {
			System.out.print("|-----------------------------------------------------------------------------|\n" + 
					"| Train Options                       | Track Options                         |\n" + 
					"|    A. Add new Train                 |    TA. Add Track                      |\n" + 
					"|    N. Select next Train             |    TR. Remove selected Track          |\n" + 
					"|    V. Select previous Train         |    TS. Switch Track                   |\n" + 
					"|    R. Remove selected Train         |   TPS. Print selected Track           |\n" + 
					"|    P. Print selected Train          |   TPA. Print all Tracks               |\n" + 
					"|-----------------------------------------------------------------------------|\n" + 
					"| Station Options                                                             |\n" + 
					"|   SI. Print Station Information                                             |\n" + 
					"|    Q. Quit                                                                  |\n" + 
					"|-----------------------------------------------------------------------------|\n\n" +
					"Choose an operation: ");
			
			command = stdin.next().toLowerCase();
			
			switch (command) {
			case "a":
				System.out.print("Enter train number: ");
				trainNum = stdin.nextInt();
				stdin.nextLine();
				System.out.print("Enter train destination: ");
				destination = stdin.nextLine();
				System.out.print("Enter arrival time: ");
				aTime = stdin.nextInt();
				stdin.nextLine();
				System.out.print("Enter train transfer time: ");
				tTime = stdin.nextInt();
				stdin.nextLine();
				try {
					if (station.cursor != null) {
						station.cursor.addTrain(new Train(trainNum, destination, aTime, tTime));
						System.out.println("new train added to track " + trackNum);
					} else {
						System.out.println("cannot add train here");
					}
				} catch (ConflictingTimeError e) {
						System.out.println(e.getMessage());
				}
				break;
			case "n":
				if (!station.cursor.selectNextTrain()) {
					System.out.println("no next train");
				} else {
					System.out.println("next train selected");
				}
				break;
			case "v":
				if (!station.cursor.selectPrevTrain()) {
					System.out.println("no previous train");
				} else {
					System.out.println("previous train selected");
				}
				break;
			case "r":
				if (station.cursor.getCursor() != null) {
					station.cursor.removeSelectedTrain();
					System.out.println("selected train removed");
				} else {
					System.out.println("no train selected");
				}
				break;
			case "p":
				if (station.cursor.getCursor() != null) {
					station.cursor.printSelectedTrain();
				} else {
					System.out.println("no train selected");
				}
				break;
			case "ta":
				try {
					station.addTrack(new Track(++trackNum));
					System.out.println("new track added");
				} catch (IdenticalTracksError e) {
					System.out.println(e.getMessage());
				}
				break;
			case "tr":
				station.removeSelectedTrack();
				System.out.println("selected track removed");
				break;
			case "ts":
				System.out.print("Enter track number: ");
				if (station.selectTrack(stdin.nextInt())) {
					System.out.println("selected track");
				} else {
					System.out.println("no such track");
				}
				break;
			case "tps":
				if (station.cursor != null) {
					station.printSelectedTrack();
				} else {
					System.out.println("no track selected");
				}
				break;
			case "tpa":
				station.printAllTracks();
				break;
			case "si":
				System.out.println("Station (" + station.trackCount + " tracks):");
				Track newCursor = station.head;
				while (newCursor != null) {
					System.out.println("\tTrack " + newCursor.getTrackNumber() + ": " + newCursor.getTrainCount() + " trains arriving (" + newCursor.getUtilizationRate() +
							" Utilization Rate)");
					newCursor = newCursor.getNext();
				}
				break;
			case "q":
				stdin.close();
				System.out.println("Terminating");
				System.exit(0);
			}
		} while (true);
	}

	

	
}


package hw5;

import java.util.Scanner;

/**
 * This class represents the driver for a SceneTree object, able to create an play stories
 *
 * @author Judah Ben-Eliezer
 *
 */
public class AdventureDesigner {

	static SceneTree story = new SceneTree();
	static Scanner stdin = new Scanner(System.in);

	/**
	 * This is the main method which presents the user a menu and performs different SceneTree operations based on input
	 * 
	 * @param args
	 * 		no command line arguments used
	 */
	public static void main(String[] args) {
		
		System.out.print("Creating a story...\n\nPlease enter a title: ");
		String title = stdin.nextLine();
		System.out.print("Please enter a scene: ");
		String scene = stdin.nextLine();
		
		try {
			story.addNewNode(title, scene);
		} catch (FullSceneException e4) {
			SceneNode.revert();
			System.out.println(e4.getMessage());
		}
		
		System.out.println("\nScene #" + SceneNode.getNumScenes() + " added.");
		
		while (true) {
			System.out.println("\nA) Add Scene\n" + 
					"R) Remove Scene\n" + 
					"S) Show Current Scene\n" + 
					"P) Print Adventure Tree\n" + 
					"B) Go Back A Scene\n" + 
					"F) Go Forward A Scene\n" + 
					"G) Play Game\n" + 
					"N) Print Path To Cursor\n" + 
					"M) Move scene\n" + 
					"Q) Quit");
			System.out.print("\nPlease enter a selection: ");
			
			String command = stdin.next().toLowerCase();
			stdin.nextLine();
			
			switch (command) {
			case "a":
				System.out.print("\nPlease enter a title: ");
				title = stdin.nextLine();
				System.out.print("Please enter a scene: ");
				scene = stdin.nextLine();
				
				try {
					story.addNewNode(title, scene);
					System.out.println("\nScene #" + SceneNode.getNumScenes() + " added.");
				} catch (FullSceneException e3) {
					SceneNode.revert();
					System.out.println(e3.getMessage());
				}
				
				break;
			case "r":
				System.out.print("Please enter an option: ");
				String option = stdin.next();
				stdin.nextLine();
				try {
					SceneNode s = story.removeScene(option);
					if (s == null)
						System.out.println("\nNo scenes present at current location.");
					else
						System.out.println("\n" + s.getTitle() + " removed.");
				} catch (InvalidOptionException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "s":
				System.out.println();
				story.getCursor().displayFullScene();
				break;
			case "p":
				System.out.println("\n" + story.toString(story.getRoot()));
				break;
			case "b":
				try {
					story.moveCursorBackwards(story.getRoot());
					System.out.println("\nSuccessfully moved back to " + story.getCursor().getTitle());
				} catch (NoSuchNodeException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "f":
				System.out.print("Which option do you wish to go to: ");
				option = stdin.next();
				stdin.nextLine();
				try {
					story.moveCursorForwards(option);
				} catch (InvalidOptionException | NoSuchNodeException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "g":
				playGame();
				break;
			case "n":
				SceneNode tmpCursor = story.getCursor();
				System.out.println("\n" + story.getPathFromRoot(story.getRoot()));
				story.setCursor(tmpCursor);
				break;
			case "m":
				System.out.print("\nMove current scene to: ");
				int destinationID = stdin.nextInt();
				stdin.nextLine();
				try {
					story.moveScene(destinationID);
					System.out.println("\nSuccessfully moved scene.");
				} catch (NoSuchNodeException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "q":
				stdin.close();
				System.out.println("\nProgram terminating normally.");
				System.exit(0);
			default:
				System.out.println("\nInvalid command.");
				break;
			}
		}

	}
	
	/**
	 * This method plays the story by presenting the user with a menu for each scene taking input for the next scene to go to
	 */
	public static void playGame() {
		System.out.println("\nNow beginning game...");
		story.setCursor(story.getRoot());
		while (!story.getCursor().isEnding()) {
			story.getCursor().displayScene();
			System.out.print("\nPlease enter an option: ");
			String option = stdin.next();
			stdin.nextLine();
			try {
				story.moveCursorForwards(option);
			} catch (NoSuchNodeException | InvalidOptionException e) {
				System.out.println(e.getMessage());
			}
		}
		
		story.getCursor().displayScene();
		
		System.out.println("\nThe End.\n\nReturning back to creation mode.");
		
	}

}

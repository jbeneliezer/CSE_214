
package hw5;

/**
 * This class represents a individual scene in a story
 * 
 * @author Judah Ben-Eliezer
 *
 */
public class SceneNode {

	private String title;
	private String sceneDescription;
	private int sceneID;
	private SceneNode left;
	private SceneNode middle;
	private SceneNode right;
	
	private static int numScenes;
	
	/**
	 * default constructor
	 */
	public SceneNode() {
		
	}
	
	/**
	 * Parameterized constructor for SceneNode, sets ID to incremented numScenes
	 *
	 * @param t
	 * 		title
	 * @param s
	 * 		description of scene
	 */
	public SceneNode(String t, String s) {
		title = t;
		sceneDescription = s;
		sceneID = ++numScenes;
	}

	/**
	 * setter for left node
	 *
	 * @param scene
	 * 		scene set to left
	 */
	public void setLeft(SceneNode scene) {
		left = scene;
	}
	
	/**
	 * setter for middle node
	 *
	 * @param scene
	 * 		scene set to middle
	 */
	public void setMiddle(SceneNode scene) {
		middle = scene;
	}
	
	/**
	 * setter for right node
	 *
	 * @param scene
	 * 		scene set to right
	 */
	public void setRight(SceneNode scene) {
		right = scene;
	}
	
	/**
	 * getter for left node
	 *
	 * @return
	 * 		left
	 */
	public SceneNode getLeft() {
		return left;
	}

	/**
	 * getter for middle node
	 *
	 * @return
	 * 		middle
	 */
	public SceneNode getMiddle() {
		return middle;
	}

	/**
	 * getter for right node
	 *
	 * @return
	 * 		right
	 */
	public SceneNode getRight() {
		return right;
	}
	
	/**
	 * getter for sceneID
	 *
	 * @return
	 * 		sceneID
	 */
	public int getID() {
		return sceneID;
	}

	/**
	 * getter for title
	 *
	 * @return
	 * 		title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * getter for numScenes
	 *
	 * @return
	 * 		numScenes
	 */
	public static int getNumScenes() {
		return numScenes;
	}
	
	/**
	 * This method is called when an invalid scene is created and not added to the story tree
	 */
	public static void revert() {
		--numScenes;
	}

	/**
	 * This method adds a scene to the SceneNode object it is called on
	 *
	 * @param scene
	 * 		the scene to be added
	 * @throws FullSceneException
	 * 		thrown when an attempt is made to add a scene to a SceneNode object that has all its branches filled
	 */
	public void addScene(SceneNode scene) throws FullSceneException {
		if (getLeft() == null) {
			setLeft(scene);
		} else if (getMiddle() == null) {
			setMiddle(scene);
		} else if (getRight() == null) {
			setRight(scene);
		} else {
			throw new FullSceneException("Error: Cannot add to full scene.");
		}
	}
	
	/**
	 * This method checks if a SceneNode object is an ending
	 *
	 * @return
	 * 		true or false depending on whether a SceneNode has children
	 */
	public boolean isEnding() {
		return (getLeft() == null && getMiddle() == null && getRight() == null);
	}
	
	/**
	 * This method prints out a scene and its children
	 */
	public void displayScene() {
		System.out.println(title + "\n" + sceneDescription);
		System.out.println(isEnding() ? "": "\n\n" + ((getLeft() == null) ? "": "A) " + getLeft().title) + 
				((getMiddle() == null) ? "": "\nB) " + getMiddle().title) +
				((getRight() == null) ? "": "\nC) " + getRight().title));
	}
	
	/**
	 * this method prints out a detailed summary of a scene
	 */
	public void displayFullScene() {
		System.out.println("Scene ID #" + sceneID);
		System.out.println("Title: " + title);
		System.out.println("Scene: " + sceneDescription);
		System.out.println("Leads to: " + (isEnding() ? "NONE": ((getLeft() == null) ? "": "\'" + getLeft().title + "\' (#" + getLeft().sceneID + ")") +
				((getMiddle() == null) ? "": ", \'" + getMiddle().title + "\' (#" + getMiddle().sceneID + ")") +
				((getRight() == null) ? "": ", \'" + getRight().title + "\' (#" + getRight().sceneID + ")")));
	}
	
	/**
	 * This method creates a string from a scene's title and description
	 * 
	 * @return
	 * 		the string representation of a scene's title combined with its description
	 */
	public String toString() {
		return title + " (#" + sceneID + ")";
	}

}

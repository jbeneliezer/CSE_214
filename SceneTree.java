
package hw5;

/**
 * This class represents a ternary tree of SceneNode objects
 * 
 * @author Judah Ben-Eliezer
 *
 */
public class SceneTree {

	private SceneNode root;
	private SceneNode cursor;
	
	/**
	 * This is a private method used to move the cursor to a specific node
	 *
	 * @param sn
	 * 		should be set to root for method to search entire tree
	 * @param sceneIDToMove
	 * 		the sceneID of the destination node
	 * @return
	 * 		true or false depending on whether destination node is found
	 */
	private boolean moveCursorToID(SceneNode sn, int sceneIDToMove) {
		if (sn == null) {
			return false;
		} else if (sn.getID() == sceneIDToMove) {
			cursor = sn;
			return true;
		} else {
			return (moveCursorToID(sn.getLeft(), sceneIDToMove) || moveCursorToID(sn.getMiddle(), sceneIDToMove) || moveCursorToID(sn.getRight(), sceneIDToMove));
		}
	}
	
	/**
	 * default constructor for SceneTree
	 */
	public SceneTree() {
		root = cursor = null;
	}
	
	/**
	 * getter for root
	 *
	 * @return
	 * 		root
	 */
	public SceneNode getRoot() {
		return root;
	}
	
	/**
	 * getter for cursor
	 *
	 * @return
	 * 		cursor
	 */
	public SceneNode getCursor() {
		return cursor;
	}
	
	/**
	 * setter cursor
	 *
	 * @param s
	 * 		node to be set to cursor
	 */
	public void setCursor(SceneNode s) {
		cursor = s;
	}
	
	/**
	 * This method calculates the distance between a node and the root, it is 1 based: ie: height(root, root) == 1
	 *
	 * @param r
	 * 		should be set to root
	 * @param sn
	 * 		node to calculate distance to
	 * @return
	 * 		1 based distance from node to root, or null if node does not exist
	 */
	public Integer height(SceneNode r, SceneNode sn) {
		if (r == null) {
			return null;
		} else if (r.getID() == sn.getID()) {
			return 1;
		} else {
			
			Integer a = height(r.getLeft(), sn);
			Integer b = height(r.getMiddle(), sn);
			Integer c = height(r.getRight(), sn);
			
			if (a != null) {
				return 1 + a;
			} else if (b != null) {
				return 1 + b;
			} else if (c != null) {
				return 1 + c;
			} else {
				return null;
			}
			
		}
	}
	
	/**
	 * This method sets cursor equal to its parent node
	 *
	 * @param r
	 * 		should be set to root
	 * @return
	 * 		true or false depending on whether r is valid
	 * @throws NoSuchNodeException
	 * 		thrown when an attempt is made to move backwards from root
	 */
	public boolean moveCursorBackwards(SceneNode r) throws NoSuchNodeException {
		if (r == null) {
			return false;
		} else if (cursor.getID() == root.getID()) {
			throw new NoSuchNodeException("Error: Cannot move backwards from root.");
		} else if (r.getLeft() == cursor || r.getMiddle() == cursor || r.getLeft() == cursor) {
			cursor = r;
			return true;
		} else {
			return (moveCursorBackwards(r.getLeft()) || moveCursorBackwards(r.getMiddle()) || moveCursorBackwards(r.getRight()));  
		}
	}
	
	/**
	 * This method sets the cursor equal to one of its children
	 *
	 * @param option
	 * 		choice of child cursor can be set to, enter either a, b, or c
	 * @throws NoSuchNodeException
	 * 		thrown when an attempt is made to move forward from an ending node
	 * @throws InvalidOptionException
	 * 		thrown when an invalid option is entered
	 */
	public void moveCursorForwards(String option) throws NoSuchNodeException, InvalidOptionException {
		if (!cursor.isEnding()) {
			switch (option.toLowerCase()) {
			case "a":
				if (cursor.getLeft() != null)
					cursor = cursor.getLeft();
				else
					throw new NoSuchNodeException("Error: no node exists here.");
				break;
			case "b":
				if (cursor.getMiddle() != null)
					cursor = cursor.getMiddle();
				else
					throw new NoSuchNodeException("Error: no node exists here.");
				break;
			case "c":
				if (cursor.getRight() != null)
					cursor = cursor.getRight();
				else
					throw new NoSuchNodeException("Error: no node exists here.");
				break;
			default:
				throw new InvalidOptionException("Error: Invalid option.");
			}
		} else {
			throw new NoSuchNodeException("Error: node is an ending, cannot go forward");
		}
	}
	
	/**
	 * This method creates a new node and adds it to the tree
	 *
	 * @param title
	 * 		title for the new node
	 * @param sceneDescription
	 * 		description for the new node
	 * @throws FullSceneException
	 * 		thrown when an attempt is made to add a new node to a full scene
	 */
	public void addNewNode(String title, String sceneDescription) throws FullSceneException {
		if (root == null) {
			root = new SceneNode(title, sceneDescription);
			cursor = root;
		} else {
			cursor.addScene(new SceneNode(title, sceneDescription));
		}
	}
	
	/**
	 * This method removes a child from a node
	 *
	 * @param option
	 * 		a, b, or c to select child to remove
	 * @return
	 * 		the removed scene
	 * @throws InvalidOptionException
	 * 		thrown when an invalid option parameter is entered
	 */
	public SceneNode removeScene(String option) throws InvalidOptionException {
		SceneNode ret = null;
		switch(option.toLowerCase()) {
		case "a":
			ret = cursor.getLeft();
			cursor.setLeft(null);
			break;
		case "b":
			ret = cursor.getMiddle();
			cursor.setMiddle(null);
			break;
		case "c":
			ret = cursor.getRight();
			cursor.setRight(null);
			break;
		default:
			throw new InvalidOptionException("Error: Invalid option");
		}
		return ret;
	}
	
	/**
	 * This method moves a scene to be a child of a specified node
	 *
	 * @param sceneIDToMove
	 * 		sceneID of the destination parent node
	 * @throws NoSuchNodeException
	 * 		thrown when an invalid sceneID is entered
	 */
	public void moveScene(int sceneIDToMove) throws NoSuchNodeException {
		SceneNode tmp = cursor;
		moveCursorBackwards(root);
		SceneNode parent = cursor;
		
		if (moveCursorToID(root, sceneIDToMove) == false) {
			cursor = tmp;
			throw new NoSuchNodeException("Error: No node " + sceneIDToMove);
		} else {
			try {
				cursor.addScene(tmp);
				if (tmp.getID() == parent.getLeft().getID()) {
					parent.setLeft(null);
				} else if (tmp.getID() == parent.getMiddle().getID()) {
					parent.setMiddle(null);
				} else if (tmp.getID() == parent.getRight().getID()) {
					parent.setRight(null);
				}
			} catch (FullSceneException e) {
				cursor = tmp;
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * This method returns a string representation of the path from the root to the cursor node
	 *
	 * @param r
	 * 		should be set to root to get entire path
	 * @return
	 * 		the string sequence of nodes from root to cursor
	 * @throws NoSuchNodeException
	 */
	public String getPathFromRoot(SceneNode r)  {
		if (r == null || cursor == null) {
			return null;
		} else if (cursor.getID() == r.getID()) {
			return r.getTitle();
		} else if (getPathFromRoot(r.getLeft()) != null) {
			return r.getTitle() + ", " + getPathFromRoot(r.getLeft());
		} else if (getPathFromRoot(r.getMiddle()) != null) {
			return r.getTitle() + ", " + getPathFromRoot(r.getMiddle());
		} else if (getPathFromRoot(r.getRight()) != null) {
			return r.getTitle() + ", " + getPathFromRoot(r.getRight());
		} else {
			return null;
		}
	}
	
	/**
	 * This method creates a string representation of a SceneTree object
	 *
	 * @param sn
	 * 		should be set to root
	 * @return
	 * 		a list of the nodes with indentation to show depth 
	 */
	public String toString(SceneNode sn) {
		if (root == null) {
			return "empty tree";
		} else if (sn == null) {
			return "";
		} else {
			return sn.toString() + ((sn.getID() == cursor.getID()) ? " *" : "") +
					((sn.getLeft() == null) ? "": "\n" + new String(new char[height(root, sn)]).replace("\0", "\t") + "A) " + toString(sn.getLeft())) + 
					((sn.getMiddle() == null) ? "": "\n" + new String(new char[height(root, sn)]).replace("\0", "\t") + "B) " + toString(sn.getMiddle())) +
					((sn.getRight() == null) ? "": "\n" + new String(new char[height(root, sn)]).replace("\0", "\t") + "C) " + toString(sn.getRight()));
		}
	}
}

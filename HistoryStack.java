
package hw3;

import java.util.Stack;;

/**
 *
 * @author Judah Ben-Eliezer
 *
 */
public class HistoryStack extends Stack<Equation> {
	private Stack<Equation> UndoStack;
	
	/**
	 *constructor
	 */
	public HistoryStack() {
		super();
		UndoStack = new Stack<Equation>();
	}
	
	/**
	 * push
	 */
	public Equation push(Equation newEquation) {
		return super.push(newEquation);
	}
	
	/**
	 * pop
	 */
	public Equation pop() {
		return super.pop();
	}
	
	/**
	 * undo
	 */
	public void undo() {
		UndoStack.push(pop());
	}
	
	/**
	 * redo
	 * @throws CanNotRedoException
	 */
	public void redo() throws CanNotRedoException {
		if (!UndoStack.isEmpty()) {
			push(UndoStack.pop());
		} else {
			throw new CanNotRedoException("Error: Nothing undone, can not redo");
		}
	}
	
	/**
	 * size
	 * 
	 * @return size
	 */
	public int size() {
		return super.size();
	}
	
	/**
	 *
	 *	gets equation from stack
	 * @param position
	 * @return
	 * @throws InvalidPositionException
	 */
	public Equation getEquation(int position) throws InvalidPositionException {
		if (position > size() || position < 1) {
			throw new InvalidPositionException("Error: position out of bounds");
		} else {
			Stack<Equation> tmp = new Stack<Equation>();
			while (!isEmpty()) {
				tmp.push(pop());
			}
			int count = 1;
			Equation eq = null;
			while (!tmp.isEmpty()) {
				if (position == count) {
					eq = tmp.peek();
				}
				push(tmp.pop());
			}
			return eq;
		}
	}
	
	/**
	 *
	 *	sets equation
	 * @param eq
	 * @param position
	 * @throws InvalidPositionException
	 */
	public void setEquation(Equation eq, int position) throws InvalidPositionException {
		if (position > size() || position < 1) {
			throw new InvalidPositionException("Error: position out of bounds");
		} else {
			Stack<Equation> tmp = new Stack<Equation>();
			while (!isEmpty()) {
				tmp.push(pop());
			}
			int count = 1;
			while (!tmp.isEmpty()) {
				if (position == count) {
					push(eq);
				}
				push(tmp.pop());
			}
		}
	}
	
	
	/**
	 *
	 */
	public String toString() {
		String str = "#   Equation                           Pre-Fix                            Post-Fix                           Answer               Binary  Hexadecimal\r\n" + 
				"-----------------------------------------------------------------------------------------------------------------------------------------------------";
		HistoryStack tmp = new HistoryStack();
		while (!isEmpty()) {
			tmp.push(pop());
		}
		int count = 1;
		while (!tmp.isEmpty()) {
			str += tmp.peek().toString(count++);
			push(tmp.pop());
		}
		return str;
	}
}

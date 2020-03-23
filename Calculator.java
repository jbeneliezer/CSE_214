
package hw3;

import java.util.EmptyStackException;
import java.util.Scanner;

/**
 *
 * @author Judah Ben-Eliezer
 *
 */
/**
 *
 * @author Judah Ben-Eliezer
 *
 */
public class Calculator {

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String command;
		Scanner stdin = new Scanner(System.in);
		HistoryStack history = new HistoryStack();
		
		do {
			System.out.println("\nWelcome to calculat0r.\n\n" +
					"[A] Add new equation \n" + 
					"[F] Change equation from history \n" + 
					"[B] Print previous equation \n" + 
					"[P] Print full history \n" + 
					"[U] Undo \n" + 
					"[R] Redo \n" + 
					"[C] Clear history \n" + 
					"[Q] Quit \n");
			System.out.print("Enter a command: ");
			
			command = stdin.nextLine().toLowerCase();
			
			switch (command) {
			case "a":
				System.out.print("Enter the equation (infix form): ");
				Equation equation = new Equation(stdin.nextLine());
				
				if (equation.isBalanced()) {
					System.out.println("Equation is balanced and the answer is " + equation.getAnswer());
				} else {
					System.out.println("Equation is not balanced");
				}
				history.push(equation);
				break;
			case "f":
				System.out.print("Which equation would you like to change? ");
				int position = stdin.nextInt();
				stdin.nextLine();
				
				try {
					System.out.println(history.getEquation(position));
					System.out.print("What would you like to do to the equation (Replace / remove / add)? ");
					command = stdin.nextLine().toLowerCase();
						
					do {
						if (command.equals("replace")) {
							System.out.println("What position would you like to change? ");
							int position2 = stdin.nextInt();
							System.out.println("What would you like to replace it with? ");
							char s = stdin.next().charAt(0);
							stdin.nextLine();
							Equation eq = history.getEquation(position);
							eq.modify(position2, s);
							history.setEquation(eq, position);
							
							System.out.println(eq);
						} else if (command.equals("remove")) {
							System.out.print("What position would you like to remove? ");
							int position2 = stdin.nextInt();
							
							Equation eq = history.getEquation(position);
							eq.remove(position2);
							history.setEquation(eq, position);
						} else if (command.equals("add")) {
							System.out.print("What position would you like to add? ");
							int position2 = stdin.nextInt();
							System.out.print("What would you like to add? ");
							char c = stdin.next().charAt(0);
							
							Equation eq = history.getEquation(position);
							eq.add(position2, c);
							history.setEquation(eq, position);
						} else {
							System.out.println("Invalid command");
						}
						
						System.out.print("Would you like to make any more changes?");
						command = stdin.nextLine().toLowerCase();
							
					} while (!command.equals("no") && !command.equals("n"));
					
				} catch (InvalidPositionException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "b":
				try {
					System.out.println(history.peek().toString(1));
				} catch (EmptyStackException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "p":
				try {
					System.out.println(history.toString());
				} catch (EmptyStackException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "u":
				history.undo();
				break;
			case "r":
				try {
					history.redo();
				} catch (CanNotRedoException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "c":
				history.clear();
				break;
			case "q":
				stdin.close();
				System.exit(0);
			default:
				System.out.println("Invalid command");
				break;
			} 
		} while (true);

	}

}

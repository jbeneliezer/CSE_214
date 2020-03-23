
package hw3;

import java.util.Stack;

/**
 *
 * @author Judah Ben-Eliezer
 *
 */
public class Equation {
	
	private String equation;
	private String prefix;
	private String postfix;
	private double answer;
	private String binary;
	private String hex;
	private boolean balanced;
	
	/**
	 *
	 * @param c
	 * @return
	 */
	private int precedence(char c) {
		if (c == '+' || c == '-') {
			return 0;
		} else if (c == '*' || c == '/') {
			return 1;
		} else if (c == '(') {
			return 2;
		} else {
			return -1;
		}
	}
	
	/**
	 *
	 * @param eq
	 * @return
	 */
	private String equationReverse(String eq) {
		String ret = null;
		for (char i: eq.toCharArray()) {
			if (i == '(') {
				ret = ')' + ret;
			} else if (i == ')') {
				ret = '(' + ret;
			} else {
				ret = i + ret;
			}
		}
		return ret;
	}
	
	/**
	 *
	 */
	public Equation() {
		
	}
	
	/**
	 *
	 * @param s
	 */
	public Equation(String s)  {
		equation = s;
		balanced = isBalanced();
		try {
			postfix = infixToPostfix(s);
			prefix = infixToPrefix(s);
			answer = solve(equation);
			binary = decToBin(answer, 8);
			hex = decToHex(answer, 2);
			
		} catch (IllegalCharacterException | IllegalExpressionException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	/**
	 *
	 * @return
	 */
	public double getAnswer() {
		return answer;
	}
	
	/**
	 *
	 * @param position
	 * @param replacement
	 * @throws InvalidPositionException
	 */
	public void modify(int position, char replacement) throws InvalidPositionException {
		if (position > equation.length() || position < 1) throw new InvalidPositionException("Error: position out of bounds");
		equation = equation.substring(0, position) + replacement + equation.substring(position + 1);
		balanced = isBalanced();
		try {
			postfix = infixToPostfix(equation);
			prefix = infixToPrefix(equation);
			answer = solve(equation);
			binary = decToBin(answer, 8);
			hex = decToHex(answer, 2);
			
		} catch (IllegalCharacterException | IllegalExpressionException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 *
	 * @param position
	 * @throws InvalidPositionException
	 */
	public void remove(int position) throws InvalidPositionException {
		if (position > equation.length() || position < 1) throw new InvalidPositionException("Error: position out of bounds");
		equation = equation.substring(0, position) + equation.substring(position  + 1);
		balanced = isBalanced();
		try {
			postfix = infixToPostfix(equation);
			prefix = infixToPrefix(equation);
			answer = solve(equation);
			binary = decToBin(answer, 8);
			hex = decToHex(answer, 2);
			
		} catch (IllegalCharacterException | IllegalExpressionException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 *
	 * @param position
	 * @param c
	 * @throws InvalidPositionException
	 */
	public void add(int position, char c) throws InvalidPositionException {
		if (position > equation.length() || position < 1) throw new InvalidPositionException("Error: position out of bounds");
		equation = equation.substring(0, position) + c + equation.substring(position);
		balanced = isBalanced();
		try {
			postfix = infixToPostfix(equation);
			prefix = infixToPrefix(equation);
			answer = solve(equation);
			binary = decToBin(answer, 8);
			hex = decToHex(answer, 2);
			
		} catch (IllegalCharacterException | IllegalExpressionException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 *
	 * @return
	 */
	public boolean isBalanced() {
		Stack<Character> parentheses = new Stack<Character>();
		for (char i: equation.toCharArray()) {
			if (i == '(') {
				parentheses.push(i);
			} else if (i == ')') {
				if (!parentheses.isEmpty()) {
					parentheses.pop();
				} else {
					return false;
				}
			}
		}
		return parentheses.isEmpty();
	}
	
	/**
	 *
	 * @param number
	 * @param precision
	 * @return
	 */
	public String decToBin(double number, int precision) {
		int integer = (int) number;
		String ret = "";
		while (integer > 0) {
			if (integer % 2 == 0) {
				ret = "0" + ret;
			} else {
				ret = "1" + ret;
			}
			number /= 2;
		}
		
		double decimal = number - (double) integer;
		
		ret += ".";
		
		while (precision > 0) {
			decimal *= 2;
			if ((int) decimal == 0) {
				ret += "0";
			} else {
				ret += "1";
			}
			--precision;
		}
		
		return ret;
	}
	
	/**
	 *
	 * @param number
	 * @param prec
	 * @return
	 */
	public String decToHex(double number, int prec) {
		String ret = "";
		String binary = decToBin(number, prec * 4);
		while (binary.substring(0, binary.indexOf(".")).length() % 4 != 0) {
			binary = "0" + binary;
		}
		for (int i = binary.indexOf('.'); i > 3; i -= 4) {
			switch (binary.substring(i - 4, i)) {
			case "0000":
				ret  = "0" + ret;
				break;
			case "0001":
				ret  = "1" + ret;
				break;
			case "0010":
				ret  = "2" + ret;
				break;
			case "0011":
				ret  = "3" + ret;
				break;
			case "0100":
				ret  = "4" + ret;
				break;
			case "0101":
				ret  = "5" + ret;
				break;
			case "0110":
				ret  = "6" + ret;
				break;
			case "0111":
				ret  = "7" + ret;
				break;
			case "1000":
				ret  = "8" + ret;
				break;
			case "1001":
				ret  = "9" + ret;
				break;
			case "1010":
				ret  = "A" + ret;
				break;
			case "1011":
				ret  = "B" + ret;
				break;
			case "1100":
				ret  = "C" + ret;
				break;
			case "1101":
				ret  = "D" + ret;
				break;
			case "1110":
				ret  = "E" + ret;
				break;
			case "1111":
				ret  = "F" + ret;
				break;
			}
		}
		
		ret += ".";
	
		for (int i = binary.indexOf('.') + 1; i < binary.length(); i += 4) {
			switch (binary.substring(i, i + 4)) {
			case "0000":
				ret += "0";
				break;
			case "0001":
				ret += "1";
				break;
			case "0010":
				ret += "2";
				break;
			case "0011":
				ret += "3";
				break;
			case "0100":
				ret += "4";
				break;
			case "0101":
				ret += "5";
				break;
			case "0110":
				ret += "6";
				break;
			case "0111":
				ret += "7";
				break;
			case "1000":
				ret += "8";
				break;
			case "1001":
				ret += "9";
				break;
			case "1010":
				ret += "A";
				break;
			case "1011":
				ret += "B";
				break;
			case "1100":
				ret += "C";
				break;
			case "1101":
				ret += "D";
				break;
			case "1110":
				ret += "E";
				break;
			case "1111":
				ret += "F";
				break;
			}
		}
		
		return ret;
	}
	
	/**
	 *
	 * @param eq
	 * @return
	 * @throws IllegalCharacterException
	 * @throws IllegalExpressionException
	 */
	public String infixToPostfix(String eq) throws IllegalCharacterException, IllegalExpressionException {
		Stack<Character> operators = new Stack<Character>();
		String post = new String("");
		for (char i: eq.toCharArray()) {
			if (Character.isDigit(i)) {
				post += i;
			} else if (i == '(') {
				operators.push(i);
			} else if (i == ')') {
				char top = operators.pop();
				while (top != '(') {
					post += top;
					top = operators.pop();
				}
			} else if (i == '+' || i == '-' || i == '*' || i == '/') {
				if (operators.isEmpty()) {
					operators.push(i);
					post += " ";
					break;
				}
				char top = operators.peek();
				while (precedence(top) > precedence(i)) {
					post += operators.pop();
					top = operators.peek();
				}
				operators.push(i);
				post += " ";
			} else if (i == ' ' || i == '\n') {
				continue;
			} else {
				throw new IllegalCharacterException("Error: illegal expression. ");
			}
		}
		while (!operators.isEmpty()) {
			post += operators.pop();
		}
		return post;
	}
	
	/**
	 *
	 * @param eq
	 * @return
	 * @throws IllegalCharacterException
	 * @throws IllegalExpressionException
	 */
	public String infixToPrefix(String eq) throws IllegalCharacterException, IllegalExpressionException {
		return equationReverse(infixToPostfix(equationReverse(eq)));
	}
	
	/**
	 *
	 * @param eq
	 * @return
	 * @throws IllegalExpressionException
	 */
	public double solve(String eq) throws IllegalExpressionException {
		Stack<Double> numbers = new Stack<Double>();
		try {
			String post = infixToPostfix(eq);
			for (char i: post.toCharArray()) {
				if (Character.isDigit(i)) {
					numbers.push((double) i);
				} else {
					double a = numbers.pop();
					double b = numbers.pop();
					
					if (i == '+') {
						numbers.push(a + b);
					} else if (i == '-') {
						numbers.push(a - b);
					} else if (i == '*') {
						numbers.push(a * b);
					} else if (i == '/') {
						numbers.push(a / b);
					}
				}
			}
			return numbers.pop();
		} catch (IllegalCharacterException e) {
			System.out.println(e.getMessage());
			return Double.NaN;
		}
	}
	
	
	/**
	 *
	 * @param position
	 * @return
	 */
	public String toString(int position) {
		
		return String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", position, equation,
			prefix, postfix, answer, binary, hex);
		
	}

}

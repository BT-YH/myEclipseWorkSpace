import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class InfixEvaluator {
	private static String doubleRegex = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"; // Java double regex
	
	public static double evaluate(java.util.Queue<Object> tokens) {
		System.out.println("Evaluate " + tokens);
		while(!tokens.isEmpty()) {
			System.out.println("while " + tokens.peek());
			Object t = tokens.poll();
			String s = t + "";
			if (s.matches(doubleRegex)) {
				return Double.parseDouble(s);
			} else if (s.equals("(")) {
				double firstOperand = evaluate(tokens);
				String operator = (String) tokens.poll();
				double secondOperand = evaluate(tokens);
				tokens.poll();
				return operator.equals("+") ? (firstOperand + secondOperand): (firstOperand * secondOperand); 
			} else {
				System.err.println("Invalid token: " + t);
			}
		}
		return 0; 
	}
	
	public static void main(String[] args) {
		Queue<Object> tokens = new LinkedList<>();
		tokens.add("(");
		tokens.add("19");
		tokens.add("*");
		tokens.add("(");
		tokens.add("2");
		tokens.add("+");
		tokens.add("3");
		tokens.add(")");
		tokens.add(")");
		double result = evaluate(tokens);
		System.out.println(result);

	}
}








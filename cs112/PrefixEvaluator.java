import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PrefixEvaluator {
        private static String doubleRegex = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"; // Java double regex
        
        public static double evaluate(Queue<Object> tokens) {
                while(!tokens.isEmpty()) {
                        Object t = tokens.poll();
                        String s = t + "";
                        if (s.matches(doubleRegex)) {
                                return Double.parseDouble(s);
                        } else if (s.equals("+") || s.equals("*")) {
                                double firstOperand = evaluate(tokens);
                                double secondOperand = evaluate(tokens);
                                return s.equals("+") ? (firstOperand + secondOperand): (firstOperand * secondOperand); 
        }
                        else {

                                System.err.println("Invalid token: " + t);
                                System.exit(1);
                        }
                }
                return 0; 
        }
        
        public static void main(String[] args) {
                Scanner in = new Scanner("* * 1.0 + 3.0 -7.0 * + -6.0 1.0 * 3.0 * 8.0 -5.0"); // Output: 14.0
                Queue<Object> tokens = new LinkedList<Object>();
                while (in.hasNext())
                        tokens.offer(in.hasNextDouble() ? in.nextDouble() : in.next());
                in.close();
                System.out.println(evaluate(tokens));
        }

}

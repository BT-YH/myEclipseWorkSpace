import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class PostScriptDemo { //PostScript stack machine demonstration
	private static String doubleRegex = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"; // Java double regex
	private static String idRegex = "/?[A-Za-z_][A-Za-z0-9_]*"; // (PostScript identifiers are more general)
	private static String tokenRegex = String.format("(%s)|(%s)|[\\{\\}]", doubleRegex, idRegex);
	private static boolean verbose = true; // show stack and next token with each step
	private static Map<String, Object> map = new HashMap<String, Object>(); 
	private static Stack<Object> stack = new Stack<Object>();

	public static ArrayList<Object> getTokens(Scanner in) { // recursive
		ArrayList<Object> tokens = new ArrayList<Object>();
		// Read and return a list of tokens from the standard input.
		while (in.hasNext(tokenRegex)) {
			String t = in.next(tokenRegex);
			if (t.matches(doubleRegex)) {
				tokens.add(Double.parseDouble(t));
			} else if (t.equals("{")) {
				tokens.add(getTokens(in)); // tokens are recursively added
			} else if (t.equals("}")) {
				break;
			} else {
				tokens.add(t);
			}
		}
		if (!in.hasNext(tokenRegex) && in.hasNext()) {
			System.err.printf("Illegal token on input: %s\n", in.next());
			System.exit(1); // 
		}
		
		return tokens;
	}

	@SuppressWarnings("unchecked")
	public static void interpret(List<Object> tokens) {
		// TODO - For each token, interpret (i.e. execute) the PostScript program
		for (Object t : tokens) {
			if (verbose) {
				System.out.println("Stack: " + stack + " Token: " + t);
			}
			if (t instanceof String) {
				String s = (String) t;
				// Operators
				if (s.equals("show")) {
					System.out.println(stack.pop()); // show = print top of stack
				} else if (s.equals("add")) { // addition
					stack.push((Double) stack.pop() + (Double) stack.pop());
				} else if (s.equals("sub")) { // subtraction
					stack.push(-(Double) stack.pop() + (Double) stack.pop());
				} else if (s.equals("mul")) { // multiplication
					stack.push((Double) stack.pop() * (Double) stack.pop());
				} else if (s.equals("div")) { // multiplication
					double divisor = (Double) stack.pop();
					stack.push((Double) stack.pop() * divisor);
				} 
				else if (s.equals("eq")) { // equality
					stack.push((stack.pop().equals(stack.pop())));
				} 
				else if (s.equals("gt")) { // greater than
					stack.push((Double) stack.pop() < (Double) stack.pop());
				} 
				else if (s.equals("and")) { // and
					Boolean b2 = (Boolean) stack.pop();
					Boolean b1 = (Boolean) stack.pop();
					stack.push(b1 && b2);
				} 
				else if (s.equals("or")) {
					Boolean b2 = (Boolean) stack.pop();
					Boolean b1 = (Boolean) stack.pop();
					stack.push(b1 || b2);
				} 
				else if (s.equals("not")) {
					stack.push(!(Boolean) stack.pop());
				} 
				else if (s.charAt(0) == '/') { // literal
					stack.push(s);
				} 
				else if (s.equals("def")) {
					Object value = stack.pop();
					String literal = (String) stack.pop();
					map.put(literal.substring(1), value);
				}
				else if (s.equals("dup")) { // duplicate top stack value
					stack.push(stack.peek());
				}
				else if (s.equals("pop")) { // pop top stack value
					stack.pop();
				}
				else if (s.equals("exch")) { // exchange top two stack values
					Object top = stack.pop();
					Object second = stack.pop();
					stack.push(top);
					stack.push(second);
				}
				else if (s.equals("ifelse")) { // if-else: else block, if block, and boolean form the top down in the stack
					List<Object> elseBlock = (List<Object>) stack.pop();
					List<Object> ifBlock = (List<Object>) stack.pop();
					Boolean condVal = (Boolean) stack.pop();
					interpret(condVal ? ifBlock : elseBlock);
				}
				else if (s.equals("loop")) { // while true (exit to break)
					List<Object> loopBlock = (List<Object>) stack.pop();
					try {
						while (true) {
							interpret(loopBlock);
						}
					} 
					catch (LoopExitException e) { // loop exited
					}
				}
				else if (s.equals("exit")) {
					throw new LoopExitException();
				}
				else { // variable lookup - if code block, immediately execute, otherwise push the result
					Object value = map.get(s);
					if (value instanceof List) {
						interpret((List<Object>) value);
					}
					else {
						stack.push(s);
					}
				}
				
			} // end String values
			else { // number or other 
				stack.push(t);
			}
		}

	} 

	public static void main(String[] args) {
		interpret(getTokens(new Scanner(System.in)));
	}
}

@SuppressWarnings("serial")
class LoopExitException extends RuntimeException {
	
}

/*

Mini-PostScript Demo code: (copy and paste bits between % comment lines)

% print 1 + 2:

	1 2 add show

% if ( 1 == 2 ) { print 3 } else { print 4 }

	1 2 eq { 3 show } { 4 show } ifelse

% x = 1
% print 5 - x

	/x 1 def 5 x sub show

% define function incr(x) = x + 1; print incr(2)

	/incr { 1 add } def
	2 incr show

% recursive sum to n:

	/sumto { dup 0 eq { pop 0 } { dup 1 sub sumto add } ifelse } def
	5 sumto show

% iterative sum to n: put sentinel value -1, stack numbers to add, add down to token, remove token

	/sumtoiter { 
	-1 exch
	{ dup 0 eq { exit } { dup 1 sub } ifelse } loop
	{ exch dup -1 eq { pop exit } { add } ifelse } loop
	} def
	5 sumtoiter show

% iterative sum to n, single loop with temp variable sumtoiter_n,
%   trading stack places of sum and loop control variable: 

/sumtoiter {
  0 
  {
    exch dup 0 eq
    { pop exit }
    { dup /sumtoiter_n exch def 1 sub exch sumtoiter_n add }
    ifelse 
  } loop
} def
5 sumtoiter show

% iterative sum to n with multiple temporary variables:

/sumtoiter { /sumtoiter_n exch def /sumtoiter_sum 0 def sumtoiter_helper sumtoiter_sum } def
/sumtoiter_helper { { sumtoiter_n 0 eq 
                      { exit } 
                      { sumtoiter_sum sumtoiter_n add /sumtoiter_sum exch def 
                        sumtoiter_n 1 sub /sumtoiter_n exch def } 
                      ifelse } loop } def
3 sumtoiter show

% Recursive factorial
% (Factorial example from https://www.math.ubc.ca/~cass/graphics/manual/pdf/ch9.pdf)

/factorial { dup 0 eq { pop 1 } { dup 1 sub factorial mul } ifelse } def
5 factorial show

% Recursive Fibonacci followed by iterative sequence printing fib(iinit) through fib(istop - 1)
% (Fibonacci example adapted from https://en.wikipedia.org/wiki/Stack-oriented_programming_language)

  /fib
  {
     dup dup 1 eq exch 0 eq or not
     {
        dup 1 sub fib
        exch 2 sub fib
        add
     } { } ifelse
  } def

  /iinit 1 def
  /istop 10 def
  iinit { dup istop eq { exit } { dup fib show 1 add }  ifelse } loop

 */

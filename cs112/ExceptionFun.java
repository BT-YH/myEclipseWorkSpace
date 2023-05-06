import java.util.InputMismatchException;
import java.util.Scanner;


public class ExceptionFun {

	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
//		divide();
//		tryDivide();
		recoveryLoop();
	}
	
	public static void recoveryLoop() {
        int lastChances = 3;
        while (true) {
                try {
                        tryDivide();
                        break;
                }
                catch (MyException e) {
                        System.out.println(e.getMessage());
                        if (--lastChances == 0) {
                                System.out.println("3 strikes and you're out!");
                                System.exit(1); // error termination (!= 0)
                        }
                        System.out.println("Let's try that again.");
                }
        }
	}
	
	public static void tryDivide() throws MyException {
		try { // For non-RuntimeExceptions, you can (1) handle it with a try-catch statement.
			divide();
		}
		catch (ArithmeticException e) {
			throw new MyException("You cannot divide by zero.");
		}
		catch (InputMismatchException e) {
			in.nextLine();
			throw new MyException("Integer input only, please.");
		}
		catch (Exception e) { // demonstrate this going before InputMismatchException in catch order
			throw new MyException(e.getMessage());
		}
		finally {
			System.out.println("(finally)");
        }
	}
	
	public static void divide() throws Exception { // For non-RuntimeExceptions, you can (1) catch it, or (2) declare it as thrown.
		System.out.print("a? ");
		int a = in.nextInt();
		System.out.print("b? ");
		int b = in.nextInt();
		int quotient = a / b;
		System.out.printf("a / b = %d\n", quotient);
//		Exception e = new Exception("(message here)");
//		e.printStackTrace();
//		throw new RuntimeException("My exception description here. Your ad could be here.");
		throw new Exception("My exception description here. Your ad could be here.");
	}

}

@SuppressWarnings("serial")
class MyException extends Exception {

	public MyException(String string) {
		super(string);
	}
	
}

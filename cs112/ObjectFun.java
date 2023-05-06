import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Stack;

/**
 * Demonstration of Java Stack class, auto-(un)boxing, BigInteger, BigDecimal
 * @author Todd W. Neller
 */
public class ObjectFun {

	public static void main(String[] args) {
		Random random = new Random(3);
		Stack<Integer> intStack = new Stack<Integer>();
		int numOps = 20;
		for (int i = 0; i < numOps; i++) {
			int op = random.nextInt(3);
			if (op == 0) { // push
				int n = random.nextInt(numOps);
				System.out.println("push " + n);
				intStack.push(n); // auto-boxing
			}
			else if (op == 1) { // pop
				if (intStack.isEmpty()) {
					i--; // decrement counter
					continue;
				}
				int n = intStack.pop(); // auto-unboxing
				System.out.println("pop() -> " + n);
			}
			else { // peek
				if (intStack.isEmpty()) {
					i--; // decrement counter
					continue;
				}
				System.out.println("peek() -> " + intStack.peek());
			}
			System.out.println(intStack.isEmpty() ? "(empty stack)" : "size " + intStack.size() + ": " + intStack);
		}

		Stack<Double> doubleStack = new Stack<Double>();
		doubleStack.push(Math.PI); // auto-boxing
		System.out.println(doubleStack.pop()); // auto-unboxing
		
		// Computing large factorials:
		
		// BigInteger
		BigInteger fact = BigInteger.ONE; // or: new BigInteger("1");
		BigInteger n = new BigInteger("2");
		for (int i = 1; i <= 100; i++) {
			System.out.printf("%2d! = %s\n", i, fact);
			fact = fact.multiply(n);
			n = n.add(BigInteger.ONE);
		}
		System.out.println();
		
		
		// Computing many digits of phi using repeated fraction method 
		
		int extraScale = 10; // extra digits of precision to have greater assurance of convergence to the desired precision
		String phiString = "2";
		for (int scale = 1; scale <= 1000; scale++) {
			BigDecimal eps = BigDecimal.ONE;
			BigDecimal phi = new BigDecimal(phiString); // use previous digits as starting point for next iteration
			for (int i = 0; i < scale + extraScale / 2; i++)
				eps = eps.divide(BigDecimal.TEN, scale + extraScale, RoundingMode.FLOOR); // maximum allowable last iteration change
			while (true) { // repeatedly take the reciprocal and add one until changes are small enough to get correct digits
//				System.out.println(phi);
				BigDecimal oldPhi = phi;
				phi = BigDecimal.ONE.divide(phi, scale + extraScale, RoundingMode.FLOOR).add(BigDecimal.ONE); 
				BigDecimal diff = phi.subtract(oldPhi).abs();
				if (diff.compareTo(eps) <= 0) // absolute value of difference between old and new estimates differs a few places further from the digit we care about.
					break;
			}
			phiString = phi.divide(BigDecimal.ONE, scale, RoundingMode.FLOOR).toString();
			System.out.println("phi = " + phiString); // Flooring, not rounding gives correct last digit, but RoundingMode.HALF_UP does normal rounding
		}
	}

}

import java.util.Arrays;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class PigTurnMath {
	
	public static BigFraction expectedTurnScore(int holdAt) {
		// create array e
		BigFraction[] e = new BigFraction[holdAt + 6];
		
		// initialize array e
		for (int i = holdAt; i < e.length; i++) {
			BigInteger numerator = new BigInteger(i+"");
			e[i] = new BigFraction(numerator, BigInteger.ONE);
		}
		
		BigInteger sixI = new BigInteger(6+"");
		BigFraction sixF = new BigFraction(sixI, BigInteger.ONE);
		
		for (int i = holdAt - 1; i >= 0; i--) {
			e[i] = e[i+2].divide(sixF).add(e[i+3].divide(sixF)).add(e[i+4].divide(sixF)).add(e[i+5].divide(sixF)).add(e[i+6].divide(sixF));
		}
		return e[0];
	}
	
	public static void main(String[] args) {
		BigFraction resultF = expectedTurnScore(100);
		BigDecimal resultD = resultF.asBigDecimal(6, RoundingMode.HALF_UP);
		System.out.println("The expected score of a Pig turn holding at or above 100 is approximately " 
							+ resultD + " and exactly " + resultF + ".");
	}
}

import java.math.*;
/**
 * Write a description of class BigFraction here.
 *
 * @author (Barry Tang)
 * @version (Sep 6, 2022)
 */
public class BigFraction
{   
    public static final BigFraction ONE = new BigFraction(BigInteger.ONE, BigInteger.ONE);
    public static final BigFraction ZERO = new BigFraction(BigInteger.ZERO, BigInteger.ONE);
    private BigInteger numerator;
    private BigInteger denominator;
    
    private void simplify() {
    	BigInteger gcd = numerator.gcd(denominator);
    	if (numerator.compareTo(BigInteger.ZERO) < 0 && denominator.compareTo(BigInteger.ZERO) < 0) {
    		gcd = gcd.negate();
    	} else if(denominator.compareTo(BigInteger.ZERO) < 0) {
    		numerator = numerator.negate();
    		denominator = denominator.negate();
    	}
    	numerator = numerator.divide(gcd);
    	denominator = denominator.divide(gcd);
    }
    
    public BigFraction(BigInteger numerator, BigInteger denominator) {
        BigInteger gcd = numerator.gcd(denominator);
        this.numerator = numerator.divide(gcd);
        this.denominator = denominator.divide(gcd);
        this.simplify();
    }
    
    public BigFraction(String s) {
        String[] array = s.split("/");
        numerator = new BigInteger(array[0]);
        denominator = new BigInteger(array[1]);
        this.simplify();
    }
    
    public BigFraction(BigFraction f) { 
        numerator = f.getNum();
        denominator = f.getDen();
        this.simplify();
    }
    
    public BigFraction(long numerator, long denominator) {
        this.numerator = new BigInteger(numerator + "");
        this.denominator = new BigInteger(denominator + "");
        this.simplify();
    }
    
    public BigInteger getNum() {
        return this.numerator;
    }
    
    public BigInteger getDen() {
        return this.denominator;
    }
    
    public String toString() {
        return this.getNum() + "/" + this.getDen();
    }
    
    public BigDecimal asBigDecimal(int scale, RoundingMode roundingMode) {
        BigDecimal deciNum = new BigDecimal(numerator);
        BigDecimal deciDen = new BigDecimal(denominator);
        BigDecimal value = deciNum.divide(deciDen, scale, roundingMode);
        return value;
    }
    
    public BigFraction negate() {
        BigInteger negatedNum = numerator.negate();
        return new BigFraction(negatedNum, denominator);
    }
    
    public BigFraction add(BigFraction b) {
        BigInteger commonDen = this.denominator.multiply(b.denominator);
        BigInteger newNumerator = this.numerator.multiply(b.denominator).add(b.numerator.multiply(this.denominator));
        BigFraction sum = new BigFraction(newNumerator, commonDen);
        return sum;
    }
    
    public BigFraction subtract(BigFraction b) {
        BigInteger commonDen = this.denominator.multiply(b.denominator);
        BigInteger newNumerator = this.numerator.multiply(b.denominator).subtract(b.numerator.multiply(this.denominator));
        BigFraction difference = new BigFraction(newNumerator, commonDen);
        return difference;
    }
    
    public BigFraction multiply(BigFraction b) {
        BigInteger commonDen = this.denominator.multiply(b.denominator);
        BigInteger newNumerator = this.numerator.multiply(b.numerator);
        BigFraction product = new BigFraction(newNumerator, commonDen);
        return product;
    }
    
    public BigFraction divide(BigFraction b) {
        BigInteger commonDen = this.denominator.multiply(b.numerator);
        BigInteger newNumerator = this.numerator.multiply(b.denominator);
        BigFraction quotient = new BigFraction(newNumerator, commonDen);
        return quotient;
    }
}







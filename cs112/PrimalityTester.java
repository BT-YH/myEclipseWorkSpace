import java.util.Scanner;

public class PrimalityTester {
	public static void main(String[] args) {
	int integer = 0;
	Scanner input = new Scanner(System.in);
	System.out.print("Enter an integer greater than 1: ");
	try {
		integer = input.nextInt();
	} catch (Exception e) {
		System.out.println("invalid input");
		System.exit(0);
	} finally {
		if (integer == 2) {
			System.out.println("prime");
		}
	
		for (int i = 2; i <= Math.sqrt(integer); i++) {
			if (integer % i == 0) {
				System.out.println("composite");
				System.exit(0);
			} 
		}
		
		System.out.println("prime");
	}
	input.close();
}
}

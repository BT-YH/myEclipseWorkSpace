import java.util.Scanner;

public class InputSign {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter an integer: ");
		int integer = input.nextInt();
		String result = integer > 0 ? "positive" :
						integer < 0 ? "negative" : "zero";
		System.out.println(result);
		input.close();
	}
}

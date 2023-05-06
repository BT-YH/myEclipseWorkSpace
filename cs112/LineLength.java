import java.util.Scanner;

public class LineLength {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a line of text: ");
		String usrInput = input.nextLine();
		char[] charArray = usrInput.toCharArray();
		System.out.printf("That line is %d character(s) long", charArray.length);
		input.close();
	}
}

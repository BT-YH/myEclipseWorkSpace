import java.util.ArrayList;
import java.util.Scanner;

public class Unique {

	public static void main(String[] args) {
		ArrayList<String> inputs = new ArrayList<>();
		
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String next = in.nextLine();
			if (inputs.isEmpty()) {
				inputs.add(next);
			} else {
				if (inputs.contains(next)) {
					
				} else {
					inputs.add(next);
				}
			}
		}
		
		for (int i = 0; i < inputs.size(); i++) {
			System.out.println(inputs.get(i));
		}
	}

}

import java.util.ArrayList;
import java.util.Scanner;

public class SearchNodeTest {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		SearchNode node = new BucketsNode(); 
		while (!node.isGoal()) {
			ArrayList<SearchNode> children = node.expand();
			for (int i = 0; i < children.size(); i++) {
				System.out.printf("Child %d:\n", i);
				System.out.println(children.get(i));
			}
			System.out.printf("Please enter a child 0-%d, or -1 to quit: ", children.size() - 1);
			try {
				int child = in.nextInt(); // 1 2 0 3 2 3 0 -> goal
				if (child == -1)
					break;
				node = children.get(child);
			}
			catch (Exception e) {
				in.nextLine();
				System.out.println("Invalid input.");
			}
		}
		if (node.isGoal())
			System.out.println("Goal reached.");
		in.close();
	}

}

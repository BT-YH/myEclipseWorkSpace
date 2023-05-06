import java.util.Scanner;

public class Freecell {
	
	public void play() {
		play(System.currentTimeMillis());
	}
	
	public void play(long seed) {
		FreecellGame g = new FreecellGame(seed);
		System.out.println(g);
		Scanner in = new Scanner(System.in);
		while (!g.isGameOver()) {
			try {
				System.out.print("Please enter source and destination card stacks, or \"-1\" to quit: ");
				int fromStack = in.nextInt();
				if (fromStack == -1) {
					return;
				}
				int toStack = in.nextInt();
				g.play(fromStack, toStack);
			} catch (java.util.InputMismatchException e) {
				System.out.println("\nSource and destination card stacks must be entered as integers (1-16).");
				in.nextLine();
			} catch (IllegalPlayException e) { 
				System.out.println("\nIllegal Play: " + e.getMessage());
			}
			System.out.println();
     		System.out.println(g);
		}
	}
	
	public static void main(String[] args) {
		new Freecell().play(2L);
	}
	
}

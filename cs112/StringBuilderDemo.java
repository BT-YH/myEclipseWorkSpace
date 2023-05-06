import java.util.Scanner;

public class StringBuilderDemo {

	public static String getCheckerboard(int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++)
				sb.append((i + j) % 2 == 0 ? "*" : " ");
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public static String getCheckerboardConcat(int size) {
		String s = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++)
				s += (i + j) % 2 == 0 ? "*" : " ";
			s += '\n';
		}
		return s;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Start with size 100 and increase by powers of 2 (e.g. 100, 200, 400, 800, ...).");
		System.out.print("Checkerboard size? ");
		int size = in.nextInt();
		System.out.println("Built with StringBuilder:");
		long start = System.currentTimeMillis();
		System.out.println(getCheckerboard(size));
		System.out.printf("Time: %d ms\n\n", System.currentTimeMillis() - start);
		System.out.println("Built with concat:");
		start = System.currentTimeMillis();
		System.out.println(getCheckerboardConcat(size));
		System.out.printf("Time: %d ms\n\n", System.currentTimeMillis() - start);
		in.close();
	}

}

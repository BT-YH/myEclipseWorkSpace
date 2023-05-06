
public class LineColTester {
	public static void main(String[] args) {
		LineCol g = new LineCol();
		int[] array = {0, 0};
		int move = g.getWinningMove(array, 1);
		System.out.println(move);
	}
}

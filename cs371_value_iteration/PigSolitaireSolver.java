
public class PigSolitaireSolver {

	private double p[][][];
	private boolean[][][] computed, roll;
	private int goal, turns;
	
	public PigSolitaireSolver(int goal, int turns) {
		this.goal = goal;
		this.turns = turns;
		
		p = new double[goal][turns][goal];
		computed = new boolean[goal][turns][goal];
		roll = new boolean[goal][turns][goal];
		
		for (int i = 0; i < goal; i++)
			for (int j = 0; j < turns; j++)
				for (int k = 0; i + k < goal; k++)
					pWin(i, j, k);
	}
	
	public double pWin(int i, int j, int k) {
		if (i + k >= goal) return 1.0;
		if (j >= turns) return 0.0;
		if (computed[i][j][k]) return p[i][j][k];
		
		// Prob win
		double pWinRoll = (pWin(i, j+1, 0) + pWin(i, j, k+2) + pWin(i, j, k+3) + pWin(i, j, k+4) + pWin(i, j, k+5) + pWin(i, j, k+6)) / 6;
		// Prob win
		double pWinHold = pWin(i + k, j + 1, 0);
		
		p[i][j][k] = Math.max(pWinRoll, pWinHold);
		
		computed[i][j][k] = true;
		
		roll[i][j][k] = pWinRoll > pWinHold;
		return p[i][j][k];
	}
	
	public boolean shouldRoll(int i, int j, int k) {
		if (i+k >= goal) return false;
		if (j+1 >= turns) return true;
		return roll[i][j][k];
	}
	
	public static void main(String[] args) {
		int goal = 100, turns = 10;
		
		
		PigSolitaireSolver solver = new PigSolitaireSolver(goal, turns);
//		for (int j = 0; j < turns; j++) {
//			for (int i = 0; i < goal; i++) {
//				for (int k = 0; i + k < goal; k++) {
//					System.out.printf("(%d, %d, %d): {%5f, %b}\n", i, j, k, solver.pWin(i, j, k), solver.shouldRoll(i, j, k));
//				}
//			}
//		}
		
		
		int n = 0;
		while (new PigSolitaireSolver(goal, n++).pWin(0, 0, 0) < .5);
		System.out.println(new PigSolitaireSolver(goal, --n).pWin(0, 0, 0));
		System.out.println(n);
	}
}

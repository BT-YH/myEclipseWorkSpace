import java.util.Arrays;

public class PigSolver {

	private int goal;
	private double epsilon;

	private double[][][] p;
	private boolean[][][] roll;

	public PigSolver(int goal, double epsilon) {
		this.goal = goal;
		this.epsilon = epsilon;
		p = new double[goal][goal][goal];
		roll = new boolean[goal][goal][goal];
		valueIterate();
	}

	private void valueIterate() {
		double delta;
		do {
			delta = 0;
			for (int i = 0; i < goal; i++)
				for (int j = 0; j < goal; j++)
					for (int k = 0; k < goal - i; k++) {
						double oldProb = p[i][j][k];
						
						double probRoll = 0;
						for (int f = 1; f <= 6; f++) {
							if (f == 1)
								probRoll += (1 - pWin(j, i, 0));
							else
								probRoll += pWin(i, j, k+f);
						}
						probRoll /= 6.0;
						double probHold = (1 - p[j][i+k][0]);
						
						p[i][j][k] = Math.max(probRoll, probHold);
						roll[i][j][k] = probRoll > probHold;
						
						double change = Math.abs(p[i][j][k] - oldProb);
						delta = Math.max(delta, change);
					}
			System.out.println(p[0][0][0] + ", " + delta);
		} while (delta >= epsilon);
	}

	public double pWin(int i, int j, int k) {
		if (i + k >= goal) return 1.0;
		if (j >= goal) return 0.0;
		
		double probRoll = (1 - p[j][i][0]);
		for (int f = 2; f <= 6; f++)
			if (i + k + f >= goal)
				probRoll += 1.0;
			else
				probRoll += p[i][j][k+f];
		probRoll /= 6;
		double probHold = (1 - p[j][i+k][0]);

		if (probRoll >= probHold) {
			roll[i][j][k] = true;
			return probRoll;
		} else
			roll[i][j][k] = false;
		
		return p[i][j][k];
	}

	public boolean shouldRoll(int i, int j, int k) {
		return roll[i][j][k];
	}

	public static void main(String[] args) {
		int goal = 100;
		PigSolver solver = new PigSolver(goal, 0.00001);
//		for (int j = 0; j < goal; j++) {
//			for (int i = 0; i < goal; i++) {
//				for (int k = 0; i + k < goal; k++) {
//					System.out.printf("(%d, %d, %d): {%5f, %b}\n", i, j, k, solver.pWin(i, j, k), solver.shouldRoll(i, j, k));
//				}
//			}
//		}
	}
}

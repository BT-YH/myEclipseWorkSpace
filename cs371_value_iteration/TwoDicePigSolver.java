import java.util.Arrays;

public class TwoDicePigSolver {

	private int goal;
	private double theta;

	private double[][][] p;
	private boolean[][][] roll;

	public TwoDicePigSolver(int goal, double theta) {
		this.goal = goal;
		this.theta = theta;
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
					for (int k = 0; k < goal-i; k++) {
						double oldProb = p[i][j][k];
						double probRoll = 0;
						for (int f = 1; f <= 6; f++)
							for (int h = 1; h <= 6; h++) {
								if (h == 1 && f == 1)
									probRoll += (1 - pWin(j, 0, 0));
								else if (h == 1 || f == 1)
									probRoll += (1 - pWin(j, i, 0));
								else
									probRoll += pWin(i, j, k+f+h);
							}
						probRoll /= 36;
						double probHold = 1 - pWin(j, i+k, 0);
						
						p[i][j][k] = Math.max(probRoll, probHold);
						roll[i][j][k] = probRoll > probHold;
						
						double change = Math.abs(p[i][j][k] - oldProb);
						delta = Math.max(delta, change);
					}
			System.out.println(p[0][0][0] + ", " + delta);
		} while (delta >= theta);
	}

	public double pWin(int i, int j, int k) {
		if (i + k >= goal) return 1.0;
		if (j >= goal) return 0.0;
		return p[i][j][k];
	}
	
	public static void main(String[] args) {
		int goal = 100;
		TwoDicePigSolver solver = new TwoDicePigSolver(goal, 0.00001);
//		for (int j = 0; j < goal; j++) {
//			for (int i = 0; i < goal; i++) {
//				for (int k = 0; i + k < goal; k++) {
//					System.out.printf("(%d, %d, %d): {%5f, %b}\n", i, j, k, solver.pWin(i, j, k), solver.roll[i][j][k]);
//				}
//			}
//		}
	}
}

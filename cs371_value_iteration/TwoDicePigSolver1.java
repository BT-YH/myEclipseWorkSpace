import java.util.Arrays;

public class TwoDicePigSolver1 {

	private int goal;
	private double theta;

	private double[][][] p;
	private boolean[][][] roll;

	public TwoDicePigSolver1(int goal, double theta) {
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
			for (int i = 0; i < p.length; i++)
				for (int j = 0; j < p[i].length; j++)
					for (int k = 0; k < p[i][j].length; k++) {
						double newV = pWin(i, j, k);
						double change = Math.abs(p[i][j][k] - newV);
						p[i][j][k] = newV;
						delta = Math.max(delta, change);
					}
			System.out.println(p[0][0][0] + ", " + delta);
		} while (delta >= theta);
	}

	public double pWin(int myScore, int otherScore, int turnScore) {
		if (myScore+turnScore >= goal) return 1.0;
		if (otherScore >= goal) return 0.0;

		double probRoll = 10*(1 - p[otherScore][myScore][0]) + (1 - p[otherScore][0][0]);
		for (int f = 2; f <= 6; f++)
			for (int h = 2; h <= 6; h++)
				if (myScore+turnScore+f+h >= goal)
					probRoll += 1.0;
				else
					probRoll += p[myScore][otherScore][turnScore+f+h];
		probRoll /= 36;
		double probHold = (1 - p[otherScore][myScore+turnScore][0]);
		
		roll[myScore][otherScore][turnScore] = probRoll >= probHold;
		return roll[myScore][otherScore][turnScore] ? probRoll : probHold;
	}
	
	public static void main(String[] args) {
		int goal = 100;
		TwoDicePigSolver1 solver = new TwoDicePigSolver1(goal, 1.00000e-08);
//		for (int j = 0; j < goal; j++) {
//			for (int i = 0; i < goal; i++) {
//				for (int k = 0; i + k < goal; k++) {
//					System.out.printf("(%d, %d, %d): {%5f, %b}\n", i, j, k, solver.pWin(i, j, k), solver.roll[i][j][k]);
//				}
//			}
//		}
	}
}
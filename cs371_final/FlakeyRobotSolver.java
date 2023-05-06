import java.util.Arrays;
import java.util.Random;

public class FlakeyRobotSolver {
	
	public static final Random rand = new Random();
	public static final int GOAL = 0;
	public static final double GAMMA = 1.0, EPS = 1e-14; // Discount and convergence threshold constants
	
	
	private int size;
	private double probF;
	private double[] pos_costs;
	
	public FlakeyRobotSolver(int size, double probF) {
		this.size = size;
		this.probF = probF;
		pos_costs = new double[size + 1]; 
		for (int i = 0; i < size; i++) {
			pos_costs[i] = i;
		}
	}
	
	public void valueIterate() {
		double maxChange;
		do {
			maxChange = 0;
			for (int i = 1; i <= size; i++) {
				double oldV = cost(i);
				double newV = 0;
				
				double rightCost = (i == size) ? cost(i) : cost(i+1);
				double leftCost = (i == 0) ? cost(i) : cost(i-1);
				
				double rightV = (1 - probF) * (GAMMA * rightCost) + probF * (GAMMA * leftCost) - 1;
				double leftV = (1 - probF) * (GAMMA * leftCost) + probF * (GAMMA * rightCost) - 1;
				
				newV = Math.max(rightV, leftV);
				pos_costs[i] = newV;
				maxChange = Math.abs(newV - oldV);
				
			}
		} while (maxChange > EPS);
	}
	
	public double cost(int pos) {
		if (pos == 0) {
			return 0;
		} else {
			return pos_costs[pos];
		}
	}
	
	public static void main(String[] args) {
		FlakeyRobotSolver solver = new FlakeyRobotSolver(12, .7);
		solver.valueIterate();
		System.out.println(Arrays.toString(solver.pos_costs));
		for (int i = 0; i < solver.pos_costs.length; i++) {
			System.out.printf("\n%-3d: %13.10f", i, solver.pos_costs[i] );
		}
	}
	
}

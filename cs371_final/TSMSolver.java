import java.util.Arrays;
import java.util.Random;

public class TSMSolver {
	
	private static Random rand = new Random();
	private int iterations;
	private int numVouchers;
	private int numCities;
	private double acceptRate;
	private City[] cities;
	
	public TSMSolver(int iterations, int numVouchers, double acceptRate, City[] cities) {
		this.iterations = iterations;
		this.numVouchers = numVouchers;
		this.acceptRate = acceptRate;
		this.cities = cities;
		numCities = cities.length;
	}
	
	public double evaluate() {
		
		// initialize a path
		City[] path = new City[numCities + 1];
		System.out.println(Arrays.toString(path));
		double cost = 0;
		path[0] = cities[0];
		cities[0].visit();
		
		for (int i = 1; i < path.length - 1; i++) {
			int index = 0;
			do {
				index = rand.nextInt(numCities - 1) + 1;
			} while (cities[index].isVisited);
			
			System.out.println(index);
			if (!cities[index].isVisited) {
				cities[index].visit();
				path[i] = cities[index];
				cost += path[i].euclideanDist(path[i-1]);
			}
		}
		
		path[path.length - 1] = cities[0];
		cost += path[path.length-2].euclideanDist(path[path.length - 1]);
		
		System.out.println(Arrays.toString(path));
		System.out.println(cost);
		
		double minCost = cost;
		City[] minPath = clonePath(path);
		
		
		for (int j = 0; j < iterations; j++) {
			if (j % 1000000 == 0) {
				System.out.println(minCost + "\t" + cost);
			}
			
			// randomly flip two cities
			int rand1 = rand.nextInt(numCities - 1) + 1; //exclude src and dst
			int rand2 = rand.nextInt(numCities - 1) + 1;
			
			while (rand1 == rand2) {
				rand2 = rand.nextInt(numCities - 1) + 1;
			}
			
			City temp = path[rand1];
			path[rand1] = path[rand2];
			path[rand2] = temp;
			
			double nextCost = computeCost(path, numVouchers);
			if (nextCost <= cost || rand.nextDouble() < acceptRate) {
				cost = nextCost;
				if (nextCost < minCost) {
					minCost = nextCost;
					minPath = clonePath(path);
				}
			} else { //undo
				path[rand2] = path[rand1];
				path[rand1] = temp;
			}
		}
		
		System.out.println(Arrays.toString(minPath));
		
		return minCost;
	}
	
	private static City[] clonePath(City[] path) {
		City[] clone = new City[path.length];
		
		for (int i = 0; i < path.length; i++) {
			clone[i] = (City) path[i].clone();
		}
		return clone;
	}
	
	private static double computeCost(City[] path, int numVouchers) {
		double[] costs = new double[path.length - 1];

		for (int i = 1; i < path.length; i++) {
			costs[i - 1] = path[i].euclideanDist(path[i-1]);
		}
		
		double cost = 0;
		for (int i = 0; i < costs.length; i++) {
			cost += costs[i];
		}
		
		int counter = 0;
		double max = 0;
		
		while (counter < numVouchers) {
			for (int i = 0; i < costs.length; i++) {
				if (costs[i] > max) {
					max = costs[i];
					costs[i] = 0;
				}
			}
			cost -= max;
			max = 0;
			counter++;
		}
		
		return cost;
	}
	
	private static class City implements Cloneable{
		double xVal;
		double yVal;
		boolean isVisited;
		
		public City(double x, double y) {
			xVal = x;
			yVal = y;
			isVisited = false;
		}
		
		public void visit() {
			isVisited = true;
		}
		
		public double euclideanDist(City dst) {
			return Math.sqrt((xVal - dst.xVal)*(xVal - dst.xVal) + (yVal - dst.yVal)*(yVal - dst.yVal));
		}
		
		@Override
		public City clone() {
			return new City(xVal, yVal);
		}
		
		@Override
		public String toString() {
			return String.format("(%.15f, %.15f) - %s", xVal, yVal, isVisited);
		}
	}
	
	public static void main(String[] args) {
		double[][] citiesIn = {{0.11097180242499916, 0.03320740704434211},
							   {0.4357555149654383,  0.11851360714746517},
							   {0.16408133646739165, 0.8965683523168885},
							   {0.3219101753921322, 0.7736435343095579},
							   {0.37059859461054756, 0.6178218490619197},
							   {0.8161704504015653, 0.7390815675672229},
							   {0.6408707908935043, 0.09585623192284287},
							   {0.8843856096502889, 0.7045099736524251},
							   {0.08910768558888083, 0.5757683674985833},
							   {0.6752430713785342, 0.01972619139308851}};
		
		City[] myCities = new City[citiesIn.length];
		
		for (int i = 0; i < citiesIn.length; i++) {
			myCities[i] = new City(citiesIn[i][0], citiesIn[i][1]);
		}
		
		System.out.println(Arrays.toString(myCities));
		
		TSMSolver solver = new TSMSolver(10000000, 2, 0.1, myCities);
		double result = solver.evaluate();
		System.out.println("Final result: " + result);
	}
}

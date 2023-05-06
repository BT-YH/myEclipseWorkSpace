import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;

public class SetCollectionSolver implements Cloneable {
	
	public static final int UNREACHED = 100000;
//	public static int seed = 1;
	public static Random rand = new Random();
	
	public static int n;
	public static int m; // number of lots
	public static BitSet[] lots;
	public static double[] costs;
	private boolean[] mySelection; // bitSets collected
	private boolean[] itemsCollected;
	
	private int itemIndex; // item to remove
	private double cost;
	
	public SetCollectionSolver(int numItems, BitSet[] lots, double[] costs) {
		n = numItems;
		SetCollectionSolver.lots = lots;
		SetCollectionSolver.costs = costs;
		m = lots.length;
		mySelection = new boolean[m];
		itemsCollected = new boolean[n];
		for (int i = 0; i < m; i++) {
			mySelection[i] = true; // start with purchasing every single lot
		}
		
	}
	
	/**
	 * checking the item Collected is full
	 * @return
	 */
	private boolean full() { 
		for (int i = 0; i < n; i++) {
			if (!itemsCollected[i]) {
				return false;
			} 
		}
		return true;
	}
	
	public void step() { // taking out sets
		
		itemIndex = rand.nextInt(m);
		mySelection[itemIndex] = false; 
	}
	

	public void undo() {
		mySelection[itemIndex] = true; // puting the set back
	}
	
	
	public double energy() {
		
		cost = 0;
		itemsCollected = new boolean[n];
		// computing for itemsCollected
		for (int i = 0; i < m; i++) {
			if (mySelection[i]) {
				BitSet lot = lots[i];
				for (int j = 0; j < n; j++) {
					if (lot.get(j))
						itemsCollected[j] = lot.get(j);
				}
				cost += costs[i];
			}
		}
		
		if (!full()) {
			return UNREACHED;
		} 
		
		return cost;
	}
	
	
	public static boolean[] solve(int numItems, BitSet[] lots, double[] costs) {
		// create new Solver Object
		// first check if full collection is possible
		// if not return false boolean Array
		// if yes:
		// minState = solver.search
		// return minState.mySelection;
		
		SetCollectionSolver solver = new SetCollectionSolver(numItems, lots, costs);
		boolean[] results = new boolean[m];
		
		// initializing mySelection
		for (int i = 0; i < m; i++) {
			if (solver.mySelection[i]) {
				BitSet lot = lots[i];
				for (int j = 0; j < n; j++) {
					if (lot.get(j))
						solver.itemsCollected[j] = lot.get(j);
				}
			}
		}
		
		if (!solver.full()) {
			return results;
		} 
		
		SetCollectionSolver minState = solver.search(10000, .5, 0.999998);
		System.out.println(minState);
		System.out.println(Arrays.toString(minState.mySelection));
		System.out.println(Arrays.toString(minState.itemsCollected));
		System.out.println("cost: " + minState.cost);
		return minState.mySelection;
	}
	
	
	
	public SetCollectionSolver search(int iterations, double temperature, double decayRate) {
		Random random = new Random(); //***
		double minEnergy = Integer.MAX_VALUE;
		double energy = energy();
		SetCollectionSolver minState = null;
		for (int i = 0; i < iterations; i++) {
//			if (i % 1000 == 0)
//				System.out.println(minEnergy + "\t" + energy);
			step();
			double nextEnergy = energy();
			if (nextEnergy <= energy || random.nextDouble() < Math.exp((energy - nextEnergy) / temperature)) { //***
				energy = nextEnergy;
				if (nextEnergy < minEnergy) { 
					minState = (SetCollectionSolver) clone();
					minEnergy = nextEnergy;
				}
			} else
				undo();
			
			temperature *= decayRate; //***
		}
		return minState;
	}
	
	public SetCollectionSolver search(int iterations, double acceptRate) {
		Random random = new Random();
		double minEnergy = Integer.MAX_VALUE;
		double energy = energy();
		SetCollectionSolver minState = null;
		for (int i = 0; i < iterations; i++) {
//			if (i % 1000 == 0)
//				System.out.println(minEnergy + "\t" + energy);
//			System.out.println(state);
			step();
			double nextEnergy = energy();
			if (nextEnergy <= energy || random.nextDouble() < acceptRate) {
				energy = nextEnergy;
				if (nextEnergy < minEnergy) { 
					minState = (SetCollectionSolver) clone();
					minEnergy = nextEnergy;
				}
			} else {
				undo();
			}
		}
		return minState;
	}
	
	public SetCollectionSolver clone() {
		try {
			SetCollectionSolver copy = (SetCollectionSolver) super.clone();
			copy.mySelection = (boolean[]) mySelection.clone();
			copy.itemsCollected = (boolean[]) itemsCollected.clone();
			return copy;

		} catch(Exception e) {}
		return null;
	}
	
	public static void main(String[] args) {
		BitSet a = new BitSet(9); // 00 - 1
		BitSet b = new BitSet(9); // 10 - 4
		BitSet c = new BitSet(9); // 01 - 4
		BitSet d = new BitSet(9); // 11 - 9
		BitSet e = new BitSet(9); // 01 - 9
		BitSet f = new BitSet(9); // 10 - 5
		BitSet g = new BitSet(9); // 11 - 4
		BitSet h = new BitSet(9);
		BitSet i = new BitSet(9);
		BitSet j = new BitSet(9);

		a.set(0);
		a.set(4);
		a.set(5);
		
		b.set(1);
		b.set(7);
		b.set(9);
		
		c.set(2);
		c.set(7);
		c.set(9);
		
		d.set(2);
		d.set(3);
		d.set(4);
		
		e.set(2);
		e.set(4);
		e.set(8);
		
		f.set(5);
		f.set(6);
		f.set(7);
		
		g.set(1);
		g.set(4);
		g.set(6);
		
		h.set(3);
		h.set(4);
		h.set(7);
		
		i.set(4);
		i.set(7);
		i.set(8);
		
		j.set(7);
		j.set(8);
		j.set(9);
		
		BitSet[] testlots = new BitSet[10];
		testlots[0] = a;
		testlots[1] = b;
		testlots[2] = c;
		testlots[3] = d;
		testlots[4] = e;
		testlots[5] = f;
		testlots[6] = g;
		testlots[7] = h;
		testlots[8] = i;
		testlots[9] = j;
		
		double[] costs = {11.407599191217091, 14.295361734747463, 24.24276237128449, 
				11.291312305309468, 16.95372359864754, 12.949763223697971, 
				10.918129211276337, 14.34573710858607, 17.829498875860185, 22.06983356062065};
		int numItems = 10;
		
		boolean[] result = solve(numItems, testlots, costs);
		System.out.println(Arrays.toString(result));
		// I expect my selection to select g, which give a cost 4
		// the returned boolean array should only have true at the last index
		
		
	}
}

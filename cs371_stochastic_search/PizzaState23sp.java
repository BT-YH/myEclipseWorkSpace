import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class PizzaState23sp implements State, Cloneable {

	static int numPeople = 11, numToppings = 8, slicesPerPizza = 8;
	static String[] toppings = ("cheese pepperoni sausage ham beef"
			+ " mushroom pineapple onion").split(" ");
	static int[] slices = {3, 3, 3, 3, 5, 4, 4, 3, 4, 4, 2};
	static int[][] accept = {
		{0, 1, 2, 3, 4, 5, 7},
		{0, 1, 4, 5},
		{1, 2, 3, 4, 5, 6, 7},
		{0, 1, 2, 3, 4},
		{0, 5, 6, 7},
		{1, 2, 7},
		{0, 1, 2, 5},
		{0, 1, 2, 5, 6, 7},
		{3, 4, 6},
		{0, 1, 2, 3, 4},
		{0, 1, 3}
	};
	static ArrayList<Set<Integer>> acceptSets = new ArrayList<>();
	static int numPizzas, numHalves;
	
	static {
		for (int i = 0; i < numPeople; i++) {
			TreeSet<Integer> acceptSet = new TreeSet<>();
			for (int topping : accept[i])
				acceptSet.add(topping);
			acceptSets.add(acceptSet);
		}
		int numSlices = 0;
		for (int s : slices)
			numSlices += s;
		numPizzas = (int) Math.ceil((double) numSlices / slicesPerPizza);
		numHalves = numPizzas * 2;
	}
	
	
	int prevI, prevTopping;
	int[] order;
	Double energy = null, prevEnergy;
	
	public PizzaState23sp() {
		order = new int[numHalves];
		for (int i = 0; i < order.length; i++)
			order[i] = i % numToppings;
	}
	
	public PizzaState23sp(PizzaState23sp original) {
		this.order = original.order.clone();
		this.energy = original.energy;
	}
	
	
	@Override
	public void step() {
		prevI = (int) (numHalves * Math.random());
		prevTopping = order[prevI];
		prevEnergy = energy;
		while (order[prevI] == prevTopping)
			order[prevI] = (int) (numToppings * Math.random());
		energy = null;
	}

	@Override
	public void undo() {
		order[prevI] = prevTopping;
		energy = prevEnergy;
	}

	@Override
	public double energy() {
		if (energy == null) {
			int trials = 20;
			ArrayList<Double> badnesses = new ArrayList<>();
			for (int t = 0; t < trials; t++)
				badnesses.add(simNomNom());
			Collections.sort(badnesses);
			energy = badnesses.get(trials / 2); 
			
			// add bonus for each distinct topping included
			Set<Integer> toppingsSet = new TreeSet<Integer>();
			for (int topping : order)
				toppingsSet.add(topping);
			energy -= 0.1 * toppingsSet.size();
			
		}
		return energy;

		// a very rough draft energy function
		// that simulates a random line of people trying to get
		// the first acceptable slice
	}
	
	public double simNomNom() {
		double badness = 0;
		// simulate people eating, penalizing no acceptable topping
		ArrayList<Integer> line = new ArrayList<>();
		for (int i = 0; i < numPeople; i++)
			line.add(i);
		Collections.shuffle(line);
		ArrayList<Integer> toppings = new ArrayList<>();
		for (int i = 0; i < order.length; i++)
			toppings.add(order[i]);
		int[] slicesLeft = new int[order.length];
		Arrays.fill(slicesLeft, slicesPerPizza / 2);
		int[] slicesWanted = slices.clone();
		boolean done = false;
		while (!done) {
			done = true;
			for (int person : line) {
				if (slicesWanted[person] == 0)
					continue;
				boolean served = false;
				for (int i = 0; i < order.length; i++) {
					int slices = slicesLeft[i];
					if (slices == 0) continue;
 					int topping = toppings.get(i);
 					if (acceptSets.get(person).contains(topping)) {
 						served = true;
 						slicesLeft[i]--;
 						slicesWanted[person]--;
 					}
				}
				if (!served) {
					badness += slicesWanted[person];
					slicesWanted[person] = 0;
				}
			}
		}
		return badness;
	}
	
	public Object clone() {
		return new PizzaState23sp(this);
	}

	public String toString() {
		int[] orderCopy = order.clone();
		Arrays.sort(orderCopy);
		StringBuilder sb = new StringBuilder();
		for (int topping : orderCopy)
			sb.append(toppings[topping] + " ");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		final int ITERATIONS = 10000;
		State state = new PizzaState23sp();
		State minState = new SimulatedAnnealer(state, 1000, .99998).search(ITERATIONS); 
		System.out.println(minState);
		System.out.println(minState.energy());
	}

}

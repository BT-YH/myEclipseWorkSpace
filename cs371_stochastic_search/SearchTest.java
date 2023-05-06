
public class SearchTest {

	public static void main(String[] args) {
		final int ITERATIONS = 1000000;
		
//		State state = new BinPackingProblem();
//		State state = new Rastrigin();
		State state = new ManhattanDistribution(13, 10, 20);
		
//		State minState = new HillDescender(state).search(ITERATIONS);
		State minState = new HillDescender(state, 0.001).search(ITERATIONS);
//		State minState = new SimulatedAnnealer(state, 10000, .99999).search(ITERATIONS);
		
		System.out.println(minState);
		System.out.println("Energy: " + minState.energy());

	}

}

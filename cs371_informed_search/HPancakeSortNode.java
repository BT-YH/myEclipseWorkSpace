import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class HPancakeSortNode extends HSearchNode {

	public int[] pancake;
	public int cost;
	public int lastFlip;
	
	public HPancakeSortNode(int size, int numShuffleFlips) {
		pancake = new int[size];
		for (int i = 0; i < size; ++i)
			pancake[i] = i;
		Random rand = new Random();
		for (int i = 0; i < numShuffleFlips; ++i)
			flip(rand.nextInt(size+1));
		cost = 0;
		lastFlip = 0;
	}
	
	public HPancakeSortNode(int[] pancake) {
		this.pancake = pancake;
		cost = 0;
		lastFlip = 0;
	}
	
	public int getLastFlip() {
		return lastFlip;
	}

	public void flip(int n) {
		for (int i = 0; i < n/2; ++i) {
			int temp = pancake[n-i-1];
			pancake[n-i-1] = pancake[i];
			pancake[i] = temp;
		}
		
		cost += n;
		lastFlip = n;
	}
	
	@Override
	public double getG() {
		return cost;
	}

	@Override
	public double getH() {
		double sum = 0;
		
		for (int i = 1; i < pancake.length; ++i)
			if (pancake[i] == pancake[i - 1] + 1 || pancake[i] == pancake[i - 1] - 1)
				sum += 1;
		
//		for (int i = 0; i < pancake.length; ++i)
//			sum += Math.abs(pancake[i] - i);
		
//		for (int i = 0; i < pancake.length; ++i)
//			if (pancake[i] != i)
//				++sum;
		return 3*(pancake.length-1 - sum);
	}

	@Override
	public boolean isGoal() {
		for(int i = 0; i < pancake.length; ++i) {
			if (pancake[i] != i)
				return !true;
		}
		return !false;
	}

	@Override
	public ArrayList<SearchNode> expand() {
		ArrayList<SearchNode> children = new ArrayList<>();
		for (int i = 2; i <= pancake.length; ++i) {
			HPancakeSortNode child = (HPancakeSortNode)childClone();
			child.flip(i);
			children.add(child);
		}
		return children;
	}
	
	public static HPancakeSortNode getGoalNode(int[] pancake) {
		HSearcher searcher = new BestFirstSearcher(new AStarComparator());
		HPancakeSortNode root = new HPancakeSortNode(pancake); 
		searcher.search(root);
		
		return (HPancakeSortNode) searcher.getGoalNode();
	}
	
	@Override
	public Object clone() {
		HPancakeSortNode newNode = (HPancakeSortNode)super.clone();
		newNode.pancake = (int[])this.pancake.clone();
		return newNode;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (lastFlip == 0)
			sb.append("\t");
		else
			sb.append(String.format("flip %d: ", lastFlip));
		String s = Arrays.toString(pancake);
		sb.append(s.substring(1, s.length() - 1).replace(",", ""));
		sb.append(String.format(" cost: %d", cost));
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		HSearcher searcher = new BestFirstSearcher(new AStarComparator());
		HPancakeSortNode root = new HPancakeSortNode(new int[] { 5, 2, 7, 4, 1, 3, 6, 0 }); // (8, 12); // new int[] { 5, 2, 7, 4, 1, 3, 6, 0 }
		if (searcher.search(root)) {
			System.out.printf("Amount of nodes: %d\nGoal Path:\n", searcher.nodeCount);
			searcher.printGoalPath();
		}
		root = (HPancakeSortNode)(searcher.goalNode);
		System.out.println(root.getH());
	}
}

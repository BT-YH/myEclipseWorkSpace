import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


// Purpose: To generate alpha-beta pruning problems that have a high likelihood of presenting tie pruning cases

public class GenAlphaBetaProblem { 
	Random random = new Random();
	public static int MAX = 0, MIN = 1;
	public static int[] state;
	public static Node root;
	public static boolean[] visited;
	
	class Node {
		int player;
		ArrayList<Node> children = new ArrayList<Node>();
		int terminalNodeIndex = -1;
		double v;
		
		Node(int player) {
			this.player = player;
		}
		
		Node(int player, int index) {
			this.player = player;
			this.terminalNodeIndex = index;
		}
		
		double eval(double alpha, double beta) {
			if (terminalNodeIndex >= 0) {
				visited[terminalNodeIndex] = true;
				return state[terminalNodeIndex];
			}
			if (player == MAX) {
				v = Double.NEGATIVE_INFINITY;
				for (Node child : children) {
					v = Math.max(v, child.eval(alpha, beta));
					if (v >= beta)
						return v;
//					if (v > alpha && alpha != Double.NEGATIVE_INFINITY)
//						System.out.println("Alpha increased to " + v);
					alpha = Math.max(alpha, v);
				}
			}
			else { // MIN
				v = Double.POSITIVE_INFINITY;
				for (Node child : children) {
					v = Math.min(v, child.eval(alpha, beta));
					if (v <= alpha)
						return v;
					beta = Math.min(beta, v);
				}
			}
			return v;
		}
	}
	
	public void createNodes() {
		ArrayList<Node> terminals = new ArrayList<Node>();
		for (int i = 0; i < state.length; i++)
			terminals.add(new Node(0, i));
		ArrayList<Node> minnodes = new ArrayList<Node>();
		for (int i = 0; i < terminals.size() / 3; i++) {
			Node node = new Node(MIN);
			node.children.add(terminals.get(3 * i));
			node.children.add(terminals.get(3 * i + 1));
			node.children.add(terminals.get(3 * i + 2));
			minnodes.add(node);
		}
		root = new Node(MAX);
		root.children = minnodes;
	}
	
	public void swap() {
		int i = 0, j = 0;
		while (i == j) {
			i = random.nextInt(9);
			j = random.nextInt(9);
		}
		int tmp = state[i];
		state[i] = state[j];
		state[j] = tmp;
	}
	
	public void optimize() {
		for (int i = 0; i < 1000; i++)
			swap(); // shuffle
		int numPruned = 0;
		
		while (numPruned < 2) {
			swap();
			visited = new boolean[9];
			root.eval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			numPruned = 0;
			for (boolean wasVisited : visited)
				if (!wasVisited)
					numPruned++;
		}
		
		System.out.println((int) root.v);
		for (Node child : root.children)
			System.out.print((int) child.v + " ");
		System.out.println();

		for (int i = 0; i < visited.length; i++)
			System.out.printf("%s=%d ", "" + (char) ('A' + i), state[i]);
		System.out.println();
		System.out.println("Pruned: ");
		for (int i = 0; i < visited.length; i++)
			if (!visited[i])
				System.out.printf("%s", "" + (char) ('A' + i));
		System.out.println();
	}
	
	public void run() {
		state = new int[9];
		for (int i = 0; i < state.length; i++)
				state[i] = random.nextInt(5) + 1;
		createNodes();
		optimize();
	}
	
	public static void main(String[] args) {
		new GenAlphaBetaProblem().run();
	}

}

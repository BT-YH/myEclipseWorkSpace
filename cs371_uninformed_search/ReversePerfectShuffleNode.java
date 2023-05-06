import java.util.ArrayList;
import java.util.Scanner;

public class ReversePerfectShuffleNode extends SearchNode {
	
	public static final int SIZE = 52;
	
	private int[] deck;
	private ArrayList<Integer> top;
	private ArrayList<Integer> bottom;
	
	public ReversePerfectShuffleNode() {
		deck = new int[SIZE];
		for (int i = 0; i < deck.length; i++) {
			deck[i] = i;
		}
		
		top = new ArrayList<Integer>(SIZE/2);
		bottom = new ArrayList<Integer>(SIZE/2);
	}
	
	@Override
	public boolean isGoal() {
		return (deck[4] == 0 && deck[9] == 1 && deck[14] == 2 && deck[19] == 3);
	}

	@Override
	public ArrayList<SearchNode> expand() {
		ArrayList<SearchNode> children = new ArrayList<>();
		
		ReversePerfectShuffleNode out = (ReversePerfectShuffleNode)this.clone();
		out.outShuffle();
		children.add(out);
		
		ReversePerfectShuffleNode in = (ReversePerfectShuffleNode)this.clone();
		in.inShuffle();
		children.add(in);
		
		return children;
	}
	
	private void inShuffle() {
		top.clear();
		bottom.clear();
		
		for (int i = 0; i < deck.length; i++) {
			if (i % 2 == 0) {
				bottom.add(deck[i]);
			} else {
				top.add(deck[i]);
			}
		}
		
		ArrayList<Integer> temp = new ArrayList<>(52);
		temp.addAll(top);
		temp.addAll(bottom);
		
		for (int i = 0; i < deck.length; i++) {
			deck[i] = temp.get(i);
		}
	}
	
	private void outShuffle() {
		top.clear();
		bottom.clear();
		
		for (int i = 0; i < deck.length; i++) {
			if (i % 2 == 0) {
				top.add(deck[i]);
			} else {
				bottom.add(deck[i]);
			}
		}
		
		ArrayList<Integer> temp = new ArrayList<>(52);
		temp.addAll(top);
		temp.addAll(bottom);
		
		for (int i = 0; i < deck.length; i++) {
			deck[i] = temp.get(i);
		}
	}
	
	@Override
	public Object clone() {
		ReversePerfectShuffleNode newNode = (ReversePerfectShuffleNode)super.clone();
		newNode.parent = this;
		newNode.depth = this.depth+1;
		newNode.deck = (int[])deck.clone();
//		newNode.deck = new int[SIZE];
//		for (int i = 0; i < SIZE; ++i) {
//			newNode.deck[i] = deck[i];
//		}
		return newNode;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < deck.length; i++) {
			sb.append(deck[i]);
			if (i < deck.length - 1)
				sb.append(", ");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
//		ReversePerfectShuffleNode n = new ReversePerfectShuffleNode();
//		Scanner scan = new Scanner(System.in);
//		while (scan.hasNext()) {
//			if (scan.next().equals("o"))
//				n.outShuffle();
//			else
//				n.inShuffle();
//			System.out.println(n);
//		}
		IterativeDeepeningDepthFirstSearcher searcher = new IterativeDeepeningDepthFirstSearcher();
		ReversePerfectShuffleNode rootNode = new ReversePerfectShuffleNode();
		//rootNode.outShuffle();
//		System.out.println(rootNode.toString());
		if (searcher.search(rootNode)) {
//			System.out.println("Amount of Nodes: " + searcher.nodeCount);
			searcher.printGoalPath();
		}
//		for (int i = 0; i < 52; i++) {
//			//rootNode.InShuffle();
//			rootNode.OutShuffle();
//			
//			System.out.println(i+": " + rootNode.toString());
//		}
	}
}

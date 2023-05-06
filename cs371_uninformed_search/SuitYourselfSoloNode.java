import java.util.ArrayList;
import java.util.Stack;

public class SuitYourselfSoloNode extends SearchNode {

	private static ArrayList<Stack<Integer>> board;
	private static int numSuits, numColumns;
	
	private int[] topIndices;
	
	public SuitYourselfSoloNode(int numSuits, int numRanks, int numColumns, long seed) {
		SuitYourselfSoloNode.numSuits = numSuits;
		SuitYourselfSoloNode.numColumns = numColumns;
		board = SuitYourselfSoloGenerator.generate(numSuits, numRanks, numColumns, seed);
		topIndices = new int[numColumns];
		for (int i = 0; i < numColumns; i++) {
			topIndices[i] = board.get(i).size() - 1; // modification made (previous bug?)
		}
	}
	
	@Override
	public boolean isGoal() {
		for (int i = 0; i < numColumns; i++)
			if (topIndices[i] >= 0)
				return false;
		return true;
	}


	@Override
	public ArrayList<SearchNode> expand() {
		ArrayList<SearchNode> children = new ArrayList<>();
		for (int i = 0; i < numSuits; i++) {
			boolean rankContained = false;
			for (int j = 0; j < numColumns && !rankContained; j++)
				if (topIndices[j] >= 0 && (board.get((j)).get(topIndices[j]) == i)) 
						rankContained = true;
			if (!rankContained)
				continue;
			
			SuitYourselfSoloNode child = (SuitYourselfSoloNode)this.clone();
			for (int j = 0; j < numColumns; j++)
				while (child.topIndices[j] >= 0 && 
				(board.get(j)).get(child.topIndices[j]) == i)
					--child.topIndices[j];
			children.add(child);
		}
		
		return children;
	}
	
	@Override
	public Object clone() {
		SuitYourselfSoloNode newNode = (SuitYourselfSoloNode)super.clone();
		newNode.parent = this;
		newNode.depth = depth + 1;
		newNode.topIndices = (int[])topIndices.clone();
		return newNode;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numColumns; i++) {
			sb.append(i + ":");
			for (int j = 0; j <= topIndices[i]; j++)
				sb.append(" " + (board.get(i)).get(j));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Searcher s = new BreadthFirstSearcher();
		SuitYourselfSoloNode root = new SuitYourselfSoloNode(4, 13, 5, 12);
		if (s.search(root)) {
			System.out.println("Amount of Nodes: " + s.nodeCount);
			System.out.println("Optimal Depth: " + s.goalNode.depth);
			s.printGoalPath();
		} else {
			System.out.println("No solution was found in " + s.nodeCount + " nodes.");
		}
	}
}
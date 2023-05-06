import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class HTilePuzzleNode extends HSearchNode {
	private static Random random = new Random();
	int size;
	int[][] tile;
	int zeroRow, zeroCol;
	
	public HTilePuzzleNode(int size, int numShuffles) {
		this.size = size;
		int i = 0;
		tile = new int[size][size];
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				tile[r][c] = i++;
		// shuffle
		for (i = 0; i < numShuffles; i++)
			if (!move(random.nextInt(4)))
				i--;
	}
	
	private boolean move(int dir) {
		final int[] dRow = {0, -1, 0, 1};
		final int[] dCol = {1, 0, -1, 0};
		int r2 = zeroRow + dRow[dir];
		int c2 = zeroCol + dCol[dir];
		if (r2 < 0 || r2 >= size || c2 < 0 || c2 >= size)
			return false;
		else {
			tile[zeroRow][zeroCol] = tile[r2][c2];
			tile[r2][c2] = 0;
			zeroRow = r2;
			zeroCol = c2;
			return true;
		}
	}
	
	@Override
	public boolean isGoal() {
		int i = 0;
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				if (tile[r][c] != i++)
					return false;
		return true;
	}

	@Override
	public ArrayList<SearchNode> expand() {
		ArrayList<SearchNode> children = new ArrayList<SearchNode>();
		for (int dir = 0; dir < 4; dir++) {
			HTilePuzzleNode child = (HTilePuzzleNode) childClone();
			if (child.move(dir))
				children.add(child);
		}
		return children;
	}

	public String toString() {
		int chars = new Integer(size * size - 1).toString().length();
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++)
				sb.append(String.format("%" + chars + "d ", tile[r][c]));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public HTilePuzzleNode clone() {
		HTilePuzzleNode copy = (HTilePuzzleNode) super.clone();
		copy.tile = new int[size][size];
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				copy.tile[r][c] = tile[r][c];
		return copy;
		
	}
	
	@Override
	public double getG() {
		return depth;
	}

	@Override
	public double getH() {
		int sum = 0;
		
		
		// number of misplaced tiles
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
			if (tile[i][j] == 0)
				continue;
				sum += (tile[i][j] != size*i + j) ? 1 : 0;
			}
		}
		return sum;
		
		// Manhattan distance
//		for (int i = 0; i < size; ++i) {
//			for (int j = 0; j < size; ++j) {
//				if (tile[i][j] == 0)
//					continue;
//				sum += (tile[i][j] != size*i + j) ? Math.abs((i+j) - (tile[i][j] % size + tile[i][j] / size)) : 0;
//			}
//		}
//		return sum;
		
		// sld zero position
//		return Math.sqrt(zeroRow * zeroRow + zeroCol * zeroCol);
//		return 0;
	}
	
	public static void main(String[] args) {
		HTilePuzzleNode root = new HTilePuzzleNode(4, 20);
		
		//Searcher searcher = new IterativeDeepeningDepthFirstSearcher();
		//Searcher searcher = new BestFirstSearcher(new UniformComparator());
		//Searcher searcher = new BestFirstSearcher(new GreedyComparator());
		Searcher searcher = new BestFirstSearcher(new AStarComparator());
//		if (searcher.search(root)) {
//		    // successful search
//		    System.out.println("Goal node found in " + searcher.getNodeCount() 
//				       + " nodes.");
//		    System.out.println("Goal path:");
//		    searcher.printGoalPath();
//		} else {
//		    // unsuccessful search
//		    System.out.println("Goal node not found in " 
//				       + searcher.getNodeCount() + " nodes.");
//		}
//		
		
		final int TRIALS = 100000;
		ArrayList<Integer> nodeCounts = new ArrayList<>();
		final int SIZE = 4;
		final int NUM_MOVES = 12;
		for (int i = 0; i < TRIALS; i++) {
			root = new HTilePuzzleNode(SIZE, NUM_MOVES);
			searcher.search(root);
			System.out.println("node count: " + searcher.getNodeCount());
			nodeCounts.add(searcher.getNodeCount());
		}
		long totalNodeCount = 0;
		for (int count : nodeCounts) {
			totalNodeCount += count;
		}
		System.out.printf("Average node count: %g\n",
				((double) totalNodeCount) / TRIALS);
		Collections.sort(nodeCounts);
		System.out.printf("Median node count: %d\n",
				nodeCounts.get(TRIALS / 2));
	}

}

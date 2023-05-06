import java.util.ArrayList;
import java.util.Collections;

public class ParkingNode extends HSearchNode {
	
	int size;
	int rowOfIndice;
	int colOfIndice;
	private int[][] lots;
	double cost;
	
	
	public ParkingNode(int size, int[][] array, int row, int col) {
		lots = new int[size][size];
		this.size = size;
		rowOfIndice = row;
		colOfIndice = col;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				lots[i][j] = array[i][j];
			}
		}
	}
	
	
	@Override
	public boolean isGoal() {
		if (rowOfIndice == size && colOfIndice == size)
			return true;
		return false;
	}

	@Override
	public ArrayList<SearchNode> expand() {
		ArrayList<SearchNode> children = new ArrayList<>();
		if (rowOfIndice < size && colOfIndice < size) {
			if (lots[rowOfIndice][colOfIndice] == 0) {
				ParkingNode child = (ParkingNode)this.clone();
				child.rowOfIndice = ++rowOfIndice;
				child.colOfIndice = ++colOfIndice;
				child.cost += Math.sqrt(2);
				children.add(child);
			} 
		}
		
		if (colOfIndice <= size && rowOfIndice <= size) {
			//Go right
			ParkingNode child = (ParkingNode)this.clone();
			child.colOfIndice = ++colOfIndice;
			child.cost++;
			children.add(child);
			
			//Go down
			ParkingNode child1 = (ParkingNode)this.clone();
			child1.rowOfIndice = ++rowOfIndice;
			child1.cost++;

			children.add(child1);
		}
		
		return children;
	}
	
	public String toString() {
		String str = "Row of Indice: " + rowOfIndice + "\nColumn of Indice: " + colOfIndice + "\n";
		return str;
	}

	public double getG() {
		return cost;
	}

	public double getH() {
		return 0;
	}
	
	public static void main(String[] args) {
		int[][] board = {
				{0,0,1,0},
				{0,1,0,1},
				{1,1,1,0},
				{0,1,0,0},
		};
		ParkingNode root = new ParkingNode(4, board, 0, 0);
		Searcher searcher = new BestFirstSearcher(new AStarComparator());
		
		if (searcher.search(root)) {
	    // successful search
	    System.out.println("Goal node found in " + searcher.getNodeCount() 
			       + " nodes.");
	    System.out.println("Goal cost:");
	    SearchNode goal= searcher.getGoalNode();
	    double c = ((ParkingNode) goal).cost;
	    System.out.println(c);
		} else {
	    // unsuccessful search
	    System.out.println("Goal node not found in " 
			       + searcher.getNodeCount() + " nodes.");
	}
		
	}

}

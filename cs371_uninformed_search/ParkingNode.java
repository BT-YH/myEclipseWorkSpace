import java.util.ArrayList;

public class ParkingNode extends SearchNode {
	
	int size;
	int rowOfIndice;
	int colOfIndice;
	private int[][] lots;
	int cost;
	
	
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
		
	}

}

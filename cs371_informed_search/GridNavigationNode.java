import java.util.ArrayList;

public class GridNavigationNode extends HSearchNode {

	private int startRow, startCol, goalRow, goalCol, row, col;
	public double distance;
	private static boolean[][] isBlocked;
	private static boolean[][] isVisited;
	private static int moves[][] = { { -1, -1 }, { 0, -1 }, { 1, -1}, 
								     { -1,  0 }, 			{ 1,  0},
								     { -1,  1 }, { 0,  1 }, { 1,  1} };
	
	public GridNavigationNode(int startRow, int startCol, int goalRow, int goalCol, boolean[][] isBlocked) {
		this.startRow = startRow;
		this.startCol = startCol;
		this.goalRow = goalRow;
		this.goalCol = goalCol;
		GridNavigationNode.isBlocked = isBlocked;
		isVisited = (boolean[][])isBlocked.clone();
		for (int i = 0; i < isBlocked.length; ++i)
			isVisited[i] = (boolean[])isBlocked[i].clone();
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	@Override
	public double getG() {
		return distance;
	}

	@Override
	public double getH() {
		return 1.00*Math.sqrt(Math.pow(goalRow - row, 2) + Math.pow(goalCol - col, 2));
	}

	@Override
	public boolean isGoal() {
		return row == goalRow && col == goalCol;
	}

	@Override
	public ArrayList<SearchNode> expand() {
		ArrayList<SearchNode> children = new ArrayList<>();
		
		for (int[] move : moves) {
			int newRow = row + move[1];
			int newCol = col + move[0];
			if (newRow >= 0 && newCol >= 0 &&
				newRow < isBlocked.length && newCol < isBlocked[0].length) {
				if (!isBlocked[newRow][newCol] && !isVisited[newRow][newCol]) {
					GridNavigationNode child = (GridNavigationNode) childClone();
					child.row = newRow;
					child.col = newCol;
					child.distance += Math.sqrt(Math.abs(move[0]) + Math.abs(move[1]));
					children.add(child);
					isVisited[row][col] = true;
				}
			}
		}
		
		return children;
	}

	@Override
	public Object clone() {
		GridNavigationNode newNode = (GridNavigationNode)super.clone();
		return newNode;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "" + distance;
	}

}

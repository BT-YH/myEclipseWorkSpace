import java.util.Random;

public class ManhattanDistribution implements State {
	
	public static Random random = new Random();
	public int n, rows, cols;
	public int[] row, col; // indexed by item number
	public int prevItem, prevRow, prevCol;
	
	public ManhattanDistribution(int numItems, int numRows, int numCols) {
		n = numItems;
		rows = numRows;
		cols = numCols;
		row = new int[n];
		col = new int[n];
	}
	
	
	@Override
	public void step() {
		// pick a random item
		int i = random.nextInt(n);
		
		// store undo information
		prevItem = i;
		prevRow = row[i];
		prevCol = col[i];
		
		// move item to a random adjacent position
		// small adjustment allow us to fine tune the search
		int minRow = Math.max(0, row[i] - 1);
		int maxRow = Math.min(rows - 1, row[i] + 1);
		int minCol = Math.max(0, col[i] - 1);
		int maxCol = Math.min(cols - 1, col[i] + 1);
		
		while (row[i] == prevRow && col[i] == prevCol) {
			row[i] = minRow + random.nextInt(maxRow - minRow + 1);
			col[i] = minCol + random.nextInt(maxCol - minCol + 1);
		}

	}
	

	@Override
	public void undo() {
		row[prevItem] = prevRow;
		col[prevItem] = prevCol;
	}

	@Override
	public double energy() {
		int energy = 0;
		for (int r = 0; r < rows; r++) 
			for (int c = 0; c < cols; c++) {
				int minDist = Integer.MAX_VALUE;
				for (int i = 0; i < n; i++) {
					int dist = Math.abs(r - row[i]) + Math.abs(c - col[i]);
					if (dist < minDist)
						minDist = dist;
				}
				energy += minDist * minDist;
			}
		return energy;
	}
	
	@Override
	public Object clone() {
		try {
			ManhattanDistribution copy = (ManhattanDistribution) super.clone();
			copy.row = row.clone();
			copy.col = col.clone();
			return copy;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		char[][] grid = new char[rows][cols];
		for (int r = 0; r < rows; r++) 
			for (int c = 0; c < cols; c++)
				grid[r][c] = '-';
		for (int i = 0; i < n; i++) 
			grid[row[i]][col[i]] = '#';
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++)
				sb.append(grid[r][c] + " ");
			sb.append("\n");
		}
		return sb.toString();
	}
}

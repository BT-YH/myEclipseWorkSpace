import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class LatinSquare {
	private static int[][] goalGrid;
	private static int nodeCountExpanded = 1;
	private static int nodeCountGT;

	public static boolean solve(int[][] grid) {
		if (checkGrid(grid)) {
			LatinSquareNode1 root = new LatinSquareNode1(grid);
			boolean result =  search(root);
			
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					grid[i][j] = goalGrid[i][j];
				}
			}
			
			return result;
		}
		return false;
	}
	
	public static boolean checkGrid(int[][] grid) {
		for (int row = 0; row < grid.length; row++) 
			for (int col = 0; col < grid.length; col++) {
				
				int cell = grid[row][col];
				if (cell != 0) {
					// check row
					for (int j = 0; j < grid.length; j++) {
						if ( j != col && grid[row][j] == cell) {
							return false;
						}
					}
					// check column
					for (int i = 0; i < grid.length; i++) {
						if ( i != row && grid[i][col] == cell) {
							return false;
						}
					}
				}
				
			}
		return true;
	}
	
	public static boolean search(LatinSquareNode1 root) {
		
		Stack<LatinSquareNode1> q = new Stack<>();
		q.push(root);
		
		while(!q.isEmpty()) {
			LatinSquareNode1 node = q.pop();
			nodeCountGT++;
			
			if (nodeCountGT % 100000 == 0)
				System.out.println(node.toString() + "\n");
			if (node.isGoal()) {
				goalGrid = node.grid;
				return true;
			}
			
			for (LatinSquareNode1 child : node.expand()) {
				q.push(child);
				nodeCountExpanded++;
				if (nodeCountExpanded % 100000 == 0) {
					System.out.println("Node count (Expanded): " + nodeCountExpanded);
				}
			}
		}
		
		return false;
	}
	
	static class LatinSquareNode1 implements Cloneable {
		static int size;
		
		int filled, area;
		
		int[][] grid;
		
		public LatinSquareNode1(int[][] grid) {
			size = grid.length;
			area = size*size;
			this.grid = grid;
			
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (grid[i][j] != 0) {
						filled++;
					}
				}
			}
		}
		
		public boolean isGoal() {
			return filled == area;
		}
		
		public ArrayList<LatinSquareNode1> expand() {
			ArrayList<LatinSquareNode1> children = new ArrayList<>();

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (grid[i][j] == 0) {
						for (int f = 1; f <= size; f++) {
							if (isValid(f, i, j)) {
								LatinSquareNode1 childClone = clone();
								childClone.filled++;
								childClone.grid[i][j] = f;
								children.add(childClone);
							}
						}
					}
				}
			}
			return children;
		}
		
		
		@Override
		public LatinSquareNode1 clone() {
			try {
				LatinSquareNode1 copy = (LatinSquareNode1)super.clone();
				copy.grid = new int[size][size];
				for (int i = 0; i < size; ++i)
					copy.grid[i] = (int[])grid[i].clone();
				return copy;
				} 
			catch (Exception e) {}
				return null;
		}
		
		/**
		 * @param cell
		 * @param row
		 * @param col
		 * @return
		 */
		private boolean isValid(int cell, int row, int col) {
			// check row
			for (int j = 0; j < size; j++) {
				if (grid[row][j] == cell) {
					return false;
				}
			}
			// check column
			for (int i = 0; i < size; i++) {
				if (grid[i][col] == cell) {
					return false;
				}
			}
			return true;
		}
		
		public String toString() {
			return Arrays.deepToString(grid);
		}
	}
	
	
	public static void main(String[] args) {
		int[][] grid = {{0, 0, 0, 0, 0},
						{0, 0, 0, 0, 4},
						{0, 0, 2, 0, 5},
						{0, 4, 0, 3, 2},
						{5, 3, 0, 0, 0}};
//		int size = 8;
//		int[][] grid = new int[size][size];
//		
//		for (int i = 0; i < size; i++) {
//			for (int j = 0; j < size; j++) {
//				if (i == j) {
//					grid[i][j] = 1;
//				}
//				if (i + j == size - 1) {
//					grid[i][j] = 2;
//				}
//			}
//		}
//		for (int row = 0; row < size; row++) {
//			for (int col = 0; col < size; col++) {
//				if (grid[row][col] == 0) {
//					double rand = Math.random();
//
//					if (rand > .5) {
//						boolean validVal = false;
//						while (!validVal) {
//							int cell = (int) (Math.random() * size + 1);
//							System.out.println(cell);
//							// check row
//							for (int j = 0; j < size; j++) {
//								if ( j != col && grid[row][j] == cell) {
//									continue;
//								}
//							}
//							// check column
//							for (int i = 0; i < size; i++) {
//								if ( i != row && grid[i][col] == cell) {
//									continue;
//								}
//							}
//
//							grid[row][col] = cell;
//							validVal = true;
//						}
//					}
//				}
//			}	
//		}
		
		System.out.println(Arrays.deepToString(grid));
		
		long startTime = System.currentTimeMillis();
		boolean result = solve(grid);
		String results = "";
		if (result) {
			results += result + "\n";
			results += Arrays.deepToString(goalGrid);
		} else {
			results += result;
		}
		
		System.out.println(results);
		System.out.println("Time taken to calculate results: " + (System.currentTimeMillis() - startTime) + "ms");
		System.out.println("Node count (Expanded): " + nodeCountExpanded);
		System.out.println("Node count (Goal Tested): " + nodeCountGT);
		
	}
}

import java.util.ArrayList;
import java.util.Scanner;

public class SudokuNode extends SearchNode
	implements Cloneable{
	
	public static final int SIZE = 9;
	public static final int UNKNOWN = 0;
	private int[][] grid = new int[SIZE][SIZE];
	
	public SudokuNode(java.util.Scanner scanner){
		String input = "";
		int counter = 0;
		while (scanner.hasNextLine()) {
			input += (scanner.nextLine() + " ");
			counter++;
			if (counter == 9) {
				break;
			}
		}
		
		String[] strArray = input.split(" ");
		char[][] charGrid = new char[SIZE][SIZE];
		
		// initializing charGrid
		for (int row = 0; row < SIZE; row++) {
			charGrid[row] = strArray[row].toCharArray();
		}
		
		// initializing grid
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				int temp = -1;
				if (charGrid[row][col] == '.') {
					temp = 0;
				} else {
					temp = charGrid[row][col] - '0';
				}
				grid[row][col] = temp;
			}
		}
	}
	
	public SudokuNode(int[][] grid) {
		this.grid = grid;
	}
	
	public int getCell(int row, int col) {
		return grid[row][col]; // Note: this is a subtle conversion
	}
	
	public String toString() {
		// passing grid to charGrid
		char[][]charGrid = new char[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (grid[i][j] == 0) {
					charGrid[i][j] = '.';
				} else {
					charGrid[i][j] = Character.forDigit(grid[i][j], 10); 
					// Determines the character representation for a specific digit in the specified radix.
				}
			}
		}
		// passing char matrix to string array
		String[] strArray = new String[SIZE];
		for (int row = 0; row < SIZE; row++) {
			strArray[row] = new String(charGrid[row]);
		}
		// concatenating output
		String output = "";
		for (int i = 0; i < strArray.length; i++) {
			if (i == 0) {
				output += strArray[i] + "\n";
			} else if (i == strArray.length - 1) {
				output += strArray[i] + "\n";
			} else {
				output += strArray[i] + "\n";
			}
		}
		return output;
	}
	
	@Override
	public boolean isGoal() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public ArrayList<SearchNode> expand() {
		ArrayList<SearchNode> children = new ArrayList<>();
		for (int k = 0; k < SIZE * 2; k++) { //traversing the grid in diagonals
											 //from top right to bottom left
			for (int col = 0; col <= k; col++) {
				int row = k - col;
				if ( row < SIZE && col < SIZE && grid[row][col] == 0) {
					for (int i = 1; i <= 9; i++) {
						if (isValid(i, row, col)) {
							SudokuNode childClone = childClone();
							childClone.grid[row][col] = i;
							children.add(childClone);
						}
					}
					return children;
				}
			}
		}
		return new ArrayList<SearchNode>();
	}
	
	@Override
	public SudokuNode childClone() {
		// Shallow copy
		SudokuNode nodeCopy = (SudokuNode)super.childClone(); 
		// Deep copy
		nodeCopy.grid = new int[SIZE][SIZE];
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				nodeCopy.grid[row][col] = grid[row][col]; 
			}
		}
		return nodeCopy;
	}
	
	private boolean isValid(int cell, int row, int col) {
		// check row
		for (int j = 0; j < SIZE; j++) {
			if (grid[row][j] == cell) {
				return false;
			}
		}
		// check column
		for (int i = 0; i < SIZE; i++) {
			if (grid[i][col] == cell) {
				return false;
			}
		}
		// check 3*3 block
		for (int i = row - row % 3; i < row - row % 3 + 3; i++) {
			for (int j = col - col % 3; j < col - col % 3 + 3; j++ ) {
				if (grid[i][j] == cell) {
					return false;
				}
			}
		}
		return true;
	}
}

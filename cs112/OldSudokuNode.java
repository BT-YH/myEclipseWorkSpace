import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class OldSudokuNode extends SearchNode 
	implements Cloneable {
	
	public static final int SIZE = 9;
	public static final int UNKNOWN = 0;
	private char[][] grid = new char[SIZE][SIZE];
	private int[][] intGrid = new int[SIZE][SIZE];
	private String[] strArray = new String[SIZE];
	
	public OldSudokuNode(java.util.Scanner scanner){
		String input = "";
		int counter = 0;
		while (scanner.hasNextLine()) {
			input += (scanner.nextLine() + " ");
			counter++;
			if (counter == 9) {
				break;
			}
		}
		
		strArray = input.split(" ");
		
		// initializing grid
		for (int row = 0; row < SIZE; row++) {
			char[] temp = strArray[row].toCharArray(); 
			grid[row] = temp;
		}
		
		// initializing intGrid
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				intGrid[row][col] = getCell(row, col);
			}
		}
	}
	
	public OldSudokuNode(int[][] grid) {
		intGrid = grid;
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (grid[i][j] == 0) {
					this.grid[i][j] = '.';
				} else {
					this.grid[i][j] = Character.forDigit(grid[i][j], 10); 
					// Determines the character representation for a specific digit in the specified radix.
				}
			}
		}
		
		for (int row = 0; row < SIZE; row++) {
			// passing char matrix to string array
			strArray[row] = new String(this.grid[row]);
		}
		
//		for (int i = 0; i < SIZE; i++) {
//			System.out.println(Arrays.toString(this.grid[i]));
//		}
	}
	
	public int getCell(int row, int column) {
		if (grid[row][column] == '.') {
			return 0;
		}
		
		return grid[row][column] - '0'; // Note: this is a subtle conversion
	}
	
	public String toString() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (intGrid[i][j] == 0) {
					grid[i][j] = '.';
				} else {
					grid[i][j] = Character.forDigit(intGrid[i][j], 10); 
					// Determines the character representation for a specific digit in the specified radix.
				}
			}
		}
		
		for (int row = 0; row < SIZE; row++) {
			// passing char matrix to string array
			strArray[row] = new String(grid[row]);
		}
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
		for (int i = 0; i < intGrid.length; i++) {
			for (int j = 0; j < intGrid[0].length; j++) {
				if (intGrid[i][j] == 0) {
					////System.out.println("isGoal() returned false");
					return false;
				}
			}
		}
		////System.out.println("isGoal() returned true");
		return true;
	}

	@Override
	public ArrayList<SearchNode> expand() {
		ArrayList<SearchNode> children = new ArrayList<>();
		////System.out.println("Expanding");
		for (int k = 0; k < SIZE * 2; k++) {
			for (int col = 0; col <= k; col++) {
				int row = k - col;
				if ( row < SIZE && col < SIZE && intGrid[row][col] == 0) {
					////System.out.println("isvalid");
					for (int i = 1; i <= 9; i++) {
						////System.out.println(i);
						if (isValid(i, row, col)) {
							OldSudokuNode childClone = childClone();
							childClone.intGrid[row][col] = i;
							children.add(childClone);
							////System.out.println("Adding child: ");
							////System.out.println(childClone);
						}
					}
					return children;
				}
			}
		}
		return new ArrayList<SearchNode>();
	}
	
	@Override
	public OldSudokuNode childClone() {
		OldSudokuNode nodeCopy = (OldSudokuNode)super.childClone(); // Shallow copy
		
		// Deep copy
		nodeCopy.grid = new char[SIZE][SIZE];
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				nodeCopy.grid[row][col] = grid[row][col]; 
			}
		}
		
		nodeCopy.intGrid = new int[SIZE][SIZE];
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				nodeCopy.intGrid[row][col] = intGrid[row][col]; 
			}
		}
		
		nodeCopy.strArray = new String[SIZE];
		for (int i = 0; i < SIZE; i++) {
			nodeCopy.strArray[i] = strArray[i] + "";
		}
		return nodeCopy;
	}
	
	private boolean isValid(int cell, int row, int col) {
		// check row
		for (int j = 0; j < SIZE; j++) {
			if (intGrid[row][j] == cell) {
				return false;
			}
		}
		
		// check column
		for (int i = 0; i < SIZE; i++) {
			if (intGrid[i][col] == cell) {
				return false;
			}
		}
		
		// check 3*3 block
		for (int i = row - row % 3; i < row - row % 3 + 3; i++) {
			for (int j = col - col % 3; j < col - col % 3 + 3; j++ ) {
				if (intGrid[i][j] == cell) {
					return false;
				}
			}
		}
		return true;
	}
	

}










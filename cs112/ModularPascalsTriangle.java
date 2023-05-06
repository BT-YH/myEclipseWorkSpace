
public class ModularPascalsTriangle {
	
	private int rows, cols, mod;
	private int[][] triangleArray;
	
	public ModularPascalsTriangle(int rows, int cols, int mod) {
		super();
		this.rows = rows;
		this.cols = cols;
		this.mod = mod;
		triangleArray = new int[rows][cols];
		this.initializeArray();
		this.setArray();
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getMod() {
		return mod;
	}

	public void setMod(int mod) {
		this.mod = mod;
	}
	
	public void setValue(int row, int col, int value) {
		triangleArray[row][col] = value;
	}
	
	public int getValue(int row, int col) {
		return triangleArray[row][col];
	}
	
	private void initializeArray() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (col == 0) {
					triangleArray[row][col] = 1;
					continue;
				}
				triangleArray[row][col] = 0; 
			}
		}
	}
	
	private void setArray() {
		for (int row = 1; row < rows; row++) {
			for (int col = 1; col < cols; col++) {
				triangleArray[row][col] = (triangleArray[row-1][col-1] + triangleArray[row-1][col]) % mod;
			}
		}
	}
	
	public void printArray() {
		for (int row = 0; row < rows; row++) {
			System.out.print("\n");
			for (int col = 0; col < row + 1; col++) {
				System.out.print(triangleArray[row][col] + " ");
			}
		}
	}
	
}

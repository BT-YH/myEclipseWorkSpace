import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class RookJumpingMaze implements State {
	
	public static final int UNREACHED = -1000000;
//	public static int seed = 1;
	public static Random rand = new Random(); //(seed);

	
	protected int[][] maze, depths;
	private static int size;
	private int row, col, jump;
	private int prevJump, prevRow, prevCol;
	private int[][] dirs = new int[][] { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
	
	public RookJumpingMaze(int size) {
		maze = new int[size][size];
		depths = new int[size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				int maxJump = Math.max(Math.max(size - 1 - i, i), 
						   Math.max(size - 1 - j, j));
				maze[i][j] = (int)(Math.random() * maxJump) + 1;
			}
		}
		
		for (int i = 0; i < size; ++i) {
			for (int f = 0; f < size; ++f) {
				depths[i][f] = UNREACHED;
			}
		}
		
		maze[size-1][size-1] = 0;
		RookJumpingMaze.size = size;
	}
	
	@Override
	public void step() {
		
		do {
			row = rand.nextInt(size);
			col = rand.nextInt(size);
		} while (row == size - 1 && col == size - 1);
		
		int maxJump = Math.max(Math.max(size - 1 - row, row), 
							   Math.max(size - 1 - col, col));
		
		prevJump = maze[row][col];
		
		do {
			jump = rand.nextInt(maxJump) + 1;
		} while (jump == prevJump);
		
		maze[row][col] = jump;
	}

	@Override
	public void undo() {
		maze[row][col] = prevJump;
	}

	@Override
	public double energy() {
		for (int i = 0; i < size; ++i) {
			for (int f = 0; f < size; ++f) {
				depths[i][f] = UNREACHED;
			}
		}
		
		Queue<int[]> queue = new LinkedList<>();
		depths[0][0] = 0;
		queue.add(new int[]{ 0, 0 });
		
		while (!queue.isEmpty()) {
			int[] node = queue.poll();
			int xPos = node[0], yPos = node[1];
			if (xPos == size - 1 && yPos == size - 1) {
//				for (int i = 0; i < size; ++i) {
//					for (int f = 0; f < size; ++f)
//						System.out.print((String.format("%d ", depths[i][f])));
//					System.out.println();
//				}
//				System.out.println();
				return -depths[size-1][size - 1];
			}
			int d = maze[yPos][xPos];
			for (int[] dir : dirs) {
				if (xPos + dir[0] * d >= 0 && xPos + dir[0] * d < size &&
					yPos + dir[1] * d >= 0 && yPos + dir[1] * d < size) {
					int nx = xPos + dir[0]*d;
					int ny = yPos + dir[1]*d;
					if (depths[ny][nx] == UNREACHED) {
						depths[ny][nx] = depths[yPos][xPos] + 1;
						queue.add(new int[] { nx, ny });
					}
				}
			}
		}
		
//		for (int i = 0; i < size; ++i) {
//			for (int f = 0; f < size; ++f)
//				System.out.print((String.format("%d ", depths[i][f])));
//			System.out.println();
//		}
//		System.out.println();
		
		return -UNREACHED;
	}
	
	public int getJump(int row, int col) {
		return maze[row][col];
	}
	
	public int getSize() {
		return size;
	}
	
	@Override
	public RookJumpingMaze clone() {
		try {
		RookJumpingMaze copy = (RookJumpingMaze)super.clone();
//		copy.maze = (int[][])maze.clone();
		copy.maze = new int[size][];
		for (int i = 0; i < size; ++i)
			copy.maze[i] = (int[])maze[i].clone();
		copy.depths = new int[size][];
		for (int i = 0; i < size; ++i)
			copy.depths[i] = (int[])depths[i].clone();
		return copy;
		} catch (Exception e) {}
		return null;
	}
	
	public RookJumpingMaze search(int iterations, double temperature, double decayRate) {
		Random random = new Random(); //***
		double minEnergy = Integer.MAX_VALUE;
		double energy = energy();
		RookJumpingMaze minState = null;
		for (int i = 0; i < iterations; i++) {
//			if (i % 1000 == 0)
//				System.out.println(minEnergy + "\t" + energy);
			step();
			double nextEnergy = energy();
			if (nextEnergy <= energy || random.nextDouble() < Math.exp((energy - nextEnergy) / temperature)) { //***
				energy = nextEnergy;
				if (nextEnergy < minEnergy) { 
					minState = (RookJumpingMaze) clone();
					minEnergy = nextEnergy;
				}
			} else
				undo();
			
			temperature *= decayRate; //***
		}
		return minState;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < size; ++i) {
			for (int f = 0; f < size; ++f)
				sb.append(String.format("%d ", maze[i][f]));
			sb.append("\n");
		}		
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
//		RookJumpingMaze maze = new RookJumpingMaze(5);
//		System.out.println(maze);
//		System.out.println(maze.energy());
//		System.out.println("step");
//		maze.step();
//		System.out.println(maze);
//		System.out.println(maze.energy());
	}
}

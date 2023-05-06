import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TrafficLightsSolver {
	public static final int ROWS = 3, COLS = 4; // board size - must be ROWS = 3, COLS = 4 for final submission
	public static final int OFF = 0, GREEN = 1, YELLOW = 2, RED = 3; // piece constants
	public static final int UNKNOWN = 0, LOSS = 1, WIN = 2; // game state values - UNKNOWN means unreached before a call to solve(), and unreachable after a call to solve()
	public static int[][] lines = { // positions that are 3 in a row horizontally, vertically, or diagonally in a 3 row, 4 column grid represented as a 1-dimensional array with row-major ordering
            { 0, 1, 2 }, { 1, 2, 3 }, { 4, 5, 6 }, { 5, 6, 7 }, { 8, 9, 10 }, { 9, 10, 11 }, // horizontal
            { 0, 4, 8 }, { 1, 5, 9 }, { 2, 6, 10 }, { 3, 7, 11 }, // vertical
            { 0, 5, 10 }, { 1, 6, 11 }, { 2, 5, 8 }, { 3, 6, 9 } // diagonal
    };
	
	// Since the full game analysis for a 3-by-4 board is computationally intensive and will take several minutes on a 2018 computer,
	// you may wish to do your testing with a 3-by-3 board which takes a few seconds to compute.  If so, set COLS = 3 above, and
	// this static initialization code will change the lines used to check for a three-in-a-row win.  
	static {
		if (COLS == 3)
			lines = new int[][] { 
				{ 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // horizontal
				{ 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // vertical
				{ 0, 4, 8 }, { 2, 4, 6 } // diagonal
		    };
	}

	/**
	 * Your TwoBitRandomAccessFile for storing the computed solution to the game.  
	 * Note: An optimal player will choose any play that leads to a losing state for the opponent.
	 * A good player in a losing state will follow the Enough Rope Principle:
	 * "If you are in a losing position, it pays to follow the Enough Rope Principle: Make the position
	 * as complicated as you can with your next move." - David Wolfe,
	 * Lessons in Play: An Introduction to Combinatorial Game Theory (Chapter 1, p. 18)
	 */
	private TwoBitRandomAccessFile tbraf;

	/**
	 * Create a TwoBitRandomAccessFile large enough to access any Traffic Lights game state (reachable or not).
	 * For a 3 row, 4 column board, the file will have 4^(ROWS*COLS) = 16777216 2-bit entries, and thus be 
	 * 4^(ROWS*COLS - 1) = 4194304 bytes.
	 * @param filename name of binary solution file
	 */
	public TrafficLightsSolver(String filename) {
		System.out.printf("Opening %s with memory for %d unique states.\n", filename, 1 << (2 * ROWS * COLS));
		try {
			tbraf = new TwoBitRandomAccessFile(filename, 1 << (2 * ROWS * COLS));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Play a text-based Traffic Lights game, displaying winning/losing state information as the game progresses.
	 * @throws IOException 
	 */
	public void play() throws IOException {
		Scanner in = new Scanner(System.in);
		int[] state = new int[ROWS * COLS]; // initially all OFF
		int	player = 1;	
		while (!isGameOver(state)) {
			System.out.println(stateToString(state));
			System.out.printf("This is a %s state.\n", eval(state) == WIN ? "winning" : "losing"); 
			int pos = -1;
			int r = -1, c = -1;
			while (r < 0 || c < 0 || r >= ROWS || c >= COLS || state[pos] == RED) {
				try {
					System.out.printf("Player %d play row and column? ", player);
					r = in.nextInt();
					c = in.nextInt();
				}
				catch (Exception e) {
					System.err.println("Illegal input. Use row and column separated by a space.");
				}
				finally {
					in.nextLine();
				}
				pos = r * COLS + c;
			}
			state[pos]++;
			player = (player == 1) ? 2 : 1;
		}
		System.out.println(stateToString(state));
		System.out.printf("Player %d wins!\n", (player == 1) ? 2 : 1);
		in.close();
	}

	/**
	 * Starting with the initial game state, recursively evaluate the value of each state given optimal play, 
	 * assigning each reachable game state to WIN or LOSS.  
	 * @throws IOException 
	 */
	public void solve() throws IOException {
		System.out.println("Resetting all states to UNKNOWN and solving all states reachable from initial state...");
		try {
			tbraf.clear();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2);
		}
		eval(new int[ROWS * COLS]); // initially all OFF
	}
	
	/**
	 * Given a game state, recursively evaluate the value of that state and all states reachable from it
	 * given optimal play, assigning it the value WIN or LOSS.
	 * 
	 * If the game is over, since the player changes with each play, the previous player made a play resulting 
	 * in the three-in-a-row, so the current player evaluates a game over state as a LOSS.  If the game is not
	 * over and the player has a play to a LOSS state for their opponent, it is evaluated as a WIN state for 
	 * the player.  Otherwise, all plays lead to WIN states for their opponent and it is evaluated as a LOSS
	 * state.  
	 * 
	 * Any state not yet evaluated has the file value UNKNOWN.  After all recursive evaluation is complete any 
	 * remaining UNKNOWN states are unreachable in legal play.  For example, one can never legally reach a 
	 * game state with two disjoint (e.g. parallel) three-in-a-row lines.   
	 * @param state current state to be evaluated as WIN or LOSS
	 * @return value of state, either WIN or LOSS
	 * @throws IOException 
	 */
	public int eval(int[] state) throws IOException {
		
		// Convert the state to an integer (stateToInt(int[])) for random-access lookup
		// Lookup UNKNOWN/LOSS/WIN state value in two-bit random access file (tbraf) at position of state integer 
		// If the state value is UNKNOWN (we have NOT already computed the WIN/LOSS value of this state),
		// - Assume initially that the state is a LOSS.
		// - If the game is not over in the state, (see isGameOver(int[]))
		// - - For each legal play position, 
		// - - - Make the play at that position (i.e. increment the legal play position) to achieve a new successor state.
		// - - - If a recursive evaluation of this new state is found to be a LOSS for the opponent,
		// - - - - then the original state is a WIN for the current player. 
		// - - - Undo the play at that position (i.e. decrement the same legal play position).
		// - Store the WIN/LOSS value of the state in two-bit random access file (tbraf) at position of state integer.
		// Return the stored WIN/LOSS value of the state.

		// TODO - implement
		int stateInt = stateToInt(state);
		int stateValue = tbraf.get(stateInt);
		if (stateValue == UNKNOWN) {
			stateValue = LOSS;
			if (!isGameOver(state)) {
				for (int i = 0; i < state.length; i++) {
					if (isLegalPlay(i, state)) {
						state[i]++;
						if (eval(state) == LOSS) {
							stateValue = WIN;
						}
						state[i]--;
					}
				}
			}
			tbraf.set(stateInt, stateValue);
		}
		return tbraf.get(stateInt);
	}

	/**
	 * Return whether or not the game is over (3 GREEN/YELLOW/RED in a row horizontally, vertically, or diagonally).
	 * @param state game state
	 * @return whether or not the game is over for the game state 
	 */
	public boolean isLegalPlay(int play, int[] state) {
		if (!isGameOver(state) && state[play] != RED) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Return whether or not the game is over (3 GREEN/YELLOW/RED in a row horizontally, vertically, or diagonally).
	 * @param state game state
	 * @return whether or not the game is over for the game state 
	 */
	public boolean isGameOver(int[] state) {
		
		// TODO - implement
		for (int[] line : lines) {
			if (state[line[0]] != OFF &&
				state[line[0]] == state[line[1]] &&
				state[line[1]] == state[line[2]]) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Convert the state to a unique integer by treating positions a base 4 digits.
	 * @param state game state
	 * @return a unique state integer
	 */
	public static int stateToInt(int[] state) {
		int stateNum = 0;
		for (int n : state) {
			stateNum *= RED + 1;
			stateNum += n;
		}
		return stateNum;
	}

	/**
	 * Return a String representation of the given game state.
	 * @param state game state
	 * @return a String representation of the given game state
	 */
	public static String stateToString(int[] state) {
		final char[] stateChars = {'-', 'G', 'Y', 'R'};
		StringBuilder sb = new StringBuilder(" ");
		for (int c = 0; c < COLS; c++)
			sb.append(c);
		sb.append("\n");
		for (int r = 0; r < ROWS; r++) {
			sb.append(r);
			for (int c = 0; c < COLS; c++) 
				sb.append(stateChars[state[r * COLS + c]]);
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Test code that solves the game and then allows users to play the game with solution information.
	 * @param args (unused)
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// Set this to false if you want to retain your previously-computed solution:
		boolean computeSolution = true;

		String filename = "traffic-lights.dat";
		TrafficLightsSolver solver = new TrafficLightsSolver(filename);
		if (computeSolution) {
			long startTime = System.currentTimeMillis();
			solver.solve();
			long endTime = System.currentTimeMillis();
			System.out.println("All reachable state values computed in " + (endTime - startTime) + " ms");
		}
		solver.play();
		
		// You can uncomment this next line if you want to delete the output file each time:
		new File(filename).delete(); 
	}

}

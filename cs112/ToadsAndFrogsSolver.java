import java.util.Scanner;


// https://en.wikipedia.org/wiki/Toads_and_Frogs

public class ToadsAndFrogsSolver {
	public static final char TOAD = 'T', FROG = 'F', EMPTY = '-';

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Board position (using TF-)? ");
		String board = in.nextLine().trim().toUpperCase();
		System.out.print("Current player (T/F)? ");
		char currentPlayer = in.nextLine().trim().toUpperCase().charAt(0);
		char[] boards = board.toCharArray();
		System.out.println("Winner: " + getWinner(boards, currentPlayer));

		
		in.close();
	}

	public static char getWinner(char[] board, char currentPlayer) {
		
		// Algorithm: 
		// For each legal move for the current player,
		//   - if we call getWinner on the resulting game state (new board, other player)
		//     and find that this leads to a win for the current player, 
		//     then return the current player.
		// Otherwise, failing to find a winning play (or any legal play), return the opponent player.
		
		
		
		for (int i = 0; i < board.length; i++) {
			if (currentPlayer == TOAD && board[i] == TOAD) { // TOAD on TOAD's turn
				if ((i+1) < board.length && board[i+1] == EMPTY ) { // Jump
					board[i+1] = TOAD;
					board[i] = EMPTY;
					char winner = getWinner(board, FROG); // recursive call
					board[i+1] = EMPTY;
					board[i] = TOAD;
					if (winner == currentPlayer) 
						return currentPlayer;
				} 
				if ((i+2) < board.length && board[i+2] == EMPTY && board[i+1] == FROG) { // Hop
					board[i+2] = TOAD;
					board[i] = EMPTY;
					char winner = getWinner(board, FROG); // recursive call
					board[i+2] = EMPTY;
					board[i] = TOAD;
					if (winner == currentPlayer) 
						return currentPlayer;
				}
				
			} else if (currentPlayer == FROG && board[i] == FROG) { // FROG on FROG's turn
				if ((i-1) >= 0 && board[i-1] == EMPTY) { // Jump
					board[i-1] = FROG;
					board[i] = EMPTY;
					char winner = getWinner(board, TOAD); // recursive call
					board[i-1] = EMPTY;
					board[i] = FROG;
					if (winner == currentPlayer) 
						return currentPlayer;
				} 
				if ((i-2) >= 0 && board[i-2] == EMPTY && board[i-1] == TOAD) { // Hop
					board[i-2] = FROG;
					board[i] = EMPTY;
					char winner = getWinner(board, TOAD); // recursive call
					board[i-2] = EMPTY;
					board[i] = FROG;
					if (winner == currentPlayer) 
						return currentPlayer;
					
				}
			}
		}
		
		char winner = currentPlayer == TOAD ? FROG : TOAD;
		
		return winner; // base case
	}

}

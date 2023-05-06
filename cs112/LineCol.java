
public class LineCol {
    
    public static final int BLUE = 1;
    public static final int NO_WINNING_MOVE = -1;
    public static final int RED = 2;
    public static final int UNCOLORED = 0;
    //private int[] state;
    
    LineCol() {}
    
    public static int getWinningMove(int[] state, int player) {
        for (int i = 0; i < state.length; i++) { 
            if (isValidMove(state, i, player)) { //recursive case
                state[i] = player;
                    
                int winningMove = getWinningMove(state, (player == BLUE) ? RED : BLUE); // recursive call
                
                state[i] = UNCOLORED;
                if (winningMove == NO_WINNING_MOVE) { 
                    return i; 
                }
            }
        }
        return NO_WINNING_MOVE; // base case: no valid move
    }
    
    private static boolean isValidMove(int[] state, int move, int player) {
        boolean noConflictLeft = move == 0 || state[move - 1] != player;
        boolean noConflictRight = move == state.length - 1 || state[move + 1] != player;
        return state[move] == UNCOLORED && noConflictLeft && noConflictRight;
        
        /*
        if (state[move] != UNCOLORED) {
            return false;
        }
        
        //state[move] == 0
        if (move == 0) {
            if (state[move + 1] != player){
                return true;
            }
        } else if (move == state.length - 1) {
            if (state[move - 1] == UNCOLORED) {
                return true;
            }
        } else if (state[move - 1] != player && state[move + 1] != player) {
            return true;
        }
        
        return false;
        */
    }
}












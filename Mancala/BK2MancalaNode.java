
public class BK2MancalaNode extends MancalaNode {
	
	private static final int HALF_MARBLES = 24;
	private static final int WIN_POINTS = 1000000;
	
	
	public BK2MancalaNode() {
		super();
	}

	public BK2MancalaNode(MancalaNode node) {
		super(node);
	}

	public BK2MancalaNode(int stateIndex) {
		super(stateIndex);
	}
	
//	@Override
//	public double utility() {
//		int score = state[MAX_SCORE_PIT] - state[MIN_SCORE_PIT];
//		if (gameOver())
//			return score;
//		
//		int emptyPits = 0;
//		//int capPieces = 0;
//		for (int i = 0; i < PLAY_PITS; ++i) {
//			if (state[i + (player == MAX ? 0 : MAX_SCORE_PIT)] == 0) {
//				++emptyPits;
//				//capPieces += state[i + (player == MAX ? PLAY_PITS : 0)];
//			}
//		}
////		
////		player = (player == MAX ? MIN : MAX);
////		int othersMoves = getLegalMoves().size();
////		player = (player == MAX ? MIN : MAX);
//		return  score + getLegalMoves().size() + 0.2 * emptyPits;
//				//score - getLegalMoves().size() + othersMoves - emptyPits + capPieces;
//	}
	
	
	
	@Override
	public double utility() {
		double scoreDiff = state[MAX_SCORE_PIT] - state[MIN_SCORE_PIT];
		if (gameOver())
			return scoreDiff;
		
		
		int playerPit = player == MAX ? MAX_SCORE_PIT : MIN_SCORE_PIT;
		//int playerStartPit = player == MAX ? 0 : (MAX_SCORE_PIT+1);
		int maxStartPit = 0;
		int minStartPit = MAX_SCORE_PIT + 1;
		
		int maxPieces = 0;
		int maxMoves = 0;
		int maxFreemoves = 0;
		int maxCaptures = 0;
		
		if (state[playerPit] >= HALF_MARBLES)
			return player == MAX ? WIN_POINTS : -WIN_POINTS;
		
		for (int i = maxStartPit; i < MAX_SCORE_PIT; ++i) {
			maxPieces += state[i];
			if (state[i] != 0)
				++maxMoves;
			if (state[i] == (MAX_SCORE_PIT-i))
				++maxFreemoves;
			if (state[i] == 0) {
				for (int j = 0; j < i; j++) {
					if (state[j] == i-j) {
						maxCaptures += state[13 - j - 1];
					}
				}
			}
		}
		
		//playerPit = player != MAX ? MAX_SCORE_PIT : MIN_SCORE_PIT;
		//playerStartPit = player != MAX ? 0 : (MAX_SCORE_PIT+1);
		int minPieces = 0;
		int minMoves = 0;
		int minFreemoves = 0;
		int minCaptures = 0;
		for (int i = minStartPit; i < minStartPit + PLAY_PITS; ++i) {
			minPieces += state[i];
			if (state[i] != 0)
				++minMoves;
			if (state[i] == (MIN_SCORE_PIT-i))
				++minFreemoves;
			if (state[i] == 0) {
				for (int j = minStartPit; j < i; j++) {
					if (state[j] == i-j) {
						minCaptures += state[13 - j - 1];
					}
				}
			}
		}
		
		double pieDiff = maxPieces - minPieces;
		double capDiff = maxCaptures - minCaptures;
		double mobDiff = maxMoves - minMoves;
		double freeDiff = maxFreemoves - minFreemoves;
		
		// tunings
		double a = 1;
		double b = 0.183876;
		double c = 0.0043;
		double d = 0.2463;
		double e = 0.66066;
		
		// linear regression constant
		double intercept = 38 * (-0.08218);
		if (player != MAX) {
			intercept = -intercept;
		}
		
		double heuristic = intercept + a * scoreDiff + b * mobDiff + c * freeDiff + d * capDiff + e * pieDiff; // add tunings\
		//double heuristic = intercept + a * scoreDiff + b * mobDiff + c * freeDiff + e * pieDiff; // add tunings
		
		
		return heuristic;
	}
}

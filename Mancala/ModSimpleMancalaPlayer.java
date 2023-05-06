import java.util.ArrayList;

public class ModSimpleMancalaPlayer implements MancalaPlayer {
	private static final double FOPEN = 16;
	private static final double BRANCHING_FACTOR = 6;
	
	@Override
	public int chooseMove(MancalaNode node, long timeRemaining) {
		ArrayList<GameNode> children = node.expand();
		double remainingMoves = 0;
		for (GameNode child : children) {
			while (!child.gameOver()) {
				++remainingMoves;
				child = step(child);
			}
		}
		remainingMoves /= children.size();
		double timeNextMove = FOPEN * timeRemaining / remainingMoves;
		timeNextMove = Math.min(timeRemaining, timeNextMove);

		//System.out.println("MS Time: " + timeRemaining);
		//System.out.println(timeNextMove);
		
//		int depthLimit = (int)(2*Math.log(timeNextMove));
//		if (depthLimit < 1) depthLimit = 1;
//		System.out.println("BK: " + depthLimit);
//		MinimaxSearcher searcher = new MinimaxSearcher(depthLimit); 
		
		BKAlphaBetaSearcher searcher = new BKAlphaBetaSearcher(0);
//		ScoreDiffMancalaNode searchNode = new ScoreDiffMancalaNode(node);
		BK1MancalaNode searchNode = new BK1MancalaNode(node);
		long t1, past = 0;
		do {
			++searcher.depthLimit;
			t1 = System.currentTimeMillis();
			searcher.eval(searchNode);
			past += System.currentTimeMillis() - t1;
		} while (timeNextMove > past * BRANCHING_FACTOR);

		// Create a new copy of the input node that uses the
		// score difference heuristic evaluation function. 

		return searcher.getBestMove();
	}
	
	private GameNode step(GameNode node) {
		GameNode newState = (GameNode) node.clone();
		
		ArrayList<GameNode> children = newState.expand();
		newState = children.get((int)(Math.random() * children.size()));
		
		return newState;
	}

}

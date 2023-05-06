import java.util.ArrayList;

public class BKAlphaBetaSearcher implements GameTreeSearcher {

	int depthLimit;
	int nodeCount;
	int bestMove = GameNode.UNDEFINED_MOVE;
	
	public BKAlphaBetaSearcher(int depthLimit) {
		this.depthLimit = depthLimit;
	}
	
	@Override
	public double eval(GameNode node) {
		nodeCount = 0;
		return alphaBetaSearch(node, depthLimit, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	}
	
	public double alphaBetaSearch(GameNode node, int depthLeft, double alpha, double beta) {
		int localBestMove = GameNode.UNDEFINED_MOVE;
		boolean maximizing = node.getPlayer() == GameNode.MAX;
		++nodeCount;
		
		if (node.gameOver() || depthLeft == 0)
			return node.utility();
		
		ArrayList<GameNode> children = node.expand();
		for (GameNode child : children) {
			double childUtility = alphaBetaSearch(child, depthLeft - 1, alpha, beta);
			if ((maximizing && childUtility == 1000000) || (!maximizing && childUtility == -1000000)) {
				bestMove = child.prevMove;
				return childUtility;
			}
			
			if (alpha >= beta) {
				return alpha;
			} else if (maximizing && childUtility > alpha) {
				localBestMove = child.prevMove;
				alpha = childUtility;
			} else if (!maximizing && childUtility < beta) {
				localBestMove = child.prevMove;
				beta = childUtility;
			}
		}
		
		bestMove = localBestMove;
		return maximizing ? alpha : beta;
	}

	@Override
	public int getBestMove() {
		return bestMove;
	}

	@Override
	public int getNodeCount() {
		return nodeCount;
	}
}
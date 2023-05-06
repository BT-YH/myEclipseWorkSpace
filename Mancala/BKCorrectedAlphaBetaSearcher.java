import java.util.ArrayList;

public class BKCorrectedAlphaBetaSearcher implements GameTreeSearcher {

	int depthLimit;
	int nodeCount;
	int bestMove = GameNode.UNDEFINED_MOVE;
	
	public BKCorrectedAlphaBetaSearcher(int depthLimit) {
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
		double bestUtility = maximizing ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
		for (GameNode child : children) {
			double childUtility = alphaBetaSearch(child, depthLeft - 1, alpha, beta);
			if (maximizing && childUtility > bestUtility) {
				bestUtility = childUtility;
				localBestMove = child.prevMove;
				alpha = Math.max(alpha, childUtility);
			}
			else if (!maximizing && childUtility < bestUtility) {
				bestUtility = childUtility;
				localBestMove = child.prevMove;
				beta = Math.min(beta, childUtility);
			}
			if (alpha >= beta) {
				return bestUtility;
			}
		}
		
		bestMove = localBestMove;
		return bestUtility;
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
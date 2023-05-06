
public class RecursiveDepthFirstSearcher extends Searcher {

	@Override
	public boolean search(SearchNode root) {
		++nodeCount;
		if (root.isGoal()) {
			goalNode = root;
			return true;
		}
		for (SearchNode node : root.expand()) {
			if (search(node)) {
				return true;
			}
		}
		
		return false;
	}

}

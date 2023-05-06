import java.util.Stack;

public class IterativeDeepeningDepthFirstSearcher extends Searcher {
	
	@Override
	public boolean search(SearchNode rootNode) {
		nodeCount = 0;
		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			DepthLimitedSearcher searcher = new DepthLimitedSearcher(i);
			searcher.search(rootNode);
			nodeCount += searcher.nodeCount;
			if (searcher.goalNode != null) {
				goalNode = searcher.goalNode;
				return true;
			}
//			Stack<SearchNode> stack = new Stack<>();
//			stack.add(rootNode);
//			
//			// Main search loop.
//			while (true) {
//
//				// If the search stack is empty, return with failure
//				// (false).
//				if (stack.isEmpty()) {
//					break;
//				}
//				// Otherwise pop the next search node from the top of
//				// the stack.
//				SearchNode node = stack.pop();
//				nodeCount++;
//				
//				// If the search node is a goal node, store it and return
//				// with success (true).
//				if (node.isGoal()) {
//					goalNode = node;
//					return true;
//				}
//				
//				if (node.depth >= i) {
//					continue;
//				}
//				
//				// Otherwise, expand the node and push each of its
//				// children into the stack.
//				for (SearchNode child : node.expand()) {
//					stack.push(child);
//				}
//			}
		}
		return false;
	}

}

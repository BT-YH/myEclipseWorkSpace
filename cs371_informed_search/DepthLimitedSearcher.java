import java.util.Stack;

public class DepthLimitedSearcher extends Searcher{
	
	private int depth;
	
	public DepthLimitedSearcher(int depth) {
		this.depth = depth;
	}
	
	@Override
	public boolean search(SearchNode rootNode) {
		
		// Initialize search variables.
				Stack<SearchNode> stack = new Stack<>();
				stack.add(rootNode);
				nodeCount = 0;
				
				// Main search loop.
				while (true) {

					// If the search stack is empty, return with failure
					// (false).
					if (stack.isEmpty()) {
						return false;
					}
					// Otherwise pop the next search node from the top of
					// the stack.
					SearchNode node = stack.pop();
					nodeCount++;
					
					// If the search node is a goal node, store it and return
					// with success (true).
					if (node.isGoal()) {
						goalNode = node;
						return true;
					}
					
					if (node.depth >= depth) {
						continue;
					}
					
					// Otherwise, expand the node and push each of its
					// children into the stack.
					for (SearchNode child : node.expand()) {
						stack.push(child);
					}
				}
	}

}

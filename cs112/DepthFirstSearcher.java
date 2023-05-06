import java.util.Stack;

public class DepthFirstSearcher extends Searcher {

	@Override
	public boolean search(SearchNode root) {
		// Initialize search variables
		Stack<SearchNode> stack = new Stack<SearchNode>();
		stack.push(root);
		nodeCount = 0;
		
		// Main search loop
		while (true) {
			// if stack is empty, return with failure (false).
			if (stack.isEmpty()) {
				////System.out.println("Stack is empty");
				return false;
			}
			// Otherwise, pop the next node from the end of the stack
			SearchNode node = stack.pop();

			nodeCount++;
			
			// if it's a goal node, store it and return success (true).
			if (node.isGoal()) {
				goalNode = node;
				return true;
			}
			// Otherwise, expand it and add its children to the stack.
			for (SearchNode child : node.expand()) {
				////System.out.println("Pushing child");
				stack.push(child);
				
			}
		}
	}

}

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearcher extends Searcher {

	@Override
	public boolean search(SearchNode root) {
		// Initialize search variables
		Queue<SearchNode> queue = new LinkedList<SearchNode>();
		queue.offer(root);
		nodeCount = 0;
		
		// Main search loop
		while (true) {
			// If queue is empty, return with failure (false).
			if (queue.isEmpty()) {
				return false;
			}
			// Otherwise, poll the next node from the front of the queue.
			SearchNode node = queue.poll();
			nodeCount++;
			
			// If it's a goal node, store it and return success (true).
			if (node.isGoal()) {
				goalNode = node;
				return true;
			}
			// Otherwise, expand it and add its children to the queue.
			for (SearchNode child : node.expand()) {
				queue.offer(child);
			}
		}

	}

}

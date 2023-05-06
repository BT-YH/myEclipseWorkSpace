import java.util.ArrayList;

public abstract class SearchNode implements Cloneable {
	
	public SearchNode parent = null;
	
	public int depth = 0;
	
	public SearchNode() {} // constructor
	
	public abstract boolean isGoal();  // we want to have implementation but not useless implementation
	
	public abstract ArrayList<SearchNode> expand(); // allow node to trace itself
	
	public SearchNode childClone() {
		SearchNode child = (SearchNode) clone();
		child.parent = this;
		child.depth = this.depth + 1;
		return child;
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("This class does not implement Cloneable");
			// Cloneable is an interface
			// it matters in java that we implement Cloneable 
			// as the inherited method clone() checks whether the class
			// intends to implement Cloneable
			//
			// other languages usually use a copy-constructor for cloning
			//
			// pros for java: added fields are updated automatically
			// pros for others: easier implementation
		}
	}
}

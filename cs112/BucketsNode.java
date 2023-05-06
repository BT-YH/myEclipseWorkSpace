import java.util.ArrayList;
public class BucketsNode extends SearchNode {
	
	public int bucket1, bucket2;
	public static final int MAX_AMOUNT1 = 5, MAX_AMOUNT2 = 3;
	
	@Override
	public boolean isGoal() {
		return bucket1 + bucket2 == 4;
	}
	
	@Override
	public ArrayList<SearchNode> expand() {
		ArrayList<SearchNode> children = new ArrayList<>();
		
		// Empty bucket1
		if (bucket1 > 0) {
			BucketsNode child = (BucketsNode) childClone();
			child.bucket1 = 0;
			children.add(child);
		}
		
		// Empty bucket2
		if (bucket2 > 0) {
			BucketsNode child = (BucketsNode) childClone();
			child.bucket2 = 0;
			children.add(child);
		}
		
		// Bucket1
		if (bucket1 < MAX_AMOUNT1) {
			BucketsNode child = (BucketsNode) childClone();
			child.bucket1 = MAX_AMOUNT1;
			children.add(child);
		}
		
		// Bucket2
		if (bucket2 < MAX_AMOUNT1) {
			BucketsNode child = (BucketsNode) childClone();
			child.bucket2 = MAX_AMOUNT2;
			children.add(child);
		}
		
		// Pour bucket1 into bucket2
		if (bucket2 < MAX_AMOUNT2 && bucket1 > 0) {
			BucketsNode child = (BucketsNode) childClone();
			int pourAmount = Math.min(bucket1, MAX_AMOUNT2 - bucket2);
			child.bucket1 -= pourAmount;
			child.bucket2 += pourAmount;
			children.add(child);
		}
		
		// Pour bucket2 into bucket1
		if (bucket1 < MAX_AMOUNT1 && bucket2 > 0) {
			BucketsNode child = (BucketsNode) childClone();
			int pourAmount = Math.min(bucket2, MAX_AMOUNT1 - bucket1);
			child.bucket2 -= pourAmount;
			child.bucket1 += pourAmount;
			children.add(child);
		}
		return children;
	}
	
	public String toString() {
		return "Bucket 1: " + bucket1 + ", Bucket 2: " + bucket2;
	}
}

import java.util.Comparator;


public class BTree <E> {
	
	int numKeys;
	int deg;
	Comparator<E> cmp;
	BTreeNode<E> root;
	
	/**
	 * Set up and initializes the instance variables
	 * @param cmp	A Comparator
	 * @param deg	the degree of the tree and the node
	 */
	public BTree(Comparator<E> cmp, int deg) {
		this.cmp = cmp;
		this.deg = deg;
	}
	
	
	/**
	 * Looks through the keys in ptr and returns the child to which target might belong.
	 * if ptr is a leaf node, return null
	 * @param <E>
	 * @param ptr
	 * @param target
	 * @param cmp
	 * @return
	 */
	private static <E> BTreeNode<E> stepDown(BTreeNode<E> ptr, E target, Comparator<E> cmp) {
		return ptr;
	}
	
	/**
	 * Starting from the root node, finds and returns the leaf node to which target
	 * should be added OR belong by repeatedly calling the stepDown method form above
	 * until a leaf node is reached
	 * @param target
	 * @return
	 */
	private BTreeNode<E> findLeaf(E target) {
		return null;
	}
	
	
	/**
	 * return the content of the tree as String in 
	 * level-order
	 */
	public String toString() {
		return null;
	}
	
	/**
	 * returns the total number of keys in this tree
	 * @return
	 */
	public int size() {
		return numKeys;
	}
	
	/**
	 * returns the degree of this tree
	 * @return
	 */
	public int getDegree() {
		return deg;
	}
	
	/**
	 * returns the pointer to the root of this tree
	 * @return
	 */
	public BTreeNode<E> getRoot() {
		return root;
	}
}

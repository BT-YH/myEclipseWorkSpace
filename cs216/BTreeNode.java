import java.util.ArrayList;
import java.util.Comparator;

public class BTreeNode <E> {
	
	int deg;
	ArrayList<E> keys;
	ArrayList<BTreeNode<E>> children;
	Comparator cmp;
	BTreeNode<E> parent;
	
	public BTreeNode(BTreeNode<E> p, Comparator cmp, int deg) {
		this.deg = deg;
		this.cmp = cmp;
		parent = p;
		
		keys = new ArrayList<E>();
		children = new ArrayList<BTreeNode<E>>(deg);
	}
	
	private <T> int getLastIndex(ArrayList<T> list) {
		int index = 0;
		
		if (list.isEmpty()) {
			return index;
		}
		
		for (int i = 0; i < deg; i++) {
			if (list.get(i) == null) {
				index = i - 1;
				break;
			}
		}
		return index;
	}
	
	
	/**
	 * returns the data(keys) of this node as a String
	 * @return 		returns the data(keys) of this node as a String
	 */
	public String toString() {
		return keys.toString();
	}
	
	/**
	 * returns true if this node is a leaf node
	 * @return	returns true if this node is a leaf node
	 */
	public boolean isLeaf() {
		return children.isEmpty();
	}
	
	/**
	 * returns true if there is no key in this node
	 * @return	returns true if there is no key in this node
	 */
	public boolean isEmpty() {
		return keys.isEmpty();
	}
	
	/**
	 * returns the child of this node at index,
	 * if no child at index, return null
	 * @param index
	 * @return
	 */
	public BTreeNode<E> getChild(int index) {
		return children.get(index);
	}
	
	/**
	 * returns the first child of this node
	 * null otherwise
	 * @return
	 */
	public BTreeNode<E> getFirstChild() {
		return children.get(0);
	}
	
	/**
	 * returns the last child of this node
	 * null otherwise
	 * @return
	 */
	public BTreeNode<E> getLastChild() {
		
		int index = getLastIndex(children);
		
		return children.get(index);
	}
	
	/**
	 * returns the number of keys
	 * @return
	 */
	public int nKeys() {
		return keys.size();
	}
	
	/**
	 * returns the number of children
	 * @return
	 */
	public int nChildren() {
		return children.size();
	}
	
	/**
	 * returns the key at index
	 * @param index
	 * @return
	 */
	public E getKey(int index) {
		return keys.get(index);
	}
	
	/**
	 * returns the key at index
	 * @param index
	 * @return
	 */
	public E getFirstKey() {
		return keys.get(0);
	}
	
	/**
	 * returns the last key of this node
	 * null otherwise
	 * @return
	 */
	public E getLastKey() {
		
		int index = getLastIndex(keys);
		
		return keys.get(index);
	}
	
	/**
	 * inserts new child at index if index is valid for insertion
	 * if index invalid, do nothing
	 * @param index
	 * @param newChild
	 */
	public void addChild(int index, BTreeNode<E> newChild) {
		if (0 <= index && index <= children.size()) {
			children.add(index, newChild);
			newChild.parent = this;
			return;
		}
		return;
	}
	
	/**
	 * add child to the begininng of the list
	 * @param newChild
	 */
	public void addFirstChild(BTreeNode<E> newChild) {
		children.add(0, newChild);
		newChild.parent = this;
	}
	
	/**
	 * add child to the last of the list
	 * @param newChild
	 */
	public void addLastChild(BTreeNode<E> newChild) {
		
		int index = getLastIndex(children);
		
		children.add(index, newChild);
		newChild.parent = this;
	}
	
	/**
	 * replaces the key at index with value if index is valid
	 * else do nothing
	 * @param index
	 * @param value
	 */
	public void setKey(int index, E value) {
		if (0 <= index && index <= keys.size()) {
			keys.set(index, value);
			return;
		}
		
		return;
	}
	
	/**
	 * inserts value at index if index is valid
	 * else do nothing
	 * @param index
	 * @param value
	 */
	public void addKey(int index, E value) {
		if (0 <= index && index <= keys.size()) {
			keys.add(index, value);
			return;
		}
		
		return;
	}
	
	/**
	 * inserts value to start of keys list
	 * @param value
	 */
	public void addFirstKey(E value) {
		keys.add(0, value);
	}
	
	/**
	 * inserts value to end of keys list
	 * @param value
	 */
	public void addLastKey( E value) {
		int index = getLastIndex(keys);
		keys.add(index, value);
	}
	
	/**
	 * removes and returns key at index
	 * @param index
	 * @return
	 */
	public E removeKey(int index) {
		if (0 <= index && index < keys.size()) {
			E val = keys.remove(index);
			return val;
		}
		return null;
	}
	
	/**
	 * removes and returns first key
	 * @param index
	 * @return
	 */
	public E removeFirstKey() {
		return keys.remove(0);
	}
	
	/**
	 * removes and returns the last key
	 * @return
	 */
	public E removeLastKey() {
		int index = getLastIndex(keys);
		
		return keys.remove(index);
	}
	
	/**
	 * removes and returns child at index
	 * @param index
	 * @return
	 */
	public BTreeNode<E> removeChild(int index) {
		if (0 <= index && index < children.size()) {
			BTreeNode<E> node = children.remove(index);
			return node;
		}
		return null;
	}
	
	/**
	 * removes and return first child
	 * @return
	 */
	public BTreeNode<E> removeFirstChild() {
		return children.remove(0);
	}
	
	/**
	 * removes and return last child
	 * @return
	 */
	public BTreeNode<E> removeLastChild() {
		int index = getLastIndex(children);
		return children.remove(index);
	}
	
	/**
	 * returns the index at which new_key should be inserted 
	 * in keys arraylist
	 * @param new_key
	 * @return
	 */
	public int findInsertPos(E new_key) {
		return 0;
	}
	
	/**
	 * returns the index in which target is found in this node's key arraylist
	 * or -1 if not found
	 * @param target
	 * @return
	 */
	public int indexOf(E target) {
		//TODO check efficiency for ArrayList index of
		return 0;
	}
	
	/**
	 * returns the index in which child is found in this node's children
	 * arraylist or -1 if not found
	 * @param child
	 * @return
	 */
	public int indexOf(BTreeNode<E> child) {
		return 0;
	}
	
	/**
	 * returns true if this node has target in 
	 * its keys
	 */
	public boolean contains(E target) {
		
		return false;
	}
	
	/**
	 * returns true if there is an overflow in 
	 * this node
	 * @return
	 */
	public boolean isOverflow() {
		return keys.size() > deg-1;
	}
	
	/**
	 * should only be called in add(E)
	 * @return
	 */
	private BTreeNode<E> split() {
		//TODO hard work here
		return null;
	}
	
	/**
	 * adds k to this node's key list in the right place
	 * returns null if there is no overflow after adding
	 * other wise:
	 * @param k
	 * @return
	 */
	public BTreeNode<E> add(E k) {
		return null;
	}
	
	
}











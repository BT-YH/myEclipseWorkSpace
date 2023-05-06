import java.util.Collection;
import java.util.Random;
import java.util.Stack;

public class GenericShuffler<E> {
	
	Random random = new Random();
	Stack<E> items = new Stack<>();
	
	public void setSeed(long seed) {
		random.setSeed(seed);
	}
	
	public void add(E item) {
		// swap it with any position in the stack
		int size = items.size();
		int j = random.nextInt(size + 1); // check api
		if (j == size) {
			items.push(item);
		} else {
			E tmp = items.get(j);
			items.set(j, item);
			items.push(tmp);
		}
	}
	
	public void addAll(Collection<E> collection) {
		for (E item : collection) {
			add(item);
		}
	}
	
	public boolean empty() {
		return items.empty();
	}
	
	public E get() {
		return items.pop();
	}
	
	public Stack<E> getAll() {
		Stack<E> retVal = items;
		items = new Stack<E>();
		return retVal;
	}
}

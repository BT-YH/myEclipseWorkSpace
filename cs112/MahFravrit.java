import java.util.ArrayList;
import java.util.Comparator;

public class MahFravrit<T> {
	private ArrayList<T> set = new ArrayList<T>();
	private boolean sorted = false;
	private Comparator<T> fravriter;
	
	public MahFravrit(Comparator<T> fravriter) {
		this.fravriter = fravriter; 
	}
	
	public boolean isEmpty() {
		return set.isEmpty();
	}
	
	public void add(T item) {
		set.add(item);
		sort();
	}
	
	public boolean remove(T item) {
		if (set.contains(item)) {
			set.remove(item);
			return true;
		}
		return false;
	}
	
	/*
	 * Algorithm for getFravrit()
	 * 1. check whether the set is empty
	 * 2. check whether the set is sorted
	 */
	
	public T getFravrit() {
		if (!isEmpty()) {
			if (sorted == true) { 
				return set.remove(0);
			}
			sort();
			return set.remove(0);
		}
		return null;
	}
	
	private void sort() { // selection sort using comparator 
		T currentMax;
		int currentMaxIndex;
		for (int i = 0; i < set.size()-1; i++) {
			currentMax = set.get(i);
			currentMaxIndex = i;
			for (int j = i + 1; j < set.size(); j++) {
				if (fravriter.compare(currentMax, set.get(j)) < 0) {
					currentMax = set.get(j);
					currentMaxIndex = j;
				}
			}
			if (currentMaxIndex != i) {
				T temp = set.get(i);
				set.set(i, set.get(currentMaxIndex));
				set.set(currentMaxIndex, temp);
			}
		}
		sorted = true;
	}
		
}

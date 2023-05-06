import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SetMapFun {

	public static void main(String[] args) throws MalformedURLException,
		IOException {
		
		// Monday: Sets and Maps: sets, HashSet, LinkedHashSet, TreeSet 
		// (21.1-21.2) "A set is an efficient data structure for storing
		// and processing nonduplicate elements.  A map is like a 
		// dictionary that provides a quick lookup to retrieve a value 
		// using a key."
	
		// Abstract Set has three concrete subclasses: HashSet, 
		// LinkedHashSet, or TreeSet.
		
		// Create a shuffled list of integers
		int size = 10;
		int min = -size / 2;
		ArrayList<Integer> aList = new ArrayList<>();
		for (int i = 0; i < size; i++)
			aList.add(min + i);
		Collections.shuffle(aList);
		System.out.println("Shuffled ArrayList: " + aList);
		
		// Add them to a HashSet, a LinkedHashSet, and a TreeSet
		// Printing them out, we see the order of their respective 
		// iterators:
		
		System.out.println("HashSet: " + new HashSet<Integer>(aList));
		// HashSets use the object hashCode method to find a location for
		// storage in a table.  Two objects can have the same hashCode
		// (i.e. collide), but if the _load factor_ is not too high, very
		// few positions need be searched to find an object in a hash
		// table. When the number of objects in the set exceeds the 
		// capacity times the load factor, the underlying capacity is
		// doubled.  One can specify the capacity up front. The ordering
		// of the set iterator has to do with the layout of items in the
		// hash table and thus depends on the hashCode and order of 
		// addition to the set.
		
		System.out.println("LinkedHashSet: " 
				+ new LinkedHashSet<Integer>(aList));
		// LinkedHashSets additionally maintain a LinkedList of set 
		// elements so as to be able to iterate through elements in the
		// order they were added.  Thus, order of addition is preserved
		// at a linear cost of additional linked-list memory.
		
		System.out.println("TreeSet: " + new TreeSet<Integer>(aList));
		// TreeSets are for Comparable objects (or any objects with a
		// constructor-supplied Comparator). Items are stored in a 
		// self-balancing binary search tree (e.g. a red-black tree)
		// with log(n) add, remove, and search operations. So the TreeSet
		// maintains a sorted order of elements at a cost of going from
		// an effectively constant cost per operation to a logarithmic
		// cost per operation.
		
		// Basic set operations: contains, size, addAll (union), 
		// removeAll (subtraction), retainAll (intersection)
		// These last three operations affect a set; they do not create
		// new set objects.
		Set<Integer> set1 = new HashSet<>(Arrays.asList(
				new Integer[] {1, 2}));
		Set<Integer> set2 = new HashSet<>(Arrays.asList(
				new Integer[] {2, 3}));

		System.out.printf("%s.size() -> %d\n", set1, set1.size());
		for (int i = 1; i <= 3; i++)
			System.out.printf("%s.contains(%d) -> %s\n", set1, i,
					set1.contains(i));
		
		System.out.printf("%s.addAll(%s) -> ", set1, set2);
		set1.addAll(set2);
		System.out.printf("%s (union)\n", set1);

		set1 = new HashSet<>(Arrays.asList(new Integer[] {1, 2}));
		System.out.printf("%s.removeAll(%s) -> ", set1, set2);
		set1.removeAll(set2);
		System.out.printf("%s (subtraction)\n", set1);

		set1 = new HashSet<>(Arrays.asList(new Integer[] {1, 2}));
		System.out.printf("%s.retainAll(%s) -> ", set1, set2);
		set1.retainAll(set2);
		System.out.printf("%s (intersection)\n", set1);
		
		// TreeSet operations
		
		// from interface SortedSet:
		TreeSet<Double> data = new TreeSet<>();
		Random random = new Random(0);
		for (int i = 0; i < size; i++)
			data.add(random.nextInt(1000) / 1000.0);
		System.out.println("data: " + data);
		System.out.println("data.first(): " + data.first());
		System.out.println("data.last(): " + data.last());
		System.out.println("data.headSet(.5): " + data.headSet(.5));
		// set less than
		System.out.println("data.tailSet(.5): " + data.tailSet(.5)); 
		// set greater than or equal to
		
		// from interface NavigableSet:
		System.out.println("After data.pollFirst() returns " 
				+ data.pollFirst() + ": " + data);
		System.out.println("After data.pollLast() returns " 
				+ data.pollLast() + ": " + data);
		System.out.println("data.lower(.5): " 
				+ data.lower(.5)); // next <
		System.out.println("data.lower(data.lower(.5)): " 
				+ data.lower(data.lower(.5))); 
		System.out.println("data.higher(.5): " 
				+ data.higher(.5)); // next >
		System.out.println("data.higher(data.higher(.5)): " 
				+ data.higher(data.higher(.5)));
		System.out.println("data.floor(.5): " 
				+ data.floor(.5)); // next <=
		System.out.println("data.floor(data.floor(.5)): "
				+ data.floor(data.floor(.5)));
		System.out.println("data.ceiling(.5): "
				+ data.ceiling(.5)); // next >=
		System.out.println("data.ceiling(data.ceiling(.5)): "
				+ data.ceiling(data.ceiling(.5)));
		
		System.out.println("\n\nExercises:");
		
		// Read the space-separated "words" (tokens) of the book 
		// "Fox in Socks" by Dr. Seuss into an ordered TreeSet.
		// This set is lexicographically sorted.
		TreeSet<String> words = new TreeSet<String>();
		Scanner foxInSocks = new Scanner(new URL(
			"http://cs.gettysburg.edu/~tneller/cs112/data/FoxInSocks.txt"
				).openStream());
		while (foxInSocks.hasNext())
			words.add(foxInSocks.next());
		foxInSocks.close();
		
		// Exercises:
		
		System.out.println(
			"(1.1) Print the number of unique \"words\" in the text:");
		// TODO

		System.out.println(
			"(1.2) Print the first word of the set:");
		// TODO
		
		System.out.println(
			"(1.3) Print the last word of the set:");
		// TODO
		
		System.out.println(
			"(1.4) Print the number of words before \"fox\" in the set:");
		// TODO
	
		System.out.println(
			"(1.5) Print the single word after \"fox\" in the set:");
		// TODO

		System.out.println(
			"(1.6) Print the single word before \"socks\" in the set:");
		// TODO
		
		System.out.println(
			"(1.7) Create a loop that prints words occurring from \"block\" through \"blue\"\n"
			+ "in the order using the NagivableSet methods from above.\n"
			+ "Print the words one per line:");
		// TODO

		System.out.println("\n\n");
		
		
		// Wednesday: Sets and Maps: sets versus lists, applications
		// (21.3-21.4)
		
		// "Sets are more efficient than lists for storing nonduplicate
		// elements. Lists are useful for accessing elements through the
		// index."
		// Note the chapter performance tests:
		// - All sets are efficient with randomly testing membership 
		//   (.contains(E)) and removal (.remove(E)).
		// - ArrayList and LinkedList are both inefficient with randomly
		//   testing membership and removal.
		
		// We here create our own performance tests for various sizes
		// of data:
		
		// TODO - uncomment to run:
//		performanceTests();
		
		System.out.println("\n\nExercises:\n");
		
		System.out.println("(2.1) Uncomment the following statements that are true:");
		// TODO
//		System.out.println("HashSet is inefficient with add operations.");
//		System.out.println("HashSet is inefficient with contains operations.");
//		System.out.println("HashSet is inefficient with remove operations.");
//		System.out.println("LinkedHashSet is inefficient with add operations.");
//		System.out.println("LinkedHashSet is inefficient with contains operations.");
//		System.out.println("LinkedHashSet is inefficient with remove operations.");
//		System.out.println("TreeSet is inefficient with add operations.");
//		System.out.println("TreeSet is inefficient with contains operations.");
//		System.out.println("TreeSet is inefficient with remove operations.");
//		System.out.println("ArrayList is inefficient with add operations.");
//		System.out.println("ArrayList is inefficient with contains operations.");
//		System.out.println("ArrayList is inefficient with remove operations.");
//		System.out.println("LinkedList is inefficient with add operations.");
//		System.out.println("LinkedList is inefficient with contains operations.");
//		System.out.println("LinkedList is inefficient with remove operations.");

		// Generate data for the following exercises
		size = 100000;
		System.out.println("ArrayList<Double> data1 has " + size + " random doubles.");
		ArrayList<Double> data1 = new ArrayList<>();
		for (int i = 0; i < size; i++)
			data1.add(random.nextDouble());		
		System.out.println("ArrayList<Double> data2 has the data of data1 shuffled.");
		ArrayList<Double> data2 = new ArrayList<>(data1);
		Collections.shuffle(data2);
		System.out.println("In the next exercises, print the total time (in ms) it takes to:\n"
				+ "- add all doubles in data1 to the collection, and\n"
				+ "- iterate through all doubles in data2, removing each from the collection.");
		System.out.println("(2.2) Print the number of milliseconds it takes using a HashSet:");
		// TODO

		System.out.println("(2.3) Print the number of milliseconds it takes using a LinkedHashSet:");
		// TODO

		System.out.println("(2.4) Print the number of milliseconds it takes using a TreeSet:");
		// TODO

		System.out.println("(2.5) Print the number of milliseconds it takes using an ArrayList:");
		// TODO
		
		System.out.println("(2.6) Print the number of milliseconds it takes using an LinkedList:");
		// TODO
		
		System.out.println("\n\n");
		
		
		
		// Friday: Sets and Maps: maps, HashMap, LinkedHashMap, TreeMap,
		// Collections static methods (21.5-21.7)
		
		// HashMap, LinkedHashMap, and TreeMap work as corresponding
		// sets, but with Map.Entry objects, i.e. key, value pairs.
		
		String[] digitNames = {"zero", "one", "two", "three", "four",
				"five", "six", "seven", "eight", "nine"};
		Map<String, Integer> map = new HashMap<>(); // String digit name
													// to Integer map
		for (int i = 0; i < digitNames.length; i++)
			map.put(digitNames[i], i);
		System.out.println(map);
		System.out.println("isEmpty(): " + map.isEmpty());
		System.out.println("size(): " + map.size());
		System.out.println("containsKey(\"seven\"): " 
				+ map.containsKey("seven"));
		System.out.println("containsValue(7): " + map.containsValue(7));
		System.out.println("get(\"seven\"): " + map.get("seven"));
		System.out.println("entrySet(): " + map.entrySet());
		System.out.println("keySet(): " + map.keySet());
		System.out.println("values(): " + map.values());
		Map<String, Integer> map2 = new HashMap<>();
		map2.putAll(map);
		System.out.println("putAll(map) into new HashMap map2 --> "
				+ map2);
		map2.remove("seven");
		System.out.println("after remove(\"seven\") --> " + map2);
		
		// Note that TreeMap analogues for the TreeSet methods also exist.

		// There are immutable constants for empty sets, lists, and maps:
		System.out.println(Collections.EMPTY_SET);
		System.out.println(Collections.EMPTY_LIST);
		System.out.println(Collections.EMPTY_MAP);

		// One can create immutable singleton (single-value) sets, lists,
		// and maps:
		Set<Integer> singletonSet = Collections.singleton(42);
		System.out.println(singletonSet);
		List<Integer> singletonList = Collections.singletonList(42);
		System.out.println(singletonList);
		Map<String, Integer> singletonMap 
			= Collections.singletonMap("forty-two", 42);
		System.out.println(singletonMap);
		
		// One can create immutable collections, sets, lists, and maps
		// from mutable ones:
		Set<Integer> answerSet = new HashSet<>();
		answerSet.add(42);
		Set<Integer> immutableAnswerSet 
			= Collections.unmodifiableSet(answerSet);
		System.out.println(immutableAnswerSet);
		List<Integer> answerList = new ArrayList<>();
		answerList.add(42);
		List<Integer> immutableAnswerList 
			= Collections.unmodifiableList(answerList);
		System.out.println(immutableAnswerList);
		Map<String, Integer> answerMap = new HashMap<>();
		answerMap.put("forty-two", 42);
		Map<String, Integer> immutableAnswerMap
			= Collections.unmodifiableMap(answerMap);
		System.out.println(immutableAnswerMap);
		
		// One can also create synchronized versions of collections, 
		// sets, lists, and maps from unsynchronized ones:
		// Collections.synchronizedCollection(c)
		// Collections.synchronizedList(list)
		// Collections.synchronizedMap(m)
		// Collections.synchronizedSet(s)
		
		// TODO - uncomment to run:
//		markovianFoxInSocks();

		
		System.out.println("\n\nExercises:\n");
		
		// First, we create a list of PhoneEntry objects that contain
		// first names, last names, and phone numbers.
		String[] firstNames = {"Ava", "Benjamin", "Charlotte"};
		String[] lastNames = {"Davis", "Evans", "Flores"};

		class PhoneEntry implements Comparable<PhoneEntry> {
			String first, last, phone;

			public PhoneEntry(String first, String last, String phone) {
				this.first = first;
				this.last = last;
				this.phone = phone;
			}
			
			@Override
			public String toString() {
				return String.format("%s, %s: %s", last, first, phone);
			}

			@Override
			public int compareTo(PhoneEntry o) {
				int lastCompare = last.compareTo(o.last);
				return lastCompare != 0 
						? lastCompare 
						: first.compareTo(o.first);
			}			
		}
		
		ArrayList<PhoneEntry> phoneEntries = new ArrayList<>();
		int last4 = 1234;
		for (int i = 0; i < firstNames.length; i++)
			for (int j = 0; j < lastNames.length; j++)
				phoneEntries.add(new PhoneEntry(firstNames[i],
												lastNames[j],
												"717-555-" + last4++));
		Collections.sort(phoneEntries);
		for (PhoneEntry entry : phoneEntries)
			System.out.println(entry);
		
		System.out.println("(3.1) Create a reverse phone lookup HashMap that maps phone number\n"
				+ "Strings to a corresponding PhoneEntry object.  Then use the HashMap\n"
				+ "to look up \"717-555-1240\" and print the corresponding PhoneEntry:");
		// TODO
		
		// For the next exercise, we create a lastNameMap HashMap that
		// maps each String last name to a TreeSet<PhoneEntry> that
		// can have multiple PhoneEntry objects inside:
		HashMap<String, TreeSet<PhoneEntry>> lastNameMap 
			= new HashMap<>();
		for (PhoneEntry entry : phoneEntries) {
			TreeSet<PhoneEntry> entries = lastNameMap.get(entry.last);
			if (entries == null)
				lastNameMap.put(entry.last,
						(entries = new TreeSet<PhoneEntry>()));
			// If there was no associated TreeSet in the HashMap,
			// we construct it, assign it to entries, and map to it.
			// Next we add to that TreeSet.
			entries.add(entry);
		}
		System.out.println("(3.2) Using lastNameMap, look up the TreeSet associated with\n"
				+ "\"Evans\" and print out the associated PhoneEntry objects, one per line.");
		// TODO

		System.out.println("(3.3) Create a firstNameMap that maps first names to a TreeSet\n"
				+ "with all PhoneEntry objects with that first name.  Then use it to look\n"
				+ "up and print all \"Benjamin\" entries, one per line.");
		// TODO
		
	}
	
	public static void performanceTests() {
		int[] sizes = {10000, 20000, 40000, 80000, 160000};
		ArrayList<Collection<Integer>> collections = new ArrayList<>();
		collections.add(new HashSet<Integer>());
		collections.add(new LinkedHashSet<Integer>());
		collections.add(new TreeSet<Integer>());
		collections.add(new ArrayList<Integer>());
		collections.add(new LinkedList<Integer>());
		String[] operations = {"add", "contains", "remove"};
		long[][][] results 
		  = new long[collections.size()][sizes.length][operations.length];
		
		// generate data
		ArrayList<ArrayList<Integer>> datasets = new ArrayList<>();
		for (int s = 0; s < sizes.length; s++) {
			int size = sizes[s];
			int min = -(size / 2);
			ArrayList<Integer> dataset = new ArrayList<>();
			for (int i = 0; i < size; i++)
				dataset.add(min + i);
			datasets.add(dataset);
		}
			
		// performance testing
		System.out.print("Testing... ");
		for (int c = 0; c < collections.size(); c++)
			for (int s = 0; s < sizes.length; s++) 
				results[c][s] = getAddContainsRemoveMS(collections.get(c), 
						datasets.get(s));
		System.out.println("done.");
		
		// print tabular results
		System.out.println("All performance times in milliseconds.");
		for (int o = 0; o < operations.length; o++) {
			System.out.printf("\n%s:\n         sizes:", operations[o]);
			for (int s = 0; s < sizes.length; s++)
				System.out.printf("%15d", sizes[s]);
			System.out.println();
			for (int c = 0; c < collections.size(); c++) {
				System.out.printf("%15s", 
						collections.get(c).getClass().getSimpleName());
				for (int s = 0; s < sizes.length; s++)
					System.out.printf("%15d", results[c][s][o]);
				System.out.println();
			}
		}
		System.out.println();

	}
	
	public static long[] getAddContainsRemoveMS(
			Collection<Integer> collection, List<Integer> data) {
		long[] results = new long[3];
		collection.clear();
		Collections.shuffle(data);
		long msStart = System.currentTimeMillis();
		for (Integer i : data)
			collection.add(i); 
		collection.addAll(data);
		results[0] = System.currentTimeMillis() - msStart;
		Collections.shuffle(data);
		msStart = System.currentTimeMillis();
		for (Integer i : data)
			collection.contains(i);
		results[1] = System.currentTimeMillis() - msStart;
		Collections.shuffle(data);
		msStart = System.currentTimeMillis();
		for (Integer i : data)
			collection.remove(i);
		results[2] = System.currentTimeMillis() - msStart;
		return results;
	}

	public static void markovianFoxInSocks() 
			throws MalformedURLException, IOException {
		System.out.println(
				"\nNow for the grand finale... Markovian Fox in Socks!\n");
			
		// Markov word generator:
		// Read a text and create a map of each word to an ArrayList of
		// words following that word in the text.
		// Begin with a tag <BEGIN> and end with tag <END>.
		// Then randomly generate a text where the probability of 
		// choosing a next word is in proportion to the word before.
		
		LinkedList<String> queue = new LinkedList<>();
		queue.offer("<BEGIN>");
		Scanner foxInSocks = new Scanner(new URL(
		  "http://cs.gettysburg.edu/~tneller/cs112/data/FoxInSocks.txt"
				).openStream());
		while (foxInSocks.hasNext())
			queue.offer(foxInSocks.next());
		foxInSocks.close();
		queue.offer("<END>");
		
		// Build associations between each word and the next word:
		Map<String, ArrayList<String>> nextWordsMap = new HashMap<>(); 
		String word = queue.poll();
		while (!word.equals("<END>")) {
			String nextWord = queue.poll();
			ArrayList<String> nextWords = nextWordsMap.get(word);
			if (nextWords == null) {
				nextWords = new ArrayList<String>();
				nextWordsMap.put(word, nextWords);
			}
			nextWords.add(nextWord);
			word = nextWord;
		}
		
		// Generate nonsense text according to next word frequency.
		word = "<BEGIN>";
		int i = 0;
		while (true) {
			ArrayList<String> nextWords = nextWordsMap.get(word);
			String nextWord 
			  = nextWords.get((int) (nextWords.size() * Math.random()));
			if (nextWord.equals("<END>"))
				break;
			else
				System.out.print(nextWord + " ");
			if (++i % 10 == 0) // line breaks every ten words
				System.out.println();
			word = nextWord;
		}
	}
	
}

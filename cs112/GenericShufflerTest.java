import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class GenericShufflerTest {

	/**
	 * Test our GenericShuffler. 
	 * @param args (unused)
	 */
	public static void main(String[] args) {
		// create a list of integers 0-9
		ArrayList<Integer> intList = new ArrayList<>();
		for (int i = 0; i < 10; i++)
			intList.add(i);
		
		// add integers individually to GenericShuffler with seed 42
		GenericShuffler<Integer> intShuffler = new GenericShuffler<>();
		intShuffler.setSeed(42);
		for (int i : intList)
			intShuffler.add(i);
		
		// get integers individually from GenericShuffler
		while (!intShuffler.empty())
			System.out.print(intShuffler.get() + " ");
		System.out.println();

		// reset seed to 42 again and allAll integers to GenericShuffler
		intShuffler.setSeed(42);
		intShuffler.addAll(intList);
		
		// getAll integers as stack and show that given the same seed 
		// they have the same order
		Stack<Integer> intStack = intShuffler.getAll();
		while (!intStack.empty())
			System.out.print(intStack.pop() + " ");
		System.out.println();

		// shuffle 3 strings "a", "b", "c"
		GenericShuffler<String> stringShuffler = new GenericShuffler<>();
		String[] stringArray = {"a", "b", "c"};
		for (String s : stringArray)
			stringShuffler.add(s);
		while (!stringShuffler.empty())
			System.out.print(stringShuffler.get() + " ");
		System.out.println();
		
		// demonstrate even distribution of shuffles over 1000000 trials
		// columns denote number of times at a given position
		int[][] counts = new int[stringArray.length][stringArray.length];
		for (int i = 0; i < 1000000; i++) {
			for (String s : stringArray)
				stringShuffler.add(s);
			int j = 0;
			while (!stringShuffler.empty())
				counts[stringShuffler.get().charAt(0) - 'a'][j++]++;
		}
		for (int i = 0; i < stringArray.length; i++)
			System.out.printf("%s: %s\n", stringArray[i], Arrays.toString(counts[i]));
	}

}

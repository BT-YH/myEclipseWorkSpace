import java.util.Comparator;

public class MahFravritTest {
	public static void main(String[] args) {
		// In this first example, we use random integers and prefer larger integers as fravrits (favorites).
		int size = 200;
		MahFravrit<Integer> intFravrit = new MahFravrit<Integer>(new Comparator<Integer>() 
		{
			public int compare(Integer v1, Integer v2) {
				return v1 - v2;
			}
		});
		// Add random integers, possibly with repetition.
		for (int i = 0; i < size; i++) {
			int item = (int) (size * Math.random());
			System.out.print(item + " ");
			intFravrit.add(item);
		}
		System.out.println();
		// Successively, remove integers, greatest to least as we've defined our preference with the Comparator above.
		for (int i = 0; i < size; i++) 
			System.out.print(intFravrit.getFravrit() + " ");
		System.out.println();

		// In this next example, we use Strings with book names and prefer later Strings lexicographically, 
		// except for "Gersberms", which are MOST preferred because "ERMAHGERD! GERSBERMS ... MAH FRAVRIT BERKS!" 
		// http://knowyourmeme.com/memes/ermahgerd
		MahFravrit<String> berkFravrit = new MahFravrit<String>(new Comparator<String>() 
		{
			public int compare(String v1, String v2) {
				if (v1.equals("Gersberms"))
					return 1;
				else if (v2.equals("Gersberms"))
					return -1;
				else
					return v1.compareTo(v2);
			}
		});
		berkFravrit.add("The Hobbit");
		berkFravrit.add("Gersberms");
		berkFravrit.add("Diary of a Wimpy Kid");
		System.out.println(berkFravrit.getFravrit());
		System.out.println(berkFravrit.getFravrit());
		System.out.println(berkFravrit.getFravrit());
	}
	
}

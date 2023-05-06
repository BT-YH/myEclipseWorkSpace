
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SortCSV {
	static int col;
	private static String integerRegex = "[-+]?[0-9]*"; // Java integer regex
	private static String doubleRegex = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"; // Java double regex
	static ArrayList<Object[]> list = new ArrayList<>();
	
	public static void main(String[] args) {
		try {
			col = Integer.parseInt(args[0]);
		} catch (RuntimeException e) {
			col = 0;
		}
		getInput();
		sort(list, col);
		getOutput();
	}
	
	public static void getInput() {
		Scanner in = new Scanner(System.in);;

		while (in.hasNextLine()) {
			String[] inputStrings = in.nextLine().split(",");
			Object[] inputObjects = new Object[inputStrings.length];
			for (int i = 0; i < inputStrings.length; i++) {
				if (inputStrings[i].matches(integerRegex)) {
					inputObjects[i] = Integer.parseInt(inputStrings[i]);
					continue;
				} else if (inputStrings[i].matches(doubleRegex)) {
					inputObjects[i] = Double.parseDouble(inputStrings[i]);
					continue;
				}
				inputObjects[i] = inputStrings[i];
			}
			list.add(inputObjects);
		}

		in.close();
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Comparable<E>> void sort(ArrayList<Object[]> records, int sortIndex) {
		E minimum;
		int index = 0;
		int comparison;
		for (int i = 0; i < records.size() - 1; i++) {
			minimum = (E) records.get(i)[sortIndex];
			for (int j = i+1; j < records.size(); j++) {
				E temp = (E)records.get(j)[sortIndex];
				comparison = temp.compareTo(minimum);
				if (comparison < 0) {
					minimum = temp;
					index = j;
				}
			}
			if (!minimum.equals((E)records.get(i)[sortIndex])) {
				Object[] newmin = records.remove(index);
				Object[] oldmin = records.remove(i);
				records.add(i, newmin);
				records.add(index, oldmin);
			}
			
		}
	}
	
	
	public static void getOutput() {
			for (Object[] line : list) {
				String temp = Arrays.toString(line);
				temp = temp.replaceAll(", ", ",");
				System.out.println(temp.substring(1, temp.length()-1));
			}
	}
}

import java.util.Map;
import java.util.TreeMap;

public class PigRollSequences {

	public static Map<Integer, Integer> pigMap = new TreeMap<>();

	public static int getNumSequences(int n) {
		if (!pigMap.containsKey(n)) {
			if (n < 0) return 0;
			if (n == 0) return 1;

			else { // recursive case/step
				pigMap.put(n, getNumSequences(n - 2) + getNumSequences(n - 3) + getNumSequences(n - 4) + getNumSequences(n - 5) + getNumSequences(n - 6));
			}
			
		}
		return pigMap.get(n);
	}

//
//	public static void main(String[] args) {
//		for (int i = 2; i <= 50; i++) {
//			System.out.println(getNumSequences(i));
//		}
//	}

}

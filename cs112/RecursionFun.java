import java.util.Map;
import java.util.TreeMap;

public class RecursionFun {
	
	public static int factorial(int n) { // assumes nonnegative n
//		if (n == 0) { // base cases
//			return 1;
//		} else { // recursive case/step
//			return n * factorial(n - 1);
//		}
		return (n == 0) ? 1 : n * factorial(n - 1);
	}
	
	public static long fib(int n)  { // assumes nonnegative n
		if (n == 0) { // base case 0
			return 0L;
		} else if (n == 1) { // base case 1
			return 1L;
		} else { // recursive case/step
			return fib(n - 1) + fib(n - 2);
		}
		//return (n < 2) ? n : fib(n - 1) + fib(n - 2);
	}
	
	public static Map<Integer, Long> fibMap = new TreeMap<>();
	
	public static long fibDP(int n)  { // assumes nonnegative n
		if (!fibMap.containsKey(n)) {
			if (n == 0) { // base case 0
				fibMap.put(n, 0L);
			} else if (n == 1) { // base case 1
				fibMap.put(n, 1L);
			} else { // recursive case/step
				fibMap.put(n, fibDP(n - 1) + fibDP(n - 2));
			}
		}
		return fibMap.get(n);
		//return (n < 2) ? n : fib(n - 1) + fib(n - 2);
	}
	
	/* Convert to dynamic programming DP:
	 * 1. Define a place to store results and see if they've been computed
	 * 2. Wrap up the previous recursive implementation in an 
	 * 	  if-not-yet-computed statement
	 * 3. change return statement to store statement
	 * 4. Return stored result.
	 */
	
	public static void main(String[] args) {
		for (int i = 0; i <=5; i++) {
			System.out.printf("%d! = %d\n", i, factorial(i));
		}
		
		for (int i = 0; i <= 92; i++) {
			System.out.printf("fibDP(%d) = %d\n", i, fibDP(i));
		}
		
	
		
//		for (int i = 0; i <= 60; i++) {
//			System.out.printf("fib(%d) = %d\n", i, fib(i));
//		}
		
//		moveHanoi(3, 0, 2, 1);
//		
//		// create sorted integer data
//		int size = 20;
//		int[] data = new int[size];
//		for (int i = 0; i < size; i++) {
//			data[i] = (int) (size * Math.random());
//		}
//		Arrays.sort(data);
//		System.out.println(Arrays.toString(data));
//		for (int i = 0; i < size; i++) { 
//			System.out.printf("binarySearch(%d, data) --> %d\n", 
//					i, binarySearch(i, data));
//		}
	}
	
	public static int binarySearch(int target, int[] data) {
		return binarySearch(target, data, 0, data.length - 1);
	}
	
	public static int binarySearch(int target, int[] data, int low, int high) {
		if (low > high) 
			return -1; // failure base case - no index
		int mid = (low + high) / 2;
		int val = data[mid];
		if (target == val) {
			return mid; // success base case
		} else if (val < target){
			return binarySearch(target, data, mid + 1, high);
		} else {
			return binarySearch(target, data, low, mid - 1);
		}
	}
	
	public static void moveHanoi(int numDisks, int fromPeg, int toPeg, int usingPeg) {
		if (numDisks == 1) {
			System.out.printf("%d - > %d\n", fromPeg, toPeg);
		} else {
			moveHanoi(numDisks - 1, fromPeg, usingPeg, toPeg);
			System.out.printf("%d - > %d\n", fromPeg, toPeg);
			moveHanoi(numDisks - 1, usingPeg, toPeg, fromPeg);
		}
		
	}
}

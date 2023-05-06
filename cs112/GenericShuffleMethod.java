import java.util.Arrays;
import java.util.Random;

public class GenericShuffleMethod {
	
	private static Random random = new Random();
	
	public static <E> E[] shuffle(E[] array, Random random) {
		// Knuth / Fisher-Yates Shuffle
		for (int i = array.length - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);
			E tmp = array[i];
			array[i] = array[j];
			array[j] = tmp;
		}
		return array;
	}
	
	public static <E> E[] shuffle(E[] array) {
		return shuffle(array, random);
	}
	
	public static void main(String[] args) {
		Random random = new Random(42);
		int size = 10;
		Integer[] array = new Integer[size];
		for (int i = 0; i < size; i++) {
			array[i] = i;
		}
		System.out.println(Arrays.toString(shuffle(array, random)));
		
		//repeat
		random.setSeed(42);
		for (int i = 0; i < size; i++) {
			array[i] = i;
		}
		System.out.println(Arrays.toString(shuffle(array, random)));
		
		System.out.println(Arrays.toString(shuffle("This is a test.".split(" "))));
		System.out.println(Arrays.toString(shuffle("This is a test.".split(" "))));
		System.out.println(Arrays.toString(shuffle("This is a test.".split(" "))));
	}
}

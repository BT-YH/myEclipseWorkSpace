import java.util.ArrayList;
import java.util.Scanner;

public class MergeSort {
	
	public static ArrayList<String> sort(ArrayList<String> list) {
		// Base Case
		if (list.size() <= 1) {
			return list;
		}
		// Recursive Case
		// divide the list into two sublists
		ArrayList<String> left = new ArrayList<>();
		ArrayList<String> right = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if (i < (list.size())/2) {
				left.add(list.get(i));
			} else {
				right.add(list.get(i));
			}
		}
		left = sort(left);
		right = sort(right);
		
		return merge(left, right);
	}
	
	public static ArrayList<String> merge(ArrayList<String> left, ArrayList<String> right) {
		ArrayList<String> result = new ArrayList<>();
		while (!left.isEmpty() && !right.isEmpty()) {
			if (left.get(0).compareTo(right.get(0)) < 0) {
				result.add(left.remove(0));
			} else {
				result.add(right.remove(0));
			}
		}
		
		// consume remaining elements
		while (!left.isEmpty()) {
			result.add(left.remove(0));
		}
		while (!right.isEmpty()) {
			result.add(right.remove(0));
		}
		return result;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		ArrayList<String> list = new ArrayList<>();
		while(in.hasNextLine()) {
			list.add(in.nextLine());
		}
		
		list = sort(list);
		System.out.println(list.toString());
	}
}

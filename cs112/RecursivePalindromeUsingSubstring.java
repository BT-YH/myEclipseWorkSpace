

public class RecursivePalindromeUsingSubstring {
	private static int counter;
	public static boolean isPalindrome(String s) {
		if (s.length() <= 1) {// Base case 
			counter++;
			return true;
		} else if (s.charAt(0) != s.charAt(s.length() - 1)) { // Base case
			counter++;
			return false;
		} else {
			counter++;
			return isPalindrome(s.substring(1, s.length() - 1));
		}
	}
	public static void main(String[] args) {
		System.out.println("Is noon a palindrome? "
		+ isPalindrome("noooooooon"));
		System.out.println(counter);
	}
}


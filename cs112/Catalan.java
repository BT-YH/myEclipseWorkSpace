
public class Catalan {
	public static long c(int n) {
		return (n == 0) ? 1 : ((4 * n - 2) * c(n-1)) / (n + 1);
	}
}

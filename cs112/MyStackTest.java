import java.util.NoSuchElementException;

public class MyStackTest {

	public static void main(String[] args) {
		MyStack<Integer> list = new MyStack<Integer>();
		java.util.Random random = new java.util.Random(0L);
		int trials = 200;
		for (int i = 0; i < trials; i++) {
			int op = random.nextInt(2);
			if (op < 1) { // pop
				try {
					int item = list.pop();
					System.out.println("  pop() --> \n" + item);
				}
				catch (NoSuchElementException e) {
					System.out.println(e.getMessage());
				}
			}
			else if (op == 1) { // push
				int item = random.nextInt(trials);
				list.push(item);
				System.out.printf("  push(%d)", item);
			}
			
			
			if (list.isEmpty())
				System.out.println(list);
			else
				System.out.printf("%s  head:%s", list, list.peek());
		}
	}


}
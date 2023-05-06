import java.util.NoSuchElementException;

public class MyQueueTest {
		public static void main(String[] args) {
			MyQueue<Integer> list = new MyQueue<Integer>();
			java.util.Random random = new java.util.Random(0L);
			int trials = 200;
			for (int i = 0; i < trials; i++) {
				int op = random.nextInt(2);
				if (op < 1) { // dequeue
					try {
						int item = list.dequeue();
						System.out.println(" dequeue() --> " + item);
					}
					catch (NoSuchElementException e) {
						System.out.println(e.getMessage());
					}
				}
				else if (op == 1) { // enqueue
					int item = random.nextInt(trials);
					list.enqueue(item);
					System.out.printf(" enqueue(%d)", item);
				}
				
				
				if (list.isEmpty())
					System.out.println(list);
				else
					System.out.printf("\n%s  head:%s", list, list.peek());
			}
		}
}

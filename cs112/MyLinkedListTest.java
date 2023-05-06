import java.util.NoSuchElementException;

public class MyLinkedListTest {

	public static void main(String[] args) {
		MyLinkedList<Integer> list = new MyLinkedList<Integer>();
		java.util.Random random = new java.util.Random(0L);
		int trials = 2000;
		for (int i = 0; i < trials; i++) {
			int op = random.nextInt(4);
			if (op <= 1) { // remove head
				try {
					int item = list.removeHead();
					System.out.println("removeHead() --> " + item);
				}
				catch (NoSuchElementException e) {
					System.out.println(e.getMessage());
				}
			}
			else if (op == 2) { // add to head
				int item = random.nextInt(trials);
				list.addToHead(item);
				System.out.printf("addToHead(%d)\n", item);
			}
			else { // add to tail
				int item = random.nextInt(trials);
				list.addToTail(item);
				System.out.printf("addToTail(%d)\n", item);
			}
			if (list.isEmpty())
				System.out.println(list);
			else
				System.out.printf("%s head:%s tail:%s\n", list, list.getHead(), list.getTail());
		}
	}


}

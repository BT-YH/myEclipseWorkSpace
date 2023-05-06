import java.util.NoSuchElementException;

public class MyStack<E> {
	MyLinkedListNode<E> head = null, tail = null;
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public E peek() {
		if (head == null) {
			throw new NoSuchElementException("peek: MyStack is empty");
		}
		return head.item;
	}
	
	
	public E pop() {
		if (head == null) {
			throw new NoSuchElementException("pop: MyStack is empty");
		}
		E item = head.item;
		head = head.next;
		if (head == null) {
			tail = null;
		}
		return item;
	}
	
	public void push(E item) {
		MyLinkedListNode<E> newNode = new MyLinkedListNode<E>(item, head);
		head = newNode;
		if (tail == null)
			tail = head;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		MyLinkedListNode<E> current = head;
		while(current != null) {
			sb.append(current.item);
			if (current.next != null) {
				sb.append(", ");
			}
			current = current.next;
		}
		sb.append("]");
		return sb.toString();
	}
	
	
}

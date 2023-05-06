import java.util.NoSuchElementException;

public class MyLinkedList<E> {
	MyLinkedListNode<E> head = null, tail = null;
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public E getHead() {
		return head == null ? null : head.item;
	}
	
	public E getTail() {
		return tail == null ? null : tail.item;
	}
	
	
	public E removeHead() {
		if (head == null) {
			throw new NoSuchElementException("MyLinkedList is empty");
		}
		E item = head.item;
		head = head.next;
		if (head == null) {
			tail = null;
		}
		return item;
	}
	
	public void addToHead(E item) {
		MyLinkedListNode<E> newNode = new MyLinkedListNode<E>(item, head);
		head = newNode;
		if (tail == null)
			tail = head;
	}
	
	public void addToTail(E item) {
		MyLinkedListNode<E> newNode = new MyLinkedListNode<E>(item);
		if (tail == null) {
			head = newNode; // Chained assignment
		} else {
			tail.next = newNode;
		}
		tail = newNode;
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

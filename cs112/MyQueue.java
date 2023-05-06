import java.util.NoSuchElementException;

public class MyQueue<E> {
	MyLinkedListNode<E> head = null, tail = null;
	
	public MyQueue() {
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public E dequeue() {
		if (head == null) {
			throw new NoSuchElementException("dequeue: MyQueue is empty");
		}
		E item = head.item;
		head = head.next;
		if (head == null) {
			tail = null;
		}
		return item;
	}
	
	public E peek() {
		if (head == null) {
			throw new NoSuchElementException("peek: MyQueue is empty");
		}
		return head.item;
	}
	
	public void enqueue(E item) {
		MyLinkedListNode<E> newNode = new MyLinkedListNode<E>(item);
		if (tail == null) {
			head = newNode; // Chained assignment
		} else {
			tail.next = newNode;
		}
		tail = newNode;
	}
	
	public java.lang.String toString(){
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

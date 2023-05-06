

public class MyLinkedListNode<E> {
	E item;
	MyLinkedListNode<E> next;
	
	public MyLinkedListNode(E item, MyLinkedListNode<E> next) {
		//super();
		this.item = item;
		this.next = next;
	}

	public MyLinkedListNode(E item) {
		//super();
		this.item = item;
	}
	
	
}

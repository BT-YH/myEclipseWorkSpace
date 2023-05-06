import java.util.Stack;

public class CardStack {
	
	protected Stack<Card> stack = new Stack<>();
	
	public void addCard(Card card) {
		stack.push(card);
	}
	
	public boolean canPlayFrom() throws IllegalPlayException{
		if (stack.isEmpty()) 
			throw new IllegalPlayException("You cannot play from an empty stack.");
		return !stack.isEmpty(); // also empty()
		// return stack.empty() ? false : true;
	}
	
	public Card getTopCard() throws java.util.EmptyStackException {
		if (stack.isEmpty()) 
			throw new java.util.EmptyStackException();
		return stack.peek();
	}
	
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	public boolean playTo(Card card) throws IllegalPlayException, java.lang.NullPointerException  {
		
		
		if (card == null) {
			throw new java.lang.NullPointerException();
		} 
		addCard(card); // else is not needed if the "if statement" contains a return statement
		return true;
	}
	
	public boolean playTo(CardStack otherStack) throws IllegalPlayException {
		if (!otherStack.canPlayFrom()) {
			throw new IllegalPlayException("--");
		}
		boolean legalPlay = playTo(otherStack.getTopCard());
		if (legalPlay) {
			otherStack.removeTopCard();
		}
		return legalPlay;
	}
	
	public Card removeTopCard() throws java.util.EmptyStackException {
		if (stack.isEmpty()) 
			throw new java.util.EmptyStackException();
		return stack.isEmpty() ? null : stack.pop();
	}
	
	public int size() {
		return stack.size();
	}
	
	@Override
	public String toString() {
		String s = stack.toString();
		return s.substring(1, s.length()-1);
	}
	
	public Card[] toArray() {
		return stack.toArray(new Card[stack.size()]);
	}
}
























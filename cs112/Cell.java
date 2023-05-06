
public class Cell extends CardStack {
	
	public boolean playTo(Card card) throws java.lang.NullPointerException, IllegalPlayException {
		if (card == null) 
			throw new java.lang.NullPointerException();
		
		if (stack.isEmpty()) {
			addCard(card);
			return true;
		}
		throw new IllegalPlayException("Cells may only contain a single card.");
	}
}

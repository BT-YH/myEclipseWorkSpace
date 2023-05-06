
public class Cascade extends CardStack {
	public boolean playTo(Card card) throws java.lang.NullPointerException, IllegalPlayException {
		if (card == null) 
			throw new java.lang.NullPointerException();
		
		boolean cardIsRed = Card.suitIsRed[card.getSuit()];
		if (stack.isEmpty()) {
			addCard(card);
			return true;
		} else { // stack is not empty
			boolean topIsRed = Card.suitIsRed[stack.peek().getSuit()];
			if (cardIsRed == topIsRed)
				throw new IllegalPlayException("Plays to a cascade must alternate in suit color.");
			if (card.getRank() != (stack.peek().getRank()-1))
				throw new IllegalPlayException("Plays to a cascade must have the next decreasing rank.");
		}
		addCard(card);
		return true;
	}
}


public class Foundation extends CardStack {
	public Foundation() {
	}
	
	public boolean canPlayFrom() throws IllegalPlayException {
		throw new IllegalPlayException("That card stack cannot be played from.");
	}
	
	public boolean playTo(Card card) throws java.lang.NullPointerException, IllegalPlayException {
		if (card == null) {
			throw new java.lang.NullPointerException();
		} else if (stack.isEmpty()) {
			if (card.getRank() != 0) {
				throw new IllegalPlayException("The first foundation card must be an Ace.");
			}
		} else if (card.getSuit() != stack.peek().getSuit()) {
			throw new IllegalPlayException("Plays to a foundation must match suit.");
		} else if (card.getRank() != (stack.peek().getRank()+1)) {
			throw new IllegalPlayException("Plays to a foundation must have the next increasing rank.");
		}	
		addCard(card);
		return true;
	}
	
	
}

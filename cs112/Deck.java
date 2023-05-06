import java.util.Random;

public class Deck extends CardStack {
	
	private Random random = new Random(0);
	
	public Deck(long seed) {
		for (Card c : Card.allCards) {
			addCard(c);
		}
		random.setSeed(seed);
		for (int i = stack.size() - 1; i > 0; i--) {
			int j = random.nextInt(i + 1); // be careful of the off-by-one error
			Card c = stack.get(i);
			stack.set(i, stack.get(j));
			stack.set(j, c);
		}
		
	}
	
	@Override
	public boolean canPlayFrom() throws IllegalPlayException {
		throw new IllegalPlayException("That card stack cannot be played from.");
	}
	
	@Override
	public boolean playTo(Card card) throws java.lang.NullPointerException,
    IllegalPlayException {
		if (card == null)
			throw new java.lang.NullPointerException();
		throw new IllegalPlayException("Illegal stack number. Stacks are numbered 1-16.");
	}
}

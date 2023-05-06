
public class Card {
	
	private static String[] rankNames = {
			"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
	};
	private static String[] suitNames = {"C", "d", "h", "S"}; // black upper case; red lower case
	
	public static boolean[] suitIsRed = {false, true, true, false};
	
	public static Card[] allCards;
	
	static { // class initializer
		allCards = new Card[rankNames.length * suitNames.length];
		int i = 0;
		for (int suit = 0; suit < suitNames.length; suit++) {
			for (int rank = 0; rank < rankNames.length; rank++) {
				allCards[i++] = new Card(rank, suit);
			}
		}
	}
	
	private int rank, suit;

	public Card(int rank, int suit) { // rank: 0-12  suit: 0-3
		this.rank = rank;
		this.suit = suit;
	}

	public int getRank() {
		return rank;
	}

	public int getSuit() {
		return suit;
	}

	public String toString() {
		return rankNames[rank] + suitNames[suit];
	}
}

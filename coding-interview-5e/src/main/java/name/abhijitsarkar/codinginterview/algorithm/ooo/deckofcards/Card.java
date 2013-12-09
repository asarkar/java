package name.abhijitsarkar.codinginterview.algorithm.ooo.deckofcards;

public class Card {
	private Suit suit;
	private Rank rank;

	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public Card(Suit suit, int rank) {
		this.suit = suit;
		this.rank = Rank.getByValue(rank);
	}
	
	public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}

	@Override
	public String toString() {
		return "Card [suit=" + suit + ", rank=" + rank + "]";
	}
}

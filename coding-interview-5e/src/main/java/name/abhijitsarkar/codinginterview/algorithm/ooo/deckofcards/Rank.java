package name.abhijitsarkar.codinginterview.algorithm.ooo.deckofcards;

public enum Rank {
	ACE(1), KING(11), QUEEN(12), JACK(13), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(
			6), SEVEN(7), EIGHT(8), NINE(9), TEN(10);

	private int value = 0;

	Rank(int value) {
		this.value = value;
	}

	public static Rank getByValue(int value) {
		Rank[] ranks = Rank.values();

		for (Rank aRank : ranks) {
			if (aRank.value == value) {
				return aRank;
			}
		}

		throw new IllegalArgumentException(value + " is not a valid rank.");
	}
}

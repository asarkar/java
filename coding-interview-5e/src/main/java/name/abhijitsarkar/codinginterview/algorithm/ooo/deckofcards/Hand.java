package name.abhijitsarkar.codinginterview.algorithm.ooo.deckofcards;

import java.util.AbstractList;

public class Hand extends AbstractList<Card> {
	public static final int SIZE_OF_HAND = 12;

	private Card[] cards = new Card[12];

	@Override
	public Card get(int index) {
		validateIndex(index);

		return cards[index];
	}
	
	public Card set(int index, Card card) {
		validateIndex(index);

		cards[index] = card;

		return card;
	}

	@Override
	public int size() {
		return SIZE_OF_HAND;
	}

	private void validateIndex(int index) {
		if (index < 0 || index >= SIZE_OF_HAND) {
			throw new IndexOutOfBoundsException("Index must be within the range [0, " + SIZE_OF_HAND + "]");
		}
	}
}

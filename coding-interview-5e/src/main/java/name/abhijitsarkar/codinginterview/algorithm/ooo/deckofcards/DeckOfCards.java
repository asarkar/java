package name.abhijitsarkar.codinginterview.algorithm.ooo.deckofcards;

import java.util.Random;

/*
 * Q8.1: Design the data structures for a generic deck of cards.
 */
public class DeckOfCards {
	public static final int NUM_CARDS = 52;

	private Card[] cards = new Card[NUM_CARDS];

	public DeckOfCards() {
		Suit[] suits = Suit.values();
		Rank[] ranks = Rank.values();

		int numCard = 0;

		for (Suit aSuit : suits) {
			for (Rank aRank : ranks) {
				Card newCard = new Card(aSuit, aRank);

				cards[numCard++] = newCard;
			}
		}
	}

	public Card card(int index) {
		validateIndex(index);

		return cards[index];
	}

	private void validateIndex(int index) {
		if (index < 0 || index >= NUM_CARDS) {
			throw new IndexOutOfBoundsException(
					"Index must be within the range [0, " + NUM_CARDS + "]");
		}
	}

	public void shuffle() {
		Random rand = new Random();
		final int maxNumShuffles = rand.nextInt(25) + 1;

		System.out.println("Shuffling " + maxNumShuffles + " times...");

		for (int numShuffle = 0; numShuffle < maxNumShuffles; numShuffle++) {
			int firstCardIdx = rand.nextInt(52);
			int secondCardIdx = rand.nextInt(52);

			swap(firstCardIdx, secondCardIdx);
		}
	}

	private final void swap(int firstCardIdx, int secondCardIdx) {
		if (firstCardIdx == secondCardIdx) {
			return;
		}

		Card temp = cards[firstCardIdx];
		cards[firstCardIdx] = cards[secondCardIdx];
		cards[secondCardIdx] = temp;

		System.out.println("Swapped " + temp + " with " + cards[firstCardIdx]
				+ ".");
	}
}

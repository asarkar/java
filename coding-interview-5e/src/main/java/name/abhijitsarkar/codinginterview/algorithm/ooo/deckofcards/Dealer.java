package name.abhijitsarkar.codinginterview.algorithm.ooo.deckofcards;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dealer {
	public static final Logger LOGGER = LoggerFactory.getLogger(Dealer.class);
	private DeckOfCards deck = new DeckOfCards();

	public void shuffle() {
		Random rand = new Random();
		final int maxNumShuffles = rand.nextInt(25) + 1;

		LOGGER.debug("Shuffling {} times.", maxNumShuffles);

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

		Card temp = deck.get(firstCardIdx);
		deck.set(firstCardIdx, deck.get(secondCardIdx));
		deck.set(secondCardIdx, temp);

		LOGGER.debug("Swapped {} with {}.", temp, deck.get(firstCardIdx));
	}

	public Hand deal() {
		shuffle();
		
		Hand hand = new Hand();
		Card card = null;

		for (int i = 0; i < Hand.SIZE_OF_HAND; ++i) {
			card = deck.get(i);

			LOGGER.debug("Dealing {}.", card);

			hand.set(i, card);
		}

		return hand;
	}
}

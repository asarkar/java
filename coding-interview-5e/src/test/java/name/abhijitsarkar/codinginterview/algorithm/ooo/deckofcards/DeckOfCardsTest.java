package name.abhijitsarkar.codinginterview.algorithm.ooo.deckofcards;

import org.junit.Assert;
import org.junit.Test;

public class DeckOfCardsTest {
	private DeckOfCards deck;

	public DeckOfCardsTest() {
		deck = new DeckOfCards();
	}

	@Test
	public void testDeckOfCards() {
		Assert.assertEquals(deck.card(0).getSuit(), deck.card(12).getSuit());
		Assert.assertEquals(deck.card(13).getSuit(), deck.card(25).getSuit());
		Assert.assertEquals(deck.card(26).getSuit(), deck.card(38).getSuit());
		Assert.assertEquals(deck.card(39).getSuit(), deck.card(51).getSuit());

		Assert.assertEquals(deck.card(0).getRank(), deck.card(13).getRank());
		Assert.assertEquals(deck.card(0).getRank(), deck.card(26).getRank());
		Assert.assertEquals(deck.card(0).getRank(), deck.card(39).getRank());
	}

	@Test
	public void testShuffle() {
		deck.shuffle();
	}
}

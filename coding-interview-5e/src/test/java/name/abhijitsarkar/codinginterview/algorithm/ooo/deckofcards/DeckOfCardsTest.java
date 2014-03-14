/*******************************************************************************
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software, 
 * and is also available at http://www.gnu.org/licenses.
 *******************************************************************************/
package name.abhijitsarkar.codinginterview.algorithm.ooo.deckofcards;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class DeckOfCardsTest {
	private DeckOfCards deck;

	public DeckOfCardsTest() {
		deck = new DeckOfCards();
	}

	@Test
	public void testDeckOfCards() {
		Assert.assertEquals(deck.get(0).getSuit(), deck.get(12).getSuit());
		Assert.assertEquals(deck.get(13).getSuit(), deck.get(25).getSuit());
		Assert.assertEquals(deck.get(26).getSuit(), deck.get(38).getSuit());
		Assert.assertEquals(deck.get(39).getSuit(), deck.get(51).getSuit());

		Assert.assertEquals(deck.get(0).getRank(), deck.get(13).getRank());
		Assert.assertEquals(deck.get(0).getRank(), deck.get(26).getRank());
		Assert.assertEquals(deck.get(0).getRank(), deck.get(39).getRank());
	}

	@Test
	public void testShuffle() {
		new Dealer().shuffle();
	}

	@Test
	public void testDeal() {
		new Dealer().deal();
	}
}

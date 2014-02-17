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

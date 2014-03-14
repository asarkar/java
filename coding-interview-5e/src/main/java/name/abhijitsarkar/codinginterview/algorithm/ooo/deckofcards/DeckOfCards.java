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

import java.util.AbstractList;

/**
 * @author Abhijit Sarkar
 */
/*
 * Q8.1: Design the data structures for a generic deck of cards.
 */
public class DeckOfCards extends AbstractList<Card> {
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

	private void validateIndex(int index) {
		if (index < 0 || index >= NUM_CARDS) {
			throw new IndexOutOfBoundsException("Index must be within the range [0, " + NUM_CARDS + "]");
		}
	}

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
		return NUM_CARDS;
	}
}

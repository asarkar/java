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

/**
 * @author Abhijit Sarkar
 */
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

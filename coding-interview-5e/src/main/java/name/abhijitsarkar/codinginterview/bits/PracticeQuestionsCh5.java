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
package name.abhijitsarkar.codinginterview.bits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh5 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh5.class);

	/*
	 * Q5.1: You are given two 32-bit numbers, N and M, and two bit positions, i and j . Write a method to insert M into
	 * N such that M starts at bit j and ends at bit i. You can assume that the bits j through i have enough space to
	 * fit all of M. That is, if M = 10011, you can assume that there are at least 5 bits between j and i. You would
	 * not, for example, have j = 3 and i = 2,because M could not fully fit between bit 3 and bit 2.
	 * 
	 * EXAMPLE Input: N = 10000000000, M = 10011, i = 2, j = 6 Output:N = 10001001100.
	 * 
	 * Solution: Since int is 32-bit in Java, we use int for M and N. The solution is 2 stepped. 
	 * First we clear out the bits j through i from N. Then we do a bitwise OR of M and N.
	 */
	public static int insertMIntoN(int N, int M, int i, int j) {
		LOGGER.debug("N = {}, M = {}, i = {}, j = {}.", N, M, i, j);

		/*
		 * ~0 is a bunch of 1s. Move them left of j, so that everything to the right, j included, is 0. Note that this
		 * makes the mask1 a negative number because the MSB is 1.
		 */
		final int mask1 = ~0 << (j + 1);

		/* ith bit 0, others 1 */
		final int mask2 = (1 << i) - 1;

		final int mask = mask1 | mask2;
		
		final int maskAndN = mask & N;
		final int leftShiftedM = M << i;

		LOGGER.debug("mask 1 = {}, mask 2 = {}.", mask1, mask2);
		LOGGER.debug("mask = {}.", mask);
		LOGGER.debug("maskAndN = {}.", maskAndN);
		LOGGER.debug("leftShiftedM = {}.", leftShiftedM);

		/* Clear the bits j through i by ANDing with N, move M to the correct place, OR. */
		return maskAndN | leftShiftedM;
	}
}

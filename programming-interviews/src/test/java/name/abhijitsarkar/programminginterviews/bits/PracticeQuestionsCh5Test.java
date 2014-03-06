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
package name.abhijitsarkar.programminginterviews.bits;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

import static name.abhijitsarkar.programminginterviews.bits.PracticeQuestionsCh5.*;

public class PracticeQuestionsCh5Test {

	@Test
	public void testComputeParities() {
		long[] input = null;

		Assert.assertNull(computeParities(input));

		long[] parities = computeParities(new long[87]);

		Assert.assertEquals(2, parities.length);

		input = new long[2];
		input[0] = 8L;
		input[1] = 9L;

		parities = computeParities(input);

		Assert.assertEquals(1, parities.length);
		Assert.assertEquals(1, parities[0]);
	}

	@Test
	public void testSwapBits() {
		Assert.assertEquals(2, swapBits(2, 1, 1));
		Assert.assertEquals(2, swapBits(2, -1, 1));
		Assert.assertEquals(2, swapBits(2, 1, 65));
		Assert.assertEquals(1, swapBits(2, 0, 1));
		Assert.assertEquals(14, swapBits(7, 0, 3));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPowerSet() {
		powerSet(null);
		powerSet(Collections.EMPTY_SET);

		Set<Integer> set = new HashSet<>();
		set.add(1);
		set.add(2);
		set.add(3);
		powerSet(set);
	}

	@Test
	public void testStringToInt() {
		try {
			stringToInt(null);
			Assert.fail();
		} catch (NumberFormatException iae) {
		}
		try {
			stringToInt("123abc");
			Assert.fail();
		} catch (NumberFormatException iae) {
		}

		int result = stringToInt("-123");
		Assert.assertEquals(-123, result);

		result = stringToInt("123");
		Assert.assertEquals(123, result);
	}

	@Test
	public void testConvertToBase() {
		Assert.assertNull(changeBase(null, 10, 2));
		Assert.assertEquals("", changeBase("", 10, 2));
		Assert.assertEquals("1001", changeBase("9", 10, 2));
		Assert.assertEquals("1F", changeBase("31", 10, 16));
	}
}

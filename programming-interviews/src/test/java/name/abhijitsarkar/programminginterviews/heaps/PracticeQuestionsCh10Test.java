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
package name.abhijitsarkar.programminginterviews.heaps;

import static name.abhijitsarkar.programminginterviews.heaps.PracticeQuestionsCh10.kSort;
import static name.abhijitsarkar.programminginterviews.heaps.PracticeQuestionsCh10.runningMedian;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsCh10Test {
	@Test
	public void testKSort() {
		final Integer[] arr = { 3, 1, 7, 4, 10 };

		final int[] expected = { 1, 3, 4, 7, 10 };
		int[] actual = kSort(arr, 2);

		Assert.assertArrayEquals(expected, actual);

		actual = kSort(arr, 4);

		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void testRunningMedian() {
		int[] elements = { 5, 15, 1, 3, 2, 8, 7 };
		int[] expected = { 5, 10, 5, 4, 3, 4, 5 };

		int[] actual = runningMedian(elements);

		Assert.assertArrayEquals(expected, actual);
	}
}

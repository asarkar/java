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
package name.abhijitsarkar.programminginterviews.sorting;

import static name.abhijitsarkar.programminginterviews.sorting.PracticeQuestionsCh13.intersection;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsCh13Test {

	@Test
	public void testIntersection() {
		int[] a = { -2, -1, 5, 7, 11 };
		int[] b = { 0, 1, 5, 7, 13 };

		Assert.assertArrayEquals(new Integer[] { 5, 7 }, intersection(a, b));
	}
}

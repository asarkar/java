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
package name.abhijitsarkar.programminginterviews.searches;

import static name.abhijitsarkar.programminginterviews.searches.PracticeQuestionsCh11.firstOccurrence;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsCh11Test {

	@Test
	public void testFirstOccurance() {
		final int[] arr = { -1, -10, 2, 108, 108, 243, 285, 285, 285, 401 };

		Assert.assertEquals(-1, firstOccurrence(arr, 500));
		Assert.assertEquals(3, firstOccurrence(arr, 101));
	}
	
}

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
package name.abhijitsarkar.codinginterview.datastructure.arraysnstrings;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh1Test {
	@Test
	public void testIsUnique() {
		Assert.assertTrue(PracticeQuestionsCh1.isUnique("abc"));
		Assert.assertFalse(PracticeQuestionsCh1.isUnique("aba"));
	}

	@Test
	public void testIsPermutation() {
		Assert.assertTrue(PracticeQuestionsCh1.isPermutation("abc", "cba"));
		Assert.assertTrue(PracticeQuestionsCh1.isPermutation("aBc", "acB"));
		Assert.assertFalse(PracticeQuestionsCh1.isPermutation("abc", "c"));
		Assert.assertFalse(PracticeQuestionsCh1.isPermutation("abc", "def"));
	}

	@Test
	public void testEncodeRepeatedChars() {
		Assert.assertEquals("a3b2",
				PracticeQuestionsCh1.encodeRepeatedChars("aaabb"));
		Assert.assertEquals("abc", PracticeQuestionsCh1.encodeRepeatedChars("abc"));
	}

	@Test
	public void testFillIfZero() {
		int[][] matrix = new int[][] { { 1, 2 }, { 3, 4 }, { 5, 0 } };

		PracticeQuestionsCh1.fillIfZero(matrix);

		Assert.assertEquals(0, matrix[0][1]);
		Assert.assertEquals(0, matrix[1][1]);
		Assert.assertEquals(0, matrix[2][0]);
		Assert.assertEquals(0, matrix[2][1]);

		Assert.assertEquals(1, matrix[0][0]);
		Assert.assertEquals(3, matrix[1][0]);
	}

	@Test
	public void testIsRotation() {
		Assert.assertTrue(PracticeQuestionsCh1.isRotation("erbottlewat",
				"waterbottle"));
		Assert.assertFalse(PracticeQuestionsCh1.isRotation("erbottlewat",
				"waterbottel"));
	}
}

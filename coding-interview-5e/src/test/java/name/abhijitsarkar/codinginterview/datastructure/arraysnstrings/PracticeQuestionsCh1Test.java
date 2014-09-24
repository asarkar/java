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

import static name.abhijitsarkar.codinginterview.datastructure.arraysnstrings.PracticeQuestionsCh1.encodeRepeatedChars;
import static name.abhijitsarkar.codinginterview.datastructure.arraysnstrings.PracticeQuestionsCh1.fillIfZero;
import static name.abhijitsarkar.codinginterview.datastructure.arraysnstrings.PracticeQuestionsCh1.isPermutation;
import static name.abhijitsarkar.codinginterview.datastructure.arraysnstrings.PracticeQuestionsCh1.isRotation;
import static name.abhijitsarkar.codinginterview.datastructure.arraysnstrings.PracticeQuestionsCh1.isUnique;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh1Test {
	@Test
	public void testIsUnique() {
		assertTrue(isUnique("abc"));
		assertFalse(isUnique("aba"));
	}

	@Test
	public void testIsPermutation() {
		assertTrue(isPermutation("abc", "cba"));
		assertTrue(isPermutation("aBc", "acB"));
		assertFalse(isPermutation("abc", "c"));
		assertFalse(isPermutation("abc", "def"));
	}

	@Test
	public void testEncodeRepeatedChars() {
		assertEquals("a3b2", encodeRepeatedChars("aaabb"));
		assertEquals("abc", encodeRepeatedChars("abc"));
		assertEquals("a3b3a1", encodeRepeatedChars("aaabbba"));
		assertEquals("a1b3c3", encodeRepeatedChars("abbbccc"));
	}

	@Test
	public void testFillIfZero() {
		int[][] matrix = new int[][] { { 1, 2 }, { 3, 4 }, { 5, 0 } };

		fillIfZero(matrix);

		assertEquals(0, matrix[0][1]);
		assertEquals(0, matrix[1][1]);
		assertEquals(0, matrix[2][0]);
		assertEquals(0, matrix[2][1]);

		assertEquals(1, matrix[0][0]);
		assertEquals(3, matrix[1][0]);
	}

	@Test
	public void testIsRotation() {
		assertTrue(isRotation("erbottlewat", "waterbottle"));
		assertFalse(isRotation("erbottlewat", "waterbottel"));
	}
}

package name.abhijitsarkar.codinginterview.datastructure.arraysnstrings;

import name.abhijitsarkar.codinginterview.datastructure.arraysnstrings.PracticeQuestions;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsTest {
	@Test
	public void testIsUnique() {
		Assert.assertTrue(PracticeQuestions.isUnique("abc"));
		Assert.assertFalse(PracticeQuestions.isUnique("aba"));
	}

	@Test
	public void testIsPermutation() {
		Assert.assertTrue(PracticeQuestions.isPermutation("abc", "cba"));
		Assert.assertTrue(PracticeQuestions.isPermutation("aBc", "acB"));
		Assert.assertFalse(PracticeQuestions.isPermutation("abc", "c"));
		Assert.assertFalse(PracticeQuestions.isPermutation("abc", "def"));
	}

	@Test
	public void testEncodeRepeatedChars() {
		Assert.assertEquals("a3b2",
				PracticeQuestions.encodeRepeatedChars("aaabb"));
		Assert.assertEquals("abc", PracticeQuestions.encodeRepeatedChars("abc"));
	}

	@Test
	public void testFillIfZero() {
		int[][] matrix = new int[][] { { 1, 2 }, { 3, 4 }, { 5, 0 } };

		PracticeQuestions.fillIfZero(matrix);

		Assert.assertEquals(0, matrix[0][1]);
		Assert.assertEquals(0, matrix[1][1]);
		Assert.assertEquals(0, matrix[2][0]);
		Assert.assertEquals(0, matrix[2][1]);

		Assert.assertEquals(1, matrix[0][0]);
		Assert.assertEquals(3, matrix[1][0]);
	}

	@Test
	public void testIsRotation() {
		Assert.assertTrue(PracticeQuestions.isRotation("erbottlewat",
				"waterbottle"));
		Assert.assertFalse(PracticeQuestions.isRotation("erbottlewat",
				"waterbottel"));
	}
}

package name.abhijitsarkar.codinginterview.algorithm.recursion;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsTest {

	@Test
	public void testMagicIndex() {
		int[] array = new int[] { -1, 0, 2 };

		Assert.assertEquals(2,
				PracticeQuestions.magicIndex(array, 0, array.length));

		array = new int[] { 1, 2, 3 };

		Assert.assertEquals(-1,
				PracticeQuestions.magicIndex(array, 0, array.length));
	}

	@Test
	public void testPerm() {
		String s = "abc";

		String[] expected = new String[] { "cab", "abc", "bac", "acb" };

		String[] actual = PracticeQuestions.perm(s);

		Assert.assertArrayEquals(expected, actual);
	}
}

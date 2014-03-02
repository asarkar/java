package name.abhijitsarkar.programminginterviews.arraysnstrings;

import static name.abhijitsarkar.programminginterviews.arraysnstrings.PracticeQuestionsCh6.keypadPermutation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsCh6Test {
	@Test
	public void testKeypadPermutation() {
		List<String> expected = new ArrayList<>();
		expected.add("AD");
		expected.add("AE");
		expected.add("AF");
		expected.add("BD");
		expected.add("BE");
		expected.add("BF");
		expected.add("CD");
		expected.add("CE");
		expected.add("CF");

		Assert.assertEquals(expected, keypadPermutation(23));
		
		expected.clear();
		expected.add("A");
		expected.add("B");
		expected.add("C");
		
		Assert.assertEquals(expected, keypadPermutation(12));
	}
}

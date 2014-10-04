package name.abhijitsarkar.codinginterview.nordstrom;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class AssignmentTest {
	private Assignment assignment = new Assignment();

	@Test
	public void testGetLongestString() {
		String[] input = new String[] { "Yuri", "Interview", "Nordstrom", "Cat", "Dog", "Telephone", "AVeryLongString",
				"This code puzzle is easy" };

		Assert.assertEquals("This code puzzle is easy", assignment.getLongestString(1, Arrays.asList(input)));
	}

	@Test
	public void testIsPermutationOfEachOther() {
		Assert.assertTrue(assignment.isPermutationOfEachOther("abcd", "cdab"));
		Assert.assertFalse(assignment.isPermutationOfEachOther("abcd", "123"));
	}
}

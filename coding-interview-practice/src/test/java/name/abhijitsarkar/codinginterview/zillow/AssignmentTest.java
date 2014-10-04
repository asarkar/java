package name.abhijitsarkar.codinginterview.zillow;

import org.junit.Assert;
import org.junit.Test;

public class AssignmentTest {
	private final Assignment assignment = new Assignment();

	@Test
	public void testStringToLongSuccess() {
		Assert.assertEquals(123l, assignment.stringToLong("123"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStringToLongFailure() {
		assignment.stringToLong("12a");
	}
}

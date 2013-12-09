package name.abhijitsarkar.codinginterview.datastructure.stacksnqueues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StackWithMinTest {
	StackWithMin<Integer> stack = new StackWithMin<Integer>();

	@Before
	public void setUp() {
		stack.push(99);
		stack.push(3);
		stack.push(1);
	}

	@Test
	public void testMin() {
		Assert.assertEquals(Integer.valueOf(1), stack.min());
	}
}

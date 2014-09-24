package name.abhijitsarkar.codinginterview.datastructure.stacksnqueues;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SetOfStacksTest {
	private SetOfStacks<Integer> stacks;

	@Before
	public void setUp() {
		stacks = new SetOfStacks<Integer>(1);
	}

	@Test
	public void testIsEmpty() {
		assertTrue(stacks.isEmpty());
	}

	@Test
	public void testPush() {
		stacks.push(1);
		stacks.push(2);

		assertEquals(2, stacks.size());
		assertEquals(2, stacks.numElements());
	}

	@Test
	public void testPop() {
		stacks.push(1);
		stacks.push(2);

		int e = stacks.pop();

		assertEquals(2, e);

		assertEquals(1, stacks.size());
		assertEquals(1, stacks.numElements());
	}

	@Test
	public void testPeek() {
		stacks.push(1);
		stacks.push(2);

		int e = stacks.peek();

		assertEquals(2, e);

		assertEquals(2, stacks.size());
		assertEquals(2, stacks.numElements());
	}
}

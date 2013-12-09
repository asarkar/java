package name.abhijitsarkar.codinginterview.datastructure;

import java.util.Arrays;
import java.util.NoSuchElementException;

import name.abhijitsarkar.codinginterview.datastructure.impl.LinkedListImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LinkedListImplTest {
	private LinkedList<Integer> linkedList;

	@Before
	public void setUp() {
		Integer[] elements = new Integer[] { 1, 2, 1 };
		linkedList = new LinkedListImpl<Integer>(Arrays.asList(elements));
	}

	@Test
	public void testAdd() {
		Assert.assertEquals(3, linkedList.size());
		Assert.assertTrue(linkedList.add(4));
		Assert.assertEquals(4, linkedList.size());
		Assert.assertTrue(linkedList.add(linkedList.size(), 0));
		Assert.assertEquals(5, linkedList.size());
		Assert.assertEquals(Integer.valueOf(4), linkedList.remove(3));
		Assert.assertEquals(Integer.valueOf(1), linkedList.remove());
	}

	@Test
	public void testRemove() {
		Assert.assertEquals(3, linkedList.size());
		Assert.assertEquals(Integer.valueOf(1), linkedList.remove());
		Assert.assertEquals(2, linkedList.size());
		Assert.assertEquals(Integer.valueOf(2), linkedList.remove(0));
		Assert.assertEquals(1, linkedList.size());
		Assert.assertEquals(Integer.valueOf(1), linkedList.remove());
		Assert.assertEquals(0, linkedList.size());
	}

	@Test
	public void testReverse() {
		linkedList.reverse();

		Assert.assertEquals(3, linkedList.size());
		Assert.assertEquals(Integer.valueOf(1), linkedList.remove());
		Assert.assertEquals(2, linkedList.size());
		Assert.assertEquals(Integer.valueOf(2), linkedList.remove());
		Assert.assertEquals(1, linkedList.size());
		Assert.assertEquals(Integer.valueOf(1), linkedList.remove());
		Assert.assertEquals(0, linkedList.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testInvalidIndexOperation() {
		linkedList.add(10, 1);
	}

	@Test(expected = NoSuchElementException.class)
	public void testAttemptRemoveWhenEmpty() {
		int size = linkedList.size();

		while (size-- > 0) {
			linkedList.remove();
		}

		linkedList.remove();
	}
}

package name.abhijitsarkar.datastructure;

import java.util.Arrays;

import name.abhijitsarkar.datastructure.LinkedList;
import name.abhijitsarkar.datastructure.impl.LinkedListImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LinkedListImplTest {
	private LinkedList<Integer> linkedList;

	@Before
	public void setUp() {
		Integer[] elements = new Integer[] { 3, 2, 1 };
		linkedList = new LinkedListImpl<Integer>(Arrays.asList(elements));
	}

	@Test
	public void testAdd() {
		Assert.assertEquals(3, linkedList.size());
		Assert.assertTrue(linkedList.add(4));
		Assert.assertEquals(4, linkedList.size());
		Assert.assertTrue(linkedList.add(0));
		Assert.assertEquals(5, linkedList.size());
	}

	@Test
	public void testRemove() {
		Assert.assertEquals(3, linkedList.size());
		Assert.assertEquals(Integer.valueOf(3), linkedList.remove());
		Assert.assertEquals(2, linkedList.size());
		Assert.assertEquals(Integer.valueOf(2), linkedList.remove());
		Assert.assertEquals(1, linkedList.size());
		Assert.assertEquals(Integer.valueOf(1), linkedList.remove());
		Assert.assertEquals(0, linkedList.size());
	}

	@Test
	public void testAddAndRemove() {
		Assert.assertEquals(3, linkedList.size());
		Assert.assertTrue(linkedList.add(0, 4));
		Assert.assertEquals(4, linkedList.size());
		Assert.assertEquals(Integer.valueOf(4), linkedList.remove());
		Assert.assertEquals(3, linkedList.size());
	}

	@Test
	public void testReverse() {
		linkedList.reverse();

		Assert.assertEquals(3, linkedList.size());
		Assert.assertEquals(Integer.valueOf(1), linkedList.remove());
		Assert.assertEquals(2, linkedList.size());
		Assert.assertEquals(Integer.valueOf(2), linkedList.remove());
		Assert.assertEquals(1, linkedList.size());
		Assert.assertEquals(Integer.valueOf(3), linkedList.remove());
		Assert.assertEquals(0, linkedList.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testInvalidIndexOperation() {
		linkedList.add(10, 1);
	}
}

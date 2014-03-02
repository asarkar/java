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
package name.abhijitsarkar.algorithms.core.datastructure;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class LinkedListTest {
	private LinkedList<Integer> linkedList;

	@Before
	public void setUp() {
		Integer[] elements = new Integer[] { 1, 2, 1 };
		linkedList = new LinkedList<Integer>(Arrays.asList(elements));
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

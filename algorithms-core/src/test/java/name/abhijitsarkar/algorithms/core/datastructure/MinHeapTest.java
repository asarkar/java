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
public class MinHeapTest {
	private MinHeap<Integer> minHeap;
	private final Integer[] arr = { 4, 1, 3, 2, 16, 9, 10, 14, 8, 7 };
	private final Integer[] expected = { 1, 2, 3, 4, 7, 9, 10, 14, 8, 16 };

	@Before
	public void setUp() {
		minHeap = new MinHeap<Integer>(arr);
	}

	@Test
	public void testBuildMinHeap() {
		Assert.assertEquals(arr.length, minHeap.size());
	}

	@Test
	public void testDelete() {
		final Integer[] sorted = Arrays.copyOf(expected, expected.length);
		Arrays.sort(sorted);

		for (int i = 0; i < sorted.length; ++i) {
			Assert.assertEquals("Delete " + i + "th times should be " + sorted[i], sorted[i], minHeap.delete());
			Assert.assertEquals(sorted.length - (i + 1), minHeap.size());
		}
	}

	@Test
	public void testInsert() {
		MinHeap<Integer> mh = new MinHeap<Integer>(new Integer[] {});

		for (int i : arr) {
			mh.insert(i);
		}

		Assert.assertEquals(arr.length, mh.size());

		final Integer[] expected = { 1, 2, 3, 4, 7, 9, 10, 14, 8, 16 };

		Assert.assertArrayEquals(mh.heap, expected);
	}

	@Test(expected = NoSuchElementException.class)
	public void testDeleteWhenEmpty() {
		MinHeap<Integer> mh = new MinHeap<Integer>(new Integer[] {});

		mh.delete();
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testInsertWhenFull() {
		minHeap.insert(5);
	}

	@Test
	public void testLeftChild() {
		Assert.assertEquals(2, minHeap.leftChild(0).intValue());
		Assert.assertEquals(4, minHeap.leftChild(1).intValue());
		Assert.assertEquals(9, minHeap.leftChild(2).intValue());
		Assert.assertEquals(14, minHeap.leftChild(3).intValue());
		Assert.assertEquals(16, minHeap.leftChild(4).intValue());
		Assert.assertNull(minHeap.leftChild(5));
		Assert.assertNull(minHeap.leftChild(6));
		Assert.assertNull(minHeap.leftChild(7));
		Assert.assertNull(minHeap.leftChild(8));
		Assert.assertNull(minHeap.leftChild(9));
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testLeftChildWhenIndexOver() {
		final int indexOver = arr.length + 1;

		minHeap.leftChild(indexOver);
	}

	@Test
	public void testrightChild() {
		Assert.assertEquals(3, minHeap.rightChild(0).intValue());
		Assert.assertEquals(7, minHeap.rightChild(1).intValue());
		Assert.assertEquals(10, minHeap.rightChild(2).intValue());
		Assert.assertEquals(8, minHeap.rightChild(3).intValue());
		Assert.assertNull(minHeap.rightChild(4));
		Assert.assertNull(minHeap.rightChild(5));
		Assert.assertNull(minHeap.rightChild(6));
		Assert.assertNull(minHeap.rightChild(7));
		Assert.assertNull(minHeap.rightChild(8));
		Assert.assertNull(minHeap.rightChild(9));
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testRightChildWhenIndexOver() {
		final int indexOver = arr.length + 1;

		minHeap.rightChild(indexOver);
	}
}

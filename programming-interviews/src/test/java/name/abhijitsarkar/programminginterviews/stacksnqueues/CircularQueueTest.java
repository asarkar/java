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
package name.abhijitsarkar.programminginterviews.stacksnqueues;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import java.util.NoSuchElementException;

public class CircularQueueTest {
	private CircularQueue queue;

	@Before
	public void setUp() {
		queue = new CircularQueue();
	}

	@Test
	public void testCreateQueue() {
		Assert.assertEquals(CircularQueue.DEFAULT_CAPACITY, queue.capacity());
		Assert.assertEquals(0, queue.size());
	}

	@Test
	public void testEnqueue() {
		queue.enqueue(11);

		Assert.assertEquals(CircularQueue.DEFAULT_CAPACITY, queue.capacity());
		Assert.assertEquals(1, queue.size());
	}

	@Test
	public void testDequeue() {
		queue.enqueue(11);
		queue.enqueue(12);
		queue.enqueue(13);

		Assert.assertEquals(11, queue.dequeue());
		Assert.assertEquals(CircularQueue.DEFAULT_CAPACITY, queue.capacity());
		Assert.assertEquals(2, queue.size());
	}

	@Test(expected = NoSuchElementException.class)
	public void testDequeueWhenEmpty() {
		queue.dequeue();
	}

	@Test
	public void testCapacity() {
		CircularQueue cq = new CircularQueue(1);

		Assert.assertEquals(1, cq.capacity());
		Assert.assertEquals(0, cq.size());
	}

	@Test
	public void testResize() {
		CircularQueue cq = new CircularQueue(1);

		Assert.assertEquals(1, cq.capacity());
		Assert.assertEquals(0, cq.size());

		cq.enqueue(1);
		cq.enqueue(2);

		Assert.assertEquals(2, cq.capacity());
		Assert.assertEquals(2, cq.size());
	}
}

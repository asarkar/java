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
package name.abhijitsarkar.codinginterview.datastructure.stacksnqueues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class MyQueueTest {
	private MyQueue<Integer> queue = null;

	@Before
	public void setUp() {
		queue = new MyQueue<Integer>();

		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
	}

	@Test
	public void testQueue() {
		Assert.assertFalse(queue.isEmpty());

		Assert.assertEquals(Integer.valueOf(1), queue.dequeue());
		Assert.assertEquals(Integer.valueOf(2), queue.dequeue());

		queue.enqueue(4);

		Assert.assertEquals(Integer.valueOf(3), queue.dequeue());
		Assert.assertEquals(Integer.valueOf(4), queue.dequeue());

		Assert.assertTrue(queue.isEmpty());
	}
}

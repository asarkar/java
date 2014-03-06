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

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* Q8.10: Implement a queue API using an array for storing elements. Your API should include a constructor function, 
 * which takes as argument the capacity of the queue, enqueue and dequeue functions, a size function, 
 * which returns the number of elements stored, and implement dynamic resizing.
 */
public class CircularQueue {
	public static final Logger LOGGER = LoggerFactory.getLogger(CircularQueue.class);
	public static final int DEFAULT_CAPACITY = 10;

	private int[] queue;
	private int begin;
	private int end = -1;
	private int capacity;

	public CircularQueue() {
		this(DEFAULT_CAPACITY);
	}

	public CircularQueue(final int capacity) {
		this.capacity = capacity;
		queue = new int[capacity];
	}

	public int size() {
		return end - begin + 1;
	}

	public int capacity() {
		return capacity;
	}

	public int enqueue(int element) {
		if (isCapacityFull()) {
			resize();
		}

		queue[++end] = element;

		return element;
	}

	private boolean isCapacityFull() {
		return capacity == size();
	}

	private void resize() {
		LOGGER.debug("Resizing queue from {} to {}.", capacity, capacity * 2);

		queue = Arrays.copyOf(queue, capacity *= 2);
	}

	public int dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("Cannot dequeue from an empty queue.");
		}

		return queue[begin++];
	}

	public boolean isEmpty() {
		return end < 0;
	}
}

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
package name.abhijitsarkar.codinginterview.datastructure;

import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
public class Queue<E> {
	private LinkedList<E> queue = null;

	public Queue() {
		queue = new LinkedList<E>();
	}

	public Queue(Collection<E> elements) {
		queue = new LinkedList<E>(elements);
	}

	public E dequeue() {
		return queue.remove(0);
	}

	public boolean enqueue(E element) {
		return queue.add(element);
	}

	public boolean isEmpty() {
		return queue.size() == 0;
	}

	@Override
	public String toString() {
		return "Queue [queue=" + queue + "]";
	}
}

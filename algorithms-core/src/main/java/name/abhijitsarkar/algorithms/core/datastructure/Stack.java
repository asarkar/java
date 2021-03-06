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

import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
public class Stack<E> {
	protected LinkedList<E> stack = null;

	public Stack() {
		stack = new LinkedList<E>();
	}

	public Stack(Collection<E> elements) {
		stack = new LinkedList<E>(elements);
	}

	public E push(E element) {
		if (stack.add(0, element)) {
			return element;
		}

		return null;
	}

	public E pop() {
		return stack.remove(0);
	}

	public E peek() {
		return stack.peek();
	}

	public boolean isEmpty() {
		return stack.size() == 0;
	}

	@Override
	public String toString() {
		return "Stack [stack=" + stack + "]";
	}
}

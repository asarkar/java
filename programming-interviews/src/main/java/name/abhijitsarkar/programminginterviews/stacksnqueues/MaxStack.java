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

import name.abhijitsarkar.algorithms.core.datastructure.LinkedList;

/* Q8.1: Design a stack that supports a max operation, which returns the maximum value stored in the stack, 
 * and throws an exception if the stack is empty. Assume elements are comparable. All operations must be O(1) time. 
 * You can use O(n) additional space, beyond what is required for the elements themselves.
 * 
 * Solution: Keep a max stack in addition to the main stack. Push into it the max of the element or the current value 
 * on top of the stack. Pop from when popping from the main stack.
 */
public class MaxStack {
	private LinkedList<Integer> stack = new LinkedList<>();
	private LinkedList<Integer> maxStack = new LinkedList<>();

	public int push(int element) {
		if (maxStack.isEmpty()) {
			maxStack.add(element);
		} else {
			maxStack.add(0, Math.max(element, maxStack.peek().intValue()));
		}

		stack.add(0, element);

		return element;
	}

	public int pop() {
		maxStack.remove(0);

		return stack.remove(0);
	}

	public int max() {
		return maxStack.peek();
	}

	public int size() {
		return stack.size();
	}
}

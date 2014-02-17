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

import name.abhijitsarkar.codinginterview.datastructure.Stack;

/**
 * @author Abhijit Sarkar
 */
/*
 * Q3.5: Implement a MyQueue class which implements a queue using two stacks.
 * 
 * For each enqueue and dequeue operation, we transfer all elements from one
 * stack to the other, thus essentially reversing the order of elements. 
 */
public class MyQueue<E> {
	private Stack<E> popStack;
	private Stack<E> pushStack;

	public MyQueue() {
		popStack = new Stack<E>();
		pushStack = new Stack<E>();
	}

	public E dequeue() {
		transferElements(pushStack, popStack);

		return popStack.pop();
	}

	public boolean enqueue(E element) {
		transferElements(popStack, pushStack);

		return pushStack.push(element) != null;
	}

	public boolean isEmpty() {
		return popStack.isEmpty() && pushStack.isEmpty();
	}

	private int transferElements(Stack<E> s1, Stack<E> s2) {
		int numElementsTransferred = 0;

		if (s1.isEmpty()) {
			return numElementsTransferred;
		}

		if (!s2.isEmpty()) {
			throw new IllegalStateException(
					"Can't transfer, destinaion stack is not empty.");
		}

		E element = null;

		for (; !s1.isEmpty(); numElementsTransferred++) {
			element = s1.pop();

			s2.push(element);
		}

		return numElementsTransferred;
	}
}

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
public class PracticeQuestions {
	/*
	 * Q3.6: Write a program to sort a stack in ascending order (with biggest items on
	 * top). You may use at most one additional stack to hold items, but you may
	 * not copy the elements into any other data structure (suchasan array).The
	 * stack supports the following operations: push, pop, peek, and isEmpty.
	 */
	public static <E extends Comparable<E>> Stack<E> sort(Stack<E> stack) {

		Stack<E> holdingStack = new Stack<E>();

		while (!stack.isEmpty()) {
			E min = stack.pop();

			while (!holdingStack.isEmpty()
					&& holdingStack.peek().compareTo(min) > 0) {
				stack.push(holdingStack.pop());
			}
			holdingStack.push(min);
		}

		return holdingStack;
	}
}

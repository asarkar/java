package name.abhijitsarkar.codinginterview.datastructure.stacksnqueues;

import name.abhijitsarkar.codinginterview.datastructure.Stack;

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

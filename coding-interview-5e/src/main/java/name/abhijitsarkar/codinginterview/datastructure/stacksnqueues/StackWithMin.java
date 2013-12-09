package name.abhijitsarkar.codinginterview.datastructure.stacksnqueues;

import name.abhijitsarkar.codinginterview.datastructure.impl.StackImpl;

/* Q3.2: How would you design a stack which, in addition to push and pop, 
 * also has a function min which returns the minimum element? 
 * Push, pop and min should all operate in O(1) time.
 */

public class StackWithMin<E extends Comparable<E>> extends StackImpl<E> {
	private E min = null;

	@Override
	public E push(E element) {
		if (stack.add(0, element)) {
			adjustMin(element);

			return element;
		}

		return null;
	}

	@Override
	public E pop() {
		E element = stack.remove(0);

		adjustMin(element);

		return element;
	}

	private final void adjustMin(E element) {
		if (min == null) {
			min = element;
		} else if (min.compareTo(element) > 0) {
			min = element;
		}
	}

	public E min() {
		return this.min;
	}
}

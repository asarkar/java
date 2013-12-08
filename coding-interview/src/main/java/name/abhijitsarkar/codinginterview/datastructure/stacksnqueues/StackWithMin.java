package name.abhijitsarkar.codinginterview.datastructure.stacksnqueues;

import name.abhijitsarkar.codinginterview.datastructure.impl.StackImpl;

/**
 * Q3.2: A stack that supports a min function to return the min element. Push,
 * pop and min should all operate in O(1).
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

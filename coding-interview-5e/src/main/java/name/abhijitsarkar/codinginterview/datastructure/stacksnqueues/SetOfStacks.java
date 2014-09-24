package name.abhijitsarkar.codinginterview.datastructure.stacksnqueues;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public class SetOfStacks<E> {
	private static final int DEFAULT_CAPACITY = 10;

	private final Deque<ArrayDeque<E>> stacks = new ArrayDeque<ArrayDeque<E>>(1);
	private final int capacity;

	public SetOfStacks() {
		this(DEFAULT_CAPACITY);
	}

	public SetOfStacks(int capacity) {
		this.capacity = capacity;
		stacks.addFirst(new ArrayDeque<E>());
	}

	public E push(E element) {
		if (isFull()) {
			stacks.addFirst(new ArrayDeque<E>());
		}

		stacks.getFirst().addFirst(element);

		return element;
	}

	private boolean isFull() {
		return stacks.getFirst().size() == capacity;
	}

	/**
	 * 
	 * @return Element popped
	 * @throws NoSuchElementException
	 *             if the stack to pop from is empty
	 */
	public E pop() {
		final E element = stacks.getFirst().remove();

		if (stacks.getFirst().isEmpty() && stacks.size() != 1) {
			stacks.remove();
		}

		return element;
	}

	public E peek() {
		return stacks.getFirst().getFirst();
	}

	public boolean isEmpty() {
		return stacks.isEmpty() || stacks.getFirst().isEmpty();
	}

	public int size() {
		return stacks.size();
	}

	public int numElements() {
		int numElements = 0;

		for (final ArrayDeque<E> aStack : stacks) {
			numElements += aStack.size();
		}

		return numElements;
	}
}

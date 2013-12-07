package name.abhijitsarkar.codinginterview.datastructure.impl;

import java.util.Collection;
import java.util.NoSuchElementException;

import name.abhijitsarkar.codinginterview.datastructure.LinkedList;

public class LinkedListImpl<E> implements LinkedList<E> {
	private int size = 0;

	private LinkedListNode<E> last = null;

	// Sentinels
	private LinkedListNode<E> head = null;
	private LinkedListNode<E> tail = null;

	public LinkedListImpl() {
		tail = new LinkedListNode<E>();
		head = new LinkedListNode<E>();
	}

	public LinkedListImpl(Collection<E> elements) {
		this();

		addAll(elements);
	}

	/*
	 * Time complexity is O(1)
	 */
	public boolean add(E e) {
		return add(size, e);
	}

	public boolean add(int index, E e) {
		validateIndex(index);

		LinkedListNode<E> predecessor = getPredecessor(index);

		LinkedListNode<E> newNode = new LinkedListNode<E>(e,
				predecessor.getSuccessor());

		predecessor.setSuccessor(newNode);

		if (index == size) {
			adjustLast(newNode);
		}

		size++;

		return true;
	}

	private void adjustLast(LinkedListNode<E> current) {
		last = current;
		last.setSuccessor(tail);
	}

	public boolean addAll(Collection<E> elements) {
		LinkedListNode<E> predecessor = head;
		LinkedListNode<E> current = null;

		for (E anElement : elements) {
			current = new LinkedListNode<E>();
			current.setData(anElement);
			current.setSuccessor(null);

			predecessor.setSuccessor(current);

			predecessor = current;

			size++;
		}

		adjustLast(current);

		return true;
	}

	/*
	 * Time complexity is O(1)
	 */
	public E remove() {
		return remove(0);
	}

	public E remove(int index) {
		validateIndex(index);

		LinkedListNode<E> predecessor = getPredecessor(index);

		LinkedListNode<E> nodeToBeRemoved = predecessor.getSuccessor();

		predecessor.setSuccessor(nodeToBeRemoved.getSuccessor());

		if (index == size) {
			adjustLast(predecessor);
		}

		size--;

		return nodeToBeRemoved.getData();
	}

	private final void checkNotEmpty() {
		if (size == 0) {
			throw new NoSuchElementException(
					"Can't remove element from an empty list.");
		}
	}

	public E peek() {
		checkNotEmpty();

		return head.getSuccessor().getData();
	}

	public int size() {
		return this.size;
	}

	public void reverse() {
		if (size <= 1) {
			return;
		}

		LinkedListNode<E> predecessor = head;
		LinkedListNode<E> current = head.getSuccessor();
		LinkedListNode<E> successor = null;

		while (current != this.tail) {
			// Save the successor
			successor = current.getSuccessor();

			// Reverse the successor
			current.setSuccessor(predecessor);

			// Increment
			predecessor = current;
			// Increment
			current = successor;
		}

		// Head is now tail
		tail = head;
		tail.setSuccessor(null);

		// Tail is now head; at the end current is tail and predecessor is the
		// first element
		head = current;
		head.setSuccessor(predecessor);
	}

	public LinkedListNode<E> head() {
		return this.head;
	}

	public LinkedListNode<E> tail() {
		return this.tail;
	}

	@Override
	public E get(int index) {
		return nodeAt(index).getData();
	}

	@Override
	public void set(int index, E element) {
		nodeAt(index).setData(element);
	}

	private final LinkedListNode<E> nodeAt(int index) {
		validateIndex(index);

		LinkedListNode<E> node = head.getSuccessor();

		for (int idx = 0; idx < index; idx++, node = node.getSuccessor())
			;

		return node;
	}

	@Override
	public int indexOf(E element) {
		checkNotEmpty();

		LinkedListNode<E> node = head.getSuccessor();

		for (int idx = 0; idx < size; idx++, node = node.getSuccessor()) {
			if (element.equals(node.getData())) {
				return idx;
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(E element) {
		checkNotEmpty();

		int lastIdx = -1;

		LinkedListNode<E> node = head.getSuccessor();

		for (int idx = 0; idx < size; idx++, node = node.getSuccessor()) {
			if (element.equals(node.getData())) {
				lastIdx = idx;
			}
		}

		return lastIdx;
	}

	private final void validateIndex(int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(
					"Index must be within the range [0, " + size + "]");
		}
	}

	/*
	 * If index = 0 or index = size, time complexity is O(1). Else in the worst
	 * case, when index = (size - 1), it could be O(n).
	 */
	protected LinkedListNode<E> getPredecessor(int index) {
		validateIndex(index);

		if (index == 0) {
			return head;
		} else if (index == size) {
			return last;
		}

		LinkedListNode<E> predecessor = head;
		LinkedListNode<E> current = predecessor.getSuccessor();

		while (index-- > 0) {
			predecessor = current;
			current = predecessor.getSuccessor();
		}

		return predecessor;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();

		buffer.append("LinkedListImpl [");

		for (LinkedListNode<E> current = head.getSuccessor(); current != tail; current = current
				.getSuccessor()) {
			buffer.append(current.getData()).append(", ");
		}

		int len = buffer.length();

		buffer.delete(len - 2, len);

		buffer.append("]");

		return buffer.toString();
	}
}

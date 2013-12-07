package name.abhijitsarkar.codinginterview.datastructure.impl;

import java.util.Collection;

import name.abhijitsarkar.codinginterview.datastructure.LinkedList;

public class LinkedListImpl<E> implements LinkedList<E> {
	private int size = 0;

	private class Node<T> {
		private T data;
		private Node<T> successor;

		private Node() {
		}

		private Node(T data, Node<T> successor) {
			this.data = data;
			this.successor = successor;
		}

		private boolean isTail() {
			return (this == tail);
		}
	}

	private Node<E> last = null;

	// Sentinels
	private Node<E> head = null;
	private Node<E> tail = null;

	public LinkedListImpl() {
		tail = new Node<E>();
		head = new Node<E>();
	}

	public LinkedListImpl(Collection<E> elements) {
		this();

		addAll(elements);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * name.abhijitsarkar.codinginterview.datastructure.LinkedList#add(java.
	 * lang.Object)
	 * 
	 * Time complexity is O(1)
	 */
	public boolean add(E e) {
		return add(size, e);
	}

	public boolean add(int index, E e) {
		validateIndex(index);

		Node<E> predecessor = getPredecessor(index);

		Node<E> newNode = new Node<E>(e, predecessor.successor);

		predecessor.successor = newNode;

		if (index == size) {
			adjustLast(newNode);
		}

		size++;

		return true;
	}

	private void adjustLast(Node<E> current) {
		last = current;
		last.successor = tail;
	}

	public boolean addAll(Collection<E> elements) {
		Node<E> predecessor = head;
		Node<E> current = null;

		for (E anElement : elements) {
			current = new Node<E>();
			current.data = anElement;
			current.successor = null;

			predecessor.successor = current;

			predecessor = current;

			size++;
		}

		adjustLast(current);

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see name.abhijitsarkar.codinginterview.datastructure.LinkedList#remove()
	 * 
	 * Time complexity is O(1)
	 */
	public E remove() {
		return remove(0);
	}

	public E remove(int index) {
		validateIndex(index);

		Node<E> predecessor = getPredecessor(index);

		Node<E> nodeToBeRemoved = predecessor.successor;

		predecessor.successor = nodeToBeRemoved.successor;

		if (index == size) {
			adjustLast(predecessor);
		}

		size--;

		return nodeToBeRemoved.data;
	}

	public E peek() {
		return head.successor.data;
	}

	public int size() {
		return this.size;
	}

	public void reverse() {
		if (size <= 1) {
			return;
		}

		Node<E> predecessor = head;
		Node<E> current = head.successor;
		Node<E> successor = null;

		while (!current.isTail()) {
			// Save the successor
			successor = current.successor;

			// Reverse the successor
			current.successor = predecessor;

			// Increment
			predecessor = current;
			// Increment
			current = successor;
		}

		// Head is now tail
		tail = head;
		tail.successor = null;

		// Tail is now head; at the end current is tail and predecessor is the
		// first element
		head = current;
		head.successor = predecessor;
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
	protected Node<E> getPredecessor(int index) {
		validateIndex(index);

		if (index == 0) {
			return head;
		} else if (index == size) {
			return last;
		}

		Node<E> current = head;
		Node<E> predecessor = current;

		while (index-- > 0) {
			predecessor = current;
			current = current.successor;
		}

		return predecessor;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();

		buffer.append("LinkedListImpl [");

		for (Node<E> current = head.successor; current != tail; current = current.successor) {
			buffer.append(current.data).append(", ");
		}

		int len = buffer.length();

		buffer.delete(len - 2, len);

		buffer.append("]");

		return buffer.toString();
	}
}

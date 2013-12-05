package name.abhijitsarkar.datastructure.impl;

import java.util.Collection;

import name.abhijitsarkar.datastructure.LinkedList;

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

		private T getData() {
			return this.data;
		}

		private void setData(T data) {
			this.data = data;
		}

		private Node<T> getSuccessor() {
			return successor;
		}

		private void setSuccessor(Node<T> successor) {
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
	 * @see name.abhijitsarkar.datastructure.LinkedList#add(java.lang.Object)
	 * 
	 * Time complexity is O(1)
	 */
	public boolean add(E e) {
		return add(size, e);
	}

	public boolean add(int index, E e) {
		validateIndex(index);

		Node<E> predecessor = getPredecessor(index);

		Node<E> newNode = new Node<E>(e, predecessor.getSuccessor());

		predecessor.setSuccessor(newNode);

		if (index == size) {
			adjustLast(newNode);
		}

		size++;

		return true;
	}

	private void adjustLast(Node<E> current) {
		last = current;
		last.setSuccessor(tail);
	}

	public boolean addAll(Collection<E> elements) {
		Node<E> predecessor = head;
		Node<E> current = null;

		for (E anElement : elements) {
			current = new Node<E>();
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
	 * (non-Javadoc)
	 * 
	 * @see name.abhijitsarkar.datastructure.LinkedList#remove()
	 * 
	 * Time complexity is O(1)
	 */
	public E remove() {
		return remove(0);
	}

	public E remove(int index) {
		validateIndex(index);

		Node<E> predecessor = getPredecessor(index);

		Node<E> nodeToBeRemoved = predecessor.getSuccessor();

		predecessor.setSuccessor(nodeToBeRemoved.getSuccessor());

		if (index == size) {
			adjustLast(predecessor);
		}

		size--;

		return nodeToBeRemoved.getData();
	}
	
	public E peek() {
		return head.getSuccessor().getData();
	}

	public int size() {
		return this.size;
	}

	public void reverse() {
		if (size <= 1) {
			return;
		}

		Node<E> predecessor = head;
		Node<E> current = head.getSuccessor();
		Node<E> successor = null;

		while (!current.isTail()) {
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
			current = current.getSuccessor();
		}

		return predecessor;
	}
}

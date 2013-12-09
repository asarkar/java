package name.abhijitsarkar.codinginterview.datastructure;

import java.util.Collection;
import java.util.NoSuchElementException;

public class LinkedList<E> {
	private int size = 0;

	private LinkedListNode<E> last = null;

	// Sentinels
	private LinkedListNode<E> head = null;
	private LinkedListNode<E> tail = null;

	public LinkedList() {
		tail = new LinkedListNode<E>();
		head = new LinkedListNode<E>();
	}

	public LinkedList(Collection<E> elements) {
		this();

		addAll(elements);
	}

	/**
	 * Adds element e at the end. Time complexity is O(1).
	 * 
	 * @param e
	 *            Element to add.
	 * @return true if successful, false otherwise.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             If index is less than 0 or greater than size.
	 */
	public boolean add(E e) {
		return add(size, e);
	}

	/**
	 * Adds element e at the specified index.
	 * 
	 * @param index
	 *            Index to add element at.
	 * @param e
	 *            Element to add.
	 * @return true if successful, false otherwise.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             If index is less than 0 or greater than size.
	 */
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

	/**
	 * Adds all elements in the collection at the end.
	 * 
	 * @param c
	 *            Collection whose elements are to be added to this list.
	 * @return true if successful, false otherwise.
	 */
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

	/**
	 * Removes the first element of the list. Time complexity is O(1).
	 * 
	 * @return The element removed.
	 * 
	 * @throws NoSuchElementException
	 *             is the list is empty.
	 * @throws IndexOutOfBoundsException
	 *             If index is less than 0 or greater than size.
	 */
	public E remove() {
		checkNotEmpty();

		return remove(0);
	}

	/**
	 * Removes the element from the list as specified by the index.
	 * 
	 * @param index
	 *            Index to remove element from.
	 * @return The element removed.
	 * 
	 * @throws NoSuchElementException
	 *             is the list is empty.
	 * @throws IndexOutOfBoundsException
	 *             If index is less than 0 or greater than size.
	 */
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

	/**
	 * Returns but doesn't remove the first element of the list.
	 * 
	 * @return The first element.
	 * 
	 * @throws NoSuchElementException
	 *             is the list is empty.
	 */
	public E peek() {
		checkNotEmpty();

		return head.getSuccessor().getData();
	}

	/**
	 * 
	 * @return Number of elements in the list.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Reverse the list in place.
	 */
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

	/**
	 * Returns the head node of the list. The head is a sentinel and not a valid
	 * element of the list.
	 * 
	 * @return Head node of the list.
	 */
	public LinkedListNode<E> head() {
		return this.head;
	}

	/**
	 * Returns the tail node of the list. The tail is a sentinel and not a valid
	 * element of the list.
	 * 
	 * @return Tail node of the list.
	 */
	public LinkedListNode<E> tail() {
		return this.tail;
	}

	/**
	 * Returns but doesn't remove the element from the list as specified by the
	 * index.
	 * 
	 * @param index
	 *            Index to get element from.
	 * @return The element.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             If index is less than 0 or greater than size.
	 */
	public E get(int index) {
		return nodeAt(index).getData();
	}

	/**
	 * Replaces the element at index with the one provided.
	 * 
	 * @param index
	 *            Index to set the element to.
	 * @param element
	 *            Replacement element.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             If index is less than 0 or greater than size.
	 */
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

	/**
	 * Returns the first index of the element provided.
	 * 
	 * @param element
	 *            The one to find the index for.
	 * @return First index of the element.
	 * 
	 * @throws NoSuchElementException
	 *             is the list is empty.
	 */
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

	/**
	 * Returns the last index of the element provided.
	 * 
	 * @param element
	 *            The one to find the index for.
	 * @return last index of the element.
	 * 
	 * @throws NoSuchElementException
	 *             is the list is empty.
	 */
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

		buffer.append("LinkedList [");

		for (LinkedListNode<E> current = head.getSuccessor(); current != tail; current = current
				.getSuccessor()) {
			buffer.append(current.getData()).append(", ");
		}

		int len = buffer.length();

		buffer.delete(len - 2, len);

		buffer.append("]");

		return buffer.toString();
	}

	public static class LinkedListNode<T> {
		private T data;
		private LinkedListNode<T> successor;

		public LinkedListNode() {
		}

		public LinkedListNode(T data, LinkedListNode<T> successor) {
			this.data = data;
			this.successor = successor;
		}

		public T getData() {
			return this.data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public LinkedListNode<T> getSuccessor() {
			return successor;
		}

		public void setSuccessor(LinkedListNode<T> successor) {
			this.successor = successor;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((data == null) ? 0 : data.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			@SuppressWarnings("unchecked")
			LinkedListNode<T> other = (LinkedListNode<T>) obj;
			if (data == null) {
				if (other.data != null)
					return false;
			} else if (!data.equals(other.data))
				return false;
			return true;
		}
	}
}

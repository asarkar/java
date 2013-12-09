package name.abhijitsarkar.codinginterview.datastructure.impl;

import java.util.Collection;

import name.abhijitsarkar.codinginterview.datastructure.LinkedList;
import name.abhijitsarkar.codinginterview.datastructure.Queue;

public class QueueImpl<E> implements Queue<E> {
	private LinkedList<E> queue = null;

	public QueueImpl() {
		queue = new LinkedListImpl<E>();
	}

	public QueueImpl(Collection<E> elements) {
		queue = new LinkedListImpl<E>(elements);
	}

	@Override
	public E dequeue() {
		return queue.remove(0);
	}

	@Override
	public boolean enqueue(E element) {
		return queue.add(element);
	}

	@Override
	public boolean isEmpty() {
		return queue.size() == 0;
	}

	@Override
	public String toString() {
		return "QueueImpl [queue=" + queue + "]";
	}
}

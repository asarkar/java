package name.abhijitsarkar.codinginterview.datastructure;

import java.util.Collection;

public class Queue<E> {
	private LinkedList<E> queue = null;

	public Queue() {
		queue = new LinkedList<E>();
	}

	public Queue(Collection<E> elements) {
		queue = new LinkedList<E>(elements);
	}

	public E dequeue() {
		return queue.remove(0);
	}

	public boolean enqueue(E element) {
		return queue.add(element);
	}

	public boolean isEmpty() {
		return queue.size() == 0;
	}

	@Override
	public String toString() {
		return "Queue [queue=" + queue + "]";
	}
}

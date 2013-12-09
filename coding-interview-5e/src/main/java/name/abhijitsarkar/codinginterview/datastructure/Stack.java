package name.abhijitsarkar.codinginterview.datastructure;

import java.util.Collection;

public class Stack<E> {
	protected LinkedList<E> stack = null;

	public Stack() {
		stack = new LinkedList<E>();
	}

	public Stack(Collection<E> elements) {
		stack = new LinkedList<E>(elements);
	}

	public E push(E element) {
		if (stack.add(0, element)) {
			return element;
		}

		return null;
	}

	public E pop() {
		return stack.remove(0);
	}

	public E peek() {
		return stack.peek();
	}

	public boolean isEmpty() {
		return stack.size() == 0;
	}

	@Override
	public String toString() {
		return "Stack [stack=" + stack + "]";
	}
}

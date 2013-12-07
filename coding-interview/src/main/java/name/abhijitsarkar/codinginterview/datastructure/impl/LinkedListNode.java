package name.abhijitsarkar.codinginterview.datastructure.impl;

public class LinkedListNode<E> {
	private E data;
	private LinkedListNode<E> successor;

	public LinkedListNode() {
	}

	public LinkedListNode(E data, LinkedListNode<E> successor) {
		this.data = data;
		this.successor = successor;
	}

	public E getData() {
		return this.data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public LinkedListNode<E> getSuccessor() {
		return successor;
	}

	public void setSuccessor(LinkedListNode<E> successor) {
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
		LinkedListNode<E> other = (LinkedListNode<E>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
}

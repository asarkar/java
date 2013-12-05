package name.abhijitsarkar.datastructure.impl;

public class BinaryTreeNode<E> {
	private E data = null;
	private BinaryTreeNode<E> leftChild = null;
	private BinaryTreeNode<E> rightChild = null;

	public BinaryTreeNode(E data) {
		this(data, null, null);
	}

	public BinaryTreeNode(E data, BinaryTreeNode<E> leftChild,
			BinaryTreeNode<E> rightChild) {
		this.data = data;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public BinaryTreeNode<E> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(BinaryTreeNode<E> leftChild) {
		this.leftChild = leftChild;
	}

	public BinaryTreeNode<E> getRightChild() {
		return rightChild;
	}

	public void setRightChild(BinaryTreeNode<E> rightChild) {
		this.rightChild = rightChild;
	}

	public boolean isLeaf() {
		return (this.leftChild == null && this.rightChild == null);
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
		BinaryTreeNode<E> other = (BinaryTreeNode<E>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BinaryTreeNode [data=" + data + "]";
	}
}

/*******************************************************************************
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software, 
 * and is also available at http://www.gnu.org/licenses.
 *******************************************************************************/
package name.abhijitsarkar.algorithms.core.datastructure;

import static java.lang.Math.max;

import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */

/*
 * The depth of a node X in a tree T is defined as the length of the simple path (number of edges) from the root node of
 * T to X. The height of a node Y is the number of edges on the longest downward simple path from Y to a leaf. The
 * height of a tree is defined as the height of its root node. Note that a simple path is a path without repeat
 * vertices. The height of a tree is equal to the max depth of a tree.
 */
public class BinarySearchTree<E extends Comparable<E>> {

	private BinaryTreeNode<E> root = null;

	public BinarySearchTree() {
	}

	public BinarySearchTree(Collection<E> elements) {
		addAll(elements);
	}

	private void addAll(Collection<E> elements) {
		BinaryTreeNode<E> node = root;
		for (E anElement : elements) {
			node = add(node, anElement);
		}
	}

	public BinaryTreeNode<E> add(E value) {
		return add(root, value);
	}

	public BinaryTreeNode<E> add(BinaryTreeNode<E> node, E value) {
		if (node == null) {
			node = new BinaryTreeNode<E>(value);

			if (root == null) {
				root = node;
			}

			return node;
		}

		if (value.compareTo(node.data()) < 0) {
			node.setLeftChild(add(node.leftChild(), value));
		} else {
			node.setRightChild(add(node.rightChild(), value));
		}

		return node;
	}

	public int height() {
		return height(root);
	}

	private int height(BinaryTreeNode<E> node) {
		if (node == null || node.isLeaf()) {
			return 0;
		}

		final int leftHeight = height(node.leftChild());

		final int rightHeight = height(node.rightChild());

		return max(leftHeight, rightHeight) + 1;
	}

	public BinaryTreeNode<E> root() {
		return this.root;
	}

	public static class BinaryTreeNode<T> {
		private T data = null;
		private BinaryTreeNode<T> leftChild = null;
		private BinaryTreeNode<T> rightChild = null;
		private BinaryTreeNode<T> parent = null;

		public BinaryTreeNode(T data) {
			this(data, null, null);
		}

		public BinaryTreeNode(T data, BinaryTreeNode<T> leftChild, BinaryTreeNode<T> rightChild) {
			this.data = data;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}

		public T data() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public BinaryTreeNode<T> leftChild() {
			return leftChild;
		}

		public void setLeftChild(BinaryTreeNode<T> leftChild) {
			this.leftChild = leftChild;
		}

		public BinaryTreeNode<T> rightChild() {
			return rightChild;
		}

		public void setRightChild(BinaryTreeNode<T> rightChild) {
			this.rightChild = rightChild;
		}

		public BinaryTreeNode<T> parent() {
			return parent;
		}

		public void setParent(BinaryTreeNode<T> parent) {
			this.parent = parent;
		}

		public boolean isLeaf() {
			return this.leftChild == null && this.rightChild == null;
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
			BinaryTreeNode<T> other = (BinaryTreeNode<T>) obj;
			if (data == null) {
				if (other.data != null)
					return false;
			} else if (!data.equals(other.data))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "BinaryTreeNode {data=" + data + "}";
		}
	}
}

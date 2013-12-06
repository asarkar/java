package name.abhijitsarkar.codinginterview.datastructure.impl;

import java.util.Collection;

public class BinarySearchTree<E extends Comparable<E>> {

	private BinaryTreeNode<E> root = null;
	private int depth = 0;

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

			if (depth == 0) {
				root = node;
			}

			return node;
		}

		incrementDepthIfNeeded(node);

		if (value.compareTo(node.getData()) < 0) {
			node.setLeftChild(add(node.getLeftChild(), value));
		} else {
			node.setRightChild(add(node.getRightChild(), value));
		}

		return node;
	}

	private void incrementDepthIfNeeded(BinaryTreeNode<E> node) {
		if (node.isLeaf()) {
			depth++;
		}
	}

	public int getDepth() {
		return this.depth;
	}

	public BinaryTreeNode<E> getRoot() {
		return this.root;
	}
}

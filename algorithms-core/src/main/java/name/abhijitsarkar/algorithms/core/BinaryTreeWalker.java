package name.abhijitsarkar.algorithms.core;

import java.util.ArrayList;
import java.util.List;

import name.abhijitsarkar.algorithms.core.datastructure.BinarySearchTree.BinaryTreeNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryTreeWalker {
	public static final Logger LOGGER = LoggerFactory.getLogger(BinaryTreeWalker.class);

	/*
	 * Q9.5: Let T be the root of a binary tree in which nodes have an explicit parent field. Design an iterative
	 * algorithm that enumerates the nodes inorder and uses O(1) additional space. Your algorithm can't modify the tree.
	 */
	public static <E> List<E> iterativeInorder(BinaryTreeNode<E> root) {
		BinaryTreeNode<E> current = root;
		BinaryTreeNode<E> previous = null;

		/* This is purely to facilitate testing. It won't be used to solve the problem. */
		List<E> visited = new ArrayList<>();

		while (current != null) {
			/* Going down */
			if (previous == current.parent()) {
				LOGGER.debug("(p = c.parent) Previous {} is parent of current {}.", previous, current);
				LOGGER.debug("(p = c.parent) Setting previous {} to current {}.", previous, current);

				previous = current;

				if (current.leftChild() != null) {
					LOGGER.debug("(p = c.parent) Setting current {} to current -> left {}.", current,
							current.leftChild());

					current = current.leftChild();
				} else {
					LOGGER.debug("(p = c.parent) Added current {} to visited.", current);

					visited.add(current.data());

					if (current.rightChild() != null) {
						LOGGER.debug("(p = c.parent) Setting current {} to current -> right {}.", current,
								current.rightChild());

						current = current.rightChild();
					} else {
						LOGGER.debug("(p = c.parent) Setting current {} to current -> parent {}.", current,
								current.parent());

						current = current.parent();
					}
				}
				/* Coming up from left child */
			} else if (previous == current.leftChild()) {
				LOGGER.debug("(p = c.left) Previous {} is left child of current {}.", previous, current);
				LOGGER.debug("(p = c.left) Setting previous {} to current {}.", previous, current);

				previous = current;

				LOGGER.debug("(p = c.left) Added current {} to visited.", current);

				visited.add(current.data());

				if (current.rightChild() != null) {
					LOGGER.debug("(p = c.left) Setting current {} to current -> right {}.", current,
							current.rightChild());

					current = current.rightChild();
				} else {
					LOGGER.debug("(p = c.left) Setting current {} to current -> parent {}.", current, current.parent());

					current = current.parent();
				}
				/* Coming up from right child */
			} else if (previous == current.rightChild()) {
				LOGGER.debug("(p = c.right) Previous {} is right child of current {}.", previous, current);
				LOGGER.debug("(p = c.left) Setting previous {} to current {}.", previous, current);
				LOGGER.debug("(p = c.right) Setting current {} to current -> parent {}.", current, current.parent());

				previous = current;
				current = current.parent();
			}
		}

		return visited;
	}

	public static <E> List<E> recursiveInorder(BinaryTreeNode<E> root) {
		/* This is purely to facilitate testing. It won't be used to solve the problem. */
		List<E> visited = new ArrayList<>();

		if (root.leftChild() != null) {
			visited.addAll(recursiveInorder(root.leftChild()));
		}

		visited.add(root.data());

		if (root.rightChild() != null) {
			visited.addAll(recursiveInorder(root.rightChild()));
		}

		return visited;
	}
}

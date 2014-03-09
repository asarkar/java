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
package name.abhijitsarkar.programminginterviews.binarytrees;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh9 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh9.class);

	/*
	 * Q9.5: Let T be the root of a binary tree in which nodes have an explicti parent field. Design an iterative
	 * algorithm that enumerates the nodes inorder and uses O(1) additional space. Your algorithm can't modify the tree.
	 */
	public static List<Integer> iterativeInorder(Node<Integer> root) {
		Node<Integer> current = root;
		Node<Integer> previous = null;

		/* This is purely to facilitate testing. It won't be used to solve the problem. */
		List<Integer> visited = new ArrayList<>();

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

	public static List<Integer> recursiveInorder(Node<Integer> root) {
		/* This is purely to facilitate testing. It won't be used to solve the problem. */
		List<Integer> visited = new ArrayList<>();

		if (root.leftChild() != null) {
			visited.addAll(recursiveInorder(root.leftChild()));
		}

		visited.add(root.data());

		if (root.rightChild() != null) {
			visited.addAll(recursiveInorder(root.rightChild()));
		}

		return visited;
	}

	public static Node<Integer> lowestCommonAncestor(Node<Integer> root, Node<Integer> n1, Node<Integer> n2) {
		if (root == null || root == n1 || root == n2) {
			return root;
		}

		Node<Integer> left = lowestCommonAncestor(root.leftChild(), n1, n2);
		Node<Integer> right = lowestCommonAncestor(root.rightChild(), n1, n2);

		if (left != null && right != null) {
			return root;
		}

		return left != null ? left : right;
	}

	public static class Node<E> {
		E data;
		Node<E> parent;
		Node<E> leftChild;
		Node<E> rightChild;

		public Node() {

		}

		public Node(E data) {
			this.data = data;
		}

		public E data() {
			return data;
		}

		public void setData(E data) {
			this.data = data;
		}

		public Node<E> parent() {
			return parent;
		}

		public void setParent(Node<E> parent) {
			this.parent = parent;
		}

		public Node<E> leftChild() {
			return leftChild;
		}

		public void setLeftChild(Node<E> leftChild) {
			this.leftChild = leftChild;
		}

		public Node<E> rightChild() {
			return rightChild;
		}

		public void setRightChild(Node<E> rightChild) {
			this.rightChild = rightChild;
		}

		@Override
		public String toString() {
			return "Node {data = " + data + "}";
		}
	}
}

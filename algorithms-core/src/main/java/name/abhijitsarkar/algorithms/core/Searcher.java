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
package name.abhijitsarkar.algorithms.core;

import java.util.HashSet;
import java.util.Set;

import name.abhijitsarkar.algorithms.core.datastructure.BinarySearchTree;
import name.abhijitsarkar.algorithms.core.datastructure.BinarySearchTree.BinaryTreeNode;
import name.abhijitsarkar.algorithms.core.datastructure.Queue;
import name.abhijitsarkar.algorithms.core.datastructure.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class Searcher {
	public static final Logger LOGGER = LoggerFactory.getLogger(Searcher.class);

	public static <E extends Comparable<E>> BinaryTreeNode<E> binarySearch(BinarySearchTree<E> binTree,
			BinaryTreeNode<E> startNode, E value) {
		// startNode can be null if the value doesn't exist in the tree
		if (startNode == null || value.equals(startNode.data())) {
			return startNode;
		}

		if (value.compareTo(startNode.data()) < 0) {
			return binarySearch(binTree, startNode.leftChild(), value);
		}

		return binarySearch(binTree, startNode.rightChild(), value);
	}

	public static <E extends Comparable<E>> BinaryTreeNode<E> binarySearch(BinarySearchTree<E> binTree, E value) {
		return binarySearch(binTree, binTree.root(), value);
	}

	public static <E extends Comparable<E>> BinaryTreeNode<E> breadthFirstSearch(BinarySearchTree<E> binTree,
			BinaryTreeNode<E> startNode, E value) {
		LOGGER.debug("Searching for: {}.", value);

		Set<BinaryTreeNode<E>> visited = new HashSet<BinaryTreeNode<E>>();
		Queue<BinaryTreeNode<E>> queue = new Queue<BinaryTreeNode<E>>();

		LOGGER.debug("Visited: {}.", startNode);

		visited.add(startNode);
		queue.enqueue(startNode);

		BinaryTreeNode<E> node = null;
		BinaryTreeNode<E> leftChild = null;
		BinaryTreeNode<E> rightChild = null;

		while (!queue.isEmpty()) {
			node = queue.dequeue();

			if (node.data().equals(value)) {
				return node;
			}

			leftChild = node.leftChild();
			rightChild = node.rightChild();

			if (leftChild != null && !visited.contains(leftChild)) {
				LOGGER.debug("Visited: {}.", leftChild);

				visited.add(leftChild);
				queue.enqueue(leftChild);
			}
			if (rightChild != null && !visited.contains(rightChild)) {
				LOGGER.debug("Visited: {}.", rightChild);

				visited.add(rightChild);
				queue.enqueue(rightChild);
			}
		}

		return null;
	}

	public static <E extends Comparable<E>> BinaryTreeNode<E> breadthFirstSearch(BinarySearchTree<E> binTree, E value) {
		return breadthFirstSearch(binTree, binTree.root(), value);
	}

	public static <E extends Comparable<E>> BinaryTreeNode<E> depthFirstSearch(BinarySearchTree<E> binTree,
			BinaryTreeNode<E> startNode, E value) {
		LOGGER.debug("Searching for: {}.", value);

		Stack<BinaryTreeNode<E>> stack = new Stack<BinaryTreeNode<E>>();
		Set<BinaryTreeNode<E>> visited = new HashSet<BinaryTreeNode<E>>();

		stack.push(startNode);

		BinaryTreeNode<E> leftChild = null;
		BinaryTreeNode<E> rightChild = null;
		BinaryTreeNode<E> currentNode = null;

		while (!stack.isEmpty()) {
			currentNode = stack.pop();
			visited.add(currentNode);

			LOGGER.debug("Visited: {}.", visited);

			if (currentNode.data().equals(value)) {
				return currentNode;
			}

			rightChild = currentNode.rightChild();

			if (rightChild != null && !visited.contains(rightChild)) {
				stack.push(rightChild);
			}

			leftChild = currentNode.leftChild();

			if (leftChild != null && !visited.contains(leftChild)) {
				stack.push(leftChild);
			}
		}

		return null;
	}

	public static <E extends Comparable<E>> BinaryTreeNode<E> depthFirstSearch(BinarySearchTree<E> binTree, E value) {
		return depthFirstSearch(binTree, binTree.root(), value);
	}
}

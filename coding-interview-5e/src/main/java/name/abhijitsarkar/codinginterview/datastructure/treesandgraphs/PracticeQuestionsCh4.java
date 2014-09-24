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
package name.abhijitsarkar.codinginterview.datastructure.treesandgraphs;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import name.abhijitsarkar.algorithms.core.datastructure.BinarySearchTree.BinaryTreeNode;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh4 {
	/*
	 * Q4.1: Implement a function to check if a binary tree is balanced. For the purposes of this question, a balanced
	 * tree is defined to be a tree such that the heights of the two subtrees of any node never differ by more than one.
	 */
	
	/* We could simply get the heights of the left and right subtrees of the root using the height() method in the 
	 * BinarySearchTree class. That, however, would not be efficient because for each node, it'd recurse through the
	 * entire subtree. */
	public static <E> boolean isBalanced(final BinaryTreeNode<E> root) {
		return getHeight(root) != -1;
	}

	private static <E> int getHeight(final BinaryTreeNode<E> root) {
		if (root == null || root.isLeaf()) {
			return 0;
		}

		final int leftHeight = getHeight(root.leftChild());

		if (leftHeight == -1) {
			return -1;
		}

		final int rightHeight = getHeight(root.rightChild());

		if (rightHeight == -1) {
			return -1;
		}

		if (abs(leftHeight - rightHeight) > 1) {
			return -1;
		} else {
			return max(leftHeight, rightHeight) + 1;
		}
	}
}

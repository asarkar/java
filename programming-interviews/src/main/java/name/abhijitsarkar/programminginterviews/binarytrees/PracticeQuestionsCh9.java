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

import name.abhijitsarkar.algorithms.core.datastructure.BinarySearchTree.BinaryTreeNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh9 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh9.class);

	/*
	 * Q9.11: Design an efficient algorithm for computing the LCA of nodes a and b in a binary tree in which nodes do
	 * not have a parent pointer.
	 */
	public static BinaryTreeNode<Integer> lowestCommonAncestor(BinaryTreeNode<Integer> root,
			BinaryTreeNode<Integer> n1, BinaryTreeNode<Integer> n2) {
		if (root == null || root == n1 || root == n2) {
			return root;
		}

		BinaryTreeNode<Integer> left = lowestCommonAncestor(root.leftChild(), n1, n2);
		BinaryTreeNode<Integer> right = lowestCommonAncestor(root.rightChild(), n1, n2);

		if (left != null && right != null) {
			return root;
		}

		return left != null ? left : right;
	}
}

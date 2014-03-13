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

import static name.abhijitsarkar.programminginterviews.binarytrees.PracticeQuestionsCh9.lowestCommonAncestor;
import name.abhijitsarkar.algorithms.core.datastructure.BinarySearchTree.BinaryTreeNode;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsCh9Test {
	private BinaryTreeNode<Integer> root;
	private BinaryTreeNode<Integer> one;
	private BinaryTreeNode<Integer> two;
	private BinaryTreeNode<Integer> three;
	private BinaryTreeNode<Integer> four;
	private BinaryTreeNode<Integer> five;
	private BinaryTreeNode<Integer> seven;
	private BinaryTreeNode<Integer> eight;
	private BinaryTreeNode<Integer> nine;

	public PracticeQuestionsCh9Test() {
		buildBST();
	}

	private void buildBST() {
		/* The tree formed here may be seen at src/test/resources/bst.png */
		root = new BinaryTreeNode<>(6);

		two = new BinaryTreeNode<>(2);
		root.setLeftChild(two);
		two.setParent(root);

		one = new BinaryTreeNode<>(1);
		two.setLeftChild(one);
		one.setParent(two);

		four = new BinaryTreeNode<>(4);
		two.setRightChild(four);
		four.setParent(two);

		three = new BinaryTreeNode<>(3);
		four.setLeftChild(three);
		three.setParent(four);

		five = new BinaryTreeNode<>(5);
		four.setRightChild(five);
		five.setParent(four);

		seven = new BinaryTreeNode<>(7);
		root.setRightChild(seven);
		seven.setParent(root);

		nine = new BinaryTreeNode<>(9);
		seven.setRightChild(nine);
		nine.setParent(seven);

		eight = new BinaryTreeNode<>(8);
		nine.setLeftChild(eight);
		eight.setParent(nine);
	}

	@Test
	public void testLowestCommonAncestor() {
		BinaryTreeNode<Integer> lca = lowestCommonAncestor(root, three, five);

		Assert.assertEquals(4, lca.data().intValue());

		lca = lowestCommonAncestor(root, one, three);

		Assert.assertEquals(2, lca.data().intValue());

		lca = lowestCommonAncestor(root, three, eight);

		Assert.assertEquals(6, lca.data().intValue());
	}
}

package name.abhijitsarkar.algorithms.core;
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


import name.abhijitsarkar.algorithms.core.datastructure.BinarySearchTree;
import name.abhijitsarkar.algorithms.core.datastructure.BinarySearchTree.BinaryTreeNode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class SearcherTest {
	private BinarySearchTree<Integer> binTree;

	@Before
	public void setUp() {

		binTree = new BinarySearchTree<Integer>();

		binTree.add(3);
		binTree.add(2);
		binTree.add(4);
		binTree.add(1);
		binTree.add(5);

		Assert.assertEquals(Integer.valueOf(3), binTree.getRoot().getData());

		Assert.assertEquals(3, binTree.getDepth());
	}

	@Test
	public void testBinarySearch() {
		for (int i = 1; i < 6; i++) {
			Assert.assertNotNull(Searcher.binarySearch(binTree, i));
		}

		Assert.assertNull(Searcher.binarySearch(binTree, 10));

		BinaryTreeNode<Integer> four = Searcher.binarySearch(binTree, 4);

		Assert.assertNotNull(four);
		Assert.assertFalse(four.isLeaf());
		Assert.assertNull(four.getLeftChild());
		Assert.assertNotNull(four.getRightChild());
		Assert.assertEquals(Integer.valueOf(5), four.getRightChild().getData());

		BinaryTreeNode<Integer> five = Searcher.binarySearch(binTree, 5);
		Assert.assertTrue(five.isLeaf());
	}

	@Test
	public void testBreadthFirstSearch() {
		for (int i = 1; i < 6; i++) {
			Assert.assertNotNull(Searcher.breadthFirstSearch(binTree, i));
		}

		Assert.assertNull(Searcher.breadthFirstSearch(binTree, 10));

		BinaryTreeNode<Integer> four = Searcher.breadthFirstSearch(binTree, 4);

		Assert.assertNotNull(four);
		Assert.assertFalse(four.isLeaf());
		Assert.assertNull(four.getLeftChild());
		Assert.assertNotNull(four.getRightChild());
		Assert.assertEquals(Integer.valueOf(5), four.getRightChild().getData());

		BinaryTreeNode<Integer> five = Searcher.breadthFirstSearch(binTree, 5);
		Assert.assertTrue(five.isLeaf());
	}

	@Test
	public void testDepthFirstSearch() {
		for (int i = 1; i < 6; i++) {
			Assert.assertNotNull(Searcher.depthFirstSearch(binTree, i));
		}

		Assert.assertNull(Searcher.depthFirstSearch(binTree, 10));

		BinaryTreeNode<Integer> four = Searcher.depthFirstSearch(binTree, 4);

		Assert.assertNotNull(four);
		Assert.assertFalse(four.isLeaf());
		Assert.assertNull(four.getLeftChild());
		Assert.assertNotNull(four.getRightChild());
		Assert.assertEquals(Integer.valueOf(5), four.getRightChild().getData());

		BinaryTreeNode<Integer> five = Searcher.depthFirstSearch(binTree, 5);
		Assert.assertTrue(five.isLeaf());
	}
}

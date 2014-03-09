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

import static name.abhijitsarkar.programminginterviews.binarytrees.PracticeQuestionsCh9.iterativeInorder;
import static name.abhijitsarkar.programminginterviews.binarytrees.PracticeQuestionsCh9.recursiveInorder;
import static name.abhijitsarkar.programminginterviews.binarytrees.PracticeQuestionsCh9.lowestCommonAncestor;

import java.util.Arrays;
import java.util.List;

import name.abhijitsarkar.programminginterviews.binarytrees.PracticeQuestionsCh9.Node;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsCh9Test {
	private Node<Integer> root;
	private Node<Integer> one;
	private Node<Integer> two;
	private Node<Integer> three;
	private Node<Integer> four;
	private Node<Integer> five;
	private Node<Integer> six;
	private Node<Integer> seven;
	private Node<Integer> eight;
	private Node<Integer> nine;

	public PracticeQuestionsCh9Test() {
		buildBST();
	}

	private void buildBST() {
		/* The tree formed here may be seen at src/test/resources/bst.png */
		root = new Node<>(6);

		two = new Node<>(2);
		root.setLeftChild(two);
		two.setParent(root);

		one = new Node<>(1);
		two.setLeftChild(one);
		one.setParent(two);

		four = new Node<>(4);
		two.setRightChild(four);
		four.setParent(two);

		three = new Node<>(3);
		four.setLeftChild(three);
		three.setParent(four);

		five = new Node<>(5);
		four.setRightChild(five);
		five.setParent(four);

		seven = new Node<>(7);
		root.setRightChild(seven);
		seven.setParent(root);

		nine = new Node<>(9);
		seven.setRightChild(nine);
		nine.setParent(seven);

		eight = new Node<>(8);
		nine.setLeftChild(eight);
		eight.setParent(nine);
	}

	@Test
	public void testIterativeInorder() {

		assertInorder(iterativeInorder(root));
	}

	@Test
	public void testRecursiveInorder() {

		assertInorder(recursiveInorder(root));
	}

	private void assertInorder(List<Integer> visited) {
		List<Integer> expected = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		Assert.assertEquals(expected, visited);
	}

	@Test
	public void testLowestCommonAncestor() {
		Node<Integer> lca = lowestCommonAncestor(root, three, five);

		Assert.assertEquals(4, lca.data().intValue());
		
		lca = lowestCommonAncestor(root, one, three);

		Assert.assertEquals(2, lca.data().intValue());
		
		lca = lowestCommonAncestor(root, three, eight);

		Assert.assertEquals(6, lca.data().intValue());
	}
}

package name.abhijitsarkar.algorithms.core;

import static name.abhijitsarkar.algorithms.core.BinaryTreeWalker.iterativeInorder;
import static name.abhijitsarkar.algorithms.core.BinaryTreeWalker.recursiveInorder;

import java.util.Arrays;
import java.util.List;

import name.abhijitsarkar.algorithms.core.datastructure.BinarySearchTree.BinaryTreeNode;

import org.junit.Assert;
import org.junit.Test;

public class BinaryTreeWalkerTest {
	private BinaryTreeNode<Integer> root;
	private BinaryTreeNode<Integer> one;
	private BinaryTreeNode<Integer> two;
	private BinaryTreeNode<Integer> three;
	private BinaryTreeNode<Integer> four;
	private BinaryTreeNode<Integer> five;
	private BinaryTreeNode<Integer> seven;
	private BinaryTreeNode<Integer> eight;
	private BinaryTreeNode<Integer> nine;

	public BinaryTreeWalkerTest() {
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

}

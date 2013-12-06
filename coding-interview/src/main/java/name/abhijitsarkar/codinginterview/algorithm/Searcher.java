package name.abhijitsarkar.codinginterview.algorithm;

import java.util.HashSet;
import java.util.Set;

import name.abhijitsarkar.codinginterview.datastructure.Queue;
import name.abhijitsarkar.codinginterview.datastructure.Stack;
import name.abhijitsarkar.codinginterview.datastructure.impl.BinarySearchTree;
import name.abhijitsarkar.codinginterview.datastructure.impl.BinaryTreeNode;
import name.abhijitsarkar.codinginterview.datastructure.impl.QueueImpl;
import name.abhijitsarkar.codinginterview.datastructure.impl.StackImpl;

public class Searcher {

	public static <E extends Comparable<E>> BinaryTreeNode<E> binarySearch(
			BinarySearchTree<E> binTree, BinaryTreeNode<E> startNode, E value) {
		// startNode can be null if the value doesn't exist in the tree
		if (startNode == null || value.equals(startNode.getData())) {
			return startNode;
		}

		if (value.compareTo(startNode.getData()) < 0) {
			return binarySearch(binTree, startNode.getLeftChild(), value);
		}

		return binarySearch(binTree, startNode.getRightChild(), value);
	}

	public static <E extends Comparable<E>> BinaryTreeNode<E> binarySearch(
			BinarySearchTree<E> binTree, E value) {
		return binarySearch(binTree, binTree.getRoot(), value);
	}

	public static <E extends Comparable<E>> BinaryTreeNode<E> breadthFirstSearch(
			BinarySearchTree<E> binTree, BinaryTreeNode<E> startNode, E value) {
		System.out.println("Searching for: " + value);

		Set<BinaryTreeNode<E>> visited = new HashSet<BinaryTreeNode<E>>();
		Queue<BinaryTreeNode<E>> queue = new QueueImpl<BinaryTreeNode<E>>();

		System.out.println("Visited: " + startNode);
		visited.add(startNode);
		queue.enqueue(startNode);

		BinaryTreeNode<E> node = null;
		BinaryTreeNode<E> leftChild = null;
		BinaryTreeNode<E> rightChild = null;

		while (!queue.isEmpty()) {
			node = queue.dequeue();

			if (node.getData().equals(value)) {
				return node;
			}

			leftChild = node.getLeftChild();
			rightChild = node.getRightChild();

			if (leftChild != null && !visited.contains(leftChild)) {
				System.out.println("Visited: " + leftChild);
				visited.add(leftChild);
				queue.enqueue(leftChild);
			}
			if (rightChild != null && !visited.contains(rightChild)) {
				System.out.println("Visited: " + rightChild);
				visited.add(rightChild);
				queue.enqueue(rightChild);
			}
		}

		return null;
	}

	public static <E extends Comparable<E>> BinaryTreeNode<E> breadthFirstSearch(
			BinarySearchTree<E> binTree, E value) {
		return breadthFirstSearch(binTree, binTree.getRoot(), value);
	}

	public static <E extends Comparable<E>> BinaryTreeNode<E> depthFirstSearch(
			BinarySearchTree<E> binTree, BinaryTreeNode<E> startNode, E value) {
		System.out.println("Searching for: " + value);

		Stack<BinaryTreeNode<E>> stack = new StackImpl<BinaryTreeNode<E>>();
		Set<BinaryTreeNode<E>> visited = new HashSet<BinaryTreeNode<E>>();

		stack.push(startNode);
		visited.add(startNode);

		BinaryTreeNode<E> leftChild = null;
		BinaryTreeNode<E> rightChild = null;
		BinaryTreeNode<E> currentNode = null;

		while (!stack.isEmpty()) {
			currentNode = stack.pop();
			visited.add(currentNode);
			System.out.println("Visited: " + visited);

			if (currentNode.getData().equals(value)) {
				return currentNode;
			}

			rightChild = currentNode.getRightChild();

			if (rightChild != null && !visited.contains(rightChild)) {
				stack.push(rightChild);
			}

			leftChild = currentNode.getLeftChild();

			if (leftChild != null && !visited.contains(leftChild)) {
				stack.push(leftChild);
			}
		}

		return null;
	}

	public static <E extends Comparable<E>> BinaryTreeNode<E> recursiveDFS(
			BinarySearchTree<E> binTree, BinaryTreeNode<E> startNode, E value,
			Set<BinaryTreeNode<E>> visited) {
		BinaryTreeNode<E> leftChild = null;
		BinaryTreeNode<E> rightChild = null;

		visited.add(startNode);
		System.out.println("Visited: " + visited);

		if (startNode.getData().equals(value)) {
			return startNode;
		}

		rightChild = startNode.getRightChild();

		if (rightChild != null && !visited.contains(rightChild)) {
			recursiveDFS(binTree, rightChild, value, visited);
		}

		leftChild = startNode.getLeftChild();

		if (leftChild != null && !visited.contains(leftChild)) {
			recursiveDFS(binTree, leftChild, value, visited);
		}

		return null;
	}

	public static <E extends Comparable<E>> BinaryTreeNode<E> depthFirstSearch(
			BinarySearchTree<E> binTree, E value) {
		return depthFirstSearch(binTree, binTree.getRoot(), value);
	}
}

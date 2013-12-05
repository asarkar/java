package name.abhijitsarkar.algorithm;

import java.util.HashSet;
import java.util.Set;

import name.abhijitsarkar.datastructure.Queue;
import name.abhijitsarkar.datastructure.impl.BinarySearchTree;
import name.abhijitsarkar.datastructure.impl.BinaryTreeNode;
import name.abhijitsarkar.datastructure.impl.QueueImpl;

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

		System.out.println("Visited: " + startNode.getData());
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
				System.out.println("Visited: " + leftChild.getData());
				visited.add(leftChild);
				queue.enqueue(leftChild);
			}
			if (rightChild != null && !visited.contains(rightChild)) {
				System.out.println("Visited: " + rightChild.getData());
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
}

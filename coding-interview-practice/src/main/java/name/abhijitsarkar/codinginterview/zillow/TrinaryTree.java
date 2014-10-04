package name.abhijitsarkar.codinginterview.zillow;

/**
 * Implement insert and delete in a tri-nary tree. Much like a binary-tree but with 3 child nodes for each parent
 * instead of two -- with the left node being values < parent, the right node values > parent, and the middle node
 * values == parent.
 */

public class TrinaryTree {

	private Node root;

	public static void main(String[] args) {
		TrinaryTree tree = new TrinaryTree();

		tree.insert(5);
		tree.insert(4);
		tree.insert(9);
		tree.insert(5);
		tree.insert(7);
		tree.insert(2);
		tree.insert(2);
		tree.insert(5);

		tree.stdout(tree.root);

		tree.delete(2);

		tree.stdout(tree.root);

		tree.delete(7);

		tree.stdout(tree.root);

	}

	/**
	 * Inserts a Node in the proper location in the tree
	 */
	public void insert(int value) {
		if (this.root == null) {
			this.root = new Node(value);
			return;
		}

		this.position(this.root, value);
	}

	/**
	 * Deletes a node, moving its children to their proper places
	 */
	public void delete(int value) {
		this.reposition(this.root, value);
	}

	/**
	 * Used for adding a Node
	 */
	protected Node position(Node node, int value) {
		if (node.value > value) {
			if (node.left == null) {
				node.left = new Node(value);
			} else {
				this.position(node.left, value);
			}
		} else if (node.value == value) {
			if (node.middle == null) {
				node.middle = new Node(value);
			} else {
				this.position(node.middle, value);
			}
		} else { // node.value < value
			if (node.right == null) {
				node.right = new Node(value);
			} else {
				this.position(node.right, value);
			}
		}

		return node;
	}

	/**
	 * Used for removing a Node and repositioning it's children
	 */
	protected Node reposition(Node node, int value) {
		if (value < node.value) {
			node.left = reposition(node.left, value);
		} else if (value > node.value) {
			node.right = reposition(node.right, value);
		} else {
			if (node.middle != null) {
				node.middle = reposition(node.middle, value);
			} else if (node.right != null) {
				int min = min(node.right).value;
				
				node.value = min;
				node.right = reposition(node.right, min);
			} else {
				node = node.left;
			}
		}

		return node;
	}

	/**
	 * Finds the minimum child of a Node
	 */
	protected Node min(Node node) {
		if (node != null) {
			while (node.left != null) {
				return min(node.left);
			}
		}

		return node;
	}

	/**
	 * Recurs through the tree, printing the value to the console
	 */
	public void stdout(Node node) {
		if (node == null) {
			return;
		}

		System.out.println(node.value);

		this.stdout(node.left);
		this.stdout(node.middle);
		this.stdout(node.right);
	}

	private static class Node {
		private Node left;
		private Node right;
		private Node middle;

		private int value;

		public Node(int value) {
			this.value = value;
		}
	}
}

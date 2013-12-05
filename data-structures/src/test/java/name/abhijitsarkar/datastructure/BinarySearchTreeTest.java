package name.abhijitsarkar.datastructure;

import name.abhijitsarkar.datastructure.impl.BinarySearchTree;

import org.junit.Assert;
import org.junit.Before;

public class BinarySearchTreeTest {
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
}

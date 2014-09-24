package name.abhijitsarkar.codinginterview.datastructure.treesandgraphs;

import static name.abhijitsarkar.codinginterview.datastructure.treesandgraphs.PracticeQuestionsCh4.isBalanced;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import name.abhijitsarkar.algorithms.core.datastructure.BinarySearchTree;

import org.junit.Before;
import org.junit.Test;

public class PracticeQuesionsCh4Test {
	/* http://en.wikipedia.org/wiki/Self-balancing_binary_search_tree#mediaviewer/File:Unbalanced_binary_tree.svg */
	private BinarySearchTree<Integer> binTree;

	@Before
	public void setUp() {
		binTree = new BinarySearchTree<Integer>();

		binTree.add(50);
		binTree.add(17);
		binTree.add(76);
		binTree.add(9);
		binTree.add(23);

		assertEquals(Integer.valueOf(50), binTree.root().data());

		assertEquals(2, binTree.height());
	}

	@Test
	public void testIsBalancedWhenIs() {
		assertTrue(isBalanced(binTree.root()));
	}
	
	@Test
	public void testIsBalancedWhenIsNot() {
		binTree.add(14);
		binTree.add(19);
		
		assertEquals(3, binTree.height());
		
		assertFalse(isBalanced(binTree.root()));
	}
}

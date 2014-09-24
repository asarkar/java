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
package name.abhijitsarkar.algorithms.core.datastructure;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
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
	}

	@Test
	public void testTree() {
		assertEquals(Integer.valueOf(3), binTree.root().data());

		assertEquals(2, binTree.height());
	}
}

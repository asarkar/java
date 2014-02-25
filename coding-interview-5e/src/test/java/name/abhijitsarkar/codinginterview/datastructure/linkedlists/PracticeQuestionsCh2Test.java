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
package name.abhijitsarkar.codinginterview.datastructure.linkedlists;

import name.abhijitsarkar.codinginterview.datastructure.LinkedList;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh2Test {
	@Test
	public void testRemoveDupes() {
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		linkedList.add(1);
		linkedList.add(2);
		linkedList.add(1);

		PracticeQuestionsCh2.removeDupes(linkedList);

		Assert.assertEquals(2, linkedList.size());
		Assert.assertEquals(Integer.valueOf(1), linkedList.remove());
		Assert.assertEquals(Integer.valueOf(2), linkedList.remove());
		Assert.assertEquals(0, linkedList.size());
	}

	@Test
	public void testPartition() {
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		linkedList.add(2);
		linkedList.add(4);
		linkedList.add(5);
		linkedList.add(1);

		PracticeQuestionsCh2.partition(linkedList, 4);

		Assert.assertEquals(4, linkedList.size());
		Assert.assertEquals(Integer.valueOf(2), linkedList.get(0));
		Assert.assertEquals(Integer.valueOf(1), linkedList.get(1));
		Assert.assertEquals(Integer.valueOf(4), linkedList.get(2));
		Assert.assertEquals(Integer.valueOf(5), linkedList.get(3));
	}

	@Test
	public void testIsPalindrome() {
		LinkedList<Character> linkedList = new LinkedList<Character>();
		linkedList.add('c');
		linkedList.add('i');
		linkedList.add('v');
		linkedList.add('i');
		linkedList.add('c');

		Assert.assertTrue(PracticeQuestionsCh2.isPalindrome(linkedList));

		linkedList = new LinkedList<Character>();
		linkedList.add('a');
		linkedList.add('n');
		linkedList.add('n');
		linkedList.add('a');

		Assert.assertTrue(PracticeQuestionsCh2.isPalindrome(linkedList));

		linkedList = new LinkedList<Character>();
		linkedList.add('a');
		linkedList.add('n');
		linkedList.add('m');
		linkedList.add('a');

		Assert.assertFalse(PracticeQuestionsCh2.isPalindrome(linkedList));
	}
}

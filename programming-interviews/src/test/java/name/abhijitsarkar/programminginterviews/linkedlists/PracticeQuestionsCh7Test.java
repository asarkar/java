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
package name.abhijitsarkar.programminginterviews.linkedlists;

import static name.abhijitsarkar.programminginterviews.linkedlists.PracticeQuestionsCh7.evenOddMerge;
import static name.abhijitsarkar.programminginterviews.linkedlists.PracticeQuestionsCh7.intersection;
import static name.abhijitsarkar.programminginterviews.linkedlists.PracticeQuestionsCh7.mergeLists;
import static name.abhijitsarkar.programminginterviews.linkedlists.PracticeQuestionsCh7.startOfLoop;
import static name.abhijitsarkar.programminginterviews.linkedlists.PracticeQuestionsCh7.reverse;

import java.util.Arrays;

import name.abhijitsarkar.algorithms.core.datastructure.LinkedList;
import name.abhijitsarkar.algorithms.core.datastructure.LinkedList.LinkedListNode;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsCh7Test {

	@Test
	public void testMergeLists() {
		LinkedList<Integer> l = newLinkedList(2, 5, 7);

		LinkedList<Integer> f = newLinkedList(3, 11);

		int[] merged = new int[] { 2, 3, 5, 7, 11 };
		LinkedListNode<Integer> runningNode = mergeLists(l, f);

		assertArrayEqualsList(merged, runningNode);

		/* Test with arrays reversed */

		l = newLinkedList(3, 11);

		f = newLinkedList(2, 5, 7);

		runningNode = mergeLists(l, f);

		assertArrayEqualsList(merged, runningNode);
	}

	private LinkedList<Integer> newLinkedList(Integer... elements) {
		return new LinkedList<Integer>(Arrays.asList(elements));
	}

	private void assertArrayEqualsList(int[] merged, LinkedListNode<Integer> runningNode) {
		for (int i = 0; i < merged.length; i++, runningNode = runningNode.successor()) {
			Assert.assertEquals(merged[i], runningNode.successor().data().intValue());
		}
	}

	@Test
	public void testStartOfLoop() {
		LinkedList<Integer> l = newLinkedList(2, 5, 7);
		LinkedListNode<Integer> startOfLoop = startOfLoop(l);
		Assert.assertNull(startOfLoop);

		LinkedListNode<Integer> runningNode = l.head();
		for (; runningNode.successor() != null; runningNode = runningNode.successor()) {
			if (runningNode.successor().data() == 7) {
				runningNode.successor().setSuccessor(runningNode);

				break;
			}
		}

		startOfLoop = startOfLoop(l);

		Assert.assertNotNull(startOfLoop);
		Assert.assertEquals(5, startOfLoop.data().intValue());
	}

	@Test
	public void testIntersection() {
		LinkedList<Integer> l1 = newLinkedList(2, 3, 5, 7);
		LinkedList<Integer> l2 = newLinkedList(1, 5, 7);

		LinkedListNode<Integer> intersection = intersection(l1, l2);
		Assert.assertNotNull(intersection);
		Assert.assertEquals(5, intersection.data().intValue());
	}

	@Test
	public void testEvenOddMerge() {
		LinkedList<Integer> l = newLinkedList(1, 2, 3, 4, 5);
		evenOddMerge(l);
		LinkedListNode<Integer> runningNode = l.head();
		int[] merged = new int[] { 1, 3, 5, 2, 4 };

		assertArrayEqualsList(merged, runningNode);

		l = newLinkedList(1, 2, 3, 4, 5, 6);
		evenOddMerge(l);
		runningNode = l.head();
		merged = new int[] { 1, 3, 5, 2, 4, 6 };

		assertArrayEqualsList(merged, runningNode);
	}

	@Test
	public void testReverse() {
		LinkedList<Integer> l = newLinkedList(1, 2, 3, 4, 5);
		reverse(l);
		LinkedListNode<Integer> runningNode = l.head();
		int[] reversed = new int[] { 5, 4, 3, 2, 1 };

		assertArrayEqualsList(reversed, runningNode);
	}
}

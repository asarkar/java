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

import java.util.NoSuchElementException;

import name.abhijitsarkar.algorithms.core.datastructure.LinkedList;
import name.abhijitsarkar.algorithms.core.datastructure.LinkedList.LinkedListNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* Due to the nature of Linked lists, they easily lend to recursive solutions. 
 * Most of the solutions here can be changed to recursive ones with little effort.
 */
public class PracticeQuestionsCh7 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh7.class);

	/*
	 * Q7.1: Write a function that takes L and F (linked lists), and returns the merge of L and F. Your code should use
	 * O(1) additional storage - it should reuse the nodes from the lists provided as input. The only field you can
	 * change in a node is next.
	 * 
	 * Solution: Since the lists are sorted, we could've just compared corresponding elements and copied over the
	 * smaller one to a 3rd list. However, the O(1) space requirement prohibits the use of a 3rd list; instead, we
	 * manipulate the references to move segments of the lists before or after current, which is always the latest
	 * insertion point.
	 */
	public static LinkedListNode<Integer> mergeLists(LinkedList<Integer> l, LinkedList<Integer> f) {
		final String newline = System.getProperty("line.separator");

		/*
		 * We keep track of the insertion point i.e. the bigger node before which the smaller node is inserted. We start
		 * by assuming that the node on L is bigger; we move the bigger pointer if we find out by comparison that it is
		 * actually smaller than the node from the other list.
		 */
		LinkedListNode<Integer> currentHead = l.head();
		LinkedListNode<Integer> bigger = currentHead.successor();
		LinkedListNode<Integer> otherHead = f.head();
		LinkedListNode<Integer> smallest = currentHead.successor();

		/* If we easily knew which list it actually was, we could've checked against tail instead of null. */
		while (otherHead.successor().data() != null) {
			LOGGER.info("{}", newline);
			LOGGER.info("Current head: {}.", currentHead);
			LOGGER.info("Current: {}.", bigger);
			LOGGER.info("Other head: {}.", otherHead);
			LOGGER.info("Smallest: {}.", smallest);

			/*
			 * The node on the other list is bigger so we move bigger there and update the head references accordingly.
			 */
			if (bigger.data() < otherHead.successor().data()) {
				LOGGER.info("Setting current head successor to: {}.", bigger.successor());
				currentHead.setSuccessor(bigger.successor());

				LOGGER.info("Setting bigger successor to: {}.", otherHead.successor());
				bigger.setSuccessor(otherHead.successor());

				LOGGER.info("Setting bigger to: {}.", otherHead.successor());
				bigger = otherHead.successor();

				LOGGER.info("Setting other head successor to: {}.", smallest);
				otherHead.setSuccessor(smallest);

				/* Swap heads */
				LOGGER.info("Swapping heads.");
				LinkedListNode<Integer> tempHead = currentHead;
				currentHead = otherHead;
				otherHead = tempHead;
			}
			/* The current node is bigger, no need to move bigger pointer. */
			else {
				LinkedListNode<Integer> predecessor = predecessor(currentHead, bigger);
				LOGGER.info("Predecessor to {} is {}.", bigger, predecessor);

				LinkedListNode<Integer> temp = otherHead.successor();

				LOGGER.info("Setting predecessor successor to: {}.", temp);
				predecessor.setSuccessor(temp);

				LOGGER.info("Setting smallest to: {}.", currentHead.successor());
				smallest = currentHead.successor();

				LOGGER.info("Setting other head successor to: {}.", temp.successor());
				otherHead.setSuccessor(temp.successor());

				LOGGER.info("Setting temp successor to: {}.", bigger);
				temp.setSuccessor(bigger);
			}
		}

		return currentHead;
	}

	private static LinkedListNode<Integer> predecessor(LinkedListNode<Integer> head, LinkedListNode<Integer> node) {
		for (LinkedListNode<Integer> runningNode = head; runningNode.successor() != null; runningNode = runningNode
				.successor()) {
			if (runningNode.successor().data() == node.data()) {
				return runningNode;
			}
		}

		/* Can't happen but the compiler is unmerciful. */
		throw new NoSuchElementException("No predecessor found for " + node);
	}

	/*
	 * Given a reference to the head of a singly linked list L, how would you determine whether L ends in a null or
	 * reaches a cycle of nodes? Write a function that returns null if there does not exist a cycle, and a reference to
	 * the start of the cycle if a cycle is present. (You do not know the length of the list in advance.)
	 * 
	 * Solution: We use the Floyd's Cycle Detection Algorithm, more commonly known as Tortoise and Hare Algorithm.
	 * Simply put, we run two pointers, a slower one that hops one node at a time, and a faster one that hops two nodes
	 * at a time. If they meet before any one of them reaches the end of the list, we've found ourselves a cycle.
	 * 
	 * To find the starting node in the loop, restart the slower pointer from head (keep the faster one where it was at
	 * the end of Floyd's algo.) and advance both one node at a time. The node they meet at is the start of the loop.
	 * 
	 * Detecting presence of a loop:
	 * http://codingfreak.blogspot.com/2012/09/detecting-loop-in-singly-linked-list_22.html. Detecting first node in the
	 * loop: http://codingfreak.blogspot.com/2012/12/detecting-first-node-in-a-loop.html
	 */
	public static LinkedListNode<Integer> startOfLoop(LinkedList<Integer> l) {
		LinkedListNode<Integer> tortoise = l.head();
		LinkedListNode<Integer> hare = l.head();

		do {
			if (tortoise != l.tail()) {
				tortoise = tortoise.successor();
				LOGGER.info("Tortoise was here: {}.", tortoise);
			}

			if (hare.successor() != l.tail()) {
				hare = hare.successor().successor();
				LOGGER.info("Hare was here: {}.", hare);
			}

			if (tortoise == l.tail() || hare == l.tail()) {
				LOGGER.info("No loop found.");

				return null;
			}
		} while (!tortoise.equals(hare));

		LOGGER.info("Loop found. Tortoise is here: {}, hare is here: {}.", tortoise, hare);

		for (tortoise = l.head(); !tortoise.equals(hare); tortoise = tortoise.successor(), hare = hare.successor())
			;

		LOGGER.info("Found start of loop at: {}.", tortoise);

		return tortoise;
	}

	public static LinkedListNode<Integer> intersection(LinkedList<Integer> l1, LinkedList<Integer> l2) {
		final int sizeL1 = l1.size();
		final int sizeL2 = l2.size();

		LOGGER.info("List 1 size: {}.", sizeL1);
		LOGGER.info("List 2 size: {}.", sizeL2);

		int diff = sizeL1 - sizeL2;

		LinkedListNode<Integer> l1Pointer = l1.head();
		LinkedListNode<Integer> l2Pointer = l2.head();

		if (diff < 0) {
			for (; diff > 0; l2Pointer = l2Pointer.successor(), --diff)
				;
		} else {
			for (; diff > 0; l1Pointer = l1Pointer.successor(), --diff)
				;
		}

		while (l1Pointer != l1.tail() && l2Pointer != l2.tail()) {
			LOGGER.info("List 1 pointer is at: {}.", l1Pointer);
			LOGGER.info("List 2 pointer is at: {}.", l2Pointer);

			if (l1Pointer.equals(l2Pointer)) {
				return l1Pointer;
			}
			l1Pointer = l1Pointer.successor();
			l2Pointer = l2Pointer.successor();
		}

		return null;
	}

	/*
	 * Write a function that takes a singly linked list L, and reorders the elements of L so that the new list
	 * represents even-odd(L). Your function should use O(1) additional storage. The only field you can change in a node
	 * is next.
	 */
	public static void evenOddMerge(LinkedList<Integer> l) {
		LinkedListNode<Integer> evenPointer = l.head();
		LinkedListNode<Integer> oddPointer = l.head();
		LinkedListNode<Integer> oddHead = null;

		while (evenPointer.successor() != l.tail()) {
			oddPointer = evenPointer.successor().successor();
			LOGGER.info("Odd pointer is at: {}.", oddPointer);

			if (oddHead == null) {
				oddHead = oddPointer;
			}

			evenPointer = evenPointer.successor();
			LOGGER.info("Even pointer is at: {}.", evenPointer);

			if (oddPointer.successor() != null) {
				LOGGER.info("Setting even pointer successor to: {}.", oddPointer.successor());
				evenPointer.setSuccessor(oddPointer.successor());
			}

			if (evenPointer.successor() != l.tail()) {
				LOGGER.info("Setting odd pointer successor to: {}.", evenPointer.successor());
				oddPointer.setSuccessor(evenPointer.successor().successor());
			}
		}

		evenPointer.setSuccessor(oddHead);
	}

	/*
	 * Give a linear time non-recursive function that reverses a singly linked list. The function should use no more
	 * than constant storage beyond that needed for the list itself.
	 */
	public static void reverse(LinkedList<Integer> l) {
		LinkedListNode<Integer> previous = l.head();
		LinkedListNode<Integer> current = previous.successor();
		LinkedListNode<Integer> next = current.successor();

		while (next != l.tail()) {
			LOGGER.info("Previous: {}, current: {}, next: {}.", previous, current, next);

			current.setSuccessor(previous);

			previous = current;
			current = next;
			next = next.successor();
		}

		LOGGER.info("Swapping head and tail.");
		LOGGER.info("Previous: {}, current: {}, next: {}.", previous, current, next);
		current.setSuccessor(previous);
		previous = l.head().successor();
		l.head().setSuccessor(current);
		next.setSuccessor(previous);
	}
}

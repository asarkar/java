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

import java.util.HashSet;
import java.util.Set;

import name.abhijitsarkar.algorithms.core.datastructure.LinkedList;
import name.abhijitsarkar.algorithms.core.datastructure.LinkedList.LinkedListNode;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh2 {
	/*
	 * Q2.1: Write code to remove duplicates from an unsorted linked list.
	 */
	public static <E> void removeDupes(LinkedList<E> linkedList) {
		Set<E> dupes = new HashSet<E>();

		LinkedListNode<E> current = linkedList.head().successor();
		E data = null;

		for (int idx = 0; idx < linkedList.size(); idx++) {
			data = current.data();
			current = current.successor();

			if (dupes.contains(data)) {
				linkedList.remove(idx);
			} else {
				dupes.add(data);
			}
		}
	}

	/*
	 * Q2.4: Write code to partition a linked list around a value x, such that all nodes less than x come before all
	 * nodes greater than or equal to x.
	 * 
	 * Doesn't handle dupes in the input list.
	 * 
	 * Partition is the basic building block of quick sort.
	 */
	public static <E extends Comparable<E>> void partition(LinkedList<E> linkedList, E pivot) {
		int lastIdx = linkedList.size() - 1;
		int pivotIdx = linkedList.indexOf(pivot);

		if (pivotIdx != linkedList.lastIndexOf(pivot)) {
			throw new IllegalArgumentException("Can't handle dupes in input list.");
		}

		swap(linkedList, pivotIdx, lastIdx);

		int storeIdx = 0;

		for (int runningIdx = 0; runningIdx < lastIdx; runningIdx++) {
			if (linkedList.get(runningIdx).compareTo(pivot) <= 0) {
				swap(linkedList, runningIdx, storeIdx);
				storeIdx++;
			}
		}
		swap(linkedList, storeIdx, lastIdx);
	}

	private static <E> void swap(LinkedList<E> linkedList, int idx1, int idx2) {
		E element = linkedList.get(idx1);

		linkedList.set(idx1, linkedList.get(idx2));
		linkedList.set(idx2, element);
	}

	/*
	 * Q2.7: Implement a function to check if a linked list is a palindrome.
	 */
	public static <E> boolean isPalindrome(LinkedList<E> linkedList) {
		int lastIdx = linkedList.size() - 1;

		for (int runningIdx = 0; runningIdx < lastIdx; runningIdx++, lastIdx--) {
			if (!linkedList.get(runningIdx).equals(linkedList.get(lastIdx))) {
				return false;
			}
		}

		return true;
	}
}

package name.abhijitsarkar.codinginterview.linkedlists;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PracticeQuestions {
	/**
	 * Q2.1: Remove dupes from an unsorted linked list
	 */
	public static <E> void removeDupes(LinkedList<E> linkedList) {
		List<Integer> dupeIndices = new ArrayList<Integer>();

		for (E element : linkedList) {
			int idx = linkedList.indexOf(element);
			int lastIdx = linkedList.lastIndexOf(element);

			if (dupeIndices.contains(lastIdx)) {
				continue;
			}

			while (lastIdx > idx) {
				dupeIndices.add(lastIdx);

				lastIdx = linkedList.indexOf(element);
			}
		}

		for (int dupeIdx : dupeIndices) {
			linkedList.remove(dupeIdx);
		}
	}

	/**
	 * A variation of Q2.1: Remove dupes from an unsorted linked list without
	 * using a temporary buffer
	 */
	public static <E> void removeDupesWithoutUsingBuffer(
			LinkedList<E> linkedList) {
		E element = null;

		for (int i = 0; i < linkedList.size(); i++) {
			element = linkedList.get(i);

			int idx = linkedList.indexOf(element);
			int lastIdx = linkedList.lastIndexOf(element);

			while (lastIdx > idx) {
				linkedList.remove(lastIdx);

				lastIdx = linkedList.indexOf(element);
			}
		}
	}

	/**
	 * Q2.4: Partitions the input linked list around a pivot element such that
	 * all elements smaller than the pivot are on the left of it and all
	 * elements larger than the pivot are on the right of it. Doesn't handle
	 * dupes in the input list.
	 * 
	 * Partition is the basic building block of quick sort.
	 * 
	 * @param linkedList
	 *            Input linked list
	 * @param pivot
	 *            Pivot element
	 */
	public static <E extends Comparable<E>> void partition(
			LinkedList<E> linkedList, E pivot) {
		int lastIdx = linkedList.size() - 1;
		int pivotIdx = linkedList.indexOf(pivot);

		if (pivotIdx != linkedList.lastIndexOf(pivot)) {
			throw new IllegalArgumentException(
					"Can't handle dupes in input list.");
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

	private static <E> void swap(LinkedList<E> linkedList, int idx, int storeIdx) {
		E element = linkedList.get(idx);

		linkedList.set(idx, linkedList.get(storeIdx));
		linkedList.set(storeIdx, element);
	}

	/**
	 * Q2.7: Checks if the linked list is a palindrome
	 * 
	 * @param linkedList
	 * @return true is the list is a palindrome, false otherwise
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

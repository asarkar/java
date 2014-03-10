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
package name.abhijitsarkar.programminginterviews.heaps;

import java.util.Arrays;

import name.abhijitsarkar.algorithms.core.datastructure.Heap;
import name.abhijitsarkar.algorithms.core.datastructure.MaxHeap;
import name.abhijitsarkar.algorithms.core.datastructure.MinHeap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */

public class PracticeQuestionsCh10 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh10.class);

	/*
	 * Q10.6: The input consists of a very long sequence of numbers. Each number is at most k positions away from its
	 * correctly sorted position. Design an algorithm that outputs the numbers in the correct order and uses O(k)
	 * storage, independent of the number of elements processed.
	 * 
	 * Solution: Build a min heap with the first 2k elements (2k because the kth element might be k positions away from
	 * its correct position. Then delete from the heap (always the min) and insert an element from the input array.
	 */
	public static int[] kSort(Integer[] arr, int k) {
		final int size = Math.min(2 * k, arr.length);

		MinHeap<Integer> minHeap = new MinHeap<Integer>(Arrays.copyOf(arr, size));

		int[] sorted = new int[arr.length];
		int i = 0;

		/* Insert remaining elements from the input array. */
		for (int j = size + i; j < arr.length; ++i, ++j) {
			sorted[i] = minHeap.delete();
			minHeap.insert(arr[j]);
		}

		/* Delete until empty. */
		for (; minHeap.size() > 0; ++i) {
			sorted[i] = minHeap.delete();
		}

		return sorted;
	}

	/*
	 * Q10.8: Design an algorithm for computing the running median of a sequence. The time complexity should be O(log n)
	 * per element read in, where n is the number of values read in up to that element.
	 * 
	 * Solution: We use a min heap to store the elements smaller than the running median and a max heap to store the
	 * elements greater (the combination of both keep maintains a sorted stream). The trick is to keep the heaps
	 * balanced such that the difference of their heights is never greater than one. If inserting an element is about to
	 * violate the height balance, we transfer the top element of the heap having more elements to the other one before
	 * the insertion. At any point, the median is either the top element of the heap having more elements (the "middle"
	 * element), or the average of the top elements of both heaps if they have the same number of elements.
	 */
	public static int[] runningMedian(int[] elements) {
		final MinHeap<Integer> minHeap = new MinHeap<Integer>(new Integer[] {});
		final MaxHeap<Integer> maxHeap = new MaxHeap<Integer>(new Integer[] {});

		final int[] medians = new int[elements.length];
		int median = 0;
		int element = 0;
		boolean isElementLessThanMedian = false;
		Heap<Integer> heapWhereElementToBeInserted = null;
		Heap<Integer> otherHeap = null;

		for (int i = 0; i < elements.length; ++i) {
			element = elements[i];

			isElementLessThanMedian = element < median;

			/* If element less than median, insert into max heap. Otherwise, insert into min heap. */
			heapWhereElementToBeInserted = heapWhereElementToBeInserted(isElementLessThanMedian, minHeap, maxHeap);
			otherHeap = heapWhereElementToBeInserted == minHeap ? maxHeap : minHeap;

			/* Transfer top item, if required, to keep the heaps height balanced. */
			transferTopItemIfRequired(heapWhereElementToBeInserted, otherHeap);
			heapWhereElementToBeInserted.insert(element);

			if (isTheSizeOfBothHeapsEqual(minHeap, maxHeap)) {
				median = average(minHeap.root(), maxHeap.root());
			} else {
				median = heapWhereElementToBeInserted.root();
			}

			medians[i] = median;
		}

		return medians;
	}

	private static void transferTopItemIfRequired(Heap<Integer> fromHeap, Heap<Integer> toHeap) {
		if (isBigger(fromHeap, toHeap)) {
			toHeap.insert(fromHeap.delete());
		}
	}

	private static boolean isBigger(Heap<Integer> heap1, Heap<Integer> heap2) {
		return heap1.size() - heap2.size() > 0;
	}

	private static boolean isTheSizeOfBothHeapsEqual(MinHeap<Integer> minHeap, MaxHeap<Integer> maxHeap) {
		return minHeap.size() == maxHeap.size();
	}

	private static Heap<Integer> heapWhereElementToBeInserted(boolean elementLessThanMedian, Heap<Integer> minHeap,
			Heap<Integer> maxHeap) {
		return elementLessThanMedian ? maxHeap : minHeap;
	}

	private static int average(int val1, int val2) {
		return (val1 + val2) / 2;
	}
}

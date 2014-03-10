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
	 * The input consists of a very long sequence of numbers. Each number is at most k positions away from its correctly
	 * sorted position. Design an algorithm that outputs the numbers in the correct order and uses O(k) storage,
	 * independent of the number of elements processed.
	 */
	public static int[] kSort(Integer[] arr, int k) {
		final int size = Math.min(2 * k, arr.length);

		MinHeap<Integer> minHeap = new MinHeap<Integer>(Arrays.copyOf(arr, size));

		int[] sorted = new int[arr.length];
		int i = 0;
		for (int j = size + i; j < arr.length; ++i, ++j) {
			sorted[i] = minHeap.delete();
			minHeap.insert(arr[j]);
		}

		for (; minHeap.size() > 0; ++i) {
			sorted[i] = minHeap.delete();
		}

		return sorted;
	}

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

			heapWhereElementToBeInserted = heapWhereElementToBeInserted(isElementLessThanMedian, minHeap, maxHeap);
			otherHeap = heapWhereElementToBeInserted == minHeap ? maxHeap : minHeap;

			transferTopItemIfRequired(heapWhereElementToBeInserted, otherHeap);
			heapWhereElementToBeInserted.insert(element);

			if (isTheSizeOfBothHeapsEqual(minHeap, maxHeap)) {
				median = heapWhereElementToBeInserted.root();
			} else {
				median = average(minHeap.root(), maxHeap.root());
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
		return elementLessThanMedian ? minHeap : maxHeap;
	}

	private static int average(Integer val1, Integer val2) {
		val1 = val1 != null ? val1 : 0;
		val2 = val2 != null ? val2 : 0;

		return (val1 + val2) / 2;
	}
}

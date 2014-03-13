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
package name.abhijitsarkar.algorithms.core;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.abhijitsarkar.algorithms.core.datastructure.BinarySearchTree;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.TransformerUtils;

/**
 * @author Abhijit Sarkar
 */
public class Sorter {
	public static int[] mergeSort(int[] arr) {
		return recursiveMergeSort(arr, 0, arr.length - 1);
	}

	private static int[] recursiveMergeSort(int[] arr, int startIndex, int endIndex) {
		if (startIndex >= endIndex) {
			return Arrays.copyOfRange(arr, startIndex, endIndex + 1);
		}

		final int middleIndex = (startIndex + endIndex) >> 1;

		int[] leftArr = recursiveMergeSort(arr, startIndex, middleIndex);
		int[] rightArr = recursiveMergeSort(arr, middleIndex + 1, endIndex);

		return merge(leftArr, rightArr);
	}

	private static int[] merge(int[] leftArr, int[] rightArr) {
		final int len1 = leftArr.length;
		final int len2 = rightArr.length;

		final int[] holderArr = new int[len1 + len2];

		int leftIdx = 0, rightIdx = 0;

		while (leftIdx < len1 && rightIdx < len2) {
			if (leftArr[leftIdx] < rightArr[rightIdx]) {
				holderArr[leftIdx + rightIdx] = leftArr[leftIdx];

				leftIdx++;
			} else {
				holderArr[leftIdx + rightIdx] = rightArr[rightIdx];

				rightIdx++;
			}
		}

		for (; leftIdx < len1; leftIdx++) {
			holderArr[leftIdx + rightIdx] = leftArr[leftIdx];
		}

		for (; rightIdx < len2; rightIdx++) {
			holderArr[leftIdx + rightIdx] = rightArr[rightIdx];
		}

		return holderArr;
	}

	public static int[] quickSort(int[] arr) {
		return recursiveQuickSort(arr, 0, arr.length - 1);
	}

	private static int[] recursiveQuickSort(int[] arr, int startIdx, int endIdx) {
		if ((endIdx - startIdx) <= 1) {
			return arr;
		}

		final int newPivotIdx = partition(arr, startIdx, endIdx);

		recursiveQuickSort(arr, startIdx, newPivotIdx - 1);
		recursiveQuickSort(arr, newPivotIdx + 1, endIdx);

		return arr;
	}

	private static int partition(int[] arr, int startIdx, int endIdx) {
		if ((endIdx - startIdx) <= 1) {
			return startIdx;
		}

		int pivotValue = arr[startIdx];

		int storeIdx = startIdx;

		swap(arr, storeIdx, endIdx);

		for (int runningIdx = startIdx; runningIdx <= endIdx; runningIdx++) {
			if (arr[runningIdx] <= pivotValue) {
				swap(arr, runningIdx, storeIdx);
				storeIdx++;
			}
		}
		swap(arr, storeIdx, endIdx);

		return storeIdx;
	}

	private static void swap(int[] arr, int idx1, int idx2) {
		int element = arr[idx1];

		arr[idx1] = arr[idx2];
		arr[idx2] = element;
	}

	public static int[] bucketSort(int[] arr) {
		int[][] buckets = scatter(arr);

		ArrayDeque<int[]> stack = new ArrayDeque<int[]>();

		for (int[] bucket : buckets) {
			if (bucket != null) {
				stack.add(mergeSort(bucket));
			}
		}

		return gather(stack);
	}

	private static int[][] scatter(int[] arr) {
		@SuppressWarnings("unchecked")
		List<Integer>[] tempBuckets = (List<Integer>[]) Array.newInstance(ArrayList.class, 8);

		long hash = -1;
		int idx = -1;
		List<Integer> bucket = null;

		for (int i : arr) {
			hash = DJBHash(Integer.valueOf(i).toString());
			idx = computeIndex(hash);

			bucket = createOrGetBucket(tempBuckets, idx);

			bucket.add(i);
		}

		return toArray(tempBuckets);
	}

	private static int[][] toArray(List<Integer>[] tempBuckets) {
		final int numBuckets = tempBuckets.length;
		int[][] buckets = new int[numBuckets][];
		List<Integer> aBucket = null;

		for (int numBucket = 0; numBucket < numBuckets; numBucket++) {
			aBucket = tempBuckets[numBucket];

			if (aBucket == null) {
				continue;
			}

			final int numBucketElements = aBucket.size();
			buckets[numBucket] = new int[numBucketElements];

			for (int numElement = 0; numElement < numBucketElements; numElement++) {
				buckets[numBucket][numElement] = aBucket.get(numElement);
			}
		}

		return buckets;
	}

	private static int computeIndex(long hash) {
		// Compute modulo 8 with a bitmask
		return (int) (hash & 0x07);
	}

	/*
	 * http://www.partow.net/programming/hashfunctions
	 */
	private static final long DJBHash(String str) {
		long hash = 5381;

		for (int i = 0; i < str.length(); i++) {
			hash = hash << 5 + hash + str.charAt(i);
		}

		return hash;
	}

	private static List<Integer> createOrGetBucket(List<Integer>[] buckets, int idx) {
		List<Integer> bucket = buckets[idx];

		if (bucket == null) {
			bucket = new ArrayList<Integer>();

			buckets[idx] = bucket;
		}

		return bucket;
	}

	private static int[] gather(ArrayDeque<int[]> stack) {
		int[] mergedBucket = null;

		while (!stack.isEmpty()) {
			mergedBucket = stack.remove();

			if (!stack.isEmpty()) {
				int[] bucket = stack.remove();

				mergedBucket = merge(mergedBucket, bucket);

				stack.add(mergedBucket);
			}
		}

		return mergedBucket;
	}

	/*
	 * This is a more sophisticated implementation where the program does not know the number of distinct keys in the
	 * input array. If that is known, a simpler approach could use an array of length = max(key) + 1 and just use the
	 * key as the index to the array. The values are going to be the frequencies of each key. The downside of that
	 * approach is wastage of space if the keys are widely separated as most cells would contain zero.
	 */
	public static Integer[] countingSort(final int[] arr) {
		final Map<Integer, Pair> countMap = new HashMap<>();

		for (final int i : arr) {
			Pair p = new Pair(i, 1);

			if (countMap.containsKey(i)) {
				p.value += countMap.get(i).value;
			}

			countMap.put(i, p);
		}

		BinarySearchTree<Pair> bst = new BinarySearchTree<>(countMap.values());

		final List<Pair> sortedPairs = BinaryTreeWalker.recursiveInorder(bst.root());

		return CollectionUtils.collect(sortedPairs, TransformerUtils.<Pair, Integer> invokerTransformer("getKey"))
				.toArray(new Integer[] {});
	}

	public static class Pair implements Comparable<Pair> {
		private int key;
		private int value;

		private Pair(int key, int value) {
			this.key = key;
			this.value = value;
		}

		public int getKey() {
			return key;
		}

		public int getValue() {
			return value;
		}

		@Override
		public int compareTo(Pair o) {
			return Integer.valueOf(key).compareTo(o.key);
		}

		@Override
		public String toString() {
			return "Pair [key=" + key + ", value=" + value + "]";
		}
	}
}

package name.abhijitsarkar.codinginterview.algorithm;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sorter {
	public static int[] mergeSort(int[] arr) {
		return recursiveMergeSort(arr, 0, arr.length - 1);
	}

	private static int[] recursiveMergeSort(int[] arr, int startIndex,
			int endIndex) {
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

		for (; leftIdx < len1 && rightIdx < len2;) {
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

		final int pivotIdx = (startIdx + endIdx) >> 1;

		final int newPivotIdx = partition(arr, startIdx, endIdx, pivotIdx);

		recursiveQuickSort(arr, startIdx, newPivotIdx - 1);
		recursiveQuickSort(arr, newPivotIdx + 1, endIdx);

		return arr;
	}

	private static int partition(int[] arr, int startIdx, int endIdx,
			int pivotIdx) {
		if ((endIdx - startIdx) <= 1) {
			return startIdx;
		}

		int pivotValue = arr[startIdx];

		swap(arr, startIdx, endIdx);

		int storeIdx = startIdx;

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
		List<Integer>[] tempBuckets = (List<Integer>[]) Array.newInstance(
				ArrayList.class, 8);

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
			hash = ((hash << 5) + hash) + str.charAt(i);
		}

		return hash;
	}

	private static List<Integer> createOrGetBucket(List<Integer>[] buckets,
			int idx) {
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
}

package name.abhijitsarkar.codinginterview.algorithm;

import java.util.Arrays;

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
}

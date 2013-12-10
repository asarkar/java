package name.abhijitsarkar.codinginterview.algorithm;

import org.junit.Assert;
import org.junit.Test;

public class SorterTest {

	@Test
	public void testMergeSort() {
		int[] arr = new int[] { 17, 3, 99, 51 };
		int[] expected = new int[] { 3, 17, 51, 99 };

		int[] actual = Sorter.mergeSort(arr);

		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void testQuickSort() {
		int[] arr = new int[] { 17, 3, 99, 51 };
		int[] expected = new int[] { 3, 17, 51, 99 };

		int[] actual = Sorter.quickSort(arr);

		Assert.assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testBucketSort() {
		int[] arr = new int[] { 17, 3, 99, 51 };
		int[] expected = new int[] { 3, 17, 51, 99 };

		int[] actual = Sorter.bucketSort(arr);

		Assert.assertArrayEquals(expected, actual);
	}
}

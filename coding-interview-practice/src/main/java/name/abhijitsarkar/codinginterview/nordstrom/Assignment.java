package name.abhijitsarkar.codinginterview.nordstrom;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections4.map.MultiValueMap;

public class Assignment {
	/*
	 * Given a collection of strings, write a method to determine the nTh longest string in that list.
	 * 
	 * Solution: There are various ways to solve this problem but the most efficient is to use a Quickselect algorithm.
	 * Time Complexity: average = O(n); worse O(n^2)
	 * 
	 * We keep a map of string length to the index of the string in the list. To handle the case where several strings
	 * have the same length, we use a multivalued map. The implementation uses a class from Commons Collections library
	 * but it can easily be implemented too.
	 * 
	 * We don't sort the input strings because that could be O(nlogn) time complexity and we can do better. Without
	 * given additional information, we trade space for performance.
	 * 
	 * There could be edge cases where n is greater than the length of the input list. The implementation throws a
	 * NullPointerException in that case. It also throws a NullPointerException if a string in the list is null.
	 * 
	 * Lastly, in case there're several strings with the same length, only the first one is returned.
	 */
	public String getLongestString(int n, List<String> inputs) {
		int[] lengths = new int[inputs.size()];
		MultiValueMap<Integer, Integer> lengthToIndicesMap = new MultiValueMap<>();

		int len = -1;

		for (int i = 0; i < inputs.size(); i++) {
			len = inputs.get(i).length();
			lengths[i] = len;
			lengthToIndicesMap.put(len, i);
		}

		int maxLen = quickselect(lengths, 0, inputs.size() - 1, n - 1);

		return inputs.get(lengthToIndicesMap.getCollection(maxLen).iterator().next());
	}

	private int quickselect(int[] lengths, int first, int last, int k) {
		if (first <= last) {
			int pivot = partition(lengths, first, last);

			if (pivot == k) {
				return lengths[k];
			}
			if (pivot > k) {
				return quickselect(lengths, first, pivot - 1, k);
			}
			return quickselect(lengths, pivot + 1, last, k);
		}
		return Integer.MIN_VALUE;
	}

	private int partition(int[] lengths, int first, int last) {
		int pivot = first + new Random().nextInt(last - first + 1);

		swap(lengths, last, pivot);

		for (int i = first; i < last; i++) {
			if (lengths[i] > lengths[last]) {
				swap(lengths, i, first);
				first++;
			}
		}
		swap(lengths, first, last);

		return first;
	}

	private void swap(int[] lengths, int x, int y) {
		int tmp = lengths[x];
		lengths[x] = lengths[y];
		lengths[y] = tmp;
	}

	/*
	 * Given two input strings, write a method to determine if two strings are permutations of each other.
	 * 
	 * Solution: We just need to compare the sorted versions of the strings.
	 */
	public boolean isPermutationOfEachOther(String s, String t) {
		if (s.length() != t.length()) {
			return false;
		}
		return sort(s).equals(sort(t));
	}

	private String sort(String s) {
		char[] content = s.toCharArray();
		Arrays.sort(content);

		return new String(content);
	}
}

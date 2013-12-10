package name.abhijitsarkar.codinginterview.algorithm.recursion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PracticeQuestions {
	/*
	 * Q9.3: A magic index in an array A[0...n-1] is defined to be an index such
	 * that A[i] = i. Given a sorted array of distinct integers, write a method
	 * to find a magic index, if one exists, in array A.
	 * 
	 * This algorithm doesn't handle dupes in the input array. See the book for
	 * a solution that does.
	 */
	public static int magicIndex(int[] array, int startIndex, int endIndex) {
		if (startIndex >= endIndex) {
			return -1;
		}

		int midIdx = startIndex + endIndex >> 1;
		int val = array[midIdx];

		if (val == midIdx) {
			System.out.printf("Found magic index %d\n", midIdx);
			return midIdx;
		}

		System.out.printf(
				"Start index = %d, end index = %d, middle index = %d\n",
				startIndex, endIndex, midIdx);
		System.out.printf("val = %d\n", val);

		if (midIdx < val) {
			return magicIndex(array, startIndex, --midIdx);
		}
		return magicIndex(array, ++midIdx, endIndex);
	}

	/* Q9.5: Write a method to compute all permutations of a string. */
	public static String[] perm(String s) {
		Set<String> perms = new HashSet<String>();

		recursivePerm(s, s.length() - 1, perms);

		return perms.toArray(new String[] {});
	}

	private static Set<String> recursivePerm(String s, int index,
			Set<String> perms) {
		int len = s.length();

		if (len <= 1) {
			return stringToSet(s);
		}

		if (index < 0) {
			return perms;
		}

		char ch = s.charAt(index);
		StringBuilder buffer = buildBuffer(s, index);

		String temp = buffer.toString();

		for (int idx = 0; idx < len - 1; idx++) {
			perms.add(buffer.insert(idx, ch).toString());
			buffer.replace(0, len, temp);
		}

		recursivePerm(s, --index, perms);

		return perms;
	}

	private static StringBuilder buildBuffer(String s, int index) {
		StringBuilder buffer = new StringBuilder();
		int len = s.length();

		if (index == 0) {
			buffer.append(s.substring(1));
		} else if (index == len - 1) {
			buffer.append(s.substring(0, len - 1));
		} else {
			buffer.append(s.substring(0, index)).append(s.substring(index + 1));
		}

		return buffer;
	}

	private static Set<String> stringToSet(String s) {
		Set<String> perm = new HashSet<String>();
		perm.addAll(Arrays.asList(s));

		return perm;
	}
}

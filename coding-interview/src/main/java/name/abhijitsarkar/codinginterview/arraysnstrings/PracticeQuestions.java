package name.abhijitsarkar.codinginterview.arraysnstrings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PracticeQuestions {
	/**
	 * Q1.1: Determines if a string has all unique characters. Assumes that each
	 * character in the string can be encoded in ASCII. Also assumes that case
	 * doesn't matter.
	 **/
	public static boolean isUnique(String str) {
		// Lower case alphabets start at 97
		final short IDX_SHIFT = 97;
		short[] charCount = new short[26];

		char[] charArr = str.toCharArray();

		short idx = 0;

		for (char c : charArr) {
			c = Character.toLowerCase(c);

			// Difference of 2 short produces int
			idx = (short) (((short) c) - IDX_SHIFT);

			if (charCount[idx] > 0) {
				return false;
			}

			charCount[idx]++;
		}

		return true;
	}

	/**
	 * Q1.3: Checks if one string is a permutation of the other. Assumes that
	 * each character in the strings can be encoded in ASCII.
	 * 
	 * @param s1
	 *            String to check against for permutation
	 * @param s2
	 *            Potentially permutated String
	 * @return true if s2 is a permutation of s1, false otherwise
	 */
	public static boolean isPermutation(String s1, String s2) {
		int len = s1.length();

		if (s2.length() != len) {
			return false;
		}

		char[] charArr1 = s1.toCharArray();
		char[] charArr2 = s2.toCharArray();

		short sum1 = 0;
		short sum2 = 0;

		for (short i = 0; i < len; i++) {
			sum1 += ((short) charArr1[i]);
			sum2 += ((short) charArr2[i]);
		}

		return (sum1 == sum2);
	}

	/**
	 * Q1.5: Encodes repeated characters in a string with character followed by
	 * the number of occurrences. So "aaa" becomes "a3", "c" becomes "c1".
	 **/
	public static String encodeRepeatedChars(String str) {
		char[] charArr = str.toCharArray();
		Map<Character, Integer> encodingMap = new HashMap<Character, Integer>();
		StringBuilder buffer = new StringBuilder();

		for (char ch : charArr) {
			ch = Character.toLowerCase(ch);

			Integer repeatCount = encodingMap.get(ch);

			if (repeatCount == null) {
				repeatCount = 0;
			}

			encodingMap.put(ch, ++repeatCount);

			// The map entrySet or ketSet may not preserve order thus using
			// it may change the relative order of characters in the string

			int idx = buffer.indexOf(String.valueOf(ch));

			if (idx == -1) {
				buffer.append(ch).append(repeatCount);
			} else {
				buffer.replace(++idx, ++idx, String.valueOf(repeatCount));
			}
		}

		return (buffer.length() < str.length() ? buffer.toString() : str);
	}

	/**
	 * Q1.7: If one element of the matrix is zero, sets all the elements in the
	 * row and column to zero.
	 */
	public static void fillIfZero(int[][] matrix) {
		int numRows = matrix.length;
		int numCols = matrix[0].length;
		int rowWhereZeroFound = -1;
		int colWhereZeroFound = -1;

		for (int rowNum = 0; rowNum < numRows; rowNum++) {
			boolean isZeroFound = false;

			for (int colNum = 0; colNum < numCols; colNum++) {
				// If a zero has been found previously on this row, isZeroFound
				// is always going to be true
				isZeroFound = (matrix[rowNum][colNum] == 0) | isZeroFound;

				if (isZeroFound) {
					System.out.println("Zero found at [" + rowNum + ", "
							+ colNum + "]");

					colWhereZeroFound = colNum;
					rowWhereZeroFound = rowNum;

					break;
				}
			}

			if (isZeroFound) {
				System.out.println("Setting to zero [" + rowNum + ", "
						+ colWhereZeroFound + "]");

				matrix[rowNum][colWhereZeroFound] = 0;
			}
		}

		System.out.println("Setting to zero [" + rowWhereZeroFound + "]");

		Arrays.fill(matrix[rowWhereZeroFound], 0);

		for (int rowNum = 0; rowNum < rowWhereZeroFound; rowNum++) {
			System.out.println("Setting to zero [" + rowNum + ", "
					+ colWhereZeroFound + "]");

			matrix[rowNum][colWhereZeroFound] = 0;
		}
	}

	/**
	 * Q1.8: Checks if one string is a rotation of the other, e.g.,
	 * "waterbottle" is a rotation of "erbottlewat"
	 * 
	 * @param s1
	 *            String to check against for rotation
	 * @param s2
	 *            Potentially rotated String
	 * @return true if s2 is a rotation of s1, false otherwise
	 */
	public static boolean isRotation(String s1, String s2) {
		int len = s1.length();

		if (s2.length() != len) {
			return false;
		}

		char[] charArr1 = s1.toCharArray();
		char[] charArr2 = s2.toCharArray();

		int j = 0;
		for (int i = 0; i < len && j < len; i++) {
			if (charArr1[i] == charArr2[j]) {
				j++;
			}
		}

		return isSubstring(s1,
				String.valueOf(Arrays.copyOfRange(charArr2, j, len)));
	}

	private static boolean isSubstring(String s1, String s2) {
		return s1.contains(s2);
	}
}

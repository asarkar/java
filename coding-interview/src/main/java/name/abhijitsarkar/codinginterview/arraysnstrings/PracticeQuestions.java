package name.abhijitsarkar.codinginterview.arraysnstrings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PracticeQuestions {
	/**
	 * Q1.1: Determines if a string has all unique characters. Assumes character
	 * set ASCII.
	 **/
	public static boolean isUnique(String s) {
		/*
		 * Bit vector; note that an int in Java is 32-bit. ASCII has 128
		 * characters but first 32 are reserved for control characters. Thus, we
		 * are left with 96 characters, needing 3 int to store the status of
		 * them all.
		 * 
		 * This can also be achieved with a hash table but we'd need 96 buckets,
		 * most of which are going to be unused for a string. Bit operations
		 * save space and are faster.
		 */
		int[] arr = new int[3];

		int len = s.length();
		int ch = -1;
		final int numASCIICtrlChars = 32;

		for (int i = 0; i < len; i++) {
			/*
			 * ASCII has 128 characters but first 32 are reserved for control
			 * characters.
			 */
			ch = s.charAt(i) - numASCIICtrlChars;

			/*
			 * We need a 32-bit bitmask. Which bit to set is determined by the
			 * result of (ch % 32). So if ch = 97 ('a'), (97 - 32) % 32 = 1,
			 * meaning the least significant bit needs to be set. If ch = 103
			 * ('g'), (103 - 32) % 32 = 7, 7th bit needs to be set. The result
			 * of the modulo operation can go from 0 to 31. Since each bit
			 * represents a power of 2, left shift operator (multiplication by
			 * 2) should do the trick.
			 * 
			 * The least significant 5 bits of the result of the modulo 32
			 * operation are the same as the result of the bitwise 'and'
			 * operation below.
			 */
			int bitmask = 1 << (ch & 0x1f) - 1;

			/*
			 * The index in the bit vector is given by ch / 32, or right shift
			 * 5. We then do a bitwise 'and' to isolate the bit corresponding to
			 * 'ch' within the int.
			 */
			int bitVectorIdx = ch >> 5;

			ch = (arr[bitVectorIdx] & bitmask);

			if (ch == 1) {
				return false;
			}

			/*
			 * Set the corresponding bit keeping previously set bits intact
			 */
			arr[bitVectorIdx] |= bitmask;
		}

		return true;
	}

	/**
	 * Q1.3: Checks if one string is a permutation of the other. Assumes
	 * character set ASCII.
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

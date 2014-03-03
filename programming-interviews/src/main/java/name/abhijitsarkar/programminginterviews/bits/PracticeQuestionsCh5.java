package name.abhijitsarkar.programminginterviews.bits;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PracticeQuestionsCh5 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh5.class);

	/*
	 * Q5.1: How would you go about computing the parity of a very large number of 64-bit nonnegative integers?
	 */
	public static long[] computeParities(final long[] input) {
		final int numInput = input == null || input.length == 0 ? 0 : input.length;

		if (numInput == 0) {
			return input;
		}

		final int storage = (int) (numInput >> 6) + 1;
		final long[] parities = new long[storage];

		if (isOddParity(input[0])) {
			parities[0] ^= 1;
		}

		for (int i = 1; i < numInput; i++) {
			final int section = i >> 6;
			final int currentIdx = i % 64 - 1;

			if (isOddParity(input[i])) {
				parities[section] ^= 1 << currentIdx;
			}
		}

		return parities;
	}

	private static boolean isOddParity(long num) {
		if (num <= 0) {
			return false;
		}

		int sum = 0;

		for (int i = 0; i < 64; i++) {
			if ((num & (1L << i)) > 0) {
				sum += 1;
			}
		}

		/*
		 * x % y, when y is a multiple of 2 is equivalent to x & (y - 1)
		 * http://stackoverflow.com/questions/11076216/re-implement-modulo-using-bit-shifts
		 */
		return (sum & 0x1) == 0;
	}

	/*
	 * Q5.2: A 64-bit ineteger can be viewed as an array of 64 bits, with the bit at index 0 corresponding to the LSB,
	 * and the bit at index 63 corresponding to the MSB. Implement code that takes as input a 64-bit integer x and swaps
	 * the bits at indices i and j.
	 */
	public static long swapBits(final long x, final int i, final int j) {
		if (j <= i || i < 0 || j > 63) {
			return x;
		}

		final long mask1 = 1L << i;
		final long mask2 = 1L << j;

		final long mask = mask1 | mask2;

		return x ^ mask;
	}

	/*
	 * Q5.5: Implement a method that takes as input a set S of distinct elements, and prints the power ste of S. Print
	 * subsets one per line, with elements separated by commas. Note: The power set is the set of all subsets of S,
	 * including both the empty set Ø and S itself.
	 */
	public static void powerSet(Set<Integer> set) {
		if (set == null) {
			LOGGER.info("Null input.");

			return;
		}

		LOGGER.info("{}.", set.toString());
		LOGGER.info("{" + "Ø" + "}");

		final Integer[] elements = set.toArray(new Integer[] {});

		final int numElements = elements.length;
		final StringBuilder identitySet = new StringBuilder();

		for (int currentIdx = 0; currentIdx < numElements; currentIdx++) {
			int currentElement = elements[currentIdx];
			identitySet.append(currentElement);

			if (currentIdx != numElements - 1) {
				identitySet.append(", ");
			}

			for (int lookAheadIdx = currentIdx + 1; lookAheadIdx < numElements; lookAheadIdx++) {
				int lookAheadElement = elements[lookAheadIdx];

				LOGGER.info("{" + "{}, {}" + "}.", currentElement, lookAheadElement);

			}
		}

		LOGGER.info("{" + "{}" + "}.", identitySet.toString());
	}

	/*
	 * Q5.6: Implement string/integer inter-conversion functions. Your code should handle negative integers. It should
	 * throw an exception if the string does not encode an integer, e.g., "123abc" is not a valid encoding.
	 */
	public static int stringToInt(String s) {
		final String regex = "^-?\\d+";

		if (s == null || !s.matches(regex)) {
			throw new NumberFormatException(s + " is not a valid integer encoding.");
		}

		return parseInt(s, 10, s.length() - 1);
	}

	/*
	 * Q5.7: Write a function that performs base conversion. Specifically, the input is an integer base b1, a string s,
	 * representing an integer x in base b1, and another integer base b2; the output is the string representing the
	 * integer x in base b2. Assume 2 <= b1,b2 <= 16. Use "A" to represent 10, "B" for 11, ...,and "F" for 15.
	 */
	public static String changeBase(String s, int b1, int b2) {
		if (s == null || s.isEmpty()) {
			return s;
		}

		int x = parseInt(s, b1, s.length() - 1);
		
		StringBuilder xInB2 = convertToBase(x, b2);
		LOGGER.info("Received {} base {}, returning {} base {}.", s, b1, xInB2.toString(), b2);

		return xInB2.toString();
	}

	private static int parseInt(String x, int base, int idx) {
		if (idx < 0) {
		    return 0;
		}

		final char ch = x.charAt(idx);

		if (ch == '-') {
			return -1;
		} 
		
		final int idxOfZeroInASCII = 48;
		
		int intVal = x.charAt(idx) - idxOfZeroInASCII;
		int sum = (int) (intVal * Math.pow(base, x.length() - 1 - idx));
		
		int val = parseInt(x, base, --idx);

		if (val != -1) {
		    sum += Math.abs(val);
		}
		if (val < 0) {
		    sum *= -1;
		}

		return sum;
	}

	private static StringBuilder convertToBase(int x, int b) {
		StringBuilder buffer = new StringBuilder();
		buffer.insert(0, encode(x % b));
		
		if (x >= b) {
		    buffer.insert(0, convertToBase(x / b, b));
		}

		return buffer;
	}

	private static String encode(int val) {
		String encodedVal = null;

		switch (val) {
		case 10:
			encodedVal = "A";
			break;
		case 11:
			encodedVal = "B";
			break;
		case 12:
			encodedVal = "C";
			break;
		case 13:
			encodedVal = "D";
			break;
		case 14:
			encodedVal = "E";
			break;
		case 15:
			encodedVal = "F";
			break;
		default:
			encodedVal = String.valueOf(val);
			break;
		}

		return encodedVal;
	}
}

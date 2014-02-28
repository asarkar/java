package name.abhijitsarkar.programminginterviews.bits;

public class PracticeQuestionsCh5 {
	/*
	 * Q5.1: How would you go about computing the parity of a very large number of 64-bit nonnegative integers?
	 */
	public long[] computeParities(final long[] input) {
		final int numInput = (input == null || input.length == 0) ? 0 : input.length;

		if (numInput == 0) {
			return input;
		}

		final int storage = (int) ((numInput >> 6) + 1);
		final long[] parities = new long[storage];

		if (isOddParity(input[0])) {
			parities[0] ^= 1;
		}

		for (int i = 1; i < numInput; i++) {
			final int section = i >> 6;
			final int idx = (i % 64) - 1;

			if (isOddParity(input[i])) {
				parities[section] ^= (1 << idx);
			}
		}

		return parities;
	}

	private boolean isOddParity(long num) {
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
	public long swapBits(final long x, final int i, final int j) {
		if ((j <= i) || (i < 0) || (j > 63)) {
			return x;
		}

		final long mask1 = 1L << i;
		final long mask2 = 1L << j;

		final long mask = mask1 | mask2;

		return x ^ mask;
	}
}

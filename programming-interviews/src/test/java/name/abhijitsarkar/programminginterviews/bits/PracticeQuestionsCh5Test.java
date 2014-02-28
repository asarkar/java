package name.abhijitsarkar.programminginterviews.bits;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsCh5Test {
	private PracticeQuestionsCh5 pqCh5 = new PracticeQuestionsCh5();

	@Test
	public void testComputeParities() {
		long[] input = null;

		Assert.assertNull(pqCh5.computeParities(input));

		long[] parities = pqCh5.computeParities(new long[87]);

		Assert.assertEquals(2, parities.length);

		input = new long[2];
		input[0] = 8L;
		input[1] = 9L;

		parities = pqCh5.computeParities(input);

		Assert.assertEquals(1, parities.length);
		Assert.assertEquals(1, parities[0]);
	}

	@Test
	public void testSwapBits() {
		Assert.assertEquals(2, pqCh5.swapBits(2, 1, 1));
		Assert.assertEquals(2, pqCh5.swapBits(2, -1, 1));
		Assert.assertEquals(2, pqCh5.swapBits(2, 1, 65));
		Assert.assertEquals(1, pqCh5.swapBits(2, 0, 1));
		Assert.assertEquals(14, pqCh5.swapBits(7, 0, 3));
	}
}

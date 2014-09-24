package name.abhijitsarkar.codinginterview.arraysnstrings;

import static name.abhijitsarkar.codinginterview.datastructure.arraysnstrings.PracticeQuestions.rotateImage;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PracticeQuestionsTest {
	@Test
	public void testRotateImage() {
		int[][] image = { { 1, 2, 3, 4, 5, 6 }, { 7, 8, 9, 10, 11, 12 }, { 13, 14, 15, 16, 17, 18 },
				{ 19, 20, 21, 22, 23, 24 } };

		int[][] rotatedImage = rotateImage(image);

		assertEquals(6, rotatedImage.length);
		assertEquals(4, rotatedImage[0].length);

		assertArrayEquals(new int[] { 19, 13, 7, 1 }, rotatedImage[0]);
		assertArrayEquals(new int[] { 20, 14, 8, 2 }, rotatedImage[1]);
		assertArrayEquals(new int[] { 21, 15, 9, 3 }, rotatedImage[2]);
		assertArrayEquals(new int[] { 22, 16, 10, 4 }, rotatedImage[3]);
		assertArrayEquals(new int[] { 23, 17, 11, 5 }, rotatedImage[4]);
		assertArrayEquals(new int[] { 24, 18, 12, 6 }, rotatedImage[5]);
	}
}

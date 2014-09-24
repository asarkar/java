package name.abhijitsarkar.codinginterview.datastructure.arraysnstrings;

import java.util.stream.IntStream;

public class PracticeQuestions {
	/*
	 * Given an image, rotate it by 90°.
	 */
	
	/*
	 * Input
	 * * * ^ * * *
	 * * * | * * *
	 * * * | * * *
	 * * * | * * *
	 *
	 * Output
	 * * * *
	 * * * *
	 * * * *
	 * --- >
	 * * * *
	 * * * *
	 * * * *
	 */
	
	/*
	 * Assuming clockwise rotation and allowing for non-square images. If square, can be rotated in situ.
	 * 0,0 -> 0,3
	 * 0,1 -> 1,3
	 *
	 * 0,5 -> 5,3
	 * 1,0 -> 0,2
	 * 1,1 -> 1,2
	 *
	 * 1,5 -> 5,2
	 *
	 * 3,0 -> 0,0
	 * 3,1 -> 1,0
	 *
	 * 3,5 -> 5,0
	 */
	public static int[][] rotateImage(int[][] image) {
		final int numRows = image.length;
		final int numColumns = image[0].length;
		final int[][] rotatedImage = new int[numColumns][numRows];
		
		IntStream.range(0, numRows).forEach(rowIdx -> {
			IntStream.range(0, numColumns).forEach(colIdx -> {
				rotatedImage[colIdx][numRows - rowIdx - 1] = image[rowIdx][colIdx];
			});	
		});
		
		return rotatedImage;
	}
}

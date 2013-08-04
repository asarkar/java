package name.abhijitsarkar.bingo.businesslogic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Core class that calls a Bingo number. Also keep track of which numbers have
 * been called.
 * 
 * @author Abhijit Sarkar
 */
public class BingoNumberCaller {

	/* The maximum number upto which a Bingo number can go */
	static final int LARGEST_NUMBER = 75;
	static final String BINGO = "BINGO";
	/* How many numbers can go with each character in the word BINGO */
	static final int BUCKET_SIZE = 15;
	/*
	 * A pool of pre-generated Bingo numbers. This class randomly calls out a
	 * number from the pool. Numbers are never removed from the pool even if
	 * called
	 */
	public static final String[][] BINGO_NUMBER_POOL = new String[BUCKET_SIZE][BINGO
			.length()];
	/* A list of the Bingo numbers that have been called */
	private Set<String> numbersCalled = new HashSet<String>();
	private Random generator = new Random();

	/* Initialize the Bingo number pool */
	static {
		final int len = BINGO.length();
		for (int i = 0; i < BUCKET_SIZE; i++) {
			for (int j = 0; j < len; j++) {
				BINGO_NUMBER_POOL[i][j] = BINGO.substring(j, j + 1)
						+ (j * BUCKET_SIZE + i + 1);
			}
		}
	}

	/**
	 * Calls a Bingo number randomly from the pool
	 * 
	 * @return A random Bingo number from the pool
	 * @throws NoMoreBingoNumbersException
	 *             If there are no more Bingo numbers in the pool
	 */
	public String callBingoNumber() throws NoMoreBingoNumbersException {
		if (numbersCalled.size() == LARGEST_NUMBER) {
			throw new NoMoreBingoNumbersException(
					"All possible Bingo numbers have been called.");
		}
		/*
		 * Generate a random number between 1 and LARGEST_NUMBER and then
		 * translate it into the index of a Bingo number from the pool
		 */
		final int randomBingoNumberIndex = generator.nextInt(LARGEST_NUMBER);
		final String bingoNumber = BINGO_NUMBER_POOL[randomBingoNumberIndex
				% BUCKET_SIZE][randomBingoNumberIndex / BUCKET_SIZE];

		/*
		 * If the Bingo number has not been called before, return it. Else call
		 * another Bingo number.
		 */
		if (numbersCalled.add(bingoNumber)) {
			return bingoNumber;
		}

		return callBingoNumber();
	}

	/**
	 * 
	 * @return A list of all Bingo numbers that have been called so far
	 */
	public Set<String> getNumbersCalled() {
		return Collections.unmodifiableSet(numbersCalled);
	}

	/**
	 * Tests whether a Bingo number has been called before or not
	 * 
	 * @param bingoNumber
	 *            The Bingo number that is tested
	 * @return true if the Bingo number has been called before, false otherwise
	 */
	public boolean isCalled(String bingoNumber) {
		return numbersCalled.contains(bingoNumber);
	}
}

package name.abhijitsarkar.codinginterview.zillow;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Assignment {
	/*
	 * Given a string, write a routine that converts the string to a long, without using the built in functions that
	 * would do this. Describe what (if any) limitations the code has.
	 * 
	 * Solution: This implementation assumes that the string is ASCII encoded. It doesn't handle surrogate characters.
	 * It uses Java 8 for brevity but it's easy to use the same algorithm in any version of Java.
	 */
	public long stringToLong(final String s) {
		final int asciiDecimalStart = 48;
		final AtomicInteger index = new AtomicInteger(0);
		final AtomicLong number = new AtomicLong(0);
		final int len = s.length();

		if (s == null || s.isEmpty()) {
			throw new IllegalArgumentException(s + " can't be converted to a long.");
		}

		s.chars().map(i -> (i - asciiDecimalStart)).forEach(i -> {
			if (i < 0 || i > 9) {
				throw new IllegalArgumentException(s + " can't be converted to a long.");
			}

			number.set((long) (number.get() + i * Math.pow(10, len - index.addAndGet(1))));
		});

		return number.get();
	}
}

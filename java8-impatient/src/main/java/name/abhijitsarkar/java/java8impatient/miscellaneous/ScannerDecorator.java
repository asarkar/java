package name.abhijitsarkar.java.java8impatient.miscellaneous;

import static java.lang.System.lineSeparator;
import static java.util.Spliterator.NONNULL;
import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.intStream;
import static java.util.stream.StreamSupport.stream;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Scanner;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Q9: Write methods that turn a {@code Scanner} into a stream of words, lines,
 * integers, or double values. Hint: Look at the source code for
 * {@code BufferedReader.lines}.
 * 
 * @author Abhijit Sarkar
 *
 */
public class ScannerDecorator {
    public static Stream<String> lines(final Scanner scanner) {
	final Iterator<String> iter = new ScannerIterator(
		scanner.useDelimiter(lineSeparator()));

	return stream(spliteratorUnknownSize(iter, ORDERED | NONNULL), false);
    }

    public static Stream<String> words(final Scanner scanner) {
	return stream(
		spliteratorUnknownSize(new ScannerIterator(scanner), ORDERED
			| NONNULL), false);
    }

    public static IntStream ints(final Scanner scanner) {
	return intStream(
		spliteratorUnknownSize(new ScannerIntIterator(scanner), ORDERED
			| NONNULL), false);
    }

    private static class ScannerIterator implements Iterator<String> {
	private final Scanner scanner;

	public ScannerIterator(final Scanner scanner) {
	    this.scanner = scanner;
	}

	@Override
	public boolean hasNext() {
	    return scanner.hasNext();
	}

	@Override
	public String next() {
	    return scanner.next();
	}
    };

    private static class ScannerIntIterator implements PrimitiveIterator.OfInt {
	private final Scanner scanner;

	public ScannerIntIterator(final Scanner scanner) {
	    this.scanner = scanner;
	}

	/* This is the entry point. */
	@Override
	public void forEachRemaining(final IntConsumer action) {
	    while (hasNext()) {
		final int nextInt = nextInt();

		if (nextInt != Integer.MIN_VALUE) {
		    action.accept(nextInt);
		} else {
		    break;
		}
	    }
	}

	@Override
	public boolean hasNext() {
	    return scanner.hasNext();
	}

	@Override
	public Integer next() {
	    return nextInt();
	}

	@Override
	public int nextInt() {
	    /*
	     * Horizon of 0 searches till a match is found or end of input is
	     * reached.
	     */
	    final String nextToken = scanner.findWithinHorizon("\\d+", 0);

	    if (nextToken != null) {
		return Integer.parseInt(nextToken);
	    }

	    return Integer.MIN_VALUE;
	}
    };
}

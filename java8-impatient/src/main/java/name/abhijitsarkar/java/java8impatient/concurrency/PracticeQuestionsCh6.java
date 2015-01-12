/*******************************************************************************
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software, 
 * and is also available at http://www.gnu.org/licenses.
 *******************************************************************************/
package name.abhijitsarkar.java.java8impatient.concurrency;

import static java.lang.Runtime.getRuntime;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.find;
import static java.nio.file.Files.lines;
import static java.util.Arrays.parallelPrefix;
import static java.util.Arrays.parallelSetAll;
import static java.util.Collections.unmodifiableMap;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.ForkJoinPool.commonPool;
import static java.util.function.BinaryOperator.maxBy;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PracticeQuestionsCh6 {
    public static final Logger LOGGER = LoggerFactory
	    .getLogger(PracticeQuestionsCh6.class);

    /**
     * Q1: Write a program that keeps track of the longest string that is
     * observed by a number of threads. Use an {@code AtomicReference} and an
     * appropriate accumulator.
     * 
     * @param observed
     *            Longest string.
     * @param x
     *            String value to be compared to the longest value.
     * @return Longest string.
     */
    public static String updateLongestString(
	    final AtomicReference<String> observed, final String x) {

	final String longestString = observed.accumulateAndGet(x,
		maxBy((currentValue, givenUpdate) -> {
		    LOGGER.info("Received current value: {}, given update: {}",
			    currentValue, givenUpdate);

		    return Integer.compare(currentValue.length(),
			    givenUpdate.length());
		}));

	LOGGER.info("New observed: {}.", longestString);

	return longestString;
    }

    /**
     * Q2: Does a {@code LongAdder} help with yielding a sequence of increasing
     * IDs? Why or why not?
     * <p>
     * <b>Ans:</b> No, it's not helpful. From the Javadoc of method {@code sum},
     * <i>"The returned value is NOT an atomic snapshot."</i>. It appears that
     * {@code LongAdder} is designed so that multiple threads update a common
     * sum that is used for purposes such as collecting statistics, but the sum
     * is expected to be calculated outside of the multithreaded code.
     */
    public void existsOnlyToGlorifyJavadoc() {
    }

    /**
     * Q3: Generate 1,000 threads, each of which increments a counter 100,000
     * times. Compare the performance of using {@code AtomicLong} versus
     * {@code LongAdder}.
     */
    public static void incrementUsingAtomicLong(final AtomicLong counter) {
	run(() -> {
	    counter.getAndIncrement();
	});
    }

    /**
     * Q3: Generate 1,000 threads, each of which increments a counter 100,000
     * times. Compare the performance of using {@code AtomicLong} versus
     * {@code LongAdder}.
     */
    public static void incrementUsingLongAdder(final LongAdder counter) {
	run(() -> {
	    counter.increment();
	});
    }

    @SuppressWarnings("unchecked")
    private static void run(final Runnable task) {
	final Instant start = Instant.now();

	LOGGER.info("Starting execution.");

	commonPool().invokeAll(
		(Collection<? extends Callable<Void>>) IntStream.range(0, 1000)
			.mapToObj(i -> {
			    return new Callable<Void>() {
				@Override
				public Void call() {
				    for (int j = 0; j < 100000; j++) {
					try {
					    task.run();
					} catch (Exception e) {
					    LOGGER.error(
						    "Something went wrong. Get the hell outta here.",
						    e);

					    break;
					}
				    }

				    return null;
				}
			    };
			}).collect(toList()));

	LOGGER.info("Finished execution.");

	final Instant end = Instant.now();

	final Duration runningTime = Duration.between(start, end);

	LOGGER.info("Running time: {} seconds {} nanoseconds.",
		runningTime.getSeconds(), runningTime.getNano());
    }

    /**
     * Q5: Write an application in which multiple threads read all words from a
     * collection of files. Use a {@code ConcurrentHashMap<String, Set<File>>}
     * to track in which files each word occurs. Use the {@code merge} method to
     * update the map.
     * 
     * @param path
     *            Input File or root directory.
     * @return Map containing entries for each word vs the files in which it
     *         occurs.
     * @throws IOException
     */
    public static Map<String, Set<File>> reverseIndexUsingMerge(final Path path)
	    throws IOException {
	final ConcurrentHashMap<String, Set<File>> map = new ConcurrentHashMap<>();

	final BiConsumer<? super String, ? super Set<File>> action = (key,
		value) -> map.merge(key, value, (existingValue, newValue) -> {
	    LOGGER.info("Received key: {}, existing value: {}, new value: {}.",
		    key, existingValue, newValue);

	    newValue.addAll(existingValue);

	    return newValue;
	});

	/*
	 * Use find instead of walk, it's a little better performant. Also
	 * design wise, it's better to work with just what we want instead of
	 * picking everything and then throwing away directories.
	 */
	commonPool().invokeAll(
		find(path, 1,
			(p, fileAttributes) -> fileAttributes.isRegularFile())
			.map(p -> new ReverseIndex(p, action))
			.collect(toList()));

	return unmodifiableMap(map);
    }

    private static class ReverseIndex implements Callable<Void> {
	private final Path p;
	private final BiConsumer<? super String, ? super Set<File>> action;

	private static final Pattern AROUND_WHITESPACE = compile("\\s");

	private ReverseIndex(final Path p,
		final BiConsumer<? super String, ? super Set<File>> action) {
	    this.p = p;
	    this.action = action;
	}

	@Override
	public Void call() throws Exception {
	    reverseIndex().forEach(action);

	    return null;
	}

	// final Consumer<? super String> debugAction = word -> {
	// final int count = p.getNameCount();
	//
	// LOGGER.info("File: {}, word: {}",
	// p.getName(count > 0 ? count - 1 : 0), word);
	// };

	private Map<String, Set<File>> reverseIndex() {
	    /* File stream needs to be closed. */
	    try (Stream<String> lines = lines(p, UTF_8)) {
		return lines.flatMap(AROUND_WHITESPACE::splitAsStream)
		// .peek(debugAction)
			.collect(
				groupingBy(String::toString,
					mapping(word -> p.toFile(), toSet())));
	    } catch (IOException e) {
		LOGGER.error("Something went wrong. Get the hell outta here.",
			e);

		throw new UncheckedIOException(e);
	    }
	}
    }

    /**
     * Q7: In a {@code ConcurrentHashMap<String, Long>}, find the key with the
     * maximum value (breaking ties arbitrarily).
     * 
     * @param map
     *            Map.
     * @return Key that has the maximum value.
     */
    public static String getKeyWithMaxValue(
	    final ConcurrentHashMap<String, Long> map) {
	final int parallelismThreshold = getRuntime().availableProcessors();

	return map.reduceEntries(
		parallelismThreshold,
		(e1, e2) -> Long.compare(e1.getValue(), e2.getValue()) > 0 ? e1
			: e2).getKey();
    }

    /**
     * You can use the {@code parallelPrefix} method to parallelize the
     * computation of Fibonacci numbers. We use the fact that the nth Fibonacci
     * number is the top left coefficient of F<sup>n</sup>, where F = (1, 1, 1,
     * 0). Make an array filled with 2 x 2 matrices. Define a {@code Matrix}
     * class with a multiplication method, use {@code parallelSetAll} to make an
     * array of matrices, and use {@code parallelPefix} to multiply them.
     * <p>
     * Fibonacci series: 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, ...
     * 
     * @param n
     *            The index of the Fibonacci number to return.
     * @return The nth Fibonacci number.
     */
    public static int fibonacci(final int n) {
	if (n <= 2) {
	    return 1;
	}

	final TwoByTwoMatrix[] matrices = new TwoByTwoMatrix[n - 1];

	parallelSetAll(matrices, i -> new TwoByTwoMatrix());

	LOGGER.info("Matrix array after parallelSetAll.\n");
	printMatrices(matrices);

	parallelPrefix(matrices, TwoByTwoMatrix::multiply);

	LOGGER.info("Matrix array after parallelPrefix.\n");
	printMatrices(matrices);

	/*
	 * The last matrix in the array is the product of multiplying all
	 * elements in the array.
	 */
	return matrices[n - 2].topLeftCoefficient;
    }

    private static void printMatrices(final TwoByTwoMatrix[] matrices) {
	for (int i = 0; i < matrices.length; i++) {
	    LOGGER.info("[{}] -> {}.", i, matrices[i]);
	}
    }

    private static class TwoByTwoMatrix {
	private int topLeftCoefficient;
	private int topRightCoefficient;
	private int bottomLeftCoefficient;
	private int bottomRightCoefficient;

	TwoByTwoMatrix() {
	    this(1, 1, 1, 0);
	}

	TwoByTwoMatrix(final int topLeftCoefficient,
		final int topRightCoefficient, final int bottomLeftCoefficient,
		final int bottomRightCoefficient) {
	    this.topLeftCoefficient = topLeftCoefficient;
	    this.topRightCoefficient = topRightCoefficient;
	    this.bottomLeftCoefficient = bottomLeftCoefficient;
	    this.bottomRightCoefficient = bottomRightCoefficient;
	}

	/* https://www.youtube.com/watch?v=xMNOI8YRQIo */
	TwoByTwoMatrix multiply(final TwoByTwoMatrix other) {
	    final int productTopLeftCoefficient = topLeftCoefficient
		    * other.topLeftCoefficient + topRightCoefficient
		    * other.bottomLeftCoefficient;
	    final int productTopRightCoefficient = topLeftCoefficient
		    * other.topRightCoefficient + topRightCoefficient
		    * other.bottomRightCoefficient;
	    final int productBottomLeftCoefficient = bottomLeftCoefficient
		    * other.topLeftCoefficient + bottomRightCoefficient
		    * other.bottomLeftCoefficient;
	    final int productBottomRightCoefficient = bottomLeftCoefficient
		    * other.topRightCoefficient + bottomRightCoefficient
		    * other.bottomRightCoefficient;

	    return new TwoByTwoMatrix(productTopLeftCoefficient,
		    productTopRightCoefficient, productBottomLeftCoefficient,
		    productBottomRightCoefficient);
	}

	@Override
	public String toString() {
	    return "TwoByTwoMatrix [[0,0]=" + topLeftCoefficient + ", [0,1]="
		    + topRightCoefficient + ", [1,0]=" + bottomLeftCoefficient
		    + ", [1,1]=" + bottomRightCoefficient + "]";
	}
    }

    /**
     * Q11: Write a method
     * 
     * <pre>
     * <code>
     * public static <T> CompletableFuture<T> repeat(
     *     Supplier<T> action, Predicate<T> until)
     * </code>
     * </pre>
     * 
     * that asynchronously repeats the action until it produces a value that is
     * accepted by the {@code until} function, which should also run
     * asynchronously. Test with a function that reads a
     * {@code java.net.PasswordAuthentication} from the console, and a function
     * that simulates a validity check by sleeping for a second and then
     * checking that the password is "{@code secret}". Hint: Use recursion.
     * 
     * @param action
     *            Action that runs repeatedly.
     * @param until
     *            Condition that tests the value produced by action.
     * @return {@code CompletableFuture<T>} where T is the value that passed the
     *         test.
     */
    public static <T> CompletableFuture<T> repeat(final Supplier<T> action,
	    final Predicate<T> until) {
	final CompletableFuture<T> futureAction = supplyAsync(action);

	CompletableFuture<T> future = futureAction.thenApplyAsync(until::test)
		.thenCompose(
			isMatchFound -> isMatchFound ? futureAction : repeat(
				action, until));

	future.join();

	return future;

    }
}

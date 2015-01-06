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

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.lines;
import static java.util.function.BinaryOperator.maxBy;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

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

	final String longestString = observed.accumulateAndGet(x, maxBy((
		currentValue, givenUpdate) -> {
	    LOGGER.info("Received current value: {}, given update: {}",
		    currentValue, givenUpdate);

	    return currentValue.length() - givenUpdate.length();
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

	new ForkJoinPool()
		.invokeAll((Collection<? extends Callable<Void>>) IntStream
			.range(0, 1000).mapToObj(i -> {
			    return new Callable<Void>() {
				@Override
				public Void call() {
				    for (int j = 0; j < 100000; j++) {
					try {
					    task.run();
					} catch (Exception e) {
					    LOGGER.error("Something went wrong. Get the hell outta here.");

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

    private static void reverseIndex(final Path p) {
	try {
	    Map<String, Set<File>> map = lines(p, UTF_8).collect(
		    groupingBy(String::toString,
			    mapping((String word) -> p.toFile(), toSet())));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}

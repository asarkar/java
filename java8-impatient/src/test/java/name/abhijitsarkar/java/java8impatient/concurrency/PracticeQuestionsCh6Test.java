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

import static java.lang.Math.min;
import static java.lang.Runtime.getRuntime;
import static java.lang.System.gc;
import static java.lang.Thread.currentThread;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.joining;
import static name.abhijitsarkar.java.java8impatient.concurrency.PracticeQuestionsCh6.getKeyWithMaxValue;
import static name.abhijitsarkar.java.java8impatient.concurrency.PracticeQuestionsCh6.incrementUsingAtomicLong;
import static name.abhijitsarkar.java.java8impatient.concurrency.PracticeQuestionsCh6.incrementUsingLongAdder;
import static name.abhijitsarkar.java.java8impatient.concurrency.PracticeQuestionsCh6.reverseIndexUsingMerge;
import static name.abhijitsarkar.java.java8impatient.concurrency.PracticeQuestionsCh6.updateLongestString;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.LongStream;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh6Test {
    public static final Logger LOGGER = LoggerFactory
	    .getLogger(PracticeQuestionsCh6Test.class);

    @Test
    public void testUpdateLongestString() {
	final String[] words = new String[] { "Java 8", "Java 8 is Awesome!",
		"Java 8 is the Best thing Since Sliced Bread!",
		"Java 8 Changes Everything!" };
	final int len = words.length;
	final int stopAfter = 100;

	final AtomicReference<String> longestString = new AtomicReference<>(
		words[0]);
	final AtomicInteger count = new AtomicInteger(1);

	class UpdateLongestStringTask extends RecursiveAction {
	    private static final long serialVersionUID = -2288401002001447054L;

	    private int id = -1;

	    private UpdateLongestStringTask(final int id) {
		this.id = id;
	    }

	    @Override
	    protected void compute() {
		LOGGER.info("Executing task #: {}.", id);

		if (count.get() >= stopAfter) {
		    return;
		}

		final ForkJoinTask<Void> task = new UpdateLongestStringTask(
			count.incrementAndGet()).fork();

		updateLongestString(longestString, words[randomIndex()]);

		task.join();
	    }

	    private int randomIndex() {
		/*
		 * From the Javadoc: "Instances of java.util.Random are
		 * threadsafe. However, the concurrent use of the same
		 * java.util.Random instance across threads may encounter
		 * contention and consequent poor performance. Consider instead
		 * using ThreadLocalRandom in multithreaded designs."
		 */
		return ThreadLocalRandom.current().nextInt(len);
	    }
	}

	/* Just because we can. */
	final int parallelism = min(getRuntime().availableProcessors(), 4);

	new ForkJoinPool(parallelism).invoke(new UpdateLongestStringTask(count
		.get()));
    }

    @Ignore("Takes too long")
    public void testIncrementUsingAtomicLong() {
	LOGGER.info("Warming up\n.");

	/* Warm up */
	for (int i = 0; i < 5; i++) {
	    incrementUsingAtomicLong(new AtomicLong(0));
	}

	LOGGER.info("Rest and cleanup\n.");

	restAndCleanup();

	LOGGER.info("Do it now!\n");

	AtomicLong counter = new AtomicLong(0);

	incrementUsingAtomicLong(counter);

	assertEquals(100000000, counter.get());
    }

    @Ignore("Takes too long")
    public void testIncrementUsingLongAdder() {
	LOGGER.info("Warming up\n.");

	/* Warm up */
	for (int i = 0; i < 5; i++) {
	    incrementUsingLongAdder(new LongAdder());
	}

	LOGGER.info("Rest and cleanup\n.");

	restAndCleanup();

	LOGGER.info("Do it now!\n");

	LongAdder counter = new LongAdder();

	incrementUsingLongAdder(counter);

	assertEquals(100000000, counter.longValue());
    }

    @SuppressWarnings("static-access")
    private void restAndCleanup() {
	gc();

	try {
	    currentThread().sleep(2000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void testReverseIndexUsingMerge() throws IOException,
	    URISyntaxException {
	Path p = get(getClass().getResource("/ch6").toURI());

	Map<String, Set<File>> reverseIndex = reverseIndexUsingMerge(p);

	String filesContainingTheWordJava = joinValues("Java", reverseIndex);

	assertEquals("f1.txt,f2.txt,f3.txt", filesContainingTheWordJava);

	String filesContainingTheWordIs = joinValues("is", reverseIndex);

	assertEquals("f1.txt,f2.txt", filesContainingTheWordIs);
    }

    private String joinValues(String key, Map<String, Set<File>> map) {
	if (!map.containsKey(key)) {
	    throw new NoSuchElementException("No entry exists for key: " + key);
	}

	return map.get(key).stream().map(File::getName).sorted()
		.collect(joining(","));
    }

    @Test
    public void testGetKeyWithMaxValue() {
	ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

	LongStream.range(1, 10).forEach(l -> map.put(String.valueOf(l), l));

	String key = getKeyWithMaxValue(map);

	assertEquals("9", key);
    }
}

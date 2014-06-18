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
package name.abhijitsarkar.java.java8impatient.stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh2 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh2.class);

	/**
	 * Q2: Verify that asking for the first five long words does not call the {@code filter} method once the fifth long
	 * word has been found. Simply log each method call.
	 * 
	 * @param words
	 *            Array of words.
	 * @param longWordLength
	 *            The length beyond which a word is considered long.
	 * @param limit
	 *            Number of long words to find.
	 * 
	 * @return Number of long words found.
	 */
	/*
	 * To aid testing, the code deviates a little from the requirement. The number of long words to find is customizable
	 * and so is the length beyond which a word is considered long. Also instead of simply logging each method call as
	 * the question asks for, it accumulates the count and returns it.
	 */
	public static Optional<Integer> lazy(String[] words, int longWordLength, int limit) {
		AtomicInteger count = new AtomicInteger(0);
		Stream.of(words).filter((str) -> str.length() > longWordLength).limit(limit)
				.forEach((str) -> count.getAndIncrement());

		return Optional.of(count.get());
	}

	/**
	 * Q5: Using {@code Stream.iterate}, make an infinite stream of random numbers - not by calling {@code Math.random}
	 * but by directly implementing a <a href="http://en.wikipedia.org/wiki/Linear_congruential_generator">linear
	 * congruential generator</a>. In such a generator, you start with <code>x<sub>0</sub> = seed</code> and then
	 * produce <code>x<sub>n+1</sub> = (ax<sub>0</sub> + c) % m</code>, for appropriate values of {@code a}, {@code c},
	 * and {@code m}. You should implement a method with parameters {@code a}, {@code c}, {@code m}, and {@code seed}
	 * that yields a {@code Stream<Long>}. Try out {@code a = 25214903917}, {@code c = 11}, and
	 * <code> m = 2<sup>48</sup></code>.
	 * 
	 * @param a
	 *            The multiplier
	 * @param c
	 *            The increment
	 * @param m
	 *            The modulus
	 * @param seed
	 * @return An infinite stream of random numbers.
	 */

	/* Note that the return Stream is not closed. It is not a good idea to depend on the caller to close the Stream. */
	public static Stream<Long> infiniteRandomStream(long multiplier, long increment, long modulus, long seed) {
		return Stream.iterate(seed, (x) -> (multiplier * x + increment) % modulus);
	}

	/**
	 * The {@code characterStream} method in Section 2.3, "The filter, map and flatMap methods", was a bit clumsy, first
	 * filling an array list and then turning it into a stream. Write a stream-based one-liner instead.
	 * 
	 * @param s
	 *            The string to be converted to a Character stream.
	 * @return Character stream
	 */

	/* Note that the return Stream is not closed. It is not a good idea to depend on the caller to close the Stream. */
	public static Stream<Character> asCharacterStream(String s) {
		return IntStream.range(0, s.length()).mapToObj((index) -> s.charAt(index));
	}

	/**
	 * Write a method {@code public static <T> Stream<T> zip(Stream<T> first, Stream<T> second)} that alternates
	 * elements from the streams {@code first} and {@code second}, stopping when one of them runs out of elements.
	 * 
	 * @param first
	 *            First stream.
	 * @param second
	 *            Second stream.
	 * @return Stream of alternating elements from first and second.
	 */
	public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
		Iterator<T> it1 = first.iterator();
		Iterator<T> it2 = second.iterator();
		Stream<T> zipStream = Stream.empty();

		while (it1.hasNext() && it2.hasNext()) {
			zipStream = Stream.concat(zipStream, Stream.of(it1.next(), it2.next()));
		}

		return zipStream;
	}

	/**
	 * Q9: Join all elements in a {@code Stream<ArrayList<T>>} to one {@code <ArrayList<T>}.
	 * 
	 * @param stream
	 *            Stream of ArrayList<T>.
	 * @return ArrayList<T>.
	 */
	public static <T> List<T> joinUsingFlatMap(Stream<List<T>> stream) {
		try (Stream<T> flattenedStream = stream.flatMap((list) -> list.stream())) {
			return flattenedStream.collect(Collectors.toList());
		}
	}

	/**
	 * Q9: Join all elements in a {@code Stream<ArrayList<T>>} to one {@code ArrayList<T>}.
	 * 
	 * @param stream
	 *            Stream of ArrayList<T>.
	 * @return ArrayList<T>.
	 */
	public static <T> Optional<List<T>> joinUsingReduce1(Stream<List<T>> stream) {
		return stream.reduce((firstListElement, nextListElement) -> Stream.concat(firstListElement.stream(),
				nextListElement.stream()).collect(Collectors.toList()));
	}

	/**
	 * Q9: Join all elements in a {@code Stream<ArrayList<T>>} to one {@code ArrayList<T>}.
	 * 
	 * @param stream
	 *            Stream of ArrayList<T>.
	 * @return ArrayList<T>.
	 */
	public static <T> List<T> joinUsingReduce2(Stream<List<T>> stream) {
		return stream.reduce(
				new ArrayList<T>(),
				(firstListElement, nextListElement) -> Stream.concat(firstListElement.stream(),
						nextListElement.stream()).collect(Collectors.toList()));
	}

	/**
	 * Q9: Join all elements in a {@code Stream<ArrayList<T>>} to one {@code ArrayList<T>}.
	 * 
	 * @param stream
	 *            Stream of ArrayList<T>.
	 * @return ArrayList<T>.
	 */
	public static <T> List<T> joinUsingReduce3(Stream<List<T>> stream) {
		BinaryOperator<List<T>> func = (firstListElement, nextListElement) -> Stream.concat(firstListElement.stream(),
				nextListElement.stream()).collect(Collectors.toList());

		/*
		 * The 2nd argument is a BiFunction which is a super interface of BinaryOperator, so a BinaryOperator can be
		 * passed instead in this case.
		 */
		return stream.reduce(new ArrayList<T>(), func, func);
	}

	/**
	 * Q10: Write a call to {@code reduce} that can be used to compute the average of a {@code Stream<Double>}. Why
	 * can't you simply compute the sum and divide by {@code count()}? <i>I guess simply computing the sum may overflow
	 * the double range?</i>
	 * 
	 * @param stream
	 *            Stream of Double.
	 * @return Average.
	 */
	public static double average(Stream<Double> stream) {
		AtomicLong numElements = new AtomicLong(0);

		/* http://en.wikipedia.org/wiki/Moving_average#Cumulative moving average */
		return stream.reduce(0.0d, (avg, val) -> {

			/* Number of elements seen so far */
			long n = numElements.getAndIncrement();

			return (val + n * avg) / (n + 1);
		});

		/* Above can be achieved much easily using built-in averaging Collector */
		// return stream.collect(Collectors.averagingDouble((val) -> val));
	}

	/**
	 * Q12: Count all short words in a parallel {@code Stream<String>} as described in Section 2.13, "Parallel Streams",
	 * by updating an array of AtomicInteger. Use the atomic {@code getAndIncrement} method to safely increment each
	 * counter.
	 * 
	 * @param words
	 *            Array of words.
	 * @param shortWordMaxLength
	 *            The length below which a word is considered "short".
	 * 
	 * @return Array with the number of short words of various lengths. The index of an element in the array is the
	 *         length of the word (minus 1, due to the zero based indexing).
	 */
	public static AtomicInteger[] countShortWords(String[] words, int shortWordMaxLength) {
		AtomicInteger[] counts = new AtomicInteger[shortWordMaxLength];

		for (int i = 0; i < shortWordMaxLength; ++i) {
			counts[i] = new AtomicInteger();
		}

		Stream.of(words).parallel().forEach(word -> {
			final int wordLen = word.length();

			if (wordLen > 0 && wordLen <= shortWordMaxLength) {
				counts[wordLen - 1].incrementAndGet();
			}
		});

		return counts;
	}

	/**
	 * Q13: Repeat the preceding exercise, but filter out the short strings and use the {@code collect} method with
	 * {@code Collectors.groupingBy} and {@code Collectors.counting}.
	 * 
	 * @param words
	 *            Array of words.
	 * @param shortWordMaxLength
	 *            The length below which a word is considered "short".
	 * 
	 * @return A map where length of the word is the key and count of number of short words of that length is the value.
	 */
	public static Map<Object, Long> filterAndCountShortWords(String[] words, int shortWordMaxLength) {
		return Stream.of(words).filter(word -> {
			final int wordLen = word.length();

			return wordLen > 0 && wordLen <= shortWordMaxLength;
		}).collect(groupingBy(word -> {
			return word.length();
		}, counting()));
	}
}

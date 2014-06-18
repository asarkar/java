package name.abhijitsarkar.java.java8impatient.stream;

import static name.abhijitsarkar.java.java8impatient.stream.PracticeQuestionsCh2.asCharacterStream;
import static name.abhijitsarkar.java.java8impatient.stream.PracticeQuestionsCh2.average;
import static name.abhijitsarkar.java.java8impatient.stream.PracticeQuestionsCh2.countShortWords;
import static name.abhijitsarkar.java.java8impatient.stream.PracticeQuestionsCh2.filterAndCountShortWords;
import static name.abhijitsarkar.java.java8impatient.stream.PracticeQuestionsCh2.infiniteRandomStream;
import static name.abhijitsarkar.java.java8impatient.stream.PracticeQuestionsCh2.joinUsingFlatMap;
import static name.abhijitsarkar.java.java8impatient.stream.PracticeQuestionsCh2.joinUsingReduce1;
import static name.abhijitsarkar.java.java8impatient.stream.PracticeQuestionsCh2.joinUsingReduce2;
import static name.abhijitsarkar.java.java8impatient.stream.PracticeQuestionsCh2.joinUsingReduce3;
import static name.abhijitsarkar.java.java8impatient.stream.PracticeQuestionsCh2.lazy;
import static name.abhijitsarkar.java.java8impatient.stream.PracticeQuestionsCh2.zip;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class PracticeQuestionsCh2Test {

	@Test
	public void testLazy() {
		String[] words = { "Java8ChangesEverything", "Java8IsAwesome", "Java8Rocks" };

		int count = lazy(words, 5, 2).orElseThrow(NoSuchElementException::new);

		assertEquals(2, count);
	}

	@Test
	public void testInfiniteRandomStream() {
		long multiplier = 25214903917l;
		long increment = 11l;
		/* Long.MAX_VALUE is (2 power 63) - 1 so the cast is safe. */
		long modulus = (long) Math.pow(2, 48);
		long seed = 5l;

		try (Stream<Long> infiniteRandomStream = infiniteRandomStream(multiplier, increment, modulus, seed)) {
			/* The joining Collection can only handle String hence the need for toString. */
			String str = infiniteRandomStream.limit(5).map(Object::toString).collect(Collectors.joining(", "));

			System.out.println(str);
		}
	}

	@Test
	public void testAsCharacterStream() {
		try (Stream<Character> characterStream = asCharacterStream("Java8Rocks")) {
			String actual = characterStream.map(Object::toString).collect(Collectors.joining());

			assertEquals("Java8Rocks", actual);
		}
	}

	@Test
	public void testZip() {
		try (Stream<Integer> first = IntStream.range(1, 5).boxed();
				Stream<Integer> second = IntStream.range(5, 10).boxed();
				Stream<Integer> zippedStream = zip(first, second)) {
			String actual = zippedStream.map(Object::toString).collect(Collectors.joining(", "));

			assertEquals("1, 5, 2, 6, 3, 7, 4, 8", actual);
		}
	}

	@Test
	public void testJoinUsingFlatMap() {
		Stream<List<Integer>> stream = Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5));

		Integer[] actual = joinUsingFlatMap(stream).toArray(new Integer[] {});
		Integer[] expected = new Integer[] { 1, 2, 3, 4, 5 };

		assertArrayEquals(expected, actual);
	}

	@Test
	public void testJoinUsingReduce1() {
		Stream<List<Integer>> stream = Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5));

		Optional<List<Integer>> optional = joinUsingReduce1(stream);
		Integer[] actual = optional.orElseThrow(NoSuchElementException::new).toArray(new Integer[] {});
		Integer[] expected = new Integer[] { 1, 2, 3, 4, 5 };

		assertArrayEquals(expected, actual);
	}

	@Test
	public void testJoinUsingReduce2() {
		Stream<List<Integer>> stream = Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5));

		Integer[] actual = joinUsingReduce2(stream).toArray(new Integer[] {});
		Integer[] expected = new Integer[] { 1, 2, 3, 4, 5 };

		assertArrayEquals(expected, actual);
	}

	@Test
	public void testJoinUsingReduce3() {
		Stream<List<Integer>> stream = Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5));

		Integer[] actual = joinUsingReduce3(stream).toArray(new Integer[] {});
		Integer[] expected = new Integer[] { 1, 2, 3, 4, 5 };

		assertArrayEquals(expected, actual);
	}

	@Test
	public void testAverage() {
		Stream<Double> stream = Stream.of(1.0d, 2.0d, 3.0d);

		assertEquals(2.0d, average(stream), 0.0d);
	}

	@Test
	public void testCountShortWords() {
		String[] words = { "Java8ChangesEverything", "Java8IsAwesome", "Java8Rocks" };

		AtomicInteger[] wordLenCounts = countShortWords(words, 25);

		assertEquals(25, wordLenCounts.length);

		assertEquals(1, wordLenCounts[21].get());
		assertEquals(1, wordLenCounts[13].get());
		assertEquals(1, wordLenCounts[9].get());
	}

	@Test
	public void testFilterAndCountShortWords() {
		String[] words = { "Java8ChangesEverything", "Java8IsAwesome", "Java8Rocks" };

		Map<Object, Long> shortWords = filterAndCountShortWords(words, 25);

		assertEquals(3, shortWords.size());

		assertEquals(1, shortWords.get(22).intValue());
		assertEquals(1, shortWords.get(14).intValue());
		assertEquals(1, shortWords.get(10).intValue());
	}
}

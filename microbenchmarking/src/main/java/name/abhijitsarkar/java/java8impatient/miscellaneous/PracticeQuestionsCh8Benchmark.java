package name.abhijitsarkar.java.java8impatient.miscellaneous;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.PracticeQuestionsCh8.countLongWordsUsingStreams;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.PracticeQuestionsCh8.countLongWordsWithoutUsingStreams;
import static org.openjdk.jmh.annotations.Mode.AverageTime;
import static org.openjdk.jmh.annotations.Scope.Benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.State;

@State(Benchmark)
public class PracticeQuestionsCh8Benchmark {
    private static final int NUM_WORDS = 10000;
    private static final int LONG_WORD_MIN_LEN = 10;

    private final List<String> words = makeUpWords();

    public List<String> makeUpWords() {
	List<String> words = new ArrayList<>();
	final Random random = new Random();

	for (int i = 0; i < NUM_WORDS; i++) {
	    if (random.nextBoolean()) {
		/*
		 * Do this to avoid string interning. c.f.
		 * http://en.wikipedia.org/wiki/String_interning
		 */
		words.add(String.format("%" + LONG_WORD_MIN_LEN + "s", i));
	    } else {
		words.add(String.valueOf(i));
	    }
	}

	return words;
    }

    @Benchmark
    @BenchmarkMode(AverageTime)
    @OutputTimeUnit(MILLISECONDS)
    public int benchmarkCountLongWordsWithoutUsingStreams() {
	return countLongWordsWithoutUsingStreams(words, LONG_WORD_MIN_LEN);
    }

    @Benchmark
    @BenchmarkMode(AverageTime)
    @OutputTimeUnit(MILLISECONDS)
    public int benchmarkCountLongWordsUsingStreams() {
	return countLongWordsUsingStreams(words, LONG_WORD_MIN_LEN);
    }
}

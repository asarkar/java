package name.abhijitsarkar.java.java8impatient.lambda;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static name.abhijitsarkar.java.java8impatient.lambda.PracticeQuestionsCh3.capitalize;
import static name.abhijitsarkar.java.java8impatient.lambda.PracticeQuestionsCh3.doInParallelAsync;
import static name.abhijitsarkar.java.java8impatient.lambda.PracticeQuestionsCh3.isLoggingEnabledFor;
import static name.abhijitsarkar.java.java8impatient.lambda.PracticeQuestionsCh3.logIf;
import static name.abhijitsarkar.java.java8impatient.lambda.PracticeQuestionsCh3.map;
import static name.abhijitsarkar.java.java8impatient.lambda.PracticeQuestionsCh3.withLock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.junit.Test;

public class PracticeQuestionsCh3Test {

    @Test
    public void testCapitalize() {
	assertEquals("Java", capitalize("java"));
	assertEquals("JAVA", capitalize("jAVA"));
    }

    @Test
    public void testIsLoggingEnabledFor() {
	assertTrue(isLoggingEnabledFor("error"));
    }

    @Test
    public void testLogIfWhenTrue() {
	final boolean isEnabled = isLoggingEnabledFor("error");

	assertEquals(isEnabled,
		logIf("error", () -> true, () -> "This should be logged."));
    }

    @Test
    public void testLogIfWhenFalse() {
	assertFalse(logIf("error", () -> false,
		() -> "This shouldn't be logged."));
    }

    @Test
    public void testWithLock() throws IOException {
	ByteArrayOutputStream os = new ByteArrayOutputStream();

	withLock(new ReentrantLock(), () -> {
	    try {
		os.write("1".getBytes(UTF_8));
	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }
	});

	assertEquals("1", os.toString(UTF_8.name()));

	os.close();
    }

    @Test
    public void testDoInParallelAsyncWhenNoException() {
	Consumer<Throwable> handler = t -> t.printStackTrace();

	doInParallelAsync(newRunnable(1, 1000), newRunnable(1001, 2000),
		handler);
    }

    @Test
    public void testDoInParallelAsyncWhenException() {
	Consumer<Throwable> handler = t -> t.printStackTrace();

	doInParallelAsync(newRunnable(1, 1000), () -> {
	    throw new RuntimeException("Exception from second.");
	}, handler);
    }

    private Runnable newRunnable(final int i, final int j) {
	return () -> IntStream.range(i, j).forEach(System.out::println);
    }

    @Test
    public void testMap() {
	List<String> inputList = asList(new String[] { "1", "2" });

	List<Integer> outputList = map(inputList, s -> Integer.parseInt(s));

	assertEquals(2, outputList.size());

	assertEquals(1, outputList.get(0).intValue());
	assertEquals(2, outputList.get(1).intValue());
    }

    @Test
    public void testPairMap() {
	Pair<String> pairOfStrings = new Pair<>("1", "2");
	assertEquals(3, pairOfStrings.map(s -> Integer.valueOf(s)).stream()
		.flatMapToInt(i -> IntStream.of(i)).sum());
    }
}

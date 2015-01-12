package name.abhijitsarkar.java.java8impatient.lambda;

import static org.junit.Assert.assertEquals;

import java.util.stream.IntStream;

import org.junit.Test;

public class PairTest {
    @Test
    public void testPairMap() {
	Pair<String> pairOfStrings = new Pair<>("1", "2");
	assertEquals(3, pairOfStrings.map(s -> Integer.valueOf(s)).stream()
		.flatMapToInt(i -> IntStream.of(i)).sum());
    }
}

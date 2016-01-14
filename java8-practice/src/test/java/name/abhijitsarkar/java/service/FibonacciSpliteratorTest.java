package name.abhijitsarkar.java.service;

import name.abhijitsarkar.java.domain.FibonacciPair;
import org.junit.Test;

import java.util.Spliterator;
import java.util.stream.StreamSupport;

/**
 * @author Abhijit Sarkar
 */
public class FibonacciSpliteratorTest {
    @Test
    public void testFibonacci() {
        Spliterator<FibonacciPair> spliterator = new FibonacciSpliterator(5);

        StreamSupport.stream(spliterator, true)
                .forEachOrdered(System.out::print);
    }
}

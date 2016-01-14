package name.abhijitsarkar.java.domain;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
@Value
public class FibonacciPair {
    private final long minusOne;
    private final long minusTwo;

    @Override
    public String toString() {
        return String.format("%d %d ", minusOne, minusTwo);
    }
}

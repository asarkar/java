package name.abhijitsarkar.java.service;

import lombok.RequiredArgsConstructor;
import name.abhijitsarkar.java.domain.FibonacciPair;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
public class FibonacciSpliterator implements Spliterator<FibonacciPair> {
    private int currentIndex = 3;
    private FibonacciPair pair = new FibonacciPair(0, 1);

    private final int n;

    @Override
    public boolean tryAdvance(Consumer<? super FibonacciPair> action) {
//        System.out.println("tryAdvance called.");
//        System.out.printf("tryAdvance: currentIndex = %d, n = %d, pair = %s.\n", currentIndex, n, pair);

        action.accept(pair);

        return n - currentIndex >= 2;
    }

    @Override
    public Spliterator<FibonacciPair> trySplit() {
//        System.out.println("trySplit called.");

        FibonacciSpliterator fibonacciSpliterator = null;

        if (n - currentIndex >= 2) {
//            System.out.printf("trySplit Begin: currentIndex = %d, n = %d, pair = %s.\n", currentIndex, n, pair);

            fibonacciSpliterator = new FibonacciSpliterator(n);

            long currentFib = pair.getMinusTwo() + pair.getMinusOne();
            long nextFib = pair.getMinusOne() + currentFib;
            fibonacciSpliterator.pair = new FibonacciPair(currentFib, nextFib);

            currentIndex += 3;
            fibonacciSpliterator.currentIndex = currentIndex;

//            System.out.printf("trySplit End: currentIndex = %d, n = %d, pair = %s.\n", currentIndex, n, pair);
        }

        return fibonacciSpliterator;
    }

    @Override
    public long estimateSize() {
        return n - currentIndex;
    }

    @Override
    public int characteristics() {
        return ORDERED | IMMUTABLE | NONNULL;
    }
}

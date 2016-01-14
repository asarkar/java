package name.abhijitsarkar.java.java8lambdas.collectors;

import name.abhijitsarkar.java.java8lambdas.domain.Artist;

import java.util.Collection;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toConcurrentMap;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh5 {
    private static final Map<Integer, Long> FIBONACCI_MAP = IntStream.rangeClosed(1, 2)
            .boxed()
            .collect(toConcurrentMap(identity(), val -> Integer.toUnsignedLong(val - 1)));

    public static String findArtistWithLongestName(Collection<Artist> artists) {
        return artists.stream()
                .flatMap(artist -> artist.getMembers().stream())
                .collect(maxBy(comparingInt(String::length))).get();
    }

    public static String findArtistWithLongestName2(Collection<Artist> artists) {
        return artists.stream()
                .flatMap(artist -> artist.getMembers().stream())
                .collect(reducing("", BinaryOperator.maxBy(comparingInt(String::length))));
    }

    public static long fibonacci(int index) {
        if (index < 3) {
            return FIBONACCI_MAP.getOrDefault(index, 0l);
        }

        return FIBONACCI_MAP.computeIfAbsent(index, key -> {
            IntStream.range(3, index)
                    .boxed()
                    .forEach(k -> FIBONACCI_MAP.put(k, fibonacciInternal(k)));

            return fibonacciInternal(key);
        });
    }

    private static long fibonacciInternal(int index) {
        return FIBONACCI_MAP.get(index - 1) + FIBONACCI_MAP.get(index - 2);
    }
}

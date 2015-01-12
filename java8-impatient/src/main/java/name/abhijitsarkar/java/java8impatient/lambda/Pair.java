package name.abhijitsarkar.java.java8impatient.lambda;

import static java.util.stream.Stream.of;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Q23: Define a {@code map} operation for a class {@code Pair<T>} that
 * represents a pair of objects of type {@code T}.
 * <p>
 * Q24: Can you define a {@code flatMap} method for the {@code Pair<T>}? If so,
 * what is it? If not, why not?
 * <p>
 * <b>Ans:</b> A {@code flatMap} operation replaces each element with a set of
 * some other element, and then "flattens" the result to a single set of other
 * elements. Since pair can only have 2 item, flattening is not applicable as it
 * may result in more than 2 items. Consider a pair of lists of integers;
 * flatMap may convert each item to a list of strings but if we flatten those,
 * we're going to end up with more than 2 items.
 * 
 */
public class Pair<T> {
    private T item1;
    private T item2;

    public Pair(T item1, T item2) {
	this.item1 = item1;
	this.item2 = item2;
    }

    public <U> Pair<U> map(final Function<? super T, ? extends U> mapper) {
	return new Pair<U>(mapper.apply(item1), mapper.apply(item2));
    }

    public Stream<T> stream() {
	return of(item1, item2);
    }
}
package name.abhijitsarkar.java.masteringlambdas.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Wither;
import name.abhijitsarkar.java.masteringlambdas.domain.Book;
import name.abhijitsarkar.java.masteringlambdas.domain.NytBestSellersList;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.Collections.singleton;
import static java.util.Comparator.comparingLong;
import static java.util.Comparator.reverseOrder;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;

/**
 * @author Abhijit Sarkar
 */
public class BestSellersService {
    static Map<Integer, Collection<String>> groupByRank(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(
                        toMap(Book::getNytBestSellersRank,
                                book -> singleton(book.getTitle()),
                                (s1, s2) -> concat(s1.stream(), s2.stream()).collect(toSet())));
    }

    /* Returns a map of rank -> number of books with that rank.
     *
     * 'groupingBy' collector produces a map of rank -> list of books with that rank.
     * The 'mapping' collector operates on the list of books.
     * We pass the books through using the 'identity' function
     * and employ the 'counting' collector to count how many books for that rank.
     */
    static Map<Integer, Long> countByRank(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(
                        groupingBy(Book::getNytBestSellersRank,
                                mapping(identity(), counting()))
                );
    }

    /* Returns a map of rank -> number of books with that rank. */
    static Map<Integer, Long> countByRank2(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(
                        groupingBy(Book::getNytBestSellersRank,
                                mapping(identity(), summingLong(book -> 1l)))
                );
    }

    /* Returns a map of rank -> number of books with that rank.
     *
     * The 1st argument to 'reducing' is the identity element of what is going to be the final output which,
     * in this case, is a map.
     *
     * The 2nd argument is the mapper that takes each input element, in this case a book, and converts it to the type of
     * what is going to be the final output which, in this case, is a map.
     *
     * The 3rd argument is the reducer which takes subsequent mapped values and reduces those into a single value.
     * Since the output of the mapping operation is a map, the reduction operation consists of merging 2 maps into 1,
     * Since there is no readymade map merge in Java, we convert the 2 maps into a stream of entries
     * and then employ the 'toMap' collector to create a new map.
     * The key and the value functions of 'toMap' are straightforward. For the merge function of 'toMap',
     * that is only invoked to resolve collisions between values associated with the same key, we simply add up the
     * values.
     */
    static Map<Integer, Long> countByRank3(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(
                        reducing(
                                new HashMap<>(),
                                book -> {
                                    HashMap<Integer, Long> rankToBooksCount = new HashMap<>();
                                    /* 'val' is always equal to the 2nd param, in this case 1l. Odd signature. */
                                    rankToBooksCount.merge(book.getNytBestSellersRank(), 1l, (oldValue, val) -> oldValue + 1l);

                                    return rankToBooksCount;
                                },
                                (Map<Integer, Long> map1, Map<Integer, Long> map2) ->
                                        concat(map1.entrySet().stream(), map2.entrySet().stream()).
                                                collect(toMergedMap())
                        )
                );
    }

    private static final Collector<Entry<Integer, Long>, ?, Map<Integer, Long>> toMergedMap() {
        return toMap(Entry::getKey, Entry::getValue, Long::sum);
    }


    /* Returns a map of rank -> number of books with that rank.
     *
     * The 1st argument to 'collectingAndThen' is the 'groupingBy' collector that produces
     * a map of rank -> list of books.
     * The 2nd argument operates on the map and transforms it into a map of rank -> number of books with that rank.
     */
    static Map<Integer, Long> countByRank4(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(
                        collectingAndThen(
                                groupingBy(Book::getNytBestSellersRank),
                                map -> map.entrySet().stream().map(rankToBooksCount).
                                        collect(toMergedMap())
                        )
                );
    }

    private static final Function<Entry<Integer, List<Book>>, Entry<Integer, Long>> rankToBooksCount = entry ->
            new SimpleImmutableEntry<>(entry.getKey(), (long) entry.getValue().size());

    /* Returns the rank that has the maximum number of books.
     *
     * Since in this case, all ranks have the same number of books, we provide a sorted
     * map factory function to 'groupingBy', that sorts the entries in reverse natural order of the keys.
     * Thus if multiple ranks have the same number of books, the greater rank is returned.
     *
     */
    static int rankWithMaxNumBooks(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(
                        groupingBy(Book::getNytBestSellersRank,
                                () -> new TreeMap<Integer, Long>(reverseOrder()),
                                mapping(identity(), counting()))
                ).
                entrySet().
                stream().
                collect(
                        maxBy(comparingLong(Entry::getValue))
                ).
                get().
                getKey();

    }

    /* Returns the rank that has the maximum number of books.
     *
     * Uses a custom collector.
     */
    static int rankWithMaxNumBooks2(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                map(book -> new Pair(book.getNytBestSellersRank(), 1l)).
                collect(Collector.of(supplier, accumulator, combiner, finisher));
    }

    /* 1st sort by the maximum number of books; then if multiple ranks have the same number of books,
     * sort by rank reversed.
     */
    private static final Supplier<TreeSet<Pair>> supplier = () ->
            new TreeSet<Pair>(comparingLong(Pair::getCount).thenComparingInt(Pair::getRank).reversed());

    private static final BiConsumer<TreeSet<Pair>, Pair> accumulator =
            (set, pair) -> {
                Pair foundElem = set.floor(pair);
                Pair elem = pair;

                if (foundElem != null) {
                    set.remove(foundElem);
                    elem = foundElem.withCount(pair.getCount() + foundElem.getCount());
                }

                set.add(elem);
            };

    /* Can we do better? */
    private static final BinaryOperator<TreeSet<Pair>> combiner = (set1, set2) -> {
        return concat(set1.stream(), set2.stream()).
                peek(System.out::println).
                collect(toMap(Pair::getRank, Pair::getCount, Long::sum)).
                entrySet().
                stream().
                map(entry -> new Pair(entry.getKey(), entry.getValue())).
                collect(toCollection(supplier));
    };

    private static final Function<TreeSet<Pair>, Integer> finisher = set -> set.first().getRank();

    @RequiredArgsConstructor
    @EqualsAndHashCode(exclude = "count")
    @ToString
    @Getter
    private static final class Pair {
        private final int rank;
        @Wither
        private final long count;
    }
}

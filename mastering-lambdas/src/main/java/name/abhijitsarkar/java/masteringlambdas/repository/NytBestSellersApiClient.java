package name.abhijitsarkar.java.masteringlambdas.repository;

import name.abhijitsarkar.java.masteringlambdas.domain.Book;
import name.abhijitsarkar.java.masteringlambdas.domain.NytBestSellersList;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singleton;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;

/**
 * @author Abhijit Sarkar
 */
public interface NytBestSellersApiClient {
    void close();

    Collection<String> bestSellersListsNames();

    Collection<NytBestSellersList> bestSellersListsOverview();

    static Map<Integer, Collection<String>> groupByRank(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(
                        toMap(Book::getNytBestSellersRank,
                                book -> singleton(book.getTitle()),
                                (s1, s2) -> concat(s1.stream(), s2.stream()).collect(toSet())));
    }

    /* Produces a map of rank -> number of books with that rank.
     *
     * 'groupingBy' collector produces a map of rank -> list of books with that rank.
     * The 'mapping' collector operates on the list of books.
     * We pass the books through using the `identity` function
     * and employ the 'counting' collector to count how many books for that rank.
     */
    static Map<Integer, Long> countByRank(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(groupingBy(Book::getNytBestSellersRank, mapping(identity(), counting())));
    }

    /* Produces a map of rank -> number of books with that rank. */
    static Map<Integer, Long> countByRank2(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(groupingBy(Book::getNytBestSellersRank, mapping(identity(), summingLong(book -> 1l))));
    }

    /* Produces a map of rank -> number of books with that rank.
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
                collect(reducing(new HashMap<>(), book -> {
                    HashMap<Integer, Long> isbn13ToBooksCount = new HashMap<>();
                    /* 'val' is always equal to the 2nd param, in this case 1l. Odd signature. */
                    isbn13ToBooksCount.merge(book.getNytBestSellersRank(), 1l, (oldValue, val) -> oldValue + 1l);

                    return isbn13ToBooksCount;
                }, (Map<Integer, Long> map1, Map<Integer, Long> map2) ->
                        concat(map1.entrySet().stream(), map2.entrySet().stream()).
                                collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (val1, val2) -> val1 + val2))));
    }

    /* Produces a map of rank -> number of books with that rank.
     *
     * The 1st argument to 'collectingAndThen' is the 'groupingBy' collector that produces
     * a map of rank -> list of books.
     * The 2nd argument operates on the map and transforms it into a map of rank -> number of books with that rank.
     */
    static Map<Integer, Long> countByRank4(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(collectingAndThen(groupingBy(Book::getNytBestSellersRank), map -> {
                    return map.entrySet().stream().map(e -> new AbstractMap.SimpleImmutableEntry<Integer, Long>(
                            e.getKey(), (long) e.getValue().size())).
                            collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (val1, val2) -> val1 + val2));
                }));
    }
}

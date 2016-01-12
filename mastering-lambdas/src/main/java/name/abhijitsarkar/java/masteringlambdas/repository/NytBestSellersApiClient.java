package name.abhijitsarkar.java.masteringlambdas.repository;

import name.abhijitsarkar.java.masteringlambdas.domain.Book;
import name.abhijitsarkar.java.masteringlambdas.domain.NytBestSellersList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singleton;
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

    static Map<String, Collection<String>> findDuplicates(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(
                        toMap(Book::getIsbn13,
                                book -> singleton(book.getTitle()),
                                (s1, s2) -> concat(s1.stream(), s2.stream()).collect(toSet())));
    }

    /* 'groupingBy' does the partitioning of books, 'mapping' takes each group of books and
    * converts to group of strings and then applies the 'counting' collector.
    */
    static Map<String, Long> countDuplicates(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(groupingBy(Book::getIsbn13, mapping(Book::getTitle, counting())));
    }

    /* Does the same thing as above but uses summingLong instead of counting. */
    static Map<String, Long> countDuplicates2(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(groupingBy(Book::getIsbn13, mapping(Book::getTitle, summingLong(title -> 1l))));
    }

    static Map<String, Long> countDuplicates3(Collection<NytBestSellersList> lists) {
        return lists.stream().
                flatMap(list -> list.getBooks().stream()).
                collect(reducing(new HashMap<>(), book -> {
                    HashMap<String, Long> isbn13ToBooksCount = new HashMap<>();
                    /* 'val' is always equal to the 2nd param, in this case 1l. Odd signature. */
                    isbn13ToBooksCount.merge(book.getIsbn13(), 1l, (oldValue, val) -> oldValue + 1l);

                    return isbn13ToBooksCount;
                }, (Map<String, Long> map1, Map<String, Long> map2) ->
                        Stream.concat(map1.entrySet().stream(), map2.entrySet().stream()).
                                collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (val1, val2) -> val1 + val2))));
    }
}

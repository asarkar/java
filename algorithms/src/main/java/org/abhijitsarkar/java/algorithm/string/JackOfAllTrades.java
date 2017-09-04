package org.abhijitsarkar.java.algorithm.string;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author Abhijit Sarkar
 */
public class JackOfAllTrades {
    // https://www.youtube.com/watch?v=nYFd7VHKyWQ
    public static List<String> allPermutations(String str) {
        Map<Character, Integer> countMap = str.chars()
                .mapToObj(i -> (char) i)
                .collect(collectingAndThen(toMap(identity(), i -> 1, (i, j) -> i + j, TreeMap::new),
                        Collections::unmodifiableMap));

        return allPermutations(countMap, 0, str);
    }

    private static List<String> allPermutations(
            final Map<Character, Integer> countMap,
            final int depth,
            final String result
    ) {
        if (countMap.values().stream().allMatch(i -> i == 0)) {
            String msg = String.format("Permutation: %s", result);
            printLeftJustified(msg, depth);

            return asList(result);
        }

        StringBuilder buffer = new StringBuilder(result);

        /**
         * For each entry in the count map where the value is positive, launch a new recursion after decrementing the
         * value for the corresponding character. The idea is to keep one character same and vary the rest until all
         * permutations are exhausted. Then move on to the next character, and do the same.
         */
        return countMap.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .flatMap(e -> {
                    char firstAvailableChar = e.getKey();

                    buffer.replace(depth, depth + 1, String.valueOf(firstAvailableChar));

                    Map<Character, Integer> countMapCopy = countMap.entrySet().stream()
                            .filter(x -> x.getValue() > 0)
                            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (i, j) -> i, TreeMap::new));

                    countMapCopy.put(firstAvailableChar, countMap.get(firstAvailableChar) - 1);

                    String msg = String.format("Going into recursion with char: %c, depth: %d, count map: %s",
                            firstAvailableChar,
                            depth,
                            countMap);
                    printLeftJustified(msg, depth);

                    List<String> permutations = allPermutations(
                            unmodifiableMap(countMapCopy),
                            depth + 1,
                            buffer.toString()
                    );

                    msg = String.format("Returned from recursion with char: %c, depth: %d, count map: %s",
                            firstAvailableChar,
                            depth,
                            countMap);

                    printLeftJustified(msg, depth);

                    return permutations.stream();
                })
                .collect(toList());
    }

    private static void printLeftJustified(String msg, int depth) {
        System.out.printf("%" + (depth * 2 + msg.length()) + "s\n", msg);
    }
}

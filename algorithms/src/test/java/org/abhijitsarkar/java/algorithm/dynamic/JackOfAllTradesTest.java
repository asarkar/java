package org.abhijitsarkar.java.algorithm.dynamic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @author Abhijit Sarkar
 */
class JackOfAllTradesTest {
    @ParameterizedTest(name = "s1: {0}, s2: {1}, lcs: {2}")
    @MethodSource("lcsParams")
    void testLongestCommonSubsequence(String s1, String s2, String lcs) {
        Assertions.assertThat(JackOfAllTrades.longestCommonSubsequence(s1, s2)).isEqualTo(lcs);
    }

    static Stream<Arguments> lcsParams() {
        return Stream.of(
                Arguments.of("abcdaf", "acbcf", "abcf"),
                Arguments.of("abcda", "acbdea", "acda")
        );
    }
}
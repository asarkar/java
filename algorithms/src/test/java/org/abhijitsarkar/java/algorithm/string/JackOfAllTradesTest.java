package org.abhijitsarkar.java.algorithm.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Abhijit Sarkar
 */
class JackOfAllTradesTest {
    @Test
    void testAllPermutationsOfAString() {
        assertThat(JackOfAllTrades.allPermutations("AABC"))
                .containsExactlyInAnyOrder("AABC", "AACB", "ABAC", "ABCA", "ACAB", "ACBA",
                        "BAAC", "BACA", "BCAA", "CAAB", "CABA", "CBAA");
    }
}
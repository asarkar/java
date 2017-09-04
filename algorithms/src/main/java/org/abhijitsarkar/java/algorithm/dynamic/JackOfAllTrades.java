package org.abhijitsarkar.java.algorithm.dynamic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Abhijit Sarkar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JackOfAllTrades {
    // https://www.youtube.com/watch?v=NnD96abizww
    /*
     * Number of sub-sequences in a given sequence of length m = 2^m. For each element in a sequence of length m,
     * we can either select it or leave it. Thus, there are 2 ways to deal with each element.
     * Therefore, the total no. of ways to deal with all the m elements is 2*2*2...... m times = 2^m.
     *
     * Memoization.
     */
    public static final String longestCommonSubsequence(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        int[][] arr = new int[len2 + 1][len1 + 1];

        for (int row = 1; row < arr.length; row++) {
            for (int col = 1; col < arr[row].length; col++) {
                char c1 = s1.charAt(col - 1);
                char c2 = s2.charAt(row - 1);

                if (c1 == c2) {
                    arr[row][col] = arr[row - 1][col - 1] + 1;
                } else {
                    arr[row][col] = Math.max(arr[row][col - 1], arr[row - 1][col]);
                }
            }
        }

        int row = arr.length - 1;
        int col = arr[0].length - 1;

        StringBuilder longestCommonSubsequence = new StringBuilder(arr[row][col]);

        // backtrack to find the longest common subsequence
        while (row >= 1 && col >= 1) {
            char c1 = s1.charAt(col - 1);
            char c2 = s2.charAt(row - 1);

            if (c1 == c2) {
                longestCommonSubsequence.insert(0, c1);
                --row;
                --col;
            } else if (arr[row][col - 1] > arr[row - 1][col]) {
                --col;
            } else {
                --row;
            }
        }

        return longestCommonSubsequence.toString();
    }
}

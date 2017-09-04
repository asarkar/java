package org.abhijitsarkar.java.algorithm.list;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Abhijit Sarkar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LinkedListFactory {
    public static IntegerNode newLinkedList() {
        IntegerNode four = IntegerNode.builder()
                .value(4)
                .build();

        IntegerNode five = IntegerNode.builder()
                .value(5)
                .next(four)
                .build();

        IntegerNode six = IntegerNode.builder()
                .value(6)
                .next(five)
                .build();

        IntegerNode two = IntegerNode.builder()
                .value(2)
                .next(six)
                .build();

        return IntegerNode.builder()
                .next(two)
                .build();
    }
}

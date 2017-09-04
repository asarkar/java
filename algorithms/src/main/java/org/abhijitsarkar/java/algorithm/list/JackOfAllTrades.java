package org.abhijitsarkar.java.algorithm.list;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Abhijit Sarkar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JackOfAllTrades {
    public static IntegerNode reverseList(IntegerNode head) {
        IntegerNode first = reverse(head.next(), head);

        return IntegerNode.builder()
                .next(first)
                .build();
    }

    private static IntegerNode reverse(IntegerNode node, IntegerNode previous) {
        if (node == null) {
            return previous;
        }

        IntegerNode next = node.next();
        if (previous.value() == null) {
            node.setNext(null);
        } else {
            node.setNext(previous);
        }

        return reverse(next, node);
    }
}

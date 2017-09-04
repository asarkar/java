package org.abhijitsarkar.java.algorithm.list;

import org.junit.jupiter.api.Test;

/**
 * @author Abhijit Sarkar
 */
class JackOfAllTradesTest {
    @Test
    void testReverseList() {
        IntegerNode head = LinkedListFactory.newLinkedList();

        for (IntegerNode current = head; current != null; current = current.next()) {
            System.out.printf("%s%s", current.toString(), current.next() == null ? "\n" : "--->");
        }

        head = JackOfAllTrades.reverseList(head);

        for (IntegerNode current = head; current != null; current = current.next()) {
            System.out.printf("%s%s", current.toString(), current.next() == null ? "\n" : "--->");
        }
    }
}
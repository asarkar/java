package org.abhijitsarkar.java.algorithm.list;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Setter;

import java.util.Objects;

/**
 * @author Abhijit Sarkar
 */
@Builder
@Setter(AccessLevel.PACKAGE)
public class IntegerNode {
    private static final String EMPTY = "";

    private Integer value;
    private IntegerNode next;

    private IntegerNode(Integer value, IntegerNode next) {
        this.value = value;
        this.next = next;
    }

    public Integer value() {
        return value;
    }

    public IntegerNode next() {
        return next;
    }

    @Override
    public String toString() {
        return String.format("[v=%s, n=%s]",
                Objects.toString(value, EMPTY),
                Objects.toString(next == null ? EMPTY : next.value, EMPTY)
        );
    }
}

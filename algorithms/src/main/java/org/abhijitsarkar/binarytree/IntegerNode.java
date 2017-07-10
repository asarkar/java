package org.abhijitsarkar.binarytree;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;

import java.util.Optional;

/**
 * @author Abhijit Sarkar
 */
@Builder
@Setter(AccessLevel.PACKAGE)
public class IntegerNode implements PrintableNode {
    private Integer value;
    private IntegerNode left;
    private IntegerNode right;

    private IntegerNode(@NonNull Integer value, IntegerNode left, IntegerNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public Integer value() {
        return value;
    }

    @Override
    public IntegerNode left() {
        return left;
    }

    @Override
    public IntegerNode right() {
        return right;
    }

    @Override
    public String toString() {
        return Optional.ofNullable(value).orElse(Integer.MIN_VALUE).toString();
    }
}

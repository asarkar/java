package org.abhijitsarkar.java.algorithm.binarytree;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Setter;

import java.util.Optional;

/**
 * @author Abhijit Sarkar
 */
@Builder
@Setter(AccessLevel.PACKAGE)
public class ParentAwareIntegerNode implements PrintableNode {
    private ParentAwareIntegerNode parent;
    private Integer value;
    private ParentAwareIntegerNode left;
    private ParentAwareIntegerNode right;

    private ParentAwareIntegerNode(ParentAwareIntegerNode parent, Integer value,
                                   ParentAwareIntegerNode left, ParentAwareIntegerNode right) {
        this.parent = parent;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public ParentAwareIntegerNode parent() {
        return parent;
    }

    public Integer value() {
        return value;
    }

    @Override
    public ParentAwareIntegerNode left() {
        return left;
    }

    @Override
    public ParentAwareIntegerNode right() {
        return right;
    }

    @Override
    public String toString() {
        return String.format("[p=%s, v=%s, l=%s, r=%s]",
                toString(parent),
                Optional.ofNullable(value)
                        .map(i -> Integer.toString(i))
                        .orElse(""),
                toString(left),
                toString(right)
        );
    }

    private String toString(ParentAwareIntegerNode node) {
        return Optional.ofNullable(node)
                .map(ParentAwareIntegerNode::value)
                .map(i -> Integer.toString(i))
                .orElse("");
    }
}

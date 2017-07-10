package org.abhijitsarkar.binarytree;

import lombok.Builder;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

/**
 * @author Abhijit Sarkar
 */
public class BinarySearchTree {
    private final IntegerNode rootNode;

    public IntegerNode root() {
        return rootNode;
    }

    public List<Integer> nodes() {
        return unmodifiableList(nodes(rootNode));
    }

    private final List<Integer> nodes(IntegerNode root) {
        List<Integer> nodes = null;

        if (root == null) {
            nodes = emptyList();
        } else {
            nodes = new ArrayList<>();
            nodes.add(root.value());
            nodes.addAll(nodes(root.left()));
            nodes.addAll(nodes(root.right()));
        }

        return nodes;
    }

    @Builder
    private BinarySearchTree(Integer root, @Singular List<Integer> nodes) {
        requireNonNull(root, "Root must not be null.");

        rootNode = IntegerNode.builder()
                .value(root)
                .build();

        nodes.forEach(value -> insert(rootNode, value));
    }

    public final IntegerNode insert(IntegerNode root, int value) {
        if (root == null) {
            return IntegerNode.builder()
                    .value(value)
                    .build();
        }

        if (value < root.value()) {
            root.setLeft(insert(root.left(), value));
        } else {
            root.setRight(insert(root.right(), value));
        }

        return root;
    }

    public final boolean contains(int value) {
        return contains(rootNode, value);
    }

    private final boolean contains(IntegerNode root, int value) {
        boolean contains = false;

        if (root != null) {
            if (value == root.value()) {
                contains = true;
            } else if (value < root.value()) {
                contains = contains(root.left(), value);
            } else {
                contains = contains(root.right(), value);
            }
        }

        return contains;
    }
}

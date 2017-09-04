package org.abhijitsarkar.java.algorithm.binarytree;

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
    private ParentAwareIntegerNode rootNode;

    public ParentAwareIntegerNode root() {
        return rootNode;
    }

    public List<Integer> nodes() {
        return unmodifiableList(nodes(rootNode));
    }

    private final List<Integer> nodes(ParentAwareIntegerNode root) {
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

        rootNode = ParentAwareIntegerNode.builder()
                .value(root)
                .build();

        nodes.forEach(this::insert);
    }

    public final ParentAwareIntegerNode insert(int value) {
        return insert(rootNode, value, null);
    }

    private ParentAwareIntegerNode insert(ParentAwareIntegerNode node, int value, ParentAwareIntegerNode parent) {
        if (node == null) {
            return ParentAwareIntegerNode.builder()
                    .value(value)
                    .parent(parent)
                    .build();
        }

        if (value < node.value()) {
            node.setLeft(insert(node.left(), value, node));
        } else {
            node.setRight(insert(node.right(), value, node));
        }

        return node;
    }

    public final boolean contains(int value) {
        return find(value) != null;
    }

    final ParentAwareIntegerNode find(int value) {
        return find(rootNode, value);
    }

    private final ParentAwareIntegerNode find(ParentAwareIntegerNode node, int value) {
        ParentAwareIntegerNode found = null;

        if (node != null) {
            if (value == node.value()) {
                found = node;
            } else if (value < node.value()) {
                found = find(node.left(), value);
            } else {
                found = find(node.right(), value);
            }
        }

        return found;
    }

    // http://www.geeksforgeeks.org/binary-search-tree-set-2-delete/
    // https://www.youtube.com/watch?v=gcULXE7ViZw&vl=en
    public final ParentAwareIntegerNode delete(int value) {
        ParentAwareIntegerNode node = find(value);

        if (node != null) {
            if (isLeaf(node)) {
                if (rootNode != node) {
                    delete(node, null);
                } else {
                    rootNode = null;
                }
            } else if (hasLeftChildOnly(node)) {
                if (rootNode != node) {
                    delete(node, node.left());
                } else {
                    rootNode = node.left();
                }
            } else if (hasRightChildOnly(node)) {
                if (rootNode != node) {
                    delete(node, node.right());
                } else {
                    rootNode = node.right();
                }
            } else {
                ParentAwareIntegerNode inOrderSuccessor = inOrderSuccessor(node);
                node.setValue(inOrderSuccessor.value());
                delete(inOrderSuccessor, null);
            }
        }

        return node;
    }

    private void delete(ParentAwareIntegerNode node, ParentAwareIntegerNode nodeForParent) {
        ParentAwareIntegerNode parent = node.parent();

        if (parent.left() == node) {
            parent.setLeft(nodeForParent);
        } else {
            parent.setRight(nodeForParent);
        }
    }

    private boolean isLeaf(ParentAwareIntegerNode node) {
        return node.left() == null && node.right() == null;
    }

    private boolean hasLeftChildOnly(ParentAwareIntegerNode node) {
        return node.left() != null && node.right() == null;
    }

    private boolean hasRightChildOnly(ParentAwareIntegerNode node) {
        return node.left() == null && node.right() != null;
    }

    final ParentAwareIntegerNode inOrderSuccessor(ParentAwareIntegerNode node) {
        ParentAwareIntegerNode inOrderSuccessor = null;

        if (node != null) {
            if (node.right() != null) { // by BST property, minimum in the right subtree is the in-order successor
                inOrderSuccessor = minimum(node.right());
            } else if (node.parent().left() == node) { // parent is next
                inOrderSuccessor = node.parent();
            } else if (node.parent() != null) { // already visited parent
                inOrderSuccessor = node.parent().parent();
            }
        }

        return inOrderSuccessor;
    }

    final ParentAwareIntegerNode minimum(ParentAwareIntegerNode node) {
        ParentAwareIntegerNode minimum = null;

        if (node != null) {
            if (hasRightChildOnly(node) || isLeaf(node)) {
                minimum = node;
            } else {
                minimum = minimum(node.left());

                if (minimum == null) {
                    minimum = minimum(node.right());
                }
            }
        }

        return minimum;
    }
}

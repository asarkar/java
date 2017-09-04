package org.abhijitsarkar.java.algorithm.binarytree.util;

import org.abhijitsarkar.java.algorithm.binarytree.BinarySearchTree;
import org.abhijitsarkar.java.algorithm.binarytree.IntegerNode;

/**
 * @author Abhijit Sarkar
 */
public final class BinaryTreeFactory {
    private BinaryTreeFactory() {
    }

    public static IntegerNode newBinaryTree() {
        // left subtree
        IntegerNode five = IntegerNode.builder()
                .value(5)
                .build();

        IntegerNode three = IntegerNode.builder()
                .left(five)
                .value(3)
                .build();

        IntegerNode six = IntegerNode.builder()
                .value(6)
                .build();

        IntegerNode fifteen = IntegerNode.builder()
                .left(three)
                .right(six)
                .value(15)
                .build();

        // right subtree
        IntegerNode nine = IntegerNode.builder()
                .value(9)
                .build();

        IntegerNode eight = IntegerNode.builder()
                .value(8)
                .build();

        IntegerNode two = IntegerNode.builder()
                .right(eight)
                .left(nine)
                .value(2)
                .build();

        IntegerNode thirty = IntegerNode.builder()
                .right(two)
                .value(30)
                .build();

        // root
        IntegerNode ten = IntegerNode.builder()
                .right(thirty)
                .left(fifteen)
                .value(10)
                .build();

        return ten;
    }

    public static BinarySearchTree newBinarySearchTree() {
        return BinarySearchTree.builder()
                .root(10)
                .node(-5)
                .node(16)
                .node(-8)
                .node(7)
                .node(18)
                .build();
    }
}

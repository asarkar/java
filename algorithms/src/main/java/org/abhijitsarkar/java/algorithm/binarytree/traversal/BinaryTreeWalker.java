package org.abhijitsarkar.java.algorithm.binarytree.traversal;

import org.abhijitsarkar.java.algorithm.binarytree.IntegerNode;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * @author Abhijit Sarkar
 */
public interface BinaryTreeWalker {
    default List<IntegerNode> walk(IntegerNode root) {
        List<IntegerNode> visited = walkInternal(root, new ArrayList<>());

        return unmodifiableList(visited);
    }

    List<IntegerNode> walkInternal(IntegerNode node, List<IntegerNode> visited);
}

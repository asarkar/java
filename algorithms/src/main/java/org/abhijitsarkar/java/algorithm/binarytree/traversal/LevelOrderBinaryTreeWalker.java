package org.abhijitsarkar.java.algorithm.binarytree.traversal;

import org.abhijitsarkar.java.algorithm.binarytree.IntegerNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Abhijit Sarkar
 */

// https://www.youtube.com/watch?v=7uG0gLDbhsI&list=PLrmLmBdmIlpv_jNDXtJGYTPNQ2L1gdHxu&index=13
public class LevelOrderBinaryTreeWalker implements BinaryTreeWalker {
    private static final IntegerNode SENTINEL = IntegerNode.builder().value(Integer.MIN_VALUE).build();

    private final Queue<IntegerNode> queue = new LinkedList<>();

    @Override
    public List<IntegerNode> walkInternal(IntegerNode node, List<IntegerNode> visited) {
        queue.add(node);
        queue.add(SENTINEL);

        while (!queue.isEmpty()) {
            IntegerNode element = queue.remove();

            if (element != SENTINEL) {
                visited.add(element);
            } else if (!queue.isEmpty()) {
                queue.add(SENTINEL);
                visited.add(element);
            }

            if (element.left() != null) {
                queue.add(element.left());
            }
            if (element.right() != null) {
                queue.add(element.right());
            }
        }

        return visited;
    }
}

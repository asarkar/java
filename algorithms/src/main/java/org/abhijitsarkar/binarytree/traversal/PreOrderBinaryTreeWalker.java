package org.abhijitsarkar.binarytree.traversal;

import org.abhijitsarkar.binarytree.IntegerNode;

import java.util.List;

/**
 * @author Abhijit Sarkar
 */
public class PreOrderBinaryTreeWalker implements BinaryTreeWalker {
    @Override
    public List<IntegerNode> walkInternal(IntegerNode node, List<IntegerNode> visited) {
        if (node != null) {
            visited.add(node);
            walkInternal(node.left(), visited);
            walkInternal(node.right(), visited);
        }

        return visited;
    }
}

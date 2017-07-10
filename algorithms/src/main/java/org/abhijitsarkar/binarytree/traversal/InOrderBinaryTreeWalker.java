package org.abhijitsarkar.binarytree.traversal;

import org.abhijitsarkar.binarytree.IntegerNode;

import java.util.List;

/**
 * @author Abhijit Sarkar
 */
public class InOrderBinaryTreeWalker implements BinaryTreeWalker {
    @Override
    public List<IntegerNode> walkInternal(IntegerNode node, List<IntegerNode> visited) {
        if (node != null) {
            walkInternal(node.left(), visited);
            visited.add(node);
            walkInternal(node.right(), visited);
        }

        return visited;
    }
}

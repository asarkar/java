package org.abhijitsarkar.java.algorithm.binarytree.traversal;

/**
 * @author Abhijit Sarkar
 */
public final class BinaryTreeWalkerFactory {
    private BinaryTreeWalkerFactory() {
    }

    public static final BinaryTreeWalker newBinaryTreeWalker(TreeTraversalStrategy traversalStrategy) {
        switch (traversalStrategy) {
            case IN_ORDER:
                return new InOrderBinaryTreeWalker();
            case PRE_ORDER:
                return new PreOrderBinaryTreeWalker();
            case POST_ORDER:
                return new PostOrderBinaryTreeWalker();
            case LEVEL_ORDER:
                return new LevelOrderBinaryTreeWalker();
            default:
                throw new IllegalArgumentException("Not gonna happen!");
        }
    }
}

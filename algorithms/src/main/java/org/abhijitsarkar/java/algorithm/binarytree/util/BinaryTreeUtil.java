package org.abhijitsarkar.java.algorithm.binarytree.util;

import org.abhijitsarkar.java.algorithm.binarytree.IntegerNode;
import org.abhijitsarkar.java.algorithm.binarytree.ParentAwareIntegerNode;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

/**
 * @author Abhijit Sarkar
 */
public final class BinaryTreeUtil {
    private BinaryTreeUtil() {
    }

    public static int height(IntegerNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(height(root.left()), height(root.right())) + 1;
    }

    // https://www.youtube.com/watch?v=MILxfAbIhrE&list=PLrmLmBdmIlpv_jNDXtJGYTPNQ2L1gdHxu&index=8
    public static boolean isBST(ParentAwareIntegerNode root) {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean isBST(ParentAwareIntegerNode root, int lowerBound, int upperBound) {
        if (root == null) {
            return true;
        }

        return isInRange(root.value(), lowerBound, upperBound)
                && isBST(root.left(), lowerBound, root.value())
                && isBST(root.right(), root.value(), upperBound);
    }

    private static boolean isInRange(int value, int lowerBound, int upperBound) {
        return value >= lowerBound && value <= upperBound;
    }

    public static boolean isSame(IntegerNode root1, IntegerNode root2) {
        boolean same = false;

        if (root1 == root2) {
            same = true;
        } else if (root1 != null && root2 != null && root1.value() == root2.value()) {
            same = isSame(root1.left(), root2.left())
                    && isSame(root1.right(), root2.right());
        }

        return same;
    }

    // https://www.youtube.com/watch?v=13m9ZCB8gjw&list=PLrmLmBdmIlpv_jNDXtJGYTPNQ2L1gdHxu&index=17
    public static IntegerNode lowestCommonAncestor(IntegerNode root, int value1, int value2) {
        Map.Entry<IntegerNode, Boolean> lca = internalLCA(root, value1, value2);

        return lca != null && lca.getValue() ? lca.getKey() : null;
    }

    private static Map.Entry<IntegerNode, Boolean> internalLCA(IntegerNode root, int value1, int value2) {
        Map.Entry<IntegerNode, Boolean> lca = null;

        if (root != null) {
            Map.Entry<IntegerNode, Boolean> left = internalLCA(root.left(), value1, value2);

            if (isEqualToOneOf(root, value1, value2)) {
                if (left != null) {
                    lca = new SimpleEntry<>(root, isEqualToOneOf(left.getKey(), value1, value2));
                } else {
                    Map.Entry<IntegerNode, Boolean> right = internalLCA(root.right(), value1, value2);

                    if (right != null) {
                        lca = new SimpleEntry<>(root, isEqualToOneOf(right.getKey(), value1, value2));
                    } else {
                        lca = new SimpleEntry<>(root, false);
                    }
                }
            } else {
                if (left != null && left.getValue()) {
                    lca = new SimpleEntry<>(left.getKey(), true);
                } else {
                    Map.Entry<IntegerNode, Boolean> right = internalLCA(root.right(), value1, value2);

                    if (right != null && left != null) {
                        lca = new SimpleEntry<>(root, true);
                    } else if (right != null) {
                        lca = new SimpleEntry<>(right.getKey(), right.getValue());
                    }
                }
            }
        }
        return lca;
    }

    private static boolean isEqualToOneOf(IntegerNode node, int value1, int value2) {
        return node.value() == value1 || node.value() == value2;
    }
}

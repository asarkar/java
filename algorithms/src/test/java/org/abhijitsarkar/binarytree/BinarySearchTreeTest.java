package org.abhijitsarkar.binarytree;

import org.abhijitsarkar.binarytree.util.BinaryTreeFactory;
import org.abhijitsarkar.binarytree.util.BinaryTreePrinter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.abhijitsarkar.binarytree.util.BinaryTreeUtil.isBST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Abhijit Sarkar
 */
class BinarySearchTreeTest {
    private static final BinarySearchTree TREE = BinaryTreeFactory.newBinarySearchTree();

    @BeforeAll
    static void before() {
        BinaryTreePrinter.print(TREE.root());
    }

    @Test
    void testNodes() {
        List<Integer> nodes = TREE.nodes();

        assertThat(nodes).hasSize(6);
        assertThat(nodes).containsExactlyInAnyOrder(10, -5, -8, 7, 16, 18);
    }

    @Test
    void testContains() {
        assertThat(TREE.nodes().stream()
                .allMatch(TREE::contains)).isTrue();
        assertThat(TREE.contains(99)).isFalse();
    }

    @Test
    void testRootNotNull() {
        assertThatThrownBy(() -> BinarySearchTree.builder().build())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testIsBST() {
        assertThat(isBST(TREE.root())).isTrue();

        IntegerNode fifteen = IntegerNode.builder()
                .value(15)
                .build();

        IntegerNode three = IntegerNode.builder()
                .value(3)
                .build();

        IntegerNode five = IntegerNode.builder()
                .left(fifteen)
                .right(three)
                .value(5)
                .build();


        assertThat(isBST(five)).isFalse();
    }
}
package org.abhijitsarkar.java.algorithm.binarytree;

import org.abhijitsarkar.java.algorithm.binarytree.util.BinaryTreeFactory;
import org.abhijitsarkar.java.algorithm.binarytree.util.BinaryTreePrinter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.abhijitsarkar.java.algorithm.binarytree.util.BinaryTreeUtil.isBST;
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

        ParentAwareIntegerNode fifteen = ParentAwareIntegerNode.builder()
                .value(15)
                .build();

        ParentAwareIntegerNode three = ParentAwareIntegerNode.builder()
                .value(3)
                .build();

        ParentAwareIntegerNode five = ParentAwareIntegerNode.builder()
                .left(fifteen)
                .right(three)
                .value(5)
                .build();

        three.setParent(five);
        fifteen.setParent(five);

        assertThat(isBST(five)).isFalse();
    }

    @ParameterizedTest(name = "Delete {0}")
    @ValueSource(ints = {80, 30, 50})
    void testDelete(int value) {
        BinarySearchTree bst = newBinarySearchTreeForTest();

        System.out.printf("Before deleting: %d\n", value);
        BinaryTreePrinter.print(bst.root());
        assertThat(bst.delete(80)).isNotNull();
        assertThat(bst.contains(80)).isFalse();
        System.out.printf("After deleting: %d\n", value);
        BinaryTreePrinter.print(bst.root());
    }

    @ParameterizedTest(name = "Root: {0}, min: {1}")
    @MethodSource("minimumParams")
    void testMinimum(int root, int min) {
        BinarySearchTree bst = newBinarySearchTreeForTest();

        ParentAwareIntegerNode node = bst.find(root);
        assertThat(node).isNotNull();

        ParentAwareIntegerNode minimum = bst.minimum(node);
        assertThat(minimum).isNotNull();

        assertThat(minimum.value()).isEqualTo(min);
    }

    private static Stream<Arguments> minimumParams() {
        return Stream.of(Arguments.of(50, 30), Arguments.of(30, 30), Arguments.of(40, 40),
                Arguments.of(70, 60), Arguments.of(60, 60)
        );
    }

    @ParameterizedTest(name = "Root: {0}, successor: {1}")
    @MethodSource("successorParams")
    void testInOrderSuccessor(int root, int successor) {
        BinarySearchTree bst = newBinarySearchTreeForTest();

        ParentAwareIntegerNode node = bst.find(root);
        assertThat(node).isNotNull();

        ParentAwareIntegerNode inOrderSuccessor = bst.inOrderSuccessor(node);
        assertThat(inOrderSuccessor).isNotNull();

        assertThat(inOrderSuccessor.value()).isEqualTo(successor);
    }

    private static Stream<Arguments> successorParams() {
        return Stream.of(Arguments.of(50, 60), Arguments.of(30, 40), Arguments.of(40, 50),
                Arguments.of(70, 80), Arguments.of(60, 70), Arguments.of(80, 50)
        );
    }

    private BinarySearchTree newBinarySearchTreeForTest() {
        return BinarySearchTree.builder()
                .root(50)
                .node(30)
                .node(40)
                .node(70)
                .node(60)
                .node(80)
                .build();
    }
}
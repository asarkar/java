package org.abhijitsarkar.binarytree.util;

import org.abhijitsarkar.binarytree.IntegerNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.abhijitsarkar.binarytree.util.BinaryTreeFactory.newBinaryTree;
import static org.abhijitsarkar.binarytree.util.BinaryTreePrinter.print;
import static org.abhijitsarkar.binarytree.util.BinaryTreeUtil.isSame;
import static org.abhijitsarkar.binarytree.util.BinaryTreeUtil.lowestCommonAncestor;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Abhijit Sarkar
 */
class BinaryTreeUtilTest {
    private static final IntegerNode ROOT = newBinaryTree();

    @BeforeEach
    void before() {
        print(ROOT);
    }

    @Test
    void testHeight() {
        assertThat(BinaryTreeUtil.height(ROOT)).isEqualTo(4);
    }

    @Test
    void testIsSame() {
        IntegerNode sameAsRoot = newBinaryTree();

        assertThat(isSame(ROOT, sameAsRoot)).isTrue();

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

        assertThat(isSame(ROOT, fifteen)).isFalse();
    }

    @Test
    void testLowestCommonAncestor() {
        IntegerNode five = IntegerNode.builder()
                .value(5)
                .build();

        IntegerNode nine = IntegerNode.builder()
                .value(9)
                .build();

        IntegerNode eleven = IntegerNode.builder()
                .left(nine)
                .right(five)
                .value(11)
                .build();

        IntegerNode two = IntegerNode.builder()
                .value(2)
                .build();

        IntegerNode six = IntegerNode.builder()
                .left(two)
                .right(eleven)
                .value(6)
                .build();

        IntegerNode seven = IntegerNode.builder()
                .value(7)
                .build();

        IntegerNode thirteen = IntegerNode.builder()
                .left(seven)
                .value(13)
                .build();

        IntegerNode eight = IntegerNode.builder()
                .right(thirteen)
                .value(8)
                .build();

        IntegerNode three = IntegerNode.builder()
                .left(six)
                .right(eight)
                .value(3)
                .build();

        print(three);

        IntegerNode lca = lowestCommonAncestor(three, 2, 5);
        assertThat(lca).isNotNull();
        assertThat(lca.value()).isEqualTo(6);

        lca = lowestCommonAncestor(three, 8, 11);
        assertThat(lca).isNotNull();
        assertThat(lca.value()).isEqualTo(3);

        lca = lowestCommonAncestor(three, 2, 19);
        assertThat(lca).isNull();
    }
}
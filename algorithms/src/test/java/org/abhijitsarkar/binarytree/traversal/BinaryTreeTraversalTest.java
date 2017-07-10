package org.abhijitsarkar.binarytree.traversal;

import org.abhijitsarkar.binarytree.IntegerNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.abhijitsarkar.binarytree.traversal.BinaryTreeWalkerFactory.newBinaryTreeWalker;
import static org.abhijitsarkar.binarytree.traversal.TreeTraversalStrategy.IN_ORDER;
import static org.abhijitsarkar.binarytree.traversal.TreeTraversalStrategy.LEVEL_ORDER;
import static org.abhijitsarkar.binarytree.traversal.TreeTraversalStrategy.POST_ORDER;
import static org.abhijitsarkar.binarytree.traversal.TreeTraversalStrategy.PRE_ORDER;
import static org.abhijitsarkar.binarytree.util.BinaryTreeFactory.newBinaryTree;
import static org.abhijitsarkar.binarytree.util.BinaryTreePrinter.print;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Abhijit Sarkar
 */
class BinaryTreeTraversalTest {
    private static final IntegerNode ROOT = newBinaryTree();

    @BeforeEach
    void before() {
        print(ROOT);
    }

    @ParameterizedTest(name = "{0} => {1}")
    @MethodSource("params")
    void testTraversal(TreeTraversalStrategy traversalStrategy, List<Integer> expected) {
        List<Integer> raw = newBinaryTreeWalker(traversalStrategy)
                .walk(ROOT)
                .stream()
                .map(IntegerNode::value)
                .collect(toList());

        List<Integer> visited = raw.stream()
                .filter(value -> value > Integer.MIN_VALUE)
                .collect(toList());

        assertThat(visited).hasSize(expected.size());

        assertThat(visited).isEqualTo(expected);

        System.out.printf("%s => %s\n", traversalStrategy, visited);

        if (traversalStrategy == LEVEL_ORDER) {
            raw.stream()
                    .collect(
                            () -> {
                                ArrayList<Integer> list = new ArrayList<>();
                                ArrayList<List<Integer>> lists = new ArrayList<>();
                                lists.add(list);

                                return lists;
                            },
                            (acc, elem) -> {
                                if (elem > Integer.MIN_VALUE) {
                                    acc.get(acc.size() - 1).add(elem);
                                } else {
                                    acc.add(new ArrayList<>());
                                }
                            },
                            List::addAll
                    )
                    .stream()
                    .map(list -> list.stream()
                            .map(i -> Integer.toString(i))
                            .collect(joining(", ")))
                    .forEach(System.out::println);
        }
    }

    private static final Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(POST_ORDER, asList(5, 3, 6, 15, 9, 8, 2, 30, 10)),
                Arguments.of(PRE_ORDER, asList(10, 15, 3, 5, 6, 30, 2, 9, 8)),
                Arguments.of(IN_ORDER, asList(5, 3, 15, 6, 10, 30, 9, 2, 8)),
                Arguments.of(LEVEL_ORDER, asList(10, 15, 30, 3, 6, 2, 5, 9, 8))
        );
    }

    @Test
    void testValueNotNull() {
        assertThatThrownBy(() -> IntegerNode.builder().build())
                .isInstanceOf(NullPointerException.class);
    }
}
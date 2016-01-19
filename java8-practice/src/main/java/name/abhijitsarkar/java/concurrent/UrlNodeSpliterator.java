package name.abhijitsarkar.java.concurrent;

import lombok.extern.slf4j.Slf4j;
import name.abhijitsarkar.java.domain.UrlNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class UrlNodeSpliterator implements Spliterator<UrlNode> {
    public static final int MAX_DEPTH = 5;
    public static final int MAX_BREADTH = 10;

    private int depth;

    private final Queue<UrlNode> nodes;
    private final Function<? super UrlNode, ? extends Stream<? extends UrlNode>> mapper;

    public UrlNodeSpliterator(String rootUrl) {
        this(rootNodeAsQueue(rootUrl), 0, new UrlNodeMapper());
    }

    UrlNodeSpliterator(String rootUrl, Function<? super UrlNode, ? extends Stream<? extends UrlNode>> testMapper) {
        this(rootNodeAsQueue(rootUrl), 0, testMapper);
    }

    private UrlNodeSpliterator(Queue<UrlNode> nodes, int depth,
                               Function<? super UrlNode, ? extends Stream<? extends UrlNode>> mapper) {
        this.nodes = nodes;
        this.depth = depth;
        this.mapper = mapper;
    }

    private static Queue<UrlNode> rootNodeAsQueue(String rootUrl) {
        Queue<UrlNode> nodes = new LinkedList<>();
        nodes.add(new UrlNode(0, rootUrl, null));

        return nodes;
    }

    /* trySplit is called multiple times, with different depths but same nodes, by the same thread.
     * Since the queue is only polled by tryAdvance, we need
     * some condition to stop splitting. The mapper keeps a list of visited URLs so
     * if trySplit is called again with the same nodes, the mapper returns an empty collection of children.
     * We use that to stop splitting.
     */
    @Override
    public Spliterator<UrlNode> trySplit() {
        log.info("Depth: {}, num nodes: {}.", depth, nodes.size());

        if (depth == MAX_DEPTH || nodes.isEmpty()) {
            return null;
        }

        Queue<UrlNode> children = nodes.stream()
                .flatMap(mapper)
                .filter(node -> !node.isEmpty())
                .limit(MAX_BREADTH)
                .collect(toCollection(() -> new LinkedList<UrlNode>()));

        if (children.isEmpty()) {
            return null;
        }

        return new UrlNodeSpliterator(children, ++depth, mapper);
    }

    @Override
    public boolean tryAdvance(Consumer<? super UrlNode> action) {
        UrlNode node = nodes.poll();

        log.info("Node polled: {}. Depth: {}.", node, depth);

        if (node != null) {
            action.accept(node);

            return nodes.isEmpty();
        }

        return false;
    }

    @Override
    public long estimateSize() {
        return (MAX_DEPTH - depth) * MAX_BREADTH;
    }

    @Override
    public int characteristics() {
        return NONNULL;
    }
}


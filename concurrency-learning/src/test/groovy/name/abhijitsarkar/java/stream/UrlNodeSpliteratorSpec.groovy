package name.abhijitsarkar.java.stream

import name.abhijitsarkar.java.domain.UrlNode
import name.abhijitsarkar.java.stream.UrlNodeSpliterator
import spock.lang.Ignore
import spock.lang.Specification

import java.util.function.Consumer
import java.util.function.Function
import java.util.stream.Stream
import java.util.stream.StreamSupport

import static name.abhijitsarkar.java.stream.UrlNodeSpliterator.MAX_DEPTH
/**
 * @author Abhijit Sarkar
 */
class UrlNodeSpliteratorSpec extends Specification {
    Function<? super UrlNode, ? extends Stream<? extends UrlNode>> testMapper = { node ->
        ([childNode(1, node), childNode(2, node)] as List).stream()
    }

    private static class TestConsumer implements Consumer<UrlNode> {
        private Collection<UrlNode> nodes = [] as List

        @Override
        void accept(UrlNode urlNode) {
            nodes.add(urlNode)
        }
    }

    @Ignore
    private childNode(int idx, UrlNode node) {
        new UrlNode(node.depth + 1, "url$idx", node.url)
    }

    def "trySplit splits with 2 children"() {
        setup:
        UrlNodeSpliterator spliterator = new UrlNodeSpliterator('rootUrl', testMapper)

        when:
        Spliterator<UrlNode> split = spliterator.trySplit()
        Queue<UrlNode> nodes = split?.nodes

        then:
        split
        nodes?.size() == 2

        nodes.each {
            assert it.depth == 1
            assert it.parentUrl == 'rootUrl'
            assert it.url ==~ /url[12]/
        }
    }

    def "trySplit terminates when max depth is reached"() {
        setup:
        UrlNodeSpliterator spliterator = new UrlNodeSpliterator('rootUrl', testMapper)

        when:
        Spliterator<UrlNode> split = (1..MAX_DEPTH).inject(spliterator.trySplit()) { acc, val -> acc.trySplit() }

        then:
        !split
    }

    def "tryAdvance accepts nodes from non-empty queue"() {
        setup:
        UrlNodeSpliterator spliterator = new UrlNodeSpliterator('rootUrl', testMapper)
        TestConsumer testConsumer = new TestConsumer()

        when:
        boolean tryAdvance = spliterator.tryAdvance(testConsumer)

        then:
        tryAdvance

        UrlNode node = testConsumer.nodes?.getAt(0)

        node == new UrlNode(0, 'rootUrl', null)
    }

    def "tryAdvance returns false when queue is empty"() {
        setup:
        UrlNodeSpliterator spliterator = new UrlNodeSpliterator('rootUrl', testMapper)
        TestConsumer testConsumer = new TestConsumer()

        when:
        boolean tryAdvance = spliterator.tryAdvance(testConsumer)
        tryAdvance = spliterator.tryAdvance(testConsumer)

        then:
        !tryAdvance
    }

    def "gets urls for real"() {
        setup:
        UrlNodeSpliterator spliterator = new UrlNodeSpliterator('http://en.wikipedia.org/wiki/Java_(programming_language)')

        when:
        StreamSupport.stream(spliterator, true).map({ node -> node.getUrl() }).forEach({ println(it) })

        then:
        true
    }
}

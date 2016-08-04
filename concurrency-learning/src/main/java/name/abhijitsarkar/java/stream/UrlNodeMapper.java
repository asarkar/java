package name.abhijitsarkar.java.stream;

import lombok.extern.slf4j.Slf4j;
import name.abhijitsarkar.java.domain.UrlNode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class UrlNodeMapper implements Function<UrlNode, Stream<? extends UrlNode>> {
    private final Queue<String> visited = new LinkedList<>();
    private final Lock monitor = new ReentrantLock();

    @Override
    public Stream<? extends UrlNode> apply(UrlNode node) {
        log.info("Node: {}.", node);

        boolean alreadyVisited = visit(node.getUrl());

        if (alreadyVisited) {
            log.info("Already visited URL: {}.", node.getUrl());

            return Stream.empty();
        }

        Optional<Document> doc = exchange(node.getUrl());

        return doc.map(d -> d.select("a[href]"))
                .map(elements -> elements.stream().map(link -> urlNode(node, link)))
                .map(nodes -> nodes.limit(UrlNodeSpliterator.MAX_BREADTH))
                .orElse(Stream.empty());
    }

    private boolean visit(String url) {
        boolean lockAcquired = false;

        try {
            String normalizedUrl = normalize(url);

            lockAcquired = monitor.tryLock(3000l, SECONDS);

            if (lockAcquired && !visited.contains(normalizedUrl)) {
                visited.add(normalizedUrl);

                return false;
            }
        } catch (InterruptedException e) {
            log.error("Failed to determine if URL: {} is already visited. Defaulting to yes.", url, e);
        } finally {
            monitor.unlock();
        }

        return true;
    }

    private UrlNode urlNode(UrlNode node, Element link) {
        return new UrlNode(node.getDepth() + 1, link.attr("abs:href"), node.getUrl());
    }

    /* Not foolproof. */
    private String normalize(String url) {
        return url.split("#")[0];
    }

    private boolean isAlreadyVisited(final String url) {
        return visited.contains(url);
    }

    private Optional<Document> exchange(String url) {
        Document doc = null;

        try {
            Connection.Response response = Jsoup.connect(url).execute();

            if (response.statusCode() < 400) {
                doc = response.parse();
            }
        } catch (IOException e) {
            log.error("Error fetching content from URL: {}, message: {}.", url, e.getMessage());
        }

        return Optional.ofNullable(doc);
    }
}

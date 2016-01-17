package name.abhijitsarkar.java.service;

import lombok.extern.slf4j.Slf4j;
import name.abhijitsarkar.java.domain.UrlNode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Stream;

import static name.abhijitsarkar.java.service.UrlNodeSpliterator.MAX_BREADTH;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class UrlNodeMapper implements Function<UrlNode, Stream<? extends UrlNode>> {
    private final Queue<String> visited = new LinkedList<>();

    @Override
    public Stream<? extends UrlNode> apply(UrlNode node) {
        log.info("Node: {}.", node);

        synchronized (this) {
            String normalizedUrl = normalize(node.getUrl());

            if (isAlreadyVisited(normalizedUrl)) {
                log.info("Already visited URL: {}.", node.getUrl());

                return Stream.empty();
            }

            visited.add(normalizedUrl);
        }

        Document doc = null;
        try {
            Connection.Response response = Jsoup.connect(node.getUrl()).execute();

            if (response.statusCode() < 400) {
                doc = response.parse();
            }
        } catch (IOException e) {
            log.error("Error fetching content from URL: {}, message: {}.", node.getUrl(), e.getMessage());
        }

        if (doc == null) {
            return Stream.empty();
        }

        Stream<UrlNode> nodes = doc.select("a[href]")
                .stream()
                .peek(url -> log.info("Found URL: {} for parent URL: {}.", url, node.getUrl()))
                .map(link -> new UrlNode(node.getDepth() + 1, link.attr("abs:href"), node.getUrl()))
                .limit(MAX_BREADTH);

        return nodes;
    }

    /* Not foolproof. */
    private String normalize(String url) {
        return url.split("#")[0];
    }

    private boolean isAlreadyVisited(final String url) {
        return visited.contains(url);
    }
}

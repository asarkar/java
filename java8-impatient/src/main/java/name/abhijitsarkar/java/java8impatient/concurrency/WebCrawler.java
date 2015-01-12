package name.abhijitsarkar.java.java8impatient.concurrency;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.ForkJoinPool.commonPool;
import static java.util.stream.Collectors.toSet;
import static org.jsoup.Jsoup.connect;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebCrawler {
    public static final Logger LOGGER = LoggerFactory
	    .getLogger(WebCrawler.class);

    WebCrawler() {
	commonPool().awaitQuiescence(10, TimeUnit.SECONDS);
    }

    private final ConcurrentLinkedQueue<String> visited = new ConcurrentLinkedQueue<>();

    /**
     * Q10: Write a program that asks the user for a URL, then reads the web
     * page at that URL, and then displays all the links. Use a
     * {@code CompletableFuture} for each stage. Don't call {@code get}. To
     * prevent your program from terminating prematurely, call
     * 
     * <pre>
     * <code>
     * ForkJoinPool.commonPool().awaitQuiescence(10, TimeUnit.SECONDS);
     * </pre>
     * 
     * </code>
     * 
     * @param startingUrl
     *            URL that's content to be read.
     * @param depth
     *            Recursion depth. 0 means only this URL is printed, 1 means
     *            this URL and all URLS on this page are printed, so on and so
     *            forth.
     * @param breadth
     *            Number of URLs to print on a page. For example, a breadth of
     *            10 will print 10 URLs regardless of how many are actually
     *            present on the page.
     */
    void crawl(final String startingUrl, final int depth, final int breadth) {
	if (isMaximumDepthReached(depth)) { // Prevent too deep recursion.
	    LOGGER.info(
		    "Reached maximum allowed depth. Not going to crawl URL: {}.",
		    startingUrl);

	    return;
	} else if (isAlreadyVisited(startingUrl)) { // Prevent cycles; not
	    // foolproof.
	    LOGGER.info("Already visited URL: {}.", startingUrl);

	    return;
	}

	LOGGER.info("\n");
	LOGGER.info("URL: {}, depth: {}.", startingUrl, depth);

	visited.add(startingUrl);

	supplyAsync(getContent(startingUrl))
		.thenApply(getURLs(breadth))
		.thenApply(doForEach(depth, breadth))
		.thenApply(futures -> futures.toArray(CompletableFuture[]::new))
		.thenAccept(CompletableFuture::allOf).join();

	/* Alternative implementation */
	// of(startingUrl).map(url -> supplyAsync(getContent(url)))
	// .map(docFuture -> docFuture.thenApply(getURLs(breadth)))
	// .map(urlsFuture -> urlsFuture.thenAccept(doForEach(depth,
	// breadth))).findFirst()
	// .orElseThrow(completionException("Something went wrong when crawling URL: "
	// + startingUrl)).join();
    }

    private boolean isMaximumDepthReached(final int depth) {
	return depth <= 0;
    }

    private boolean isAlreadyVisited(final String url) {
	return visited.contains(url);
    }

    /* Alternative implementation */
    // private Supplier<RuntimeException> completionException(final String
    // message) {
    // return () -> new CompletionException(new RuntimeException(message));
    // }

    private Supplier<Document> getContent(final String url) {
	return () -> {
	    try {
		/*
		 * If it requires a proxy setting, Run Configurations ->
		 * Arguments -> VM Arguments -> -Dhttp.proxyHost=proxyHost and
		 * -Dhttp.proxyPort=proxyPort.
		 */
		return connect(url).get();
	    } catch (IOException e) {
		throw new UncheckedIOException(
			"Something went wrong when fetching the contents of the URL: "
				+ url, e);
	    }
	};
    }

    private Function<Document, Set<String>> getURLs(final int limit) {
	return doc -> {
	    LOGGER.info("\n");

	    LOGGER.info("Getting URLs for document: {}.", doc.baseUri());

	    return doc.select("a[href]").stream()
		    .map(link -> link.attr("abs:href")).limit(limit)
		    .peek(LOGGER::info).collect(toSet());
	};
    }

    /* Alternative implementation */
    // private Consumer<Set<String>> doForEach(final int depth, final int
    // breadth) {
    // return urls -> urls.stream().forEach(url -> crawl(url, depth - 1,
    // breadth));
    // }

    private Function<Set<String>, Stream<CompletableFuture<Void>>> doForEach(
	    final int depth, final int breadth) {
	return urls -> urls.stream().map(url -> {
	    crawl(url, depth - 1, breadth);

	    return completedFuture(null);
	});
    }

}

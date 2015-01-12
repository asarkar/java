package name.abhijitsarkar.java.java8impatient.concurrency;

import org.junit.Test;

public class WebCrawlerTest {
    @Test
    public void testCrawl() {
	new WebCrawler().crawl(
		"http://en.wikipedia.org/wiki/Java_(programming_language)", 3,
		10);
    }
}

package name.abhijitsarkar.java.concurrent

import name.abhijitsarkar.java.repository.YahooApiClient
import name.abhijitsarkar.java.repository.YahooApiStubClient
import spock.lang.Specification
import spock.lang.Unroll

import java.util.stream.StreamSupport

import static java.util.stream.Collectors.toMap
import static spock.util.matcher.HamcrestMatchers.closeTo
/**
 * @author Abhijit Sarkar
 */
class TickerSpliteratorSpec extends Specification {
    YahooApiClient client = new YahooApiStubClient()
    List<String> tickers = ['YHOO', 'AAPL', 'GOOG', 'MSFT'] as List
    TickerSpliterator spl = TickerSpliterator
            .builder()
            .client(client)
            .tickers(tickers)
            .endIdx(tickers.size())
            .build();

    @Unroll
    def "splits tickers in groups of #tickersSubsetSize"() {
        setup:
        TickerSpliterator spliterator = spl.toBuilder()
                .tickersSubsetSize(tickersSubsetSize)
                .build();

        when:
        Map<String, Double> actualPrices = StreamSupport.stream(spliterator, true)
                .collect(toMap({ e -> e.key }, { e -> e.value }))
        Map<String, Double> expectedPrices = ['YHOO': 29.35d, 'AAPL': 97.17d, 'GOOG': 695.99d, 'MSFT': 51.09d]

        then:
        actualPrices.size() == expectedPrices.size()
        expectedPrices.each { assert closeTo(actualPrices[it.key], 0.1d).matches(it.value) }

        where:
        tickersSubsetSize | _
        1                 | _
        2                 | _
        4                 | _
    }
}

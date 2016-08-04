package name.abhijitsarkar.java.concurrent

import name.abhijitsarkar.java.repository.YahooApiClient
import name.abhijitsarkar.java.repository.YahooApiStubClient
import spock.lang.Specification
import spock.lang.Unroll

import static spock.util.matcher.HamcrestMatchers.closeTo
import static spock.util.matcher.HamcrestSupport.that
/**
 * @author Abhijit Sarkar
 */
class NetAssetTaskSpec extends Specification {
    @Unroll
    def "calculates net asset using RecursiveTask with threshold #threshold"() {
        setup:
        YahooApiClient client = new YahooApiStubClient()
        Map<String, Integer> stocks = ['YHOO': 1, 'AAPL': 2, 'GOOG': 5, 'MSFT': 1]

        /* Makes a copy so that any changes to it doesn't affect the backing map. */
        Set<String> remaining = stocks.keySet().collect()

        NetAssetTask netAsset = NetAssetTask.builder()
                .client(client)
                .stocks(stocks)
                .remaining(remaining)
                .threshold(threshold)
                .build();
        double expectedNetAsset = 29.35d + 97.17d * 2 + 695.99d * 5 + 51.09d // 3754.73d

        /* If using when-then, drop 'that' and remove comma */
        expect:
        that netAsset.invoke(), closeTo(expectedNetAsset, 0.1d)

        where:
        threshold | _
        1         | _
        2         | _
        4         | _
    }
}

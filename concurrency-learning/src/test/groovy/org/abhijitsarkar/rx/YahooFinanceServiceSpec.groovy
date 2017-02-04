package org.abhijitsarkar.rx

import org.abhijitsarkar.repository.YahooApiStubClient
import spock.lang.Specification
import spock.lang.Unroll

import static spock.util.matcher.HamcrestMatchers.closeTo


/**
 * @author Abhijit Sarkar
 */
class YahooFinanceServiceSpec extends Specification {
    YahooFinanceService service = new YahooFinanceService(new YahooApiStubClient())
    private static final long NUM_GOOGLE_STOCKS = 3L
    private static final long NUM_APPLE_STOCKS = 5L
    private static final double PRICE_GOOGLE_STOCK = 695.99d
    private static final double PRICE_APPLE_STOCK = 97.17d

    @Unroll
    def "calculates net asset using #method"() {
        setup:
        Map.Entry<String, Long> stock1 = new AbstractMap.SimpleImmutableEntry('GOOG', NUM_GOOGLE_STOCKS)
        Map.Entry<String, Long> stock2 = new AbstractMap.SimpleImmutableEntry('AAPL', NUM_APPLE_STOCKS)

        when:
        double netAsset = service."$method"(stock1, stock2)
        def expectedNetAsset = PRICE_GOOGLE_STOCK * NUM_GOOGLE_STOCKS + PRICE_APPLE_STOCK * NUM_APPLE_STOCKS

        then:
        netAsset closeTo(expectedNetAsset, 0.1d)

        where:
        method << ['netAsset1', 'netAsset2', 'netAsset3', 'netAsset4', 'netAsset5']
    }
}
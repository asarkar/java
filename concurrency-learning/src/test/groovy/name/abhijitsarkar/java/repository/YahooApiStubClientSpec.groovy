package name.abhijitsarkar.java.repository

import spock.lang.Specification

import static spock.util.matcher.HamcrestMatchers.closeTo
/**
 * @author Abhijit Sarkar
 */
class YahooApiStubClientSpec extends Specification {
    def "retrieves stocks prices"() {
        setup:
        YahooApiClient client = new YahooApiStubClient()
        def tickers = ['YHOO', 'AAPL', 'GOOG', 'MSFT'] as List

        when:
        Map<String, Double> actualPrices = client.getPrice(tickers)
        Map<String, Double> expectedPrices = ['YHOO': 29.35d, 'AAPL': 97.17d, 'GOOG': 695.99d, 'MSFT': 51.09d]

        then:
        actualPrices.size() == expectedPrices.size()
        expectedPrices.each { assert it.value, closeTo(actualPrices[it.key], 0.1d) }
    }
}

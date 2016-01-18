package name.abhijitsarkar.java.service

import name.abhijitsarkar.java.repository.YahooApiClient
import name.abhijitsarkar.java.repository.YahooApiLiveClient
import name.abhijitsarkar.java.repository.YahooApiStubClient
import spock.lang.Ignore
import spock.lang.Specification

import java.util.function.Function
import java.util.stream.Stream

import static java.nio.file.Files.lines
import static java.nio.file.Paths.get
import static java.util.stream.Collectors.toMap

/**
 * @author Abhijit Sarkar
 */
class FinanceServiceSpec extends Specification {
    def "calculates net asset for stub client using Spliterator"() {
        setup:
        YahooApiClient client = new YahooApiStubClient()
        FinanceService service = new FinanceService(client)
        Map<String, Integer> stocks = ['YHOO': 1, 'AAPL': 2, 'GOOG': 5, 'MSFT': 1]

        when:
        double netAsset = service.netAsset(stocks)

        then:
        assert netAsset == 29.35 + 97.17 * 2 + 695.99 * 5 + 51.09
    }

    def "calculates net asset for stub client using CompletableFuture"() {
        setup:
        YahooApiClient client = new YahooApiStubClient()
        FinanceService service = new FinanceService(client)
        Map<String, Integer> stocks = ['YHOO': 1, 'AAPL': 2, 'GOOG': 5, 'MSFT': 1]

        when:
        double netAsset = service.netAsset2(stocks)

        then:
        assert netAsset == 29.35 + 97.17 * 2 + 695.99 * 5 + 51.09
    }

    def "calculates net asset for live client using Spliterator"() {
        setup:
        YahooApiClient client = new YahooApiLiveClient()
        FinanceService service = new FinanceService(client)

        Map<String, Integer> stocks = getStocks()

        when:
        double netAsset = service.netAsset(stocks)

        then:
        assert netAsset > 0
    }

    @Ignore
    private Map<String, Integer> getStocks() {
        Stream<String> lines = lines(get(getClass().getResource('/stocks.txt').toURI()))

        return lines.map(stocksParser).collect(toMap({ e -> e.key }, { e -> e.value }))
    }

    def stocksParser = new Function<String, Map.Entry<String, Integer>>() {
        @Override
        Map.Entry<String, Integer> apply(String line) {
            String[] tokens = line.split(',')

            assert tokens?.length == 2: "Unexpected format: $line. Expected <ticker>,<price>."

            return new AbstractMap.SimpleImmutableEntry<String, Integer>(tokens[0], tokens[1].toInteger())
        }
    }
}

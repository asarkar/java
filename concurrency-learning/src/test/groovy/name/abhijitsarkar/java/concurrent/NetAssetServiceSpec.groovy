package name.abhijitsarkar.java.concurrent

import name.abhijitsarkar.java.repository.YahooApiClient
import name.abhijitsarkar.java.repository.YahooApiLiveClient
import name.abhijitsarkar.java.repository.YahooApiStubClient
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.Function
import java.util.stream.Stream

import static java.nio.file.Files.lines
import static java.nio.file.Paths.get
import static java.util.stream.Collectors.toMap
import static spock.util.matcher.HamcrestMatchers.closeTo
/**
 * @author Abhijit Sarkar
 */
class NetAssetServiceSpec extends Specification {
    @Unroll
    def "calculates net asset for stub client using #method"() {
        setup:
        YahooApiClient client = new YahooApiStubClient()
        NetAssetService service = new NetAssetService(client)
        Map<String, Integer> stocks = ['YHOO': 1, 'AAPL': 2, 'GOOG': 5, 'MSFT': 1]

        when:
        double actualNetAsset = service."$method"(stocks)
        double expectedNetAsset = 29.35d + 97.17d * 2 + 695.99d * 5 + 51.09d // 3754.73d

        println("Net assert: $actualNetAsset")

        then:
        actualNetAsset closeTo(expectedNetAsset, 0.1d)

        where:
        method      | _
        'netAsset'  | _
        'netAsset2' | _
        'netAsset3' | _
    }

    def "calculates net asset for live client using Spliterator"() {
        setup:
        YahooApiClient client = new YahooApiLiveClient()
        NetAssetService service = new NetAssetService(client)

        Map<String, Integer> stocks = getStocks()

        when:
        double netAsset = service.netAsset(stocks)

        then:
        netAsset > 0
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

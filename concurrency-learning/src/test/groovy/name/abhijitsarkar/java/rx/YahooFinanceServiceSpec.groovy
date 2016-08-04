package name.abhijitsarkar.java.rx

import name.abhijitsarkar.java.repository.YahooApiStubClient
import spock.lang.Specification
import spock.lang.Unroll


/**
 * @author Abhijit Sarkar
 */
class YahooFinanceServiceSpec extends Specification {
    YahooFinanceService service = new YahooFinanceService(new YahooApiStubClient(true))

    @Unroll
    def "calculates net asset using #method"() {
        setup:
        Map.Entry<String, Long> stock1 = new AbstractMap.SimpleImmutableEntry('GOOG', 1L)
        Map.Entry<String, Long> stock2 = new AbstractMap.SimpleImmutableEntry('AAPL', 1L)

        when:
        double netAsset = service."$method"(stock1, stock2)
        println netAsset

        then:
        netAsset > 0

        where:
        method << ['netAsset1', 'netAsset2', 'netAsset3']
    }
}
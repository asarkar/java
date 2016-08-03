package name.abhijitsarkar.java.rxjava

import spock.lang.Specification
import spock.lang.Unroll


/**
 * @author Abhijit Sarkar
 */
class YahooFinanceServiceSpec extends Specification {
    YahooFinanceService service = new YahooFinanceService()

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
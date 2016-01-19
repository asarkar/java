package name.abhijitsarkar.java.stream
import name.abhijitsarkar.java.domain.NytBestSellersList
import name.abhijitsarkar.java.repository.NytBestSellersApiClient
import name.abhijitsarkar.java.repository.NytBestSellersApiStubClient
import spock.lang.Shared
import spock.lang.Specification

import static name.abhijitsarkar.java.stream.BestSellersService.groupByRank
import static name.abhijitsarkar.java.stream.BestSellersService.rankWithMaxNumBooks
import static name.abhijitsarkar.java.stream.BestSellersService.rankWithMaxNumBooks2
/**
 * @author Abhijit Sarkar
 */
class BestSellersServiceSpec extends Specification {
    @Shared
    NytBestSellersApiClient nytApiClient;

    def setupSpec() {
        nytApiClient = new NytBestSellersApiStubClient()
    }

    def cleanupSpec() {
        nytApiClient.close()
    }

    def "groups by rank"() {
        setup:
        Collection<NytBestSellersList> lists = nytApiClient.bestSellersListsOverview()

        when:
        Map<Integer, Collection<String>> groups = groupByRank(lists)

        then:
        (groups && groups.size() == 5)

        [
                1: 'THE GIRL ON THE TRAIN',
                2: 'THE MARTIAN',
                3: 'ROGUE LAWYER',
                4: 'HUMANS OF NEW YORK: STORIES',
                5: 'THE BOOK WITH NO PICTURES'
        ].each {
            assert groups[it.key].contains(it.value)
        }
    }

    def "counts by rank"() {
        setup:
        Collection<NytBestSellersList> lists = nytApiClient.bestSellersListsOverview()

        when:
        Map<Integer, Long> groups = BestSellersService."$method"(lists)

        then:
        (groups && groups.size() == 5)
        groups.each { assert it.value == 43 }

        where:
        method         | _
        'countByRank'  | _
        'countByRank2' | _
        'countByRank3' | _
        'countByRank4' | _
    }

    def "rank with max number of books"() {
        setup:
        Collection<NytBestSellersList> lists = nytApiClient.bestSellersListsOverview()

        expect:
        rankWithMaxNumBooks(lists) == 5
    }

    def "rank with max number of books using custom collector"() {
        setup:
        Collection<NytBestSellersList> lists = nytApiClient.bestSellersListsOverview()

        expect:
        rankWithMaxNumBooks2(lists) == 5
    }
}

package name.abhijitsarkar.java.masteringlambdas.service

import name.abhijitsarkar.java.masteringlambdas.domain.NytBestSellersList
import name.abhijitsarkar.java.masteringlambdas.repository.NytBestSellersApiClient
import name.abhijitsarkar.java.masteringlambdas.repository.NytBestSellersApiClientFactory
import spock.lang.Shared
import spock.lang.Specification

import static name.abhijitsarkar.java.masteringlambdas.service.BestSellersService.groupByRank
import static name.abhijitsarkar.java.masteringlambdas.service.BestSellersService.rankWithMaxNumBooks
import static name.abhijitsarkar.java.masteringlambdas.service.BestSellersService.rankWithMaxNumBooks2

/**
 * @author Abhijit Sarkar
 */
class BestSellersServiceSpec extends Specification {
    @Shared
    NytBestSellersApiClient nytApiClient;

    def setupSpec() {
        nytApiClient = NytBestSellersApiClientFactory.getInstance(false)
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
        assert (groups && groups.size() == 5)

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

        expect:
        Map<Integer, Long> groups = NytBestSellersApiClient."$method"(lists)
        assert (groups && groups.size() == 5)
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
        assert rankWithMaxNumBooks(lists) == 5
    }

    def "rank with max number of books using custom collector"() {
        setup:
        Collection<NytBestSellersList> lists = nytApiClient.bestSellersListsOverview()

        expect:
        assert rankWithMaxNumBooks2(lists) == 5
    }
}

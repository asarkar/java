package name.abhijitsarkar.java.masteringlambdas.repository

import name.abhijitsarkar.java.masteringlambdas.domain.NytBestSellersList
import spock.lang.Shared
import spock.lang.Specification

import static name.abhijitsarkar.java.masteringlambdas.repository.NytBestSellersApiClient.groupByRank

/**
 * @author Abhijit Sarkar
 */
class NytBestSellersApiClientSpec extends Specification {
    @Shared
    NytBestSellersApiClient nytApiClient;

    def setupSpec() {
        nytApiClient = NytBestSellersApiClientFactory.getInstance(false)
    }

    def cleanupSpec() {
        nytApiClient.close()
    }

    def "retrieves best sellers lists"() {
        when:
        Collection<String> lists = nytApiClient.bestSellersListsNames()

        then:
        assert lists
    }

    def "retrieves best sellers lists overview"() {
        when:
        Collection<NytBestSellersList> lists = nytApiClient.bestSellersListsOverview()

        then:
        assert lists
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
        groups.each { assert it.value > 1 }

        where:
        method         | _
        'countByRank'  | _
        'countByRank2' | _
        'countByRank3' | _
        'countByRank4' | _
    }
}

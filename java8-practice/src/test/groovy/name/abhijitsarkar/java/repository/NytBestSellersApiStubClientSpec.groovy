package name.abhijitsarkar.java.repository

import name.abhijitsarkar.java.domain.NytBestSellersList
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Abhijit Sarkar
 */
class NytBestSellersApiStubClientSpec extends Specification {
    @Shared
    NytBestSellersApiClient nytApiClient;

    def setupSpec() {
        nytApiClient = new NytBestSellersApiStubClient()
    }

    def cleanupSpec() {
        nytApiClient.close()
    }

    def "retrieves best sellers lists"() {
        when:
        Collection<String> lists = nytApiClient.bestSellersListsNames()

        then:
        lists
    }

    def "retrieves best sellers lists overview"() {
        when:
        Collection<NytBestSellersList> lists = nytApiClient.bestSellersListsOverview()

        then:
        lists
    }
}

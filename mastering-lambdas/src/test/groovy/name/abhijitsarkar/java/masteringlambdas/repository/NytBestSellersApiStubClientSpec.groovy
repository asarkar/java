package name.abhijitsarkar.java.masteringlambdas.repository

import name.abhijitsarkar.java.masteringlambdas.domain.NytBestSellersList
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Abhijit Sarkar
 */
class NytBestSellersApiStubClientSpec extends Specification {
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
}

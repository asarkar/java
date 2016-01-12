package name.abhijitsarkar.java.masteringlambdas.repository
import name.abhijitsarkar.java.masteringlambdas.domain.NytBestSellersList
import spock.lang.Shared
import spock.lang.Specification

import static name.abhijitsarkar.java.masteringlambdas.repository.NytBestSellersApiClient.countDuplicates
import static name.abhijitsarkar.java.masteringlambdas.repository.NytBestSellersApiClient.countDuplicates2
import static name.abhijitsarkar.java.masteringlambdas.repository.NytBestSellersApiClient.countDuplicates3
import static name.abhijitsarkar.java.masteringlambdas.repository.NytBestSellersApiClient.findDuplicates
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

    def "groups duplicates by ISBN-13 and verifies that all groups have an ISBN"() {
        setup:
        Collection<NytBestSellersList> lists = nytApiClient.bestSellersListsOverview()

        when:
        Map<String, Collection<String>> duplicates = findDuplicates(lists)

        then:
        assert duplicates

        assert !duplicates.find { it.key?.empty }

        assert duplicates['9781594633669'] == ['THE GIRL ON THE TRAIN'] as Set
    }

    def "counts duplicates by ISBN-13 using counting"() {
        setup:
        Collection<NytBestSellersList> lists = nytApiClient.bestSellersListsOverview()

        when:
        Map<String, Long> duplicates = countDuplicates(lists)

        then:
        countsDuplicates(duplicates)
    }

    def "counts duplicates by ISBN-13 using summingLong"() {
        setup:
        Collection<NytBestSellersList> lists = nytApiClient.bestSellersListsOverview()

        when:
        Map<String, Long> duplicates = countDuplicates2(lists)

        then:
        countsDuplicates(duplicates)
    }

    def "counts duplicates by ISBN-13 using reducing"() {
        setup:
        Collection<NytBestSellersList> lists = nytApiClient.bestSellersListsOverview()

        when:
        Map<String, Long> duplicates = countDuplicates3(lists)

        then:
        countsDuplicates(duplicates)
    }

    void countsDuplicates(Map<String, Long> duplicates) {
        assert duplicates

        assert !duplicates.find { it.key?.empty }

        assert duplicates['9781594633669'] == 1
    }
}

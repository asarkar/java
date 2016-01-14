package name.abhijitsarkar.java.java8lambdas.collectors

import name.abhijitsarkar.java.java8lambdas.domain.Artist
import spock.lang.Specification

import static name.abhijitsarkar.java.java8lambdas.collectors.PracticeQuestionsCh5.fibonacci

/**
 * @author Abhijit Sarkar
 */
class PracticeQuestionsCh5Spec extends Specification {
    def "finds the artist with the longest name"() {
        setup:
        Collection<Artist> artists = [
                Artist.builder()
                        .name('The Beatles')
                        .origin('Liverpool')
                        .members(
                        [
                                'John Lennon',
                                'Paul McCartney',
                                'George Harrison',
                                'Ringo Starr',
                                'Pete Best',
                                'Stuart Sutcliffe'
                        ] as List)
                        .build()
        ] as List

        expect:
        String artist = PracticeQuestionsCh5."$method"(artists)
        assert artist == 'Stuart Sutcliffe'

        where:
        method                       | _
        'findArtistWithLongestName'  | _
        'findArtistWithLongestName2' | _
    }

    def "fibonacci"() {
        expect:
        assert fibonacci(index) == value

        where:
        index | value
        -1    | 0
        0     | 0
        1     | 0
        2     | 1
        5     | 3
        12    | 89
    }
}

package name.abhijitsarkar.java.concurrent

import name.abhijitsarkar.java.concurrent.UrlNodeMapper
import name.abhijitsarkar.java.domain.UrlNode
import spock.lang.Specification

import java.util.stream.Stream

import static java.util.stream.Collectors.toList

/**
 * @author Abhijit Sarkar
 */
class UrlNodeMapperSpec extends Specification {
    def "gets urls"() {
        setup:
        UrlNodeMapper mapper = new UrlNodeMapper()

        when:
        Stream<? extends UrlNode> stream = mapper.apply(new UrlNode(0, 'https://en.wikipedia.org/wiki/Spock', null))
        List<? extends UrlNode> nodes = stream.collect(toList())

        then:
        nodes
    }
}

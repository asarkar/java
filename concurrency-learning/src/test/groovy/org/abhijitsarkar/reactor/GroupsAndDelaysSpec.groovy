package org.abhijitsarkar.reactor

import reactor.core.publisher.Flux
import spock.lang.Specification

import java.util.function.Function

/**
 * @author Abhijit Sarkar
 */
class GroupsAndDelaysSpec extends Specification {
    final int groupMemberDelaySeconds = 3
    final int maxRetryCount = 2
    final int retryDelaySeconds = 2
    Function<Integer, Integer> groupByFn
    Function<Integer, Integer> responseMapper

    GroupsAndDelays groupsAndDelays

    final Flux<Integer> src = Flux.fromArray(
            1, 2, 3, 4, 5, 1, 2, 3, 4, 5,
            11, 12, 13, 14, 15, 11, 12, 13, 14, 15,
            21, 22, 23, 24, 25, 21, 22, 23, 24, 25,
            31, 32, 33, 34, 35, 31, 32, 33, 34, 35,
            41, 42, 43, 44, 45, 41, 42, 43, 44, 45
    )

    def setup() {
        groupsAndDelays = new GroupsAndDelays()
        groupsAndDelays.groupMemberDelaySeconds = groupMemberDelaySeconds
        groupsAndDelays.maxRetries = maxRetryCount
        groupsAndDelays.retryDelaySeconds = retryDelaySeconds

        groupByFn = { i -> i % 10 }
        responseMapper = { i -> i }
    }

    def "test each group runs on a separate thread"() {
        when:
        groupsAndDelays.doStuff(src, groupByFn, responseMapper)
                .collectList()
                .block()

        then:
        true
    }
}

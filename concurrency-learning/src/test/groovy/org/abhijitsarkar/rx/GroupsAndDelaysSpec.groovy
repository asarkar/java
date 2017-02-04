package org.abhijitsarkar.rx

import io.reactivex.Flowable
import io.reactivex.functions.Function
import spock.lang.Specification

import java.util.concurrent.ConcurrentHashMap

/**
 * @author Abhijit Sarkar
 */
class GroupsAndDelaysSpec extends Specification {
    final int groupMemberDelaySeconds = 3
    final int remoteCallTimeoutSeconds = 3
    final int maxRetryCount = 2
    final int retryDelaySeconds = 2
    Function<Integer, Integer> groupByFn
    Function<Integer, Integer> responseMapper

    GroupsAndDelays groupsAndDelays

    final Flowable<Integer> src = Flowable.fromArray(
            1, 2, 3, 4, 5, 1, 2, 3, 4, 5,
            11, 12, 13, 14, 15, 11, 12, 13, 14, 15,
            21, 22, 23, 24, 25, 21, 22, 23, 24, 25,
            31, 32, 33, 34, 35, 31, 32, 33, 34, 35,
            41, 42, 43, 44, 45, 41, 42, 43, 44, 45
    )

    def setup() {
        groupsAndDelays = new GroupsAndDelays()
        groupsAndDelays.groupMemberDelaySeconds = groupMemberDelaySeconds
        groupsAndDelays.remoteCallTimeoutSeconds = remoteCallTimeoutSeconds
        groupsAndDelays.maxRetryCount = maxRetryCount
        groupsAndDelays.retryDelaySeconds = retryDelaySeconds
        groupsAndDelays.threadMap = new ConcurrentHashMap<Long, List<Integer>>()
        groupsAndDelays.resultThreadMap = new ConcurrentHashMap<Long, List<Integer>>()

        groupByFn = { i -> i % 10 }
        responseMapper = { i -> i }
    }

    def cleanup() {
        println("Thread map: ${groupsAndDelays.threadMap}")
        println("Result thread map: ${groupsAndDelays.resultThreadMap}")
    }

    def "retries remote call"() {
        setup:
        def remoteClient = Mock(Function)
        remoteClient.apply(_) >> { args -> throw new RuntimeException("boom!") } >> { args -> args[0] }
        groupsAndDelays.remoteClient = remoteClient

        when:
        groupsAndDelays.doStuff(src, groupByFn, responseMapper)
                .toList()
                .blockingGet()

        then:
        true
    }
}

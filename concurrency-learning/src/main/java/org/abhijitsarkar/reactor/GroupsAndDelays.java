package org.abhijitsarkar.reactor;

import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class GroupsAndDelays {
    Function<Integer, Integer> remoteClient;
    int groupMemberDelaySeconds;
    int remoteCallTimeoutSeconds;
    int maxRetryCount;
    int retryDelaySeconds;
    Map<Long, List<Integer>> threadMap;
    Map<Long, List<Integer>> resultThreadMap;

    public Flux<Integer> doStuff(Flux<Integer> src,
                                 Function<Integer, Integer> groupByFn,
                                 Function<Integer, Integer> responseMapper) {
        return src
                .groupBy(groupByFn)
                .flatMap(g -> g
                        .distinct()
                        .collectList()
                        .publishOn(Schedulers.parallel())
                        .doOnNext(c -> log.debug("Processing group: {}.", c)), 5)
                .flatMap(i -> Flux.zip(Flux.fromIterable(i),
                        Flux.interval(Duration.ofSeconds(groupMemberDelaySeconds)),
                        (x, delay) -> x)
                        .doOnNext(c -> log.debug("Processing: {}.", c)))
                .flatMap(i -> {
                    putInThreadMap(threadMap, i);
                    return remoteCall(i * 2, responseMapper);
                });
    }

    private Flux<Integer> remoteCall(int i, Function<Integer, Integer> responseMapper) {
        return Flux.create((FluxSink<Integer> emitter) -> {
            try {
                Integer result = remoteClient.apply(i);
                emitter.next(result);
            } catch (Exception e) {
                emitter.error(e);
            }
        }, FluxSink.OverflowStrategy.ERROR)
                .timeout(Duration.ofSeconds(remoteCallTimeoutSeconds))
                .publishOn(Schedulers.parallel())
                .retryWhen(t -> t.zipWith(Flux.range(1, maxRetryCount), (ex, retryCount) -> retryCount)
                        .flatMap(retryCount -> {
                            int delay = retryCount * retryDelaySeconds;
                            log.debug("Retrying in: {} seconds.", delay);
                            return Flowable.timer(delay, SECONDS);
                        }))
                .map(result -> {
                    log.debug("Processing result: {}.", result);
                    putInThreadMap(resultThreadMap, result);
                    return responseMapper.apply(result);
                });
    }

    private void putInThreadMap(Map<Long, List<Integer>> map, int i) {
        map.merge(Thread.currentThread().getId(), singletonList(i), this::merge);
    }

    private List<Integer> merge(List<Integer> a, List<Integer> b) {
        return Stream.concat(a.stream(), b.stream()).collect(Collectors.toList());
    }
}

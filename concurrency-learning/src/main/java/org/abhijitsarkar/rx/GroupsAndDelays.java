package org.abhijitsarkar.rx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
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

    public Flowable<Integer> doStuff(Flowable<Integer> src,
                                     Function<Integer, Integer> groupByFn,
                                     Function<Integer, Integer> responseMapper) {
        return src
                .groupBy(groupByFn)
                .flatMap(g -> g
                        .distinct()
                        .toList()
                        .toFlowable()
                        .observeOn(Schedulers.computation())
                        .doOnNext(c -> log.debug("Processing group: {}.", c)), 5)
                .flatMap(i -> Flowable.zip(Flowable.fromIterable(i),
                        Flowable.interval(groupMemberDelaySeconds, SECONDS),
                        (x, delay) -> x)
                        .doOnNext(c -> log.debug("Processing: {}.", c)))
                .flatMap(i -> {
                    putInThreadMap(threadMap, i);
                    return remoteCall(i * 2, responseMapper);
                });
    }

    private Flowable<Integer> remoteCall(int i, Function<Integer, Integer> responseMapper) throws
            Exception {
        return Flowable.create((FlowableEmitter<Integer> emitter) -> {
            try {
                Integer result = remoteClient.apply(i);
                emitter.onNext(result);
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.ERROR)
                .timeout(remoteCallTimeoutSeconds, SECONDS)
                .observeOn(Schedulers.computation())
                .retryWhen(t -> t.zipWith(Flowable.range(1, maxRetryCount), (ex, retryCount) -> retryCount)
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

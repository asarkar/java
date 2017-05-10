package org.abhijitsarkar.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class GroupsAndDelays {
    int groupMemberDelaySeconds;
    int maxRetries;
    int retryDelaySeconds;

    // https://github.com/reactor/reactor-core/issues/421
    public Flux<Integer> doStuff(Flux<Integer> src,
                                 Function<Integer, Integer> groupByFn,
                                 Function<Integer, Integer> responseMapper) {
        return src
                .groupBy(groupByFn)
                .flatMap(g -> g
                        .distinct()
                        .collectList()
                        .publishOn(Schedulers.newParallel("par-grp"))
                        .flatMap(this::call))
                .map(responseMapper)
                .doOnNext(i -> {
                    log.info("Received: {}.", i);
                });
    }

    private Flux<Integer> call(List<Integer> values) {
        return Flux.fromIterable(values)
                .zipWith(Flux.interval(Duration.ofSeconds(groupMemberDelaySeconds)),
                        (x, delay) -> x)
                .flatMap(this::even);
    }

    private Mono<Integer> even(int i) {
        return Mono.<Integer>create(emitter -> {
            if (i > 30) {
                log.error("Too big: {}.", i);
                emitter.error(new IllegalArgumentException("Too big."));
            } else if (i % 2 == 0) {
                log.info("Success: {}.", i);
                emitter.success(i);
            } else {
                emitter.success();
            }
        })
                .map(x -> x * 2)
                .retryWhen(errors -> errors.zipWith(Flux.range(1, maxRetries + 1), (ex, x) -> x)
                        .flatMap(retryCount -> {
                            if (retryCount <= maxRetries) {
                                long delay = (long) Math.pow(retryDelaySeconds, retryCount);
                                return Mono.delay(Duration.ofSeconds(delay));
                            }
                            log.error("Done retrying: {}.", i);
                            return Mono.<Long>empty();
                        })
                );
    }
}

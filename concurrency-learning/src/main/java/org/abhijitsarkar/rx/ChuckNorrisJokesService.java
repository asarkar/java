package org.abhijitsarkar.rx;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.abhijitsarkar.domain.Joke;
import org.abhijitsarkar.domain.Jokes;
import org.abhijitsarkar.domain.RetryWithDelay;
import org.abhijitsarkar.repository.ChuckNorrisJokesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
@Builder
public class ChuckNorrisJokesService {
    @Getter
    private final AtomicReference<Jokes> jokes = new AtomicReference<>(new Jokes());

    private final Scheduler scheduler;
    private final ChuckNorrisJokesClient client;
    private final CountDownLatch latch;
    private final RetryWithDelay retryWithDelay;
    private final Map<String, List<String>> threads;

    public static class ChuckNorrisJokesServiceBuilder {
        public ChuckNorrisJokesService build() {
            if (scheduler == null) {
                scheduler = Schedulers.io();
            }

            if (threads == null) {
                threads = new ConcurrentHashMap<>();
            }

            requireNonNull(client, "ChuckNorrisJokesClient must not be null.");
            requireNonNull(latch, "CountDownLatch must not be null.");
            requireNonNull(retryWithDelay, "RetryWithDelay must not be null.");

            return new ChuckNorrisJokesService(scheduler, client, latch, retryWithDelay, threads);
        }
    }

    public void setRandomJokes(int numJokes) {
        mergeThreadNames("getRandomJokes");

        Single.fromCallable(() -> {
            log.debug("fromCallable - before call. Latch: {}.", latch.getCount());
            mergeThreadNames("fromCallable");
            latch.countDown();

            List<Joke> randomJokes = client.getRandomJokes(numJokes);
            log.debug("fromCallable - after call. Latch: {}.", latch.getCount());

            return randomJokes;
            /* retryWhen is a complicated, perhaps even buggy, operator. See the discussion below for details:
            * https://github.com/ReactiveX/RxJava/issues/4207
            */
        }).retryWhen(retryWithDelay)
                .subscribeOn(scheduler)
                .subscribe(j -> {
                            /* Called for a successful emission, either from original attempt or from a retry attempt. */
                            log.debug("onNext. Latch: {}.", latch.getCount());
                            mergeThreadNames("onNext");

                            jokes.set(new Jokes("success", j));
                            latch.countDown();
                        },
                        ex -> {
                            /* Called for a error emission, either from original attempt or from a retry attempt. */
                            log.error("onError. Latch: {}.", latch.getCount(), ex);
                            mergeThreadNames("onError");
                            latch.countDown();
                        }
                );
    }

    private void mergeThreadNames(String methodName) {
        threads.merge(methodName,
                new ArrayList<>(Arrays.asList(Thread.currentThread().getName())),
                (value, newValue) -> {
                    value.addAll(newValue);

                    return value;
                });
    }
}

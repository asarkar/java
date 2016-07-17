package name.abhijitsarkar.java.rxjava;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
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
    private final ChuckNorrisJokesRepository jokesRepository;
    private final CountDownLatch latch;
    private final int numRetries;
    private final Map<String, List<String>> threads;

    public static class ChuckNorrisJokesServiceBuilder {
        public ChuckNorrisJokesService build() {
            if (scheduler == null) {
                scheduler = Schedulers.io();
            }

            if (jokesRepository == null) {
                jokesRepository = new ChuckNorrisJokesRepository();
            }

            if (threads == null) {
                threads = new ConcurrentHashMap<>();
            }

            requireNonNull(latch, "CountDownLatch must not be null.");

            return new ChuckNorrisJokesService(scheduler, jokesRepository, latch, numRetries, threads);
        }
    }

    public void setRandomJokes(int numJokes) {
        mergeThreadNames("getRandomJokes");

        Observable.fromCallable(() -> {
            log.debug("fromCallable - before call. Latch: {}.", latch.getCount());
            mergeThreadNames("fromCallable");
            latch.countDown();

            List<Joke> randomJokes = jokesRepository.getRandomJokes(numJokes);
            log.debug("fromCallable - after call. Latch: {}.", latch.getCount());

            return randomJokes;
        }).retryWhen(errors ->
                errors.zipWith(Observable.range(1, numRetries), (n, i) -> i).flatMap(retryCount -> {
                    log.debug("retryWhen. retryCount: {}.", retryCount);
                    mergeThreadNames("retryWhen");

                    return Observable.timer(retryCount, TimeUnit.SECONDS);
                }))
                .subscribeOn(scheduler)
                .subscribe(j -> {
                            log.debug("onNext. Latch: {}.", latch.getCount());
                            mergeThreadNames("onNext");

                            jokes.set(new Jokes("success", j));
                            latch.countDown();
                        },
                        ex -> {
                            log.error("onError. Latch: {}.", latch.getCount(), ex);
                            mergeThreadNames("onError");
                        },
                        () -> {
                            log.debug("onCompleted. Latch: {}.", latch.getCount());
                            mergeThreadNames("onCompleted");

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

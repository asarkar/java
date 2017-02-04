package org.abhijitsarkar.rx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.abhijitsarkar.repository.YahooApiClient;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.Random;

import static java.util.Collections.singleton;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
@RequiredArgsConstructor
public class YahooFinanceService {
    private final YahooApiClient client;

    public double netAsset1(SimpleImmutableEntry<String, Long> stock1, SimpleImmutableEntry<String, Long> stock2) {
        MyConsumer consumer = new MyConsumer(stock1, stock2, "netAsset1");

        Flowable.just(stock1.getKey(), stock2.getKey())
                .flatMap(this::buildFlowable)
                /* onNext is called as soon as any price comes back */
                .blockingForEach(consumer);

        return consumer.netAsset;
    }

    @RequiredArgsConstructor
    private static final class MyConsumer implements Consumer<Map.Entry<String, Double>> {
        private double netAsset;

        private final SimpleImmutableEntry<String, Long> stock1;
        private final SimpleImmutableEntry<String, Long> stock2;
        private final String caller;

        @Override
        public void accept(Map.Entry<String, Double> e) {
            log.info("[{}] Calculating net asset on thread: {}.", caller, Thread.currentThread().getName());

            String key = e.getKey();
            double price = e.getValue();

            double asset = stock1.getKey().equals(key) ? stock1.getValue() * price : stock2.getValue() * price;

            netAsset += asset;
        }
    }

    public double netAsset2(SimpleImmutableEntry<String, Long> stock1, SimpleImmutableEntry<String, Long> stock2) {
        return Flowable.combineLatest(buildFlowable(stock1.getKey()), buildFlowable(stock2.getKey()),
                /* Executes only when both prices come back */
                (e1, e2) -> {
                    log.info("[netAsset2] Calculating net asset on thread: {}.", Thread.currentThread().getName());

                    return e1.getValue() * stock1.getValue() + e2.getValue() * stock2.getValue();
                })
                .blockingSingle();
    }

    public double netAsset3(SimpleImmutableEntry<String, Long> stock1, SimpleImmutableEntry<String, Long> stock2) {
        return Flowable.zip(buildFlowable(stock1.getKey()), buildFlowable(stock2.getKey()),
                /* Executes only when both prices come back */
                (e1, e2) -> {
                    log.info("[netAsset3] Calculating net asset on thread: {}.", Thread.currentThread().getName());

                    return e1.getValue() * stock1.getValue() + e2.getValue() * stock2.getValue();
                })
                .blockingSingle();
    }

    public double netAsset4(SimpleImmutableEntry<String, Long> stock1, SimpleImmutableEntry<String, Long> stock2) {
        return Flowable.merge(buildFlowable(stock1.getKey()), buildFlowable(stock2.getKey()))
                /* reduce is called as soon as any price comes back */
                .<Double>reduce(0.0d, (acc, e) -> {
                    log.info("[netAsset4] Calculating net asset on thread: {}.", Thread.currentThread().getName());

                    String key = e.getKey();
                    double price = e.getValue();

                    double asset = stock1.getKey().equals(key) ? stock1.getValue() * price : stock2.getValue() * price;

                    return acc + asset;
                })
                .blockingGet();
    }

    public double netAsset5(SimpleImmutableEntry<String, Long> stock1, SimpleImmutableEntry<String, Long> stock2) {
        MyConsumer consumer = new MyConsumer(stock1, stock2, "netAsset5");

        Flowable.just(stock1.getKey(), stock2.getKey())
                .flatMap(this::buildFlowable2)
                .blockingForEach(consumer);

        return consumer.netAsset;
    }

    private Flowable<Map.Entry<String, Double>> buildFlowable(String stock) {
        return Flowable.just(stock)
                .subscribeOn(Schedulers.computation())
                .map(s -> {
                    sleepRandom();
                    return client.getPrice(singleton(s)).entrySet().iterator().next();
                });
    }

    private Flowable<Map.Entry<String, Double>> buildFlowable2(String stock) {
        return Flowable.<Map.Entry<String, Double>>create(emitter -> {
            sleepRandom();
            Map.Entry<String, Double> next = client.getPrice(singleton(stock)).entrySet().iterator().next();
            emitter.onNext(next);

            emitter.onComplete();
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.computation());
    }

    @SneakyThrows
    private void sleepRandom() {
        int sleepTime = new Random().nextInt(10);

        String threadName = Thread.currentThread().getName();
        log.debug("Thread {} going to sleep for {} sec.", threadName, sleepTime);

        Thread.sleep(1000 * sleepTime);
    }
}

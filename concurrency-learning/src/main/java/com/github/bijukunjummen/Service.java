package com.github.bijukunjummen;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

// http://www.java-allandsundry.com/2015/06/rx-java-subscribeon-and-observeon.html
@Slf4j
public class Service {
    private Flux<String> getData() {
        return Flux.zip(Flux.range(1, 3), Flux.interval(Duration.ofMillis(200)), (i, d) -> i)
                .map(i -> Integer.toString(i))
                .doOnNext(s -> log.info("Emitting {}.", s));
    }

    public void subscribe() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        getData()
                .subscribe(s -> {
                    log.info("Got {}.", s);
                    latch.countDown();
                });

        latch.await();
    }

    public void subscribeOn() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        getData()
                .subscribeOn(Schedulers.parallel())
                .subscribe(s -> {
                    log.info("Got {}.", s);
                    latch.countDown();
                });

        latch.await();
    }

    public void publishOn() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        getData()
                .publishOn(Schedulers.parallel())
                .subscribe(s -> {
                    log.info("Got {}.", s);
                    latch.countDown();
                });

        latch.await();
    }

    public void parallel() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        getData()
                .parallel(3)
                .runOn(Schedulers.parallel())
                .subscribe(s -> {
                    log.info("Got {}.", s);
                    latch.countDown();
                });

        latch.await();
    }

    public static void main(String[] args) throws InterruptedException {
        Service service = new Service();

        log.info("=== Begin subscribe ===");
        service.subscribe();
        log.info("=== End subscribe ===");

        log.info("=== Begin subscribeOn ===");
        service.subscribeOn();
        log.info("=== End subscribeOn ===");

        log.info("=== Begin publishOn ===");
        service.publishOn();
        log.info("=== End publishOn ===");

        log.info("=== Begin parallel ===");
        service.parallel();
        log.info("=== End publishOn ===");
    }
}

package org.abhijitsarkar.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author Abhijit Sarkar
 */
// https://github.com/Netflix/Hystrix/wiki/Configuration
// https://github.com/Netflix/Hystrix/issues/1554
// https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ThreadPoolExecutor.html
class SimpleHystrixCmdTest {
    private final Random rand = new Random();
    private HystrixCommandProperties.Setter cmdProps;

    @BeforeEach
    void init() {
        cmdProps = HystrixCommandProperties.Setter()
                .withCircuitBreakerEnabled(false)
                .withFallbackEnabled(false)
                .withExecutionTimeoutInMilliseconds(5000);
    }

    @Test
    @DisplayName("should reject task when over core and max sizes")
    void test1() {
        HystrixThreadPoolProperties.Setter threadPoolProps =
                HystrixThreadPoolProperties.Setter()
                        .withCoreSize(2);

        List<Observable<String>> obs = obs(threadPoolProps);
        TestSubscriber<String> sub = sub(obs);

        sub.awaitTerminalEvent(10, TimeUnit.SECONDS);
        sub.assertValueCount(2);
        sub.assertError(HystrixRuntimeException.class);
    }

    @Test
    @DisplayName("shouldn't reject task when over core size but equal to max size")
    void test2() {
        HystrixThreadPoolProperties.Setter threadPoolProps =
                HystrixThreadPoolProperties.Setter()
                        .withAllowMaximumSizeToDivergeFromCoreSize(true)
                        .withCoreSize(2)
                        .withMaximumSize(3);

        List<Observable<String>> obs = obs(threadPoolProps);
        TestSubscriber<String> sub = sub(obs);

        sub.awaitTerminalEvent(10, TimeUnit.SECONDS);
        sub.assertValueCount(3);
        sub.assertNoErrors();
    }

    @Test
    @DisplayName("should reset max size when less than core size")
    void test3() {
        HystrixThreadPoolProperties.Setter threadPoolProps =
                HystrixThreadPoolProperties.Setter()
                        .withAllowMaximumSizeToDivergeFromCoreSize(true)
                        .withCoreSize(3)
                        .withMaximumSize(2);

        List<Observable<String>> obs = obs(threadPoolProps);
        TestSubscriber<String> sub = sub(obs);

        sub.awaitTerminalEvent(10, TimeUnit.SECONDS);
        sub.assertValueCount(3);
        sub.assertNoErrors();
    }

    @Test
    @DisplayName("should run task immediately when cannot do direct handoff")
    void test4() {
        HystrixThreadPoolProperties.Setter threadPoolProps =
                HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withMaxQueueSize(-1);

        List<Observable<String>> obs = obs(threadPoolProps);
        TestSubscriber<String> sub = sub(obs);

        sub.awaitTerminalEvent(10, TimeUnit.SECONDS);
        sub.assertValueCount(3);
        sub.assertNoErrors();
    }

    @Test
    @DisplayName("should run task immediately when cannot queue to bounded queue")
    void test5() {
        HystrixThreadPoolProperties.Setter threadPoolProps =
                HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withMaxQueueSize(2);

        List<Observable<String>> obs = obs(threadPoolProps);
        TestSubscriber<String> sub = sub(obs);

        sub.awaitTerminalEvent(10, TimeUnit.SECONDS);
        sub.assertValueCount(3);
        sub.assertNoErrors();
    }

    private List<Observable<String>> obs(HystrixThreadPoolProperties.Setter threadPoolProps) {
        int grpIdx = rand.nextInt(100);
        return IntStream.range(1, 4)
                .mapToObj(cmdIdx -> new SimpleHystrixCmd(cmdSetter(cmdIdx, grpIdx, threadPoolProps), 3000)
                        .observe())
                .collect(toList());
    }

    private HystrixCommand.Setter cmdSetter(int cmdIdx, int grpIdx, HystrixThreadPoolProperties.Setter threadPoolProps) {
        return HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("testGrp-" + grpIdx))
                .andCommandKey(HystrixCommandKey.Factory.asKey("testCmd-" + cmdIdx))
                .andCommandPropertiesDefaults(cmdProps)
                .andThreadPoolPropertiesDefaults(threadPoolProps);
    }

    private TestSubscriber<String> sub(List<Observable<String>> obs) {
        TestSubscriber<String> sub = new TestSubscriber<>();
        Observable.concat(obs)
                .subscribe(sub);

        return sub;
    }
}
package name.abhijitsarkar.java.domain;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class RetryWithDelay implements
        Function<Flowable<? extends Throwable>, Publisher<Object>> {

    private final int maxRetries;
    private final long retryDelaySeconds;
    private final RetryDelayStrategy retryDelayStrategy;

    private int retryCount;

    /* Use Builder on constructor to avoid field retryCount being included. */
    @Builder
    private RetryWithDelay(int maxRetries, long retryDelaySeconds, RetryDelayStrategy retryDelayStrategy) {
        this.maxRetries = maxRetries;
        this.retryDelaySeconds = retryDelaySeconds;
        this.retryDelayStrategy = retryDelayStrategy;
    }

    @Override
    public Publisher<Object> apply(Flowable<? extends Throwable> attempts) throws Exception {
        return attempts
                .concatMap(new Function<Throwable, Flowable<?>>() {
                    @Override
                    public Flowable<?> apply(Throwable throwable) {
                        if (++retryCount <= maxRetries) {
                            /* When this Observable calls onNext, the original
                             * Observable will be retried (i.e. resubscribed).
                             */
                            long delaySeconds = delaySeconds();

                            log.debug("Retrying...attempt #{} in {} second(s).", retryCount, delaySeconds);
                            return Flowable.timer(delaySeconds, SECONDS);
                        }

                        /* Max retries hit. Just pass the error along. */
                        log.warn("Exhausted all retries: {}.", maxRetries);
                        return Flowable.error(throwable);
                    }
                });
    }

    private long delaySeconds() {
        requireNonNull(retryDelayStrategy, "RetryDelayStrategy must not be null.");

        switch (retryDelayStrategy) {
            case CONSTANT_DELAY:
                return retryDelaySeconds;
            case RETRY_COUNT:
                return retryCount;
            case CONSTANT_DELAY_TIMES_RETRY_COUNT:
                return retryDelaySeconds * retryCount;
            case CONSTANT_DELAY_RAISED_TO_RETRY_COUNT:
                return (long) Math.pow(retryDelaySeconds, retryCount);
            default:
                return 0;
        }
    }
}

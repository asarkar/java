package name.abhijitsarkar.java.repository;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
@RequiredArgsConstructor
public class YahooApiStubClient extends AbstractYahooApiClient {
    private final boolean sleepRandom;

    public YahooApiStubClient() {
        this(false);
    }

    @Override
    @SneakyThrows({IOException.class, InterruptedException.class})
    public Map<String, Double> getPrice(Collection<String> tickers) {
        log.debug("Tickers: {}.", tickers);

        int sleepTime = sleepRandom ? new Random().nextInt(10) : 1;

        String threadName = Thread.currentThread().getName();
        log.debug("Thread {} going to sleep for {} sec.", threadName, sleepTime);

        Thread.sleep(1000 * sleepTime);

        try (InputStream is = getClass().getResourceAsStream("/stocks-prices.json")) {
            return super.extractPrices(is, tickers);
        }
    }

    @Override
    public void close() {

    }
}

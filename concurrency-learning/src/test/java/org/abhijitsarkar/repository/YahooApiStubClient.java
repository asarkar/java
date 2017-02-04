package org.abhijitsarkar.repository;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class YahooApiStubClient extends AbstractYahooApiClient {
    @Override
    @SneakyThrows
    public Map<String, Double> getPrice(Collection<String> tickers) {
        log.debug("Tickers: {}.", tickers);

        String threadName = Thread.currentThread().getName();
        log.debug("Thread {} going to sleep for 1 sec.", threadName);

        Thread.sleep(1000);

        try (InputStream is = getClass().getResourceAsStream("/stocks-prices.json")) {
            return super.extractPrices(is, tickers);
        }
    }

    @Override
    public void close() {

    }
}

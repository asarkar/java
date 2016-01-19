package name.abhijitsarkar.java.repository;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import static java.util.Collections.emptyMap;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class YahooApiStubClient extends AbstractYahooApiClient {
    @Override
    @SneakyThrows(IOException.class)
    public Map<String, Double> getPrice(Collection<String> tickers) {
        @Cleanup
        InputStream is = null;

        log.debug("Tickers: {}.", tickers);

        long threadId = Thread.currentThread().getId();
        log.debug("Thread {} going to sleep.", threadId);

        Map<String, Double> prices = emptyMap();

        try {
            Thread.currentThread().sleep(1000);

            is = getClass().getResourceAsStream("/stocks-prices.json");
            prices = super.extractPrices(is, tickers);
        } catch (InterruptedException e) {
            log.warn("Thread {} could not sleep.", threadId);
        }

        return prices;
    }

    @Override
    public void close() {

    }
}

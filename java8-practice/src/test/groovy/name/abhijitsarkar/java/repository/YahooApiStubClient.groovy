package name.abhijitsarkar.java.repository

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Abhijit Sarkar
 */
class YahooApiStubClient extends AbstractYahooApiClient {
    Logger log = LoggerFactory.getLogger(YahooApiStubClient)

    @Override
    Map<String, Double> getPrice(Collection<String> tickers) {
        InputStream is = null;

        log.debug("Tickers: {}.", tickers)
        log.debug("{} going to sleep.", Thread.currentThread().id)

        sleep(1000)

        try {
            is = getClass().getResourceAsStream("/stocks-prices.json")

            return super.extractPrices(is, tickers)
        } finally {
            is?.close()
        }
    }

    @Override
    void close() {

    }
}

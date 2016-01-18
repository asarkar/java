package name.abhijitsarkar.java.repository
/**
 * @author Abhijit Sarkar
 */
class YahooApiStubClient extends AbstractYahooApiClient {
    @Override
    Map<String, Double> getPrice(Collection<String> tickers) {
        InputStream is = null;

        println("${getClass().simpleName} invoked with tickers: ${tickers}.")
        println("${getClass().simpleName} going to sleep.")

        sleep(5000)

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

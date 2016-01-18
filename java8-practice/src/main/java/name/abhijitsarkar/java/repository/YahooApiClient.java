package name.abhijitsarkar.java.repository;

import java.util.Collection;
import java.util.Map;

/**
 * @author Abhijit Sarkar
 */
public interface YahooApiClient {
    Map<String, Double> getPrice(Collection<String> tickers);

    void close();
}

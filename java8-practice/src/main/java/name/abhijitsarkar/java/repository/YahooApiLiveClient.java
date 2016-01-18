package name.abhijitsarkar.java.repository;

import lombok.RequiredArgsConstructor;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static name.abhijitsarkar.java.repository.AbstractClientFactory.liveClient;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
public class YahooApiLiveClient extends AbstractYahooApiClient {
    private static final String BASE_URI = "https://query.yahooapis.com";
    private static final String QUERY = "select * from yahoo.finance.quotes where symbol in ({tickers})";

    private final Client client;
    WebTarget yahooWebTarget;

    public YahooApiLiveClient() {
        client = liveClient();

        yahooWebTarget = client.target(BASE_URI)
                .path("/v1/public/yql")
                .queryParam("q", QUERY)
                .queryParam("format", "json")
                .queryParam("env", "http://datatables.org/alltables.env");
    }

    @Override
    public Map<String, Double> getPrice(Collection<String> tickers) {
        String formattedTickers = String.format("\"%s\"", tickers.stream().collect(joining("\",\"")));

        WebTarget target = yahooWebTarget.resolveTemplate("tickers", formattedTickers);

        try (InputStream body = target.request()
                .accept(APPLICATION_JSON_TYPE)
                .<InputStream>get(InputStream.class)) {
            return extractPrices(body, tickers);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() {
        client.close();
    }
}

package name.abhijitsarkar.java.rxjava;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import static com.fasterxml.jackson.databind.DeserializationFeature.UNWRAP_ROOT_VALUE;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class YahooFinanceRepository {
    private static final String YAHOO_FINANCE_URI = "http://query.yahooapis.com/v1/public/yql";
    private static final String QUERY = "select * from yahoo.finance.quotes where symbol in (\"ticker\")";
    private static final String DATA_TABLES = "http://datatables.org/alltables.env";

    public double getPrice(String ticker) {
        try {
            URL url = buildUrl(ticker);
            ObjectMapper objectMapper = new ObjectMapper();

            try (InputStream is = url.openStream()) {
                JsonNode jsonNode = objectMapper.readTree(is);

                JsonNode results = jsonNode.path("query").path("results");

                double price = -1.0d;
                if (!results.isMissingNode()) {
                    ObjectReader reader = objectMapper.reader()
                            .withFeatures(UNWRAP_ROOT_VALUE)
                            .withRootName("quote")
                            .forType(Stock.class);

                    price = reader.<Stock>readValue(results).getPrice();
                }

                int sleepTime = new Random().nextInt(10);

                log.info("Going to sleep for: {} sec.", sleepTime);

                Thread.sleep(1000 * sleepTime);

                log.info("Price of ticker: {} is: {}.", ticker, price);

                return price;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Shit happened!", e);
        }
    }

    private URL buildUrl(String ticker) throws UnsupportedEncodingException, MalformedURLException {
        StringBuilder buffer = new StringBuilder(YAHOO_FINANCE_URI);

        buffer.append("?q=").append(encode(QUERY.replaceAll("ticker", ticker),
                UTF_8.name()));
        buffer.append("&env=").append(encode(DATA_TABLES, UTF_8.name()));
        buffer.append("&format=json");

//        log.info("Built URL: {}.", buffer);

        return new URL(buffer.toString());
    }
}


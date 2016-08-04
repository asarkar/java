package name.abhijitsarkar.java.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import name.abhijitsarkar.java.domain.Stock;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.UNWRAP_ROOT_VALUE;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;

/**
 * @author Abhijit Sarkar
 */
public abstract class AbstractYahooApiClient implements YahooApiClient {
    private final ObjectMapper objectMapper = new ObjectMapperProvider().getContext(null);

    protected Map<String, Double> extractPrices(InputStream is, Collection<String> tickers) {
        try {
            JsonNode jsonNode = objectMapper.readTree(is);

            JsonNode results = jsonNode.path("query").path("results");

            if (!results.isMissingNode()) {
                TypeReference<List<Stock>> stocksType = new TypeReference<List<Stock>>() {
                };

                ObjectReader reader = objectMapper.reader()
                        .withFeatures(UNWRAP_ROOT_VALUE)
                        .withRootName("quote")
                        .forType(stocksType);

                return reader.<List<Stock>>readValue(results)
                        .stream()
                        .filter(stock -> tickers.contains(stock.getSymbol()))
                        .collect(toMap(Stock::getSymbol, Stock::getPrice));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return emptyMap();
    }
}

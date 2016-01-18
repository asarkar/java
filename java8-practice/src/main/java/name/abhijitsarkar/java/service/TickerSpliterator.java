package name.abhijitsarkar.java.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import name.abhijitsarkar.java.repository.YahooApiClient;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

import static java.util.Collections.singleton;

/**
 * @author Abhijit Sarkar
 */
@Builder(toBuilder = true)
@Slf4j
@ToString(exclude = "client")
public class TickerSpliterator implements Spliterator<Map.Entry<String, Double>> {
    @NonNull
    private final List<String> tickers;
    @NonNull
    private final YahooApiClient client;
    private final int tickersSubsetSize;

    private int beginIdx;
    private int endIdx;

    /* Keeps a tickersSubsetSize sized chunk for self, hands the rest to a new TickerSpliterator.  */
    @Override
    public Spliterator<Map.Entry<String, Double>> trySplit() {
        log.debug(toString());

        if ((endIdx - beginIdx) <= tickersSubsetSize) {
            return null;
        }

        TickerSpliterator spliterator = toBuilder()
                .beginIdx(beginIdx + tickersSubsetSize)
                .build();

        endIdx = spliterator.beginIdx;

        return spliterator;
    }

    /* Accept elements from own chunk of tickers. */
    @Override
    public boolean tryAdvance(Consumer<? super Map.Entry<String, Double>> action) {
        log.debug(toString());

        if (beginIdx < endIdx) {
            String ticker = tickers.get(beginIdx);

            Map<String, Double> prices = client.getPrice(singleton(ticker));

            Double price = prices.get(ticker);

            action.accept(new SimpleImmutableEntry<>(ticker, price));

            ++beginIdx;

            return true;
        }

        return false;
    }

    @Override
    public long estimateSize() {
        return endIdx - beginIdx;
    }

    @Override
    public int characteristics() {
        return IMMUTABLE | DISTINCT | NONNULL | SIZED;
    }
}

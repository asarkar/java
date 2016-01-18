package name.abhijitsarkar.java.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import name.abhijitsarkar.java.repository.YahooApiClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
@Slf4j
public class FinanceService {
    private static final int TICKER_SUBSET_SIZE = 2;

    private final YahooApiClient client;

    public double netAsset(Map<String, Integer> stocks) {
        DoubleAdder adder = new DoubleAdder();

        List<String> tickers = stocks.keySet().stream().collect(toList());

        TickerSpliterator tickerSpliterator = TickerSpliterator
                .builder()
                .client(client)
                .tickers(tickers)
                .endIdx(tickers.size())
                .tickersSubsetSize(TICKER_SUBSET_SIZE)
                .build();

        StreamSupport.stream(tickerSpliterator, true).forEach(
                entry -> {
                    int numShares = stocks.get(entry.getKey());
                    double price = entry.getValue();

                    adder.add(numShares * price);
                }
        );

        return adder.doubleValue();
    }

    public double netAsset2(Map<String, Integer> stocks) {
        Iterator<String> tickers = stocks.keySet().iterator();

        /* Splits the stocks into TICKER_SUBSET_SIZE sized chunks and assigns each chunk to a new task. */
        Collection<CompletableFuture<Map<String, Double>>> futures =
                IntStream.range(0, stocks.size())
                        .collect(ArrayList::new, (acc, i) -> {
                                    if (i > 0 && (i % TICKER_SUBSET_SIZE == 0)) {
                                        acc.add(toFuture(tickersSubset(true, tickers)));
                                    } else if (i == stocks.size() - 1) {
                                        acc.add(toFuture(tickersSubset(false, tickers)));
                                    }
                                }, (v1, v2) -> {
                                    // Not called. Not sure why.
                                }
                        );



        /* Joins all task into one. The subtasks are NOT invoked sequentially.
         * Subtask # i + 1 invocation does not wait for subtask # i to complete.
         */
        CompletableFuture<Map<String, Double>> prices =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}))
                        .thenApply(v -> futures.stream()
                                .flatMap(f -> f.join().entrySet().stream())
                                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue)));

        DoubleAdder adder = new DoubleAdder();

        prices.thenAccept(all -> all.entrySet().stream().forEach(entry -> {
            int numShares = stocks.get(entry.getKey());
            double price = entry.getValue();

            adder.add(numShares * price);
        }));

        try {
            prices.get();
        } catch (Exception e) {
            log.error("Failed to calculate net asset.", e);
        }

        return adder.doubleValue();
    }

    private Collection<String> tickersSubset(boolean boundary, Iterator<String> tickers) {
        List<String> tickersSubset = new ArrayList<>();

        if (boundary) {
            tickersSubset.addAll(IntStream.range(0, TICKER_SUBSET_SIZE)
                    .mapToObj(j -> tickers.next())
                    .collect(toList()));
        } else {
            while (tickers.hasNext()) {
                tickersSubset.add(tickers.next());
            }
        }

        return tickersSubset;
    }

    private CompletableFuture<Map<String, Double>> toFuture(Collection<String> tickersSubset) {
        return supplyAsync(() -> {
            log.debug("CompletableFuture invoked with tickersSubset: {}.", tickersSubset);
            return client.getPrice(tickersSubset);
        });
    }
}

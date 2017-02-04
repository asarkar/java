package org.abhijitsarkar.concurrent;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abhijitsarkar.repository.YahooApiClient;
import org.abhijitsarkar.stream.TickerSpliterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.function.Consumer;
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
public class NetAssetService {
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
                                    if (isTickersSubsetBoundary(i)) {
                                        acc.add(toFuture(tickersSubset(true, tickers)));
                                    } else if (isLast(i, stocks)) {
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

        try {
            prices.thenAccept(new NetAssetComputer(adder, stocks))
                    .get();
        } catch (Exception e) {
            log.error("Failed to calculate net asset.", e);
        }

        return adder.doubleValue();
    }

    private boolean isTickersSubsetBoundary(int idx) {
        return idx > 0 && (idx % TICKER_SUBSET_SIZE == 0);
    }

    private boolean isLast(int idx, Map<String, Integer> stocks) {
        return idx == stocks.size() - 1;
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
            return client.getPrice(tickersSubset);
        });
    }


    @RequiredArgsConstructor
    private static class NetAssetComputer implements Consumer<Map<String, Double>>,
            io.reactivex.functions.Consumer<Map<String, Double>> {
        private final DoubleAdder adder;
        private final Map<String, Integer> stocks;

        @Override
        public void accept(Map<String, Double> prices) {
            prices.entrySet().stream()
                    .forEach(entry -> {
                        int numShares = stocks.get(entry.getKey());
                        double price = entry.getValue();

                        adder.add(numShares * price);
                    });
        }
    }

    public double netAsset3(Map<String, Integer> stocks) {
        Iterator<String> tickers = stocks.keySet().iterator();

        /* Splits the stocks into TICKER_SUBSET_SIZE sized chunks and assigns each chunk to a new task. */
        ArrayList<Flowable<Map<String, Double>>> Flowables = IntStream.range(0, stocks.size())
                .collect(ArrayList::new, (acc, i) -> {
                            if (isTickersSubsetBoundary(i)) {
                                acc.add(toAsyncFlowable(tickersSubset(true, tickers)));
                            } else if (isLast(i, stocks)) {
                                acc.add(toAsyncFlowable(tickersSubset(false, tickers)));
                            }
                        }, (v1, v2) -> {
                            // Not called. Not sure why.
                        }
                );

        DoubleAdder adder = new DoubleAdder();
        NetAssetComputer compute = new NetAssetComputer(adder, stocks);

        /* As long as the Flowables being merged are async, they will all be executed concurrently.
         * 'flatMap' could be used too.
         */
        Flowable.merge(Flowables)
                .blockingForEach(compute);

        return adder.doubleValue();
    }

    private Flowable<Map<String, Double>> toAsyncFlowable(Collection<String> tickersSubset) {
        return Flowable.just(tickersSubset).map(client::getPrice).subscribeOn(Schedulers.io());
    }
}

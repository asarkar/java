package org.abhijitsarkar.concurrent;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.abhijitsarkar.repository.YahooApiClient;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.summingDouble;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
@ToString(exclude = "client")
@Builder(toBuilder = true)
public class NetAssetTask extends RecursiveTask<Double> {
    @NonNull
    private final Map<String, Integer> stocks;
    @NonNull
    private final YahooApiClient client;
    @NonNull
    private final Set<String> remaining;
    private final int threshold;

    @Override
    protected Double compute() {
        log.debug("Thread {} running.", Thread.currentThread().getId());

        if (remaining.size() <= threshold) {
            return computeInternal(remaining);
        }

        Set<String> myJob = new HashSet<>();
        Iterator<String> it = remaining.iterator();

        IntStream.range(0, threshold).forEach(i -> {
            myJob.add(it.next());
            it.remove();
        });

        NetAssetTask netAssetTask = toBuilder().remaining(remaining).build();

        netAssetTask.fork();

        return computeInternal(myJob) + netAssetTask.join();
    }

    private double computeInternal(Set<String> myJob) {
        if (myJob.isEmpty()) {
            return 0.0d;
        }

        Map<String, Double> prices = client.getPrice(myJob);

        return myJob.stream().collect(summingDouble(t -> prices.get(t) * stocks.get(t)));
    }
}

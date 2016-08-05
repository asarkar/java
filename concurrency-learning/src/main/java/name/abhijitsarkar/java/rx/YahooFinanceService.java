package name.abhijitsarkar.java.rx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import name.abhijitsarkar.java.repository.YahooApiClient;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAdder;

import static java.util.Collections.singleton;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
@RequiredArgsConstructor
public class YahooFinanceService {
    private final YahooApiClient client;

    public double netAsset1(SimpleImmutableEntry<String, Long> stock1, SimpleImmutableEntry<String, Long> stock2) {
        DoubleAdder adder = new DoubleAdder();

        Observable.just(stock1.getKey(), stock2.getKey())
                .flatMap(this::buildObservable)
                /* Without blocking, method exits before onNext is called
                   With blocking, onNext is called as soon as any price comes back
                 */
                .toBlocking()
                .forEach(e -> {
                    log.info("[netAsset1] Calculating net asset on thread: {}.", Thread.currentThread().getName());

                    String key = e.getKey();
                    double price = e.getValue();

                    double asset = stock1.getKey().equals(key) ? stock1.getValue() * price : stock2.getValue() * price;

                    adder.add(asset);
                });

        return adder.doubleValue();
    }

    public double netAsset2(SimpleImmutableEntry<String, Long> stock1, SimpleImmutableEntry<String, Long> stock2) {
        return Observable.combineLatest(buildObservable(stock1.getKey()), buildObservable(stock2.getKey()),
                /* Executes only when both prices come back */
                (e1, e2) -> {
                    log.info("[netAsset2] Calculating net asset on thread: {}.", Thread.currentThread().getName());

                    return e1.getValue() * stock1.getValue() + e2.getValue() * stock2.getValue();
                })
                .toBlocking()
                .first();
    }

    public double netAsset3(SimpleImmutableEntry<String, Long> stock1, SimpleImmutableEntry<String, Long> stock2) {
        return Observable.zip(buildObservable(stock1.getKey()), buildObservable(stock2.getKey()),
                /* Executes only when both prices come back */
                (e1, e2) -> {
                    log.info("[netAsset3] Calculating net asset on thread: {}.", Thread.currentThread().getName());

                    return e1.getValue() * stock1.getValue() + e2.getValue() * stock2.getValue();
                })
                .toBlocking()
                .first();
    }

    private Observable<Map.Entry<String, Double>> buildObservable(String stock) {
        return Observable.just(stock)
                .subscribeOn(Schedulers.computation())
                .map(s -> client.getPrice(singleton(s)).entrySet().iterator().next());
    }
}

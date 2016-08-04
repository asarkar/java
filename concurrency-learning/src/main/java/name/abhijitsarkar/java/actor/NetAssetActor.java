package name.abhijitsarkar.java.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import name.abhijitsarkar.java.repository.YahooApiClient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.summingDouble;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
@ToString(exclude = "client")
@RequiredArgsConstructor
public class NetAssetActor extends UntypedActor {
    @NonNull
    private final YahooApiClient client;
    private final int threshold;

    @Override
    public void onReceive(Object message) throws Exception {
        log.debug("Received message: {}.", message);

        if (message instanceof Stocks) {
            Map<String, Integer> stocks = ((Stocks) message).stocks;

            if (stocks.size() <= threshold) {
                double asset = computeInternal(stocks);

                getSender().tell(new NetAssetManagerActor.Asset(asset), getSelf());
            } else {
                Map<String, Integer> myJob = new HashMap<>();
                Map<String, Integer> notMyJob = new HashMap<>();
                Iterator<Map.Entry<String, Integer>> it = stocks.entrySet().iterator();

                IntStream.range(0, threshold).forEach(i -> {
                    Map.Entry<String, Integer> next = it.next();

                    myJob.put(next.getKey(), next.getValue());
                });

                while (it.hasNext()) {
                    Map.Entry<String, Integer> next = it.next();

                    notMyJob.put(next.getKey(), next.getValue());
                }

                log.debug("My job: {}.", myJob);
                log.debug("Not my job: {}.", notMyJob);

                final ActorRef child = getContext().actorOf(props(client, threshold));
                child.tell(new Stocks(notMyJob), getSender());

                double asset = computeInternal(myJob);
                getSender().tell(new NetAssetManagerActor.Asset(asset), getSelf());
            }
        } else {
            unhandled(message);
        }
    }

    private double computeInternal(Map<String, Integer> stocks) {
        if (stocks.isEmpty()) {
            return 0.0d;
        }

        Set<String> tickers = stocks.keySet();

        Map<String, Double> prices = client.getPrice(tickers);

        return tickers.stream().collect(summingDouble(t -> prices.get(t) * stocks.get(t)));
    }

    public static final Props props(YahooApiClient client, int threshold) {
        return Props.create(new Creator<NetAssetActor>() {
            @Override
            public NetAssetActor create() throws Exception {
                return new NetAssetActor(client, threshold);
            }
        });
    }

    @Value
    public static final class Stocks {
        private final Map<String, Integer> stocks;

        public Stocks(Map<String, Integer> stocks) {
            this.stocks = unmodifiableMap(stocks);
        }
    }
}

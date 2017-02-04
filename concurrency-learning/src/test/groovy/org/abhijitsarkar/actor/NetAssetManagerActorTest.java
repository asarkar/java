package org.abhijitsarkar.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.abhijitsarkar.actor.NetAssetManagerActor.Asset;
import org.abhijitsarkar.repository.YahooApiClient;
import org.abhijitsarkar.repository.YahooApiStubClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.summingDouble;
import static org.junit.Assert.assertEquals;

/**
 * @author Abhijit Sarkar
 */
public class NetAssetManagerActorTest {
    private static ActorSystem system;

    private YahooApiClient client = new YahooApiStubClient();
    private Map<String, Integer> stocks;

    @BeforeClass
    public static void setupSpec() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void cleanupSpec() {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Before
    public void setup() {
        stocks = new HashMap<>();
        stocks.put("YHOO", 1);
        stocks.put("AAPL", 2);
        stocks.put("GOOG", 5);
        stocks.put("MSFT", 1);
    }

    @Test
    public void calculatesNetAssetUsingActors() {
        new JavaTestKit(system) {
            {
                JavaTestKit probe = new JavaTestKit(system);
                Props props = NetAssetManagerActor.testProps(client, 2, probe.getRef());
                ActorRef netAssetManagerActor = system.actorOf(props, "netAssetManagerActor");

                final FiniteDuration duration = duration("5 seconds");
                netAssetManagerActor.tell(new NetAssetActor.Stocks(stocks), getRef());

                new Within(duration) {
                    protected void run() {
                        new AwaitCond() {
                            protected boolean cond() {
                                return probe.msgAvailable();
                            }
                        };

                        // checks that the probe we injected earlier got the msg
                        Object[] assets = probe.receiveN(2, Duration.Zero());

                        Double netAsset = Arrays.stream(assets)
                                .map(asset -> (Asset) asset)
                                .collect(summingDouble(Asset::getAsset));

                        assertEquals(3754.73d, netAsset, 0.1d);

                        expectNoMsg();
                    }
                };
            }
        };
    }
}

package org.abhijitsarkar.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.abhijitsarkar.repository.YahooApiClient;

import java.util.concurrent.atomic.DoubleAdder;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
@ToString(exclude = "client")
@RequiredArgsConstructor
public class NetAssetManagerActor extends UntypedActor {
    @NonNull
    private final YahooApiClient client;
    private final int threshold;

    private final DoubleAdder netAsset = new DoubleAdder();
    private ActorRef probe;

    @Override
    public void onReceive(Object message) throws Exception {
        log.debug("Received message: {}.", message);

        if (message instanceof Asset) {
            double asset = ((Asset) message).asset;

            netAsset.add(asset);

            if (probe != null) {
                probe.forward(message, getContext());
            }

            log.debug("Current net asset: {}.", netAsset.doubleValue());
        } else if (message instanceof NetAssetActor.Stocks) {
            ActorRef child = getContext().actorOf(NetAssetActor.props(client, threshold));
            child.tell(message, getSelf());
        } else {
            unhandled(message);
        }
    }

    public static final Props props(YahooApiClient client, int threshold) {
        return Props.create(new Creator<NetAssetManagerActor>() {
            @Override
            public NetAssetManagerActor create() throws Exception {
                return new NetAssetManagerActor(client, threshold);
            }
        });
    }

    static final Props testProps(YahooApiClient client, int threshold, ActorRef probe) {
        return Props.create(new Creator<NetAssetManagerActor>() {
            @Override
            public NetAssetManagerActor create() throws Exception {
                NetAssetManagerActor actor = new NetAssetManagerActor(client, threshold);
                actor.probe = probe;

                return actor;
            }
        });
    }

    @Value
    public static final class Asset {
        private final double asset;
    }
}

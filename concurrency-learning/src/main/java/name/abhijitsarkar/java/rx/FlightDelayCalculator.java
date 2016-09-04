package name.abhijitsarkar.java.rx;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.schedulers.Schedulers;
import javaslang.Tuple3;
import javaslang.control.Try;
import name.abhijitsarkar.java.domain.FlightDelayRecord;
import name.abhijitsarkar.java.domain.FlightEvent;

import java.io.File;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

/**
 * In this example we use RxJava Flowable to ingest a CSV file that contains records of all flight data in the US
 * for a single year, process the flight data, and emit a list of average flight delays per carrier.
 * The code first downloads the CSV file from here and saves to /tmp/flight-data.
 * <p>
 * Concept from: https://medium.com/@kvnwbbr/diving-into-akka-streams-2770b3aeabb0#.fba5qsw4m.
 *
 * @author Abhijit Sarkar
 */
public class FlightDelayCalculator {
    private static final URL URL;
    private static final File OUTFILE;

    static {
        try {
            URL = new URL("http://stat-computing.org/dataexpo/2009/2008.csv.bz2");
            OUTFILE = new File("/tmp/flight-data");
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void main(String... args) {
        new FlightDelayCalculator().run();
    }

    private final FlightDataDownloader downloader = new FlightDataDownloader();

    public void run() {
        Function<? super List<String>, FlightEvent> listToFlightEvent = new ListToFlightEvent();

        Function<String, FlightEvent> lineToFlightEvent = s -> Optional.ofNullable(s)
                .map(line -> Arrays.stream(line.split(",")).map(String::trim).collect(toList()))
                .map(listToFlightEvent)
                .get();

        Flowable<FlightEvent> csvToFlightEvent = Flowable.create(emitter -> {
            try {
                String out = downloader.extract(downloader.download(URL, OUTFILE));

                Files.lines(Paths.get(out))
                        .map(lineToFlightEvent)
                        .forEach(emitter::onNext);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }

        }, FlowableEmitter.BackpressureMode.ERROR);

        Flowable<FlightDelayRecord> filterAndConvert =
                csvToFlightEvent.filter(e -> Try.of(() -> parseInt(e.getArrDelayMins())).getOrElse(-1) > 0)
                        .map(e -> FlightDelayRecord.builder()
                                .year(e.getYear())
                                .month(e.getMonth())
                                .dayOfMonth(e.getDayOfMonth())
                                .flightNum(e.getFlightNum())
                                .uniqueCarrier(e.getUniqueCarrier())
                                .arrDelayMins(e.getArrDelayMins())
                                .build()
                        );

        Flowable<Tuple3<String, Integer, Integer>> averageCarrierDelay =
                filterAndConvert.groupBy(FlightDelayRecord::getUniqueCarrier)
                        .flatMap(go -> go
                                .doOnNext(rec -> System.out.println(String.format("Group %s running on thread: %s.",
                                        go.getKey(), Thread.currentThread().getName())))
                                .reduce(new Tuple3<>("", 0, 0), (acc, rec) -> {
                                    int delayCount = acc._2 + 1;
                                    int runningTotalDelay = acc._3 + Try.of(() -> parseInt(rec.getArrDelayMins())).getOrElse(0);

                                    return new Tuple3<>(rec.getUniqueCarrier(), delayCount, runningTotalDelay);
                                })
                                .subscribeOn(Schedulers.newThread()));

        Flowable<Tuple3<String, Integer, Integer>> averageSink =
                averageCarrierDelay.doOnNext(t -> {
                    System.out.println(String.format("Delays for carrier %s: " +
                            "%d average mins, %d delayed flights.", t._1, Try.of(() -> t._3 / t._2).getOrElse(0), t._2));
                });

        averageSink.blockingSubscribe();
    }
}

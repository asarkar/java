package name.abhijitsarkar.java.rx;

import javaslang.control.Try;
import name.abhijitsarkar.java.domain.FlightEvent;

import java.util.List;
import java.util.function.Function;

/**
 * @author Abhijit Sarkar
 */
public class ListToFlightEvent implements Function<List<String>, FlightEvent> {
    @Override
    public FlightEvent apply(List<String> l) {
        return Try.of(() -> FlightEvent.builder()
                .year(l.get(0))
                .month(l.get(1))
                .dayOfMonth(l.get(2))
                .dayOfWeek(l.get(3))
                .depTime(l.get(4))
                .scheduledDepTime(l.get(5))
                .arrTime(l.get(6))
                .scheduledArrTime(l.get(7))
                .uniqueCarrier(l.get(8))
                .flightNum(l.get(9))
                .tailNum(l.get(10))
                .actualElapsedMins(l.get(11))
                .crsElapsedMins(l.get(12))
                .airMins(l.get(13))
                .arrDelayMins(l.get(14))
                .depDelayMins(l.get(15))
                .originAirportCode(l.get(16))
                .destinationAirportCode(l.get(17))
                .distanceInMiles(l.get(18))
                .taxiInTimeMins(l.get(19))
                .taxiOutTimeMins(l.get(20))
                .flightCancelled(l.get(21))
                .cancellationCode(l.get(22))
                .diverted(l.get(23))
                .carrierDelayMins(l.get(24))
                .weatherDelayMins(l.get(25))
                .nasDelayMins(l.get(26))
                .securityDelayMins(l.get(27))
                .lateAircraftDelayMins(l.get(28))
                .build())
                .getOrElse(FlightEvent.builder().build());
    }
}

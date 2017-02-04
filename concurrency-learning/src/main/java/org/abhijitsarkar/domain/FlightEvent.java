package org.abhijitsarkar.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Abhijit Sarkar
 */
@Builder
@Getter
public class FlightEvent {
    private String year;
    private String month;
    private String dayOfMonth;
    private String dayOfWeek;
    private String depTime;
    private String scheduledDepTime;
    private String arrTime;
    private String scheduledArrTime;
    private String uniqueCarrier;
    private String flightNum;
    private String tailNum;
    private String actualElapsedMins;
    private String crsElapsedMins;
    private String airMins;
    private String arrDelayMins;
    private String depDelayMins;
    private String originAirportCode;
    private String destinationAirportCode;
    private String distanceInMiles;
    private String taxiInTimeMins;
    private String taxiOutTimeMins;
    private String flightCancelled;
    // (A = carrier, B = weather, C = NAS, D = security)
    private String cancellationCode;
    // 1 = yes, 0 = no
    private String diverted;
    private String carrierDelayMins;
    private String weatherDelayMins;
    private String nasDelayMins;
    private String securityDelayMins;
    private String lateAircraftDelayMins;
}

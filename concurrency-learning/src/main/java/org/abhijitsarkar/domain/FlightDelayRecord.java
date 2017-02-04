package org.abhijitsarkar.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Abhijit Sarkar
 */
@Builder
@Getter
public class FlightDelayRecord {
    private String year;
    private String month;
    private String dayOfMonth;
    private String flightNum;
    private String uniqueCarrier;
    private String arrDelayMins;

    @Override
    public String toString() {
        return String.format("%s/%s/%s - %s %s - %s", year, month, dayOfMonth, flightNum, uniqueCarrier, arrDelayMins);
    }
}

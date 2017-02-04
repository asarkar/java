package org.abhijitsarkar.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * @author Abhijit Sarkar
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(getterVisibility = NONE)
public class Stock {
    private final String symbol;
    @JsonProperty("Ask")
    private final double price;
}

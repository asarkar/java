package name.abhijitsarkar.java.java8impatient.miscellaneous;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Q16: Use a regular expression with named capturing groups to parse a line
 * containing a city,state, and zip code. Accept both 5- and 9-digit zip codes.
 * 
 * @author Abhijit Sarkar
 *
 */
public class Address {
    private static final Pattern PATTERN = Pattern
	    .compile("(?<city>[\\w+\\s]+),(?<state>[A-Z]{2}),(?<zipCode>\\d{5}|\\d{9})");

    private final String city;
    private final String state;
    private final long zipCode;

    public Address(final String line) {
	/* Matcher isn't thread safe. */
	final Matcher m = PATTERN.matcher(line);

	if (m.matches()) {
	    city = m.group("city");
	    state = m.group("state");
	    zipCode = Long.parseLong(m.group("zipCode"));
	} else {
	    this.city = "";
	    this.state = "";
	    this.zipCode = -1;
	}
    }

    public String getCity() {
	return city;
    }

    public String getState() {
	return state;
    }

    public long getZipCode() {
	return zipCode;
    }

    @Override
    public String toString() {
	return "Address [city=" + city + ", state=" + state + ", zipCode="
		+ zipCode + "]";
    }
}

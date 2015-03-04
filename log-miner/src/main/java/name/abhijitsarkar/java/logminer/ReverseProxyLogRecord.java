package name.abhijitsarkar.java.logminer;

import static java.net.URLDecoder.decode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@DiscriminatorValue(value = "rb")
public class ReverseProxyLogRecord extends AbstractLogRecord implements
	Serializable {
    private static final long serialVersionUID = -3453781562786984922L;

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(ReverseProxyLogRecord.class);

    private static final String FILE_SEPARATOR;
    private static final Pattern PATTERN;
    private static final DateTimeFormatter FORMATTER;

    static {
	StringBuilder s = new StringBuilder(
		"^\\s*(?<jvm>https\\-aac\\-allied\\-\\d)").append(".*")
		.append("\\[(?<date>\\d{2}/[a-zA-Z]{3}/\\d{4}):.*\\]\\s")
		.append("\"GET\\s(?<path>.*)\\s")
		.append("HTTP/1\\.1\\\"\\s(?<status>\\d{3})\\s")
		.append("(?<size>\\d+).*$");

	PATTERN = Pattern.compile(s.toString());
	FILE_SEPARATOR = "/";
	FORMATTER = ofPattern("dd/MMM/uuuu");
    }

    public ReverseProxyLogRecord() {
	// JPA needs a no-arg constructor
	super();
    }

    public ReverseProxyLogRecord(String line) {
	super(line);

	readLine(line.toString());
    }

    @Override
    public void readLine(String line) {
	Matcher m = PATTERN.matcher(line);

	boolean isMatchFound = m.matches() && m.groupCount() >= 5;

	if (isMatchFound) {
	    try {
		setJvm(m.group("jvm"));

		LocalDate date = parse(m.group("date"), FORMATTER);

		setDay(date.getDayOfMonth());
		setMonth(date.getMonthValue());
		setYear(date.getYear());

		String p = decode(m.group("path"), UTF_8.name());

		int index = p.indexOf(FILE_SEPARATOR, 1);

		if (index >= 1 && index < p.length()) {
		    setRoot(p.substring(1, index));
		}

		index = p.lastIndexOf(FILE_SEPARATOR) + 1;

		if (index >= 1 && index < p.length()) {
		    setFilename(p.substring(index));
		}

		setPath(p);

		setStatus(Integer.parseInt(m.group("status")));
		setSize(Long.parseLong(m.group("size")));
	    } catch (UnsupportedEncodingException e) {
		LOGGER.error("There was a problem when parsing {}...", line, e);
	    }
	} else {
	    LOGGER.debug("Skipped record: {}.", line);
	}
    }
}

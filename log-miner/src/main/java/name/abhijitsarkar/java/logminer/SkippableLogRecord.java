package name.abhijitsarkar.java.logminer;

import static java.net.URLDecoder.decode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "log")
public class SkippableLogRecord implements Serializable {
    private static final long serialVersionUID = 57504408138860634L;

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SkippableLogRecord.class);

    private static final Pattern PATTERN;
    private static final String DATE_FORMAT;
    private static final String FILE_SEPARATOR;

    private static final DateTimeFormatter FORMATTER;

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    private String jvm;
    private int day;
    private int month;
    private int year;
    private String root;
    private String filename;
    private String path;
    private int status;
    private long size;

    static {
	StringBuilder s = new StringBuilder(
		"^\\s*(?<jvm>https\\-aac\\-allied\\-\\d)").append(".*")
		.append("\\[(?<date>\\d{2}/[a-zA-Z]{3}/\\d{4}):.*\\]\\s")
		.append("\"GET\\s(?<path>.*)\\s")
		.append("HTTP/1\\.1\\\"\\s(?<status>\\d{3})\\s")
		.append("(?<size>\\d+).*$");

	PATTERN = Pattern.compile(s.toString());
	DATE_FORMAT = "dd/MMM/yyyy";
	FILE_SEPARATOR = System.getProperty("file.separator");
	FORMATTER = ofPattern("dd/MMM/uuuu");
    }

    public SkippableLogRecord() {
	// JPA needs a no-arg constructor
    }

    public SkippableLogRecord(String line) {
	readLine(line.toString());
    }

    public String getJvm() {
	return jvm;
    }

    public int getDay() {
	return day;
    }

    public int getMonth() {
	return month;
    }

    public int getYear() {
	return year;
    }

    public String getRoot() {
	return root;
    }

    public String getFilename() {
	return filename;
    }

    public String getPath() {
	return path;
    }

    public int getStatus() {
	return status;
    }

    public long getSize() {
	return size;
    }

    public boolean isSkipped() {
	return jvm == null;
    }

    private void readLine(String line) {
	Matcher m = PATTERN.matcher(line);

	boolean isMatchFound = m.matches() && m.groupCount() >= 5;

	if (isMatchFound) {
	    try {
		jvm = m.group("jvm");

		LocalDate date = parse(m.group("date"), FORMATTER);

		day = date.getDayOfMonth();
		month = date.getMonthValue();
		year = date.getYear();

		String p = decode(m.group("path"), UTF_8.name());

		int index = p.indexOf(FILE_SEPARATOR, 1);

		if (index >= 1 && index < p.length()) {
		    root = p.substring(1, index);
		}

		index = p.lastIndexOf(FILE_SEPARATOR) + 1;

		if (index >= 1 && index < p.length()) {
		    filename = p.substring(index);
		}

		path = p;

		status = Integer.parseInt(m.group("status"));
		size = Long.parseLong(m.group("size"));
	    } catch (UnsupportedEncodingException e) {
		LOGGER.error("There was a problem when parsing {}...", line, e);
	    }
	} else {
	    LOGGER.debug("Skipped record: {}.", line);
	}
    }

    @Override
    public int hashCode() {
	return Objects.hash(path, day, month, year);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!(obj instanceof SkippableLogRecord)) {
	    return false;
	}

	SkippableLogRecord other = (SkippableLogRecord) obj;

	return Objects.equals(path, other.path) && day == other.day
		&& month == other.month && year == other.year;
    }
}

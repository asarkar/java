package name.abhijitsarkar.hadoop.logminer;

import static java.lang.Math.min;
import static java.net.URLDecoder.decode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkippableLogRecord implements
	WritableComparable<SkippableLogRecord> {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SkippableLogRecord.class);

    private static final Pattern PATTERN;
    private static final String FILE_SEPARATOR;
    private static final DateTimeFormatter FORMATTER;

    private final Text jvm;
    private final IntWritable day;
    private final IntWritable month;
    private final IntWritable year;
    private final Text root;
    private final Text filename;
    private final Text path;
    private final IntWritable status;
    private final LongWritable size;

    private static final Predicate<Field> IS_HADOOP_DATATYPE;
    private static final List<Field> HADOOP_TYPE_FIELDS;

    static {
	StringBuilder s = new StringBuilder(
		"^\\s*(?<jvm>https\\-aac\\-allied\\-\\d)").append(".*")
		.append("\\[(?<date>\\d{2}/[a-zA-Z]{3}/\\d{4}):.*\\]\\s")
		.append("\"GET\\s(?<path>.*)\\s")
		.append("HTTP/1\\.1\\\"\\s(?<status>\\d{3})\\s")
		.append("(?<size>\\d+).*$");

	PATTERN = Pattern.compile(s.toString());
	FILE_SEPARATOR = System.getProperty("file.separator");
	FORMATTER = ofPattern("dd/MMM/uuuu");

	IS_HADOOP_DATATYPE = field -> field.getType().getName()
		.contains("hadoop");

	HADOOP_TYPE_FIELDS = Stream
		.of(SkippableLogRecord.class.getDeclaredFields())
		.filter(IS_HADOOP_DATATYPE).collect(toList());
    }

    public SkippableLogRecord() {
	jvm = new Text();
	day = new IntWritable();
	month = new IntWritable();
	year = new IntWritable();
	root = new Text();
	filename = new Text();
	path = new Text();
	status = new IntWritable();
	size = new LongWritable();
    }

    public SkippableLogRecord(Text line) {
	this();

	readLine(line.toString());
    }

    public Text getJvm() {
	return jvm;
    }

    public IntWritable getDay() {
	return day;
    }

    public IntWritable getMonth() {
	return month;
    }

    public IntWritable getYear() {
	return year;
    }

    public Text getRoot() {
	return root;
    }

    public Text getFilename() {
	return filename;
    }

    public Text getPath() {
	return path;
    }

    public IntWritable getStatus() {
	return status;
    }

    public LongWritable getSize() {
	return size;
    }

    public boolean isSkipped() {
	return jvm.toString() == null || jvm.toString().isEmpty();
    }

    private void readLine(String line) {
	Matcher m = PATTERN.matcher(line);

	boolean isMatchFound = m.matches() && m.groupCount() >= 5;

	if (isMatchFound) {
	    try {
		jvm.set(m.group("jvm"));

		LocalDate date = parse(m.group("date"), FORMATTER);

		day.set(date.getDayOfMonth());
		month.set(date.getMonthValue());
		year.set(date.getYear());

		String p = decode(m.group("path"), UTF_8.name());

		int index = p.indexOf(FILE_SEPARATOR, 1);

		if (index >= 1 && index < p.length()) {
		    root.set(p.substring(1, index));
		}

		index = p.lastIndexOf(FILE_SEPARATOR) + 1;

		if (index >= 1 && index < p.length()) {
		    filename.set(p.substring(index));
		}

		path.set(p);

		status.set(Integer.parseInt(m.group("status")));
		size.set(Long.parseLong(m.group("size")));
	    } catch (Exception e) {
		LOGGER.error("There was a problem when parsing {}...",
			line.substring(0, min(line.length(), 100)), e);

		isMatchFound = false;
	    }
	}
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	HADOOP_TYPE_FIELDS.forEach(field -> {
	    try {
		field.getType()
			.getDeclaredMethod("readFields", DataInput.class)
			.invoke(field.get(this), in);
	    } catch (Exception e) {
		LOGGER.error("There was a problem when reading field {}.",
			field.getName(), e);
	    }
	});
    }

    @Override
    public void write(DataOutput out) throws IOException {
	HADOOP_TYPE_FIELDS.forEach(field -> {
	    try {
		field.getType().getDeclaredMethod("write", DataOutput.class)
			.invoke(field.get(this), out);
	    } catch (Exception e) {
		LOGGER.error("There was a problem when writing field {}.",
			field.getName(), e);
	    }
	});
    }

    @Override
    public int compareTo(SkippableLogRecord other) {
	int compareValue = (day.get() - other.day.get())
		+ (month.get() - other.month.get())
		+ (year.get() - other.year.get());

	return compareValue == 0 ? path.compareTo(other.path) : compareValue;
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

	return compareTo(other) == 0;
    }
}

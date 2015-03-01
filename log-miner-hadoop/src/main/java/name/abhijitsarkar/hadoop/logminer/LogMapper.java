package name.abhijitsarkar.hadoop.logminer;

import static java.lang.Math.min;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogMapper extends
	Mapper<LongWritable, Text, SkippableLogRecord, NullWritable> {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(LogMapper.class);

    @Override
    protected void map(LongWritable key, Text line, Context context) {
	SkippableLogRecord rec = new SkippableLogRecord(line);

	String msg = line.toString();
	msg = msg.substring(0, min(msg.length(), 100));

	if (!rec.isSkipped()) {
	    try {
		context.write(rec, NullWritable.get());

		LOGGER.info("Wrote record {}.", msg);
	    } catch (IOException | InterruptedException e) {
		LOGGER.error("There was a problem when writing out {}.", msg, e);
	    }
	} else {
	    LOGGER.info("Skipped record {}.", msg);
	}
    }
}

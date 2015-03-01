package name.abhijitsarkar.hadoop.logminer;

import static java.lang.Math.min;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogReducer extends
	Reducer<SkippableLogRecord, NullWritable, DBRecord, NullWritable> {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(LogReducer.class);

    @Override
    protected void reduce(SkippableLogRecord rec,
	    Iterable<NullWritable> values, Context context) {
	String path = rec.getPath().toString();
	path = path.substring(0, min(path.length(), 100));

	try {
	    context.write(new DBRecord(rec), NullWritable.get());

	    LOGGER.info("Wrote record {}.", path);
	} catch (IOException | InterruptedException e) {
	    LOGGER.error("There was a problem when writing out {}.", path, e);
	}
    }
}

package name.abhijitsarkar.hadoop.logminer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Driver extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
	Configuration conf = getConf();
	Properties prop = getProperties();

	DBConfiguration.configureDB(conf, prop.getProperty("db.driverClass"), // driver
		prop.getProperty("db.url"), // db url
		prop.getProperty("db.username"), // user name
		prop.getProperty("db.password")); // password

	Job job = Job.getInstance(conf, "log-miner");

	job.setJarByClass(getClass());
	job.setMapperClass(LogMapper.class);
	job.setReducerClass(LogReducer.class);
	job.setMapOutputKeyClass(SkippableLogRecord.class);
	job.setMapOutputValueClass(NullWritable.class);
	job.setOutputKeyClass(DBRecord.class);
	job.setOutputValueClass(NullWritable.class);
	job.setInputFormatClass(TextInputFormat.class);
	job.setOutputFormatClass(DBOutputFormat.class);

	FileInputFormat.setInputPaths(job, new Path(args[0]));

	DBOutputFormat.setOutput(job, "log", // table name
		new String[] { "jvm", "day", "month", "year", "root",
			"filename", "path", "status", "size" } // table columns
		);

	return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
	GenericOptionsParser parser = new GenericOptionsParser(
		new Configuration(), args);

	ToolRunner.run(new Driver(), parser.getRemainingArgs());
    }

    private Properties getProperties() {
	Properties prop = new Properties();
	try {
	    prop.load(getClass().getResourceAsStream("/db.properties"));
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}

	return prop;
    }
}

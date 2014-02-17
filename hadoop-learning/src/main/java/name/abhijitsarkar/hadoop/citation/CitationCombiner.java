/*******************************************************************************
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software, 
 * and is also available at http://www.gnu.org/licenses.
 *******************************************************************************/
package name.abhijitsarkar.hadoop.citation;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class works on the cite.txt. It outputs a space separated key-value pair where the key is a citation ID and the
 * value is a comma-separated list of citations that refer to the key.
 * 
 * @author Abhijit Sarkar
 */
public class CitationCombiner extends Configured implements Tool {
	public static final Logger LOGGER = LoggerFactory.getLogger(CitationCombiner.class);

	public static class CitationMapper extends MapReduceBase implements Mapper<Text, Text, Text, Text> {

		public void map(Text key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			LOGGER.debug("Key: {}, Value: {}.", key, value);

			/* Skip the header row */
			try {
				Long.valueOf(key.toString());

				output.collect(value, key);
			} catch (NumberFormatException nfe) {
				LOGGER.warn("NumberFormatException: {}.", nfe.getMessage());
			}
		}
	}

	public static class CitationReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
		public static final byte[] COMMA = new org.apache.hadoop.io.Text(",").getBytes();

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			Text value = null;
			Text citations = new Text();

			while (values.hasNext()) {
				value = values.next();

				if (value != null && value.getLength() > 0) {
					citations.append(value.getBytes(), 0, value.getLength());

					if (values.hasNext()) {
						citations.append(COMMA, 0, 1);
					}
				}
			}

			LOGGER.debug("Key: {}, Citations: {}.", key, citations.toString());

			output.collect(key, citations);
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		JobConf conf = new JobConf(getConf(), getClass());
		conf.setJobName("citation-combiner");

		/* This is to set the separator byte for KeyValueTextInputFormat */
		conf.set("key.value.separator.in.input.line", ",");

		conf.setMapperClass(CitationMapper.class);
		conf.setReducerClass(CitationReducer.class);

		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		conf.setInputFormat(KeyValueTextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobClient.runJob(conf);

		return 0;
	}

	public static void main(String[] args) throws Exception {
		GenericOptionsParser parser = new GenericOptionsParser(new Configuration(), args);

		ToolRunner.run(new CitationCombiner(), parser.getRemainingArgs());
	}
}

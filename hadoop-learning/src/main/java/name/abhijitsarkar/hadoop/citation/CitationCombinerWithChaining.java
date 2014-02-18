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
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.lib.ChainMapper;
import org.apache.hadoop.mapred.lib.ChainReducer;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class works on the cite.txt. For the purposes of learning, it chains two mappers. It outputs a space separated
 * key-value pair where the key is a citation ID and the value is a comma-separated list of citations that refer to the
 * key.
 * 
 * @author Abhijit Sarkar
 */
public class CitationCombinerWithChaining extends Configured implements Tool {
	public static final String COMMA = ",";
	public static final Logger LOGGER = LoggerFactory.getLogger(CitationCombinerWithChaining.class);

	public static class CitationInputSplitMapper extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text> {

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			final String[] lineSplit = value.toString().split(COMMA);

			String keyStr = null;
			String valueStr = null;

			if (lineSplit.length == 2) {
				keyStr = lineSplit[0];
				valueStr = lineSplit[1];

				output.collect(new Text(keyStr), new Text(valueStr));
			}
		}
	}

	public static class CitationHeaderStripMapper extends MapReduceBase implements Mapper<Text, Text, Text, Text> {

		public void map(Text key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

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
		static final byte[] COMMA_BYTES = new Text(COMMA).getBytes();

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			Text value = null;
			Text citations = new Text();

			while (values.hasNext()) {
				value = values.next();

				if (value != null && value.getLength() > 0) {
					citations.append(value.getBytes(), 0, value.getLength());

					if (values.hasNext()) {
						citations.append(COMMA_BYTES, 0, 1);
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
		conf.setJobName("citation-combiner-with-chaining");

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobConf map1Conf = new JobConf(false);

		ChainMapper.addMapper(conf, CitationInputSplitMapper.class, LongWritable.class, Text.class, Text.class,
				Text.class, true, map1Conf);

		JobConf map2Conf = new JobConf(false);

		ChainMapper.addMapper(conf, CitationHeaderStripMapper.class, Text.class, Text.class, Text.class, Text.class,
				true, map2Conf);

		JobConf red1Conf = new JobConf(false);

		ChainReducer.setReducer(conf, CitationReducer.class, Text.class, Text.class, Text.class, Text.class, true,
				red1Conf);

		JobClient.runJob(conf);

		return 0;
	}

	public static void main(String[] args) throws Exception {
		GenericOptionsParser parser = new GenericOptionsParser(new Configuration(), args);

		ToolRunner.run(new CitationCombinerWithChaining(), parser.getRemainingArgs());
	}
}

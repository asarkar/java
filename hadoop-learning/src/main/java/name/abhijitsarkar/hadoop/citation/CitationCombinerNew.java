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
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
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
public class CitationCombinerNew extends Configured implements Tool {
	public static final String COMMA = ",";
	public static final Logger LOGGER = LoggerFactory.getLogger(CitationCombinerNew.class);

	public static class CitationMapper extends Mapper<LongWritable, Text, Text, Text> {
		@SuppressWarnings("unchecked")
		/*
		 * Key is the byte offset of the line which is useless. Value is the line content
		 */
		public void map(LongWritable key, Text value, @SuppressWarnings("rawtypes") Mapper.Context context)
				throws IOException, InterruptedException {

			final String[] lineSplit = value.toString().split(COMMA);
			String keyStr = null;
			String valueStr = null;

			if (lineSplit.length == 2) {
				keyStr = lineSplit[0];
				valueStr = lineSplit[1];
			}

			LOGGER.debug("Key: {}, Value: {}.", keyStr, valueStr);

			/* Skip the header row */
			try {
				Long.valueOf(keyStr);

				context.write(new Text(valueStr), new Text(keyStr));
			} catch (NumberFormatException nfe) {
				LOGGER.warn("NumberFormatException: {}.", nfe.getMessage());
			}
		}
	}

	public static class CitationReducer extends Reducer<Text, Text, Text, Text> {
		public static final byte[] COMMA_BYTES = new Text(COMMA).getBytes();

		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			final Iterator<Text> it = values.iterator();
			Text value = null;
			Text citations = new Text();

			while (it.hasNext()) {
				value = it.next();

				if (value != null && value.getLength() > 0) {
					citations.append(value.getBytes(), 0, value.getLength());

					if (it.hasNext()) {
						citations.append(COMMA_BYTES, 0, 1);
					}
				}
			}

			LOGGER.debug("Key: {}, Citations: {}.", key, citations.toString());

			context.write(key, citations);
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		Job job = new Job(conf, "citation-combiner-new");

		job.setMapperClass(CitationMapper.class);
		job.setReducerClass(CitationReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setJarByClass(getClass());

		job.submit();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		// TODO: Get rid of the GenericOptionsParser and just pass the args to the ToolRunner
		GenericOptionsParser parser = new GenericOptionsParser(new Configuration(), args);

		ToolRunner.run(new CitationCombinerNew(), parser.getRemainingArgs());
	}
}

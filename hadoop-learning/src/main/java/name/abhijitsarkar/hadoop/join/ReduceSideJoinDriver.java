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
package name.abhijitsarkar.hadoop.join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author Abhijit Sarkar
 */
public class ReduceSideJoinDriver extends Configured implements Tool {
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		Job job = new Job(conf, "reduce-side-join");
		job.setJarByClass(getClass());

		job.setPartitionerClass(KeyPartitioner.class);
		job.setGroupingComparatorClass(KeyGroupingComparator.class);

		job.setReducerClass(ReduceSideJoinReducer.class);

		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		MultipleInputs.addInputPath(job, new Path(args[0], "customers.txt"), TextInputFormat.class,
				CustomerMapper.class);
		MultipleInputs.addInputPath(job, new Path(args[0], "orders.txt"), TextInputFormat.class, OrderMapper.class);

		job.setMapOutputKeyClass(TaggedKey.class);
		job.setMapOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		// TODO: Get rid of the GenericOptionsParser and just pass the args to the ToolRunner
		GenericOptionsParser parser = new GenericOptionsParser(new Configuration(), args);

		if (parser.getRemainingArgs().length < 2) {
			throw new IllegalArgumentException("Not enough arguments.");
		}

		ToolRunner.run(new ReduceSideJoinDriver(), parser.getRemainingArgs());
	}
}

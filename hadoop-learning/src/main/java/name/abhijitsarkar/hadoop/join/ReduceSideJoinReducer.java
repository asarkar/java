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

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class reduces the output from {@link CustomerMapper CustomerMapper} and from {@link OrderMapper OrderMapper}.
 * Thanks to {@link KeyGroupingComparator KeyGroupingComparator} and {@link TaggedKey#compareTo(TaggedKey)}, all the
 * records for the same key (customer ID) are sent to the Reducer in one invocation and customer records are the first
 * ones to arrive.
 * 
 * @author Abhijit Sarkar
 */
public class ReduceSideJoinReducer extends Reducer<TaggedKey, Text, IntWritable, Text> {
	public static final Logger LOGGER = LoggerFactory.getLogger(ReduceSideJoinReducer.class);

	@Override
	protected void reduce(TaggedKey key, Iterable<Text> values, Context ctx) throws IOException, InterruptedException {

		StringBuilder value = new StringBuilder();

		LOGGER.debug("Entering reduce");

		for (Text v : values) {
			LOGGER.debug("Loop - Key: {}, Value: {}.", key, value);

			value.append(v.toString()).append("|");
		}
		value.delete(value.length() - 1, value.length());

		LOGGER.debug("Key: {}, Value: {}.", key.getJoinKey(), value);

		ctx.write(key.getJoinKey(), new Text(value.toString()));
	}
}

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

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class works on the orders.txt. It writes out a composite key, the sort order of which is used for secondary
 * sorting.
 * 
 * @author Abhijit Sarkar
 */
public class OrderMapper extends Mapper<LongWritable, Text, TaggedKey, Text> {
	public static final String PIPE = "\\|";
	public static final String COMMA = ",";
	public static final int SORT_ORDER = 2;
	public static final Logger LOGGER = LoggerFactory.getLogger(OrderMapper.class);

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		final String[] lineSplit = value.toString().split(PIPE, 3);

		if (lineSplit.length == 3) {
			/* Skip the header row */
			try {
				/*
				 * lineSplit[1] is the customer id. The rest of the fields are concatenated replacing pipe with comma to
				 * make it consistent with customers.txt record format.
				 */
				final int customerId = Integer.parseInt(lineSplit[1]);
				final String restOfTheLine = lineSplit[0] + COMMA + lineSplit[2].replaceAll(PIPE, COMMA);

				context.write(new TaggedKey(customerId, SORT_ORDER), new Text(restOfTheLine));
				
				LOGGER.debug("Key: {}, Value: {}.", customerId, restOfTheLine);
			} catch (NumberFormatException nfe) {
				LOGGER.warn("NumberFormatException: {}.", nfe.getMessage());
			}
		}
	}
}

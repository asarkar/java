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
 * This class works on the customers.txt. It writes out a composite key, the sort order of which is used for secondary
 * sorting.
 * 
 * @author Abhijit Sarkar
 */
public class CustomerMapper extends Mapper<LongWritable, Text, TaggedKey, Text> {
	public static final String COMMA = ",";
	public static final int SORT_ORDER = 1;
	public static final Logger LOGGER = LoggerFactory.getLogger(CustomerMapper.class);

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		final String[] lineSplit = value.toString().split(COMMA, 2);

		if (lineSplit.length == 2) {
			/* Skip the header row */
			try {
				/* lineSplit[0] is the customer id, lineSplit[1] the rest of the line */
				final int customerId = Integer.parseInt(lineSplit[0]);
				final String restOfTheLine = lineSplit[1];

				context.write(new TaggedKey(customerId, SORT_ORDER), new Text(restOfTheLine));

				LOGGER.debug("Key: {}, Value: {}.", customerId, restOfTheLine);
			} catch (NumberFormatException nfe) {
				LOGGER.warn("NumberFormatException: {}.", nfe.getMessage());
			}
		}
	}
}

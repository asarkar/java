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

import name.abhijitsarkar.hadoop.join.OrderMapper;
import name.abhijitsarkar.hadoop.join.TaggedKey;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class OrderMapperTest {
	private final OrderMapper mapper = new OrderMapper();
	private final MapDriver<LongWritable, Text, TaggedKey, Text> mapDriver = new MapDriver<LongWritable, Text, TaggedKey, Text>();

	@Test
	public void testMapValidRecord() {
		mapDriver.withMapper(mapper).withInput(new LongWritable(1L), new Text("A|1|99.99|01/01/2013"))
				.withOutput(new TaggedKey(1, OrderMapper.SORT_ORDER), new Text("A,99.99,01/01/2013")).runTest();
	}

	@Test
	public void testMapSkipHeader() {
		mapDriver.withMapper(mapper).withInput(new LongWritable(1L), new Text("ORD_ID|CUST_ID|ORD_TOTAL|ORD_DT"))
				.runTest();
	}
}

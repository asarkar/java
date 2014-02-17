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

import name.abhijitsarkar.hadoop.join.CustomerMapper;
import name.abhijitsarkar.hadoop.join.TaggedKey;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class CustomerMapperTest {
	private final CustomerMapper mapper = new CustomerMapper();
	private final MapDriver<LongWritable, Text, TaggedKey, Text> mapDriver = 
			new MapDriver<LongWritable, Text, TaggedKey, Text>();

	@Test
	public void testMapValidRecord() {
		mapDriver
				.withMapper(mapper)
				.withInput(new LongWritable(1L),
						new Text("1,Stephanie Leung,555-555-5555,123 No Such St,Fantasyland,CA 99999"))
				.withOutput(new TaggedKey(1, CustomerMapper.SORT_ORDER),
						new Text("Stephanie Leung,555-555-5555,123 No Such St,Fantasyland,CA 99999")).runTest();
	}

	@Test
	public void testMapSkipHeader() {
		mapDriver.withMapper(mapper)
				.withInput(new LongWritable(1L), new Text("CUST_ID,CUST_NAME,CUST_PH_NUM,CUST_ADDR")).runTest();
	}
}

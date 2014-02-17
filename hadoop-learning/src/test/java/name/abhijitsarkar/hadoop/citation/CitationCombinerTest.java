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

import java.util.ArrayList;
import java.util.List;

import name.abhijitsarkar.hadoop.citation.CitationCombiner;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class CitationCombinerTest {
	private final CitationCombiner.CitationMapper mapper = new CitationCombiner.CitationMapper();
	private final MapDriver<Text, Text, Text, Text> mapDriver = new MapDriver<Text, Text, Text, Text>();
	private final CitationCombiner.CitationReducer reducer = new CitationCombiner.CitationReducer();
	private final ReduceDriver<Text, Text, Text, Text> reduceDriver = new ReduceDriver<Text, Text, Text, Text>();

	@Test
	public void testMapSkipHeader() {
		mapDriver.withMapper(mapper).withInput(new Text("Key"), new Text("Value")).runTest();
	}

	@Test
	public void testMapValidRecord() {
		Text key = new Text("1");
		Text value = new Text("2");
		testMapUtility(key, value).runTest();
	}

	@Ignore
	private MapDriver<Text, Text, Text, Text> testMapUtility(Text key, Text value) {
		return mapDriver.withMapper(mapper).withInput(key, value).withOutput(value, key);
	}

	@Test
	public void testReduce() {
		Text key = new Text("1");
		List<Text> values = new ArrayList<Text>();
		values.add(new Text("2"));
		values.add(new Text("3"));
		values.add(new Text("4"));
		testReduceUtility(key, values).runTest();
	}

	@Ignore
	private ReduceDriver<Text, Text, Text, Text> testReduceUtility(Text key, List<Text> values) {
		StringBuilder expectedValue = new StringBuilder();

		for (Text v : values) {
			expectedValue.append(v.toString()).append(",");
		}
		expectedValue.deleteCharAt(expectedValue.length() - 1);
		return reduceDriver.withReducer(reducer).withInput(key, values)
				.withOutput(new Text("1"), new Text(expectedValue.toString()));
	}
}

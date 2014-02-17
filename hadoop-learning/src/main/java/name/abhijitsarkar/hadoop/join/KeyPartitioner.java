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

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * This class partitions the output from {@link CustomerMapper CustomerMapper} and from {@link OrderMapper OrderMapper}
 * so that records with the same key (customer ID) are sent to the same Reducer.
 * 
 * @author Abhijit Sarkar
 */
public class KeyPartitioner extends Partitioner<TaggedKey, Text> {

	@Override
	public int getPartition(TaggedKey key, Text value, int numPartitions) {
		return key.getJoinKey().hashCode() % numPartitions;
	}
}

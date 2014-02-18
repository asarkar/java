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

import java.io.Serializable;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * This class groups the output from {@link CustomerMapper CustomerMapper} and from {@link OrderMapper OrderMapper} so
 * that both customer and order records with the same key (customer ID) are sent to the reducer in one invocation.
 * Without custom grouping, the records are sent separately to the Reducer which forces it to save the records in memory
 * until a match for the key is found in a subsequent iteration. That is not a scalable solution and will run out of
 * memory for large data sets.
 * 
 * @author Abhijit Sarkar
 */
public class KeyGroupingComparator extends WritableComparator implements Serializable {
	private static final long serialVersionUID = 7494159558598918364L;

	public KeyGroupingComparator() {
		super(TaggedKey.class, true);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		TaggedKey aKey = (TaggedKey) a;
		TaggedKey bKey = (TaggedKey) b;

		return aKey.getJoinKey().compareTo(bKey.getJoinKey());
	}
}

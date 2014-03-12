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
package name.abhijitsarkar.algorithms.misc;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Basic implementation of a LRU cache. Not that this implementation is not synchronized. If synchronization is desired,
 * the cache should be "wrapped" using the {@link Collections#synchronizedMap Collections.synchronizedMap} method.
 * 
 * <pre>
 *   Map m = Collections.synchronizedMap(new LinkedHashMap(...));
 * </pre>
 * 
 * @author Abhijit Sarkar
 * 
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = 8109245205045494191L;

	private final int capacity;
	/* Number of entries divided by the number of buckets, 0.75 is recommended in LinkedHashMap Javadoc */
	private static final float LOAD_FACTOR = 0.75f;
	private static final boolean ACCESS_ORDER = true;

	public LRUCache(final int capacity) {
		super(capacity, LOAD_FACTOR, ACCESS_ORDER);
		this.capacity = capacity;
	}

	@Override
	protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
		return super.size() > capacity;
	}
}

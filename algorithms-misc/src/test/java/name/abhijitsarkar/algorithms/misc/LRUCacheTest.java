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

import org.junit.Assert;
import org.junit.Test;

public class LRUCacheTest {
	@Test
	public void testLRUCache() {
		LRUCache<String, String> cache = new LRUCache<>(1);

		cache.put("k1", "v1");
		Assert.assertTrue(cache.containsKey("k1"));

		cache.put("k2", "v2");
		Assert.assertFalse(cache.containsKey("k1"));
		Assert.assertTrue(cache.containsKey("k2"));
	}
}

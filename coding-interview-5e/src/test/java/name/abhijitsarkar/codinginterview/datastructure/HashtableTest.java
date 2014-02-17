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
package name.abhijitsarkar.codinginterview.datastructure;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class HashtableTest {
	private Hashtable<Integer, Integer> hashtable;

	public HashtableTest() {
		hashtable = new Hashtable<Integer, Integer>();

		hashtable.put(1, 2);
		hashtable.put(2, 3);
	}

	@Test
	public void testHashtable() {
		Assert.assertEquals(2, hashtable.size());

		Assert.assertEquals(Integer.valueOf(3), hashtable.get(2));
		Assert.assertEquals(Integer.valueOf(2), hashtable.get(1));
		hashtable.put(2, 5);
		Assert.assertEquals(Integer.valueOf(5), hashtable.get(2));
		Assert.assertEquals(null, hashtable.get(10));
	}
}

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
package name.abhijitsarkar.codinginterview.datastructure.stacksnqueues;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class StackWithMinTest {
	private StackWithMin<Integer> stack = new StackWithMin<Integer>();

	@Before
	public void setUp() {
		stack.push(99);
		stack.push(3);
		stack.push(1);
	}

	@Test
	public void testMin() {
		assertEquals(Integer.valueOf(1), stack.min());
	}
}

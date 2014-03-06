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
package name.abhijitsarkar.programminginterviews.stacksnqueues;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

public class MaxStackTest {

	@Test
	public void testPush() {
		MaxStack maxStack = new MaxStack();
		maxStack.push(1);
		maxStack.push(3);
		maxStack.push(2);

		Assert.assertEquals(3, maxStack.size());
	}

	@Test
	public void testPop() {
		MaxStack maxStack = new MaxStack();
		maxStack.push(1);
		maxStack.push(3);
		maxStack.push(2);

		Assert.assertEquals(2, maxStack.pop());
		Assert.assertEquals(3, maxStack.pop());
		Assert.assertEquals(1, maxStack.pop());
	}

	@Test
	public void testMax() {
		MaxStack maxStack = new MaxStack();
		maxStack.push(1);
		maxStack.push(3);
		maxStack.push(2);

		Assert.assertEquals(3, maxStack.max());
	}

	@Test(expected = NoSuchElementException.class)
	public void testPopWhenEmpty() {
		new MaxStack().pop();
	}
}

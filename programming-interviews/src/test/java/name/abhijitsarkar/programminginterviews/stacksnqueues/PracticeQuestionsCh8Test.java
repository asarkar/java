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

import static name.abhijitsarkar.programminginterviews.stacksnqueues.PracticeQuestionsCh8.*;

import org.junit.Test;
import org.junit.Assert;

public class PracticeQuestionsCh8Test {
	@Test
	public void testEvalRpn() {
		Assert.assertEquals(123, evalRpn("123"));
		Assert.assertEquals(3, evalRpn("1,2,+"));
		Assert.assertEquals(60, evalRpn("1,2,+,4,5,x,x"));
		Assert.assertEquals(-4, evalRpn("1,1,+,-2,x"));
		Assert.assertEquals(0, evalRpn("4,6,/,2,/"));
	}
}

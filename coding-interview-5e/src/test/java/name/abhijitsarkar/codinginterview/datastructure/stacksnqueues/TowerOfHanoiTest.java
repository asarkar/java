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

import java.util.List;

import name.abhijitsarkar.codinginterview.datastructure.stacksnqueues.TowerOfHanoi.Disk;
import name.abhijitsarkar.codinginterview.datastructure.stacksnqueues.TowerOfHanoi.Peg;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class TowerOfHanoiTest {
	TowerOfHanoi tower = null;

	public TowerOfHanoiTest() {
		tower = new TowerOfHanoi(3, 3);

		List<Peg<Disk>> pegs = tower.pegs();

		Assert.assertEquals(3, pegs.size());
		Assert.assertEquals(3, pegs.get(0).size());
		Assert.assertEquals(0, pegs.get(1).size());
		Assert.assertEquals(0, pegs.get(2).size());
	}

	@Test
	public void testMove() {
		tower.move(3, 0, 2);

		List<Peg<Disk>> pegs = tower.pegs();

		Assert.assertEquals(3, pegs.size());
		Assert.assertEquals(3, pegs.get(2).size());
		Assert.assertEquals(0, pegs.get(0).size());
		Assert.assertEquals(0, pegs.get(1).size());
	}
}

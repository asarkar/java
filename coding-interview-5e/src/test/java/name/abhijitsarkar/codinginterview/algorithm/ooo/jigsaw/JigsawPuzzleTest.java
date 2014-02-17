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
package name.abhijitsarkar.codinginterview.algorithm.ooo.jigsaw;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class JigsawPuzzleTest {

	@Test
	public void testSolve() {
		Piece[] pieces = new Piece[5];

		Piece first = new Piece(0);
		Piece second = new Piece(1);
		Piece third = new Piece(1);
		Piece fourth = new Piece(2);
		Piece fifth = new Piece(3);

		pieces[0] = first;
		pieces[1] = second;
		pieces[2] = third;
		pieces[3] = fourth;
		pieces[4] = fifth;

		JigsawPuzzle puzzle = new JigsawPuzzle(pieces);

		Assert.assertTrue(puzzle.solve());
	}

	@Test
	public void testCannotSolve() {
		Piece[] pieces = new Piece[5];

		Piece first = new Piece(0);
		Piece second = new Piece(1);
		Piece third = new Piece(1);
		Piece fourth = new Piece(2);
		Piece fifth = new Piece(6);

		pieces[0] = first;
		pieces[1] = second;
		pieces[2] = third;
		pieces[3] = fourth;
		pieces[4] = fifth;

		JigsawPuzzle puzzle = new JigsawPuzzle(pieces);

		Assert.assertFalse(puzzle.solve());
	}
}

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

import java.util.Stack;

/**
 * @author Abhijit Sarkar
 */
/*
 * Q8.6: Implement a jigsaw puzzle. Design the data structures and explain 
 * an algorithm to solve the puzzle. You can assume that you have a fitsWith method which,
 * when passed two puzzle pieces, returns true if the two pieces belong together.
 */
public class JigsawPuzzle {
	public Piece[] piecesOfPuzzle;

	public JigsawPuzzle(Piece[] pieces) {
		this.piecesOfPuzzle = pieces;
	}

	public Piece[] piecesOfPuzzle() {
		return this.piecesOfPuzzle;
	}

	public boolean solve() {
		Piece aPiece = null;
		Piece anotherPiece = null;

		Stack<Piece> stackOfPieces = stackOfPieces();

		while (stackOfPieces.size() > 1) {
			aPiece = stackOfPieces.pop();

			while (!stackOfPieces.isEmpty()) {
				anotherPiece = stackOfPieces.pop();

				if (aPiece.fitsWith(anotherPiece)) {
					Piece bigPiece = aPiece.joinWith(anotherPiece);

					stackOfPieces.push(bigPiece);

					break;
				}
			}
		}

		return (stackOfPieces.size() == 1);
	}

	private Stack<Piece> stackOfPieces() {
		Stack<Piece> stackOfPieces = new Stack<Piece>();
		final int numPieces = piecesOfPuzzle.length;

		for (int i = numPieces - 1; i >= 0; i--) {
			stackOfPieces.push(piecesOfPuzzle[i]);
		}

		return stackOfPieces;
	}
}

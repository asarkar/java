package name.abhijitsarkar.codinginterview.algorithm.ooo.jigsaw;

import java.util.Stack;

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

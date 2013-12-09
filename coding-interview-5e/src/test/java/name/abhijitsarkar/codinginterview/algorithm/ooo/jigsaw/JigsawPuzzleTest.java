package name.abhijitsarkar.codinginterview.algorithm.ooo.jigsaw;

import org.junit.Assert;
import org.junit.Test;

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

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

import java.util.Arrays;
import java.util.List;

/**
 * @author Abhijit Sarkar
 */
public class Piece {
	private int data;
	private List<Piece> pieces;

	public Piece(int data) {
		this.data = data;
	}

	public List<Piece> pieces() {
		return pieces;
	}

	public Piece joinWith(Piece otherPiece) {
		Piece bigPiece = new Piece(data + otherPiece.data);

		bigPiece.pieces = Arrays.asList(this, otherPiece);

		return bigPiece;
	}

	public int data() {
		return data;
	}

	public boolean fitsWith(Piece otherPiece) {
		return Math.abs(data - otherPiece.data) <= 1.0d;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + data;
		result = prime * result + ((pieces == null) ? 0 : pieces.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (data != other.data)
			return false;
		if (pieces == null) {
			if (other.pieces != null)
				return false;
		} else if (!pieces.equals(other.pieces))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Piece [data=" + data + ", pieces=" + pieces + "]";
	}
}

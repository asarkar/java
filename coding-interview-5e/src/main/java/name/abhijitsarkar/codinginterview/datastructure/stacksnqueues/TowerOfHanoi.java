package name.abhijitsarkar.codinginterview.datastructure.stacksnqueues;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/* Q3.4: In the classic problem of the Towers of Hanoi, you have 3 towers and N disks 
 * of different sizes which can slide on to any tower.The puzzle starts with disks 
 * sorted in ascending order of size from top to bottom 
 * (i.e., each disk sits on top of an even larger one). 
 * You have the following constraints:
 * (1) Only one disk can be moved at a time.
 * (2) A disk is slid off the top of one tower onto the next tower.
 * (3) A disk can only be placed on top of a larger disk.
 * Write a program to move the disksfrom the first tower to the last using stacks
 */
public class TowerOfHanoi {
	private List<Peg<Disk>> pegs = null;

	public TowerOfHanoi(int numPegs, int numDisks) {
		initPegs(numPegs);
		initDisks(numDisks);
	}

	private void initPegs(int numPegs) {
		pegs = new ArrayList<Peg<Disk>>();

		for (int i = 0; i < numPegs; i++) {
			Peg<Disk> peg = new Peg<Disk>();

			pegs.add(peg);
		}
	}

	private void initDisks(int numDisks) {
		Peg<Disk> firstPeg = pegs.get(0);

		for (int j = numDisks; j > 0; j--) {
			Disk disk = new Disk(j);

			firstPeg.push(disk);
		}
	}

	public List<Peg<Disk>> pegs() {
		return pegs;
	}

	public void move(int numDisks, int fromPeg, int toPeg) {
		if (numDisks == 1) {
			move(fromPeg, toPeg);
		} else {
			move(numDisks - 1, fromPeg, sparePeg(fromPeg, toPeg));
			move(fromPeg, toPeg);
			move(numDisks - 1, sparePeg(fromPeg, toPeg), toPeg);
		}
	}

	private void move(int fromPeg, int toPeg) {
		checkIfMoveAllowed(fromPeg, toPeg);

		Disk disk = pegs.get(fromPeg).pop();
		pegs.get(toPeg).push(disk);

		System.out.printf("Moved disk %d from peg %d to peg %d.\n", disk.data,
				fromPeg, toPeg);
	}

	private void checkIfMoveAllowed(int fromPeg, int toPeg) {
		Disk fromPegTopDisk = null;
		Disk toPegTopDisk = null;

		try {
			fromPegTopDisk = pegs.get(fromPeg).peek();
		} catch (EmptyStackException e) {
			throw new MoveNotAllowedException(
					"Move not allowed; start peg is empty.");
		}

		if (!pegs.get(toPeg).isEmpty()) {
			toPegTopDisk = pegs.get(toPeg).peek();
		}

		if (toPegTopDisk != null && toPegTopDisk.isSmaller(fromPegTopDisk)) {
			throw new MoveNotAllowedException(
					"Move not allowed; destination peg has a smaller disk on top than the start peg.");
		}
	}

	private int sparePeg(int fromPeg, int toPeg) {
		for (int aPeg = 0; aPeg < pegs.size(); aPeg++) {
			if (aPeg == fromPeg || aPeg == toPeg) {
				continue;
			}

			return aPeg;
		}
		return -1;
	}

	static class Disk {
		private Integer data = 0;

		private Disk(int data) {
			this.data = data;
		}

		public boolean isSmaller(Disk otherDisk) {
			return data.compareTo(otherDisk.data) < 0;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((data == null) ? 0 : data.hashCode());
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
			Disk other = (Disk) obj;
			if (data == null) {
				if (other.data != null)
					return false;
			} else if (!data.equals(other.data))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Disk [data=" + data + "]";
		}
	}

	static class Peg<T> extends Stack<Disk> {
		private static final long serialVersionUID = 5063637478775695151L;

		@Override
		public String toString() {
			return "Peg [" + super.toString() + "]";
		}
	}

	class MoveNotAllowedException extends RuntimeException {
		private static final long serialVersionUID = 6271857984782497758L;

		public MoveNotAllowedException(String message) {
			super(message);
		}
	}
}

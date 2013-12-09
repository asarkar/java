package name.abhijitsarkar.codinginterview.datastructure.stacksnqueues;

import java.util.List;

import name.abhijitsarkar.codinginterview.datastructure.stacksnqueues.TowerOfHanoi.Disk;
import name.abhijitsarkar.codinginterview.datastructure.stacksnqueues.TowerOfHanoi.Peg;

import org.junit.Assert;
import org.junit.Test;

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

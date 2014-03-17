package name.abhijitsarkar.algorithms.ooo.restaurant;

import static name.abhijitsarkar.algorithms.ooo.restaurant.Table.SpecialFeature.WINDOW_SIDE;

import java.util.Arrays;
import java.util.HashSet;

import name.abhijitsarkar.algorithms.ooo.restaurant.Table.SpecialFeature;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestTableManager {
	private Table blahTable;
	private Table windowSideTable;

	@Before
	public void setUp() {
		blahTable = new Table(1, 2);
		windowSideTable = new Table(2, 2);
		windowSideTable.setSpecialFeatures(Arrays.asList(new SpecialFeature[] { WINDOW_SIDE }));

		TableManager.setAllTables(new HashSet<Table>(Arrays.asList(new Table[] { blahTable, windowSideTable })));
	}

	@Test
	public void testReserveTable() {
		Table table = TableManager.reserveTable(2);
		Assert.assertEquals(blahTable, table);

		table = TableManager.reserveTable(2, WINDOW_SIDE);
		Assert.assertEquals(windowSideTable, table);
	}

	@Test
	public void testReserveTableWhenNoneMatches() {
		Assert.assertNull(TableManager.reserveTable(4));
	}

	@Test
	public void testReserveMoreTablesThanAvailable() {
		Table table = TableManager.reserveTable(2);
		Assert.assertEquals(blahTable, table);

		table = TableManager.reserveTable(2, WINDOW_SIDE);
		Assert.assertEquals(windowSideTable, table);

		Assert.assertNull(TableManager.reserveTable(2));
	}

	@Test(expected = IllegalStateException.class)
	public void testWhenNoTables() {
		TableManager.setAllTables(null);
		
		TableManager.reserveTable(2);
	}
}

package name.abhijitsarkar.bingo.businesslogic;

import static name.abhijitsarkar.bingo.businesslogic.BingoNumberCaller.BINGO;
import static name.abhijitsarkar.bingo.businesslogic.BingoNumberCaller.BINGO_NUMBER_POOL;
import static name.abhijitsarkar.bingo.businesslogic.BingoNumberCaller.BUCKET_SIZE;
import static name.abhijitsarkar.bingo.businesslogic.BingoNumberCaller.LARGEST_NUMBER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import name.abhijitsarkar.bingo.businesslogic.BingoNumberCaller;
import name.abhijitsarkar.bingo.businesslogic.NoMoreBingoNumbersException;

import org.junit.Before;
import org.junit.Test;

public class BingoNumberCallerTest {

	private BingoNumberCaller generator = null;

	@Before
	public void beforeEachTest() {
		generator = new BingoNumberCaller();
	}

	@Test
	public void testGenerateBingoNumber() {
		for (int i = 1; i <= LARGEST_NUMBER; i++) {
			try {
				String result = generator.callBingoNumber();

				assertNotNull(result);
				assertTrue(BINGO.contains(String.valueOf(result.charAt(0))));
				assertTrue(Integer.parseInt(result.substring(1)) <= LARGEST_NUMBER);
			} catch (NoMoreBingoNumbersException ex) {
				assertEquals(LARGEST_NUMBER, i);
			}
		}
	}

	@Test
	public void testGetBingoNumberPool() {
		assertEquals(LARGEST_NUMBER, BUCKET_SIZE * BINGO.length());

		assertEquals("B1", BINGO_NUMBER_POOL[0][0]);
		assertEquals("O61", BINGO_NUMBER_POOL[0][4]);
		assertEquals("B15", BINGO_NUMBER_POOL[14][0]);
		assertEquals("O75", BINGO_NUMBER_POOL[14][4]);
	}

	@Test
	public void testAddToCalledNumber() {
		try {
			String bingoNumber = generator.callBingoNumber();
			assertTrue(generator.getNumbersCalled().contains(bingoNumber));
		} catch (NoMoreBingoNumbersException ex) {
			fail("Shouldn't be here.");
		}
	}
}

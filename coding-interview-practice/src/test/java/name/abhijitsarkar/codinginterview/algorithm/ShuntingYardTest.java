package name.abhijitsarkar.codinginterview.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShuntingYardTest {
	private final ShuntingYard shuntingYard = new ShuntingYard();

	@Test
	public void testConvertToPostfix() {
		assertEquals("3 4 2 * 1 5 - / +", shuntingYard.convertToPostfix("3 + 4 * 2 / ( 1 - 5 )"));
	}
}

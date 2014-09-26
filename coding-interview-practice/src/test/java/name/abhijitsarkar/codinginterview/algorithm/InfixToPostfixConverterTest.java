package name.abhijitsarkar.codinginterview.algorithm;

import static org.junit.Assert.assertEquals;
import name.abhijitsarkar.codinginterview.algorithm.expression.InfixToPostfixConverter;

import org.junit.Test;

public class InfixToPostfixConverterTest {
	private final InfixToPostfixConverter converter = new InfixToPostfixConverter();

	@Test
	public void testConvertToPostfix() {
		assertEquals("3 4 2 * 1 5 - / +", converter.convert("3 + 4 * 2 / ( 1 - 5 )"));
	}
}

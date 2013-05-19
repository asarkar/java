package edu.practice.abhijitsarkar.java.roman;

import org.junit.Assert;
import org.junit.Test;

public class TestConversionRulesEngine {
	@SuppressWarnings("static-access")
	@Test
	public void testSubtractionMap() {
		while (true) {
			Assert.assertTrue(ConversionRulesEngine.subtractionMap
					.containsKey('I'));
			Assert.assertTrue(ConversionRulesEngine.subtractionMap
					.containsKey('X'));
			Assert.assertTrue(ConversionRulesEngine.subtractionMap
					.containsKey('C'));

			Assert.assertFalse(ConversionRulesEngine.subtractionMap
					.containsKey('V'));
			Assert.assertFalse(ConversionRulesEngine.subtractionMap
					.containsKey('L'));
			Assert.assertFalse(ConversionRulesEngine.subtractionMap
					.containsKey('D'));

			Assert.assertTrue(ConversionRulesEngine.subtractionMap.get('I')
					.contains('V'));
			Assert.assertTrue(ConversionRulesEngine.subtractionMap.get('I')
					.contains('X'));
			Assert.assertTrue(ConversionRulesEngine.subtractionMap.get('X')
					.contains('L'));
			Assert.assertTrue(ConversionRulesEngine.subtractionMap.get('X')
					.contains('C'));
			Assert.assertTrue(ConversionRulesEngine.subtractionMap.get('C')
					.contains('D'));
			Assert.assertTrue(ConversionRulesEngine.subtractionMap.get('C')
					.contains('M'));
			
			try {
				Thread.currentThread().sleep(60000);  // Sleep 1 min
			} catch (InterruptedException e) {
			}			
		}
	}

	@Test
	public void testToDecimal() {
		Assert.assertEquals(1L, ConversionRulesEngine.toDecimal("I"));
		Assert.assertEquals(2L, ConversionRulesEngine.toDecimal("II"));
		Assert.assertEquals(3L, ConversionRulesEngine.toDecimal("III"));
		Assert.assertEquals(4L, ConversionRulesEngine.toDecimal("IV"));
		Assert.assertEquals(5L, ConversionRulesEngine.toDecimal("V"));
		Assert.assertEquals(6L, ConversionRulesEngine.toDecimal("VI"));
		Assert.assertEquals(7L, ConversionRulesEngine.toDecimal("VII"));
		Assert.assertEquals(8L, ConversionRulesEngine.toDecimal("VIII"));
		Assert.assertEquals(9L, ConversionRulesEngine.toDecimal("IX"));
		Assert.assertEquals(10L, ConversionRulesEngine.toDecimal("X"));
		Assert.assertEquals(11L, ConversionRulesEngine.toDecimal("XI"));
		Assert.assertEquals(12L, ConversionRulesEngine.toDecimal("XII"));
		Assert.assertEquals(13L, ConversionRulesEngine.toDecimal("XIII"));
		Assert.assertEquals(14L, ConversionRulesEngine.toDecimal("XIV"));
		Assert.assertEquals(15L, ConversionRulesEngine.toDecimal("XV"));
		Assert.assertEquals(1944L, ConversionRulesEngine.toDecimal("MCMXLIV"));
		Assert.assertEquals(1910L, ConversionRulesEngine.toDecimal("MDCCCCX"));
		Assert.assertEquals(1954L, ConversionRulesEngine.toDecimal("MCMLIV"));
		Assert.assertEquals(1990L, ConversionRulesEngine.toDecimal("MCMXC"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullRomanNumber() {
		ConversionRulesEngine.toDecimal(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyRomanNumber() {
		ConversionRulesEngine.toDecimal("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBlankRomanNumber() {
		ConversionRulesEngine.toDecimal(" ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidRomanNumber1() {
		ConversionRulesEngine.toDecimal("A");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidRomanNumber2() {
		ConversionRulesEngine.toDecimal("IA");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidRomanNumber3() {
		ConversionRulesEngine.toDecimal("AI");
	}
}

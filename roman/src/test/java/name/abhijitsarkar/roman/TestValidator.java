package name.abhijitsarkar.roman;

import name.abhijitsarkar.roman.Validator;

import org.junit.Test;

public class TestValidator {
	@Test
	public void testValidNumber1() {
		Validator.validate("III");
	}

	@Test
	public void testValidNumber2() {
		Validator.validate("XXX");
	}

	@Test
	public void testValidNumber3() {
		Validator.validate("IIIX");
	}

	@Test
	public void testValidNumber4() {
		Validator.validate("XXXIX");
	}

	@Test
	public void testValidNumber5() {
		Validator.validate("IV");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumber1() {
		Validator.validate("IIII");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumber2() {
		Validator.validate("MMMM");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumber3() {
		Validator.validate("DD");
	}
}

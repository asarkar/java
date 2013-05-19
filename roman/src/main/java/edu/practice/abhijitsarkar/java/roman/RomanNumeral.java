package edu.practice.abhijitsarkar.java.roman;

public enum RomanNumeral {
	I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

	private RomanNumeral(final int decimalValue) {
		this.decimalValue = decimalValue;
	}

	private int decimalValue;

	public int getDecimalValue() {
		return decimalValue;
	}
}

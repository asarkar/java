package edu.practice.abhijitsarkar.java.roman;

import java.util.HashMap;
import java.util.Map;

public class Validator {
	public static final Map<Character, Integer> repeatMap;

	static {
		repeatMap = new HashMap<Character, Integer>();

		repeatMap.put('I', 3);
		repeatMap.put('X', 3);
		repeatMap.put('C', 3);
		repeatMap.put('M', 3);
		repeatMap.put('D', 1);
		repeatMap.put('L', 1);
		repeatMap.put('V', 1);
	}

	public static void validate(final String romanNumber) {
		if (romanNumber == null || romanNumber.trim().length() == 0) {
			throw new IllegalArgumentException(romanNumber
					+ " is not a valid Roman number.");
		}

		final int len = romanNumber.length();
		char currentChar = '\u0000';

		for (int index = 0; index < len; index++) {
			currentChar = Character.toUpperCase(romanNumber.charAt(index));

			if (romanNumber.toUpperCase().matches(
					Character.toUpperCase(currentChar) + "{"
							+ (repeatMap.get(currentChar) + 1) + ",}")) {
				throw new IllegalArgumentException(currentChar
						+ " can not be repeated more than "
						+ repeatMap.get(currentChar) + " times in succession.");
			}
		}
	}
}

package name.abhijitsarkar.roman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversionRulesEngine {
	public static final Map<Character, List<Character>> subtractionMap;

	static {
		subtractionMap = new HashMap<Character, List<Character>>();

		/* 'I' can be subtracted from 'V' and 'X' only */
		List<Character> subtrahendList = new ArrayList<Character>();
		subtrahendList.add('V');
		subtrahendList.add('X');
		subtractionMap.put('I', subtrahendList);

		/* 'X' can be subtracted from 'L' and 'C' only */
		subtrahendList = new ArrayList<Character>();
		subtrahendList.add('L');
		subtrahendList.add('C');
		subtractionMap.put('X', subtrahendList);

		/* 'C' can be subtracted from 'D' and 'M' only */
		subtrahendList = new ArrayList<Character>();
		subtrahendList.add('D');
		subtrahendList.add('M');
		subtractionMap.put('C', subtrahendList);
	}

	public static long toDecimal(final String romanNumber) {
		final int len = romanNumber.length();
		int difference = 0;
		long decimalNumber = 0L;
		char currentChar = '\u0000';
		char lookAheadChar = '\u0000';

		for (int index = 0; index < len;) {
			currentChar = Character.toUpperCase(romanNumber.charAt(index));

			/*
			 * If current character is not the last character and is an allowed
			 * subtrahend, proceed
			 */
			if (index != (len - 1) && subtractionMap.containsKey(currentChar)) {
				lookAheadChar = Character.toUpperCase(romanNumber
						.charAt(index + 1));

				/*
				 * If next character is an allowed minuend, compute the decimal
				 * difference between the current character and the next
				 * character and add to the decimal number
				 */
				if (subtractionMap.get(currentChar).contains(lookAheadChar)) {
					/* difference = minuend - subtrahend */
					difference = getDecimalValue(lookAheadChar)
							- getDecimalValue(currentChar);
					decimalNumber += difference;

					/*
					 * skip the next character as it has already been used for
					 * the subtraction
					 */
					index += 2;
				}
				/*
				 * Current character is an allowed minuend but next chacater is
				 * NOT an allowed minuend, just compute the decimal equivalent
				 * of the current character and add to the decimal number
				 */
				else {
					decimalNumber += getDecimalValue(currentChar);

					index++;
				}
			}
			/*
			 * Current character is NOT an allowed minuend, just compute the
			 * decimal equivalent of the current character and add to the
			 * decimal number
			 */
			else {
				decimalNumber += getDecimalValue(currentChar);

				index++;
			}
		}
		return decimalNumber;
	}

	private static int getDecimalValue(final char charAt) {
		try {
			return Enum.valueOf(RomanNumeral.class,
					Character.toString(charAt).toUpperCase()).getDecimalValue();
		} catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException(charAt
					+ " is not a valid Roman numeral.");
		}
	}
}

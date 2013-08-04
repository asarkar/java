package name.abhijitsarkar.learning.roman;

import java.util.Scanner;

public class Converter {
	public static void main(final String... args) {
		System.out.println("Please input a Roman number. "
				+ "To exit, press CTRL+C.");
		Scanner sc = new Scanner(System.in);
		String romanNumber = null;

		while (sc.hasNextLine()) {
			romanNumber = sc.next();

			Validator.validate(romanNumber);

			System.out.println("The decimal equivalent of " + romanNumber
					+ " is: " + ConversionRulesEngine.toDecimal(romanNumber)
					+ "\n");
			System.out.println("Please input a Roman number. "
					+ "To exit, press CTRL+C.");
		}
	}
}

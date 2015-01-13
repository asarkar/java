package name.abhijitsarkar.java.java8impatient.miscellaneous;

import static java.lang.Integer.toUnsignedLong;
import static java.lang.Long.toUnsignedString;

import java.math.BigInteger;

public class Calculator {
    public static int addUnsignedIntExact(final int unsignedAddend1,
	    final int unsignedAddend2) {
	final Long sum = toUnsignedLong(unsignedAddend1)
		+ toUnsignedLong(unsignedAddend2);

	return new BigInteger(toUnsignedString(sum)).intValueExact();
    }

    public static int subtractUnsignedIntExact(final int unsignedMinuend,
	    final int unsignedSubtrahend) {
	final Long difference = toUnsignedLong(unsignedMinuend)
		- toUnsignedLong(unsignedSubtrahend);

	return new BigInteger(toUnsignedString(difference)).intValueExact();
    }

}

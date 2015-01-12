package name.abhijitsarkar.java.java8impatient.lambda;

import static java.util.Arrays.sort;
import static name.abhijitsarkar.java.java8impatient.lambda.ComparatorFactory.newComparator;
import static name.abhijitsarkar.java.java8impatient.lambda.ComparatorFactory.ComparatorCustomizationOption.CASE_INSENSITIVE;
import static name.abhijitsarkar.java.java8impatient.lambda.ComparatorFactory.ComparatorCustomizationOption.REVERSED;
import static name.abhijitsarkar.java.java8impatient.lambda.ComparatorFactory.ComparatorCustomizationOption.SPACE_INSENSITIVE;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ComparatorFactoryTest {
    @Test
    public void testComparatorGeneratorReversed() {
	String[] actual = new String[] { "a", "b", "c" };
	String[] expected = new String[] { "c", "b", "a" };

	sort(actual, newComparator(REVERSED));

	assertArrayEquals(expected, actual);
    }

    @Test
    public void testComparatorGeneratorCaseInsensitive() {
	String[] actual = new String[] { "a", "c", "B" };
	String[] expected = new String[] { "a", "B", "c" };

	sort(actual, newComparator(CASE_INSENSITIVE));

	assertArrayEquals(expected, actual);
    }

    @Test
    public void testComparatorGeneratorCaseSensitive() {
	String[] actual = new String[] { "a", "c", "B" };
	String[] expected = new String[] { "B", "a", "c" };

	sort(actual, newComparator());

	assertArrayEquals(expected, actual);
    }

    @Test
    public void testComparatorGeneratorSpaceInsensitive() {
	String[] actual = new String[] { "b ", "a", "c" };
	String[] expected = new String[] { "a", "b ", "c" };

	sort(actual, newComparator(SPACE_INSENSITIVE));

	assertArrayEquals(expected, actual);
    }

    @Test
    public void testComparatorGeneratorSpaceSensitive() {
	String[] actual = new String[] { "a", "c", " b" };
	String[] expected = new String[] { " b", "a", "c" };

	sort(actual, newComparator());

	assertArrayEquals(expected, actual);
    }

    @Test
    public void testComparatorAllOptions() {
	String[] actual = new String[] { "B", "a ", "c" };
	String[] expected = new String[] { "c", "a ", "B" };

	sort(actual,
		newComparator(REVERSED, CASE_INSENSITIVE, SPACE_INSENSITIVE));

	assertArrayEquals(expected, actual);
    }
}

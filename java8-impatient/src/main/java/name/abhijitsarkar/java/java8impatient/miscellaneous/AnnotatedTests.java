package name.abhijitsarkar.java.java8impatient.miscellaneous;

public class AnnotatedTests {
    /**
     * Q12: Implement the {@code TestCase} annotation and a program that loads a
     * class with such annotations and invokes the annotated methods, checking
     * whether they yield the expected values. Assume that parameters and return
     * values are integers.
     * 
     * @param n
     *            Input that's to be squared.
     * @return Square of the input.
     */
    @TestCase(param = 0, expected = 0)
    public static int square1(final int n) {
	return n * n;
    }

    @TestCase(param = 2, expected = 4)
    @TestCase(param = 4, expected = 16)
    public static int square2(final int n) {
	return n * n;
    }
}

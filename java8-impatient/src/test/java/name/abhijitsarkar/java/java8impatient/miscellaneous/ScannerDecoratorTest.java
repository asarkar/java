package name.abhijitsarkar.java.java8impatient.miscellaneous;

import static java.util.stream.Collectors.toList;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.ScannerDecorator.ints;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.ScannerDecorator.lines;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.ScannerDecorator.words;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Scanner;

import org.junit.Test;

public class ScannerDecoratorTest {
    @Test
    public void testLines() {
	String input = "1 fish 2 fish" + System.lineSeparator()
		+ "red fish blue fish";
	List<String> lines = lines(new Scanner(input)).collect(toList());

	assertEquals(2, lines.size());
	assertEquals("1 fish 2 fish", lines.get(0));
	assertEquals("red fish blue fish", lines.get(1));
    }

    @Test
    public void testWords() {
	String input = "1 fish 2 fish" + System.lineSeparator()
		+ "red fish blue fish";
	List<String> lines = words(new Scanner(input)).collect(toList());

	assertEquals(8, lines.size());
	assertEquals("1", lines.get(0));
	assertEquals("fish", lines.get(7));
    }

    @Test
    public void testInts() {
	String input = "1 fish 2 fish" + System.lineSeparator()
		+ "3 fish 4 fish";
	List<Integer> lines = ints(new Scanner(input)).boxed()
		.collect(toList());

	assertEquals(4, lines.size());
	assertEquals(Integer.valueOf(1), lines.get(0));
	assertEquals(Integer.valueOf(4), lines.get(3));
    }

    @Test
    public void testIntsWhenThereIsNone() {
	String input = "one fish two fish" + System.lineSeparator()
		+ "three fish four fish";
	List<Integer> lines = ints(new Scanner(input)).boxed()
		.collect(toList());

	assertEquals(0, lines.size());
    }
}

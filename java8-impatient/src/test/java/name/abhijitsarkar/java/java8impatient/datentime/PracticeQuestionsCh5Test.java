package name.abhijitsarkar.java.java8impatient.datentime;

import static name.abhijitsarkar.java.java8impatient.datentime.PracticeQuestionsCh5.cal;
import static name.abhijitsarkar.java.java8impatient.datentime.PracticeQuestionsCh5.fridayTheThirteenth;
import static name.abhijitsarkar.java.java8impatient.datentime.PracticeQuestionsCh5.getProgrammersDayForYear;
import static name.abhijitsarkar.java.java8impatient.datentime.PracticeQuestionsCh5.next;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import org.junit.Test;

public class PracticeQuestionsCh5Test {

    @Test
    public void testGetProgrammersDayForNonLeapYear() {
	int year = 2014;

	LocalDate programmersDayExpected = LocalDate.of(year, 9, 13);
	assertEquals(programmersDayExpected, getProgrammersDayForYear(year));
    }

    @Test
    public void testGetProgrammersDayForLeapYear() {
	int year = 2016;

	LocalDate programmersDayExpected = LocalDate.of(year, 9, 12);
	assertEquals(programmersDayExpected, getProgrammersDayForYear(year));
    }

    @Test
    public void testNextWithASaturday() {
	LocalDate nextWorkingDay = LocalDate.of(2015, 1, 5);

	assertEquals(
		nextWorkingDay,
		LocalDate.of(2015, 1, 3).with(
			next(w -> w.getDayOfWeek().getValue() < 6)));
    }

    @Test
    public void testNextWithAFriday() {
	LocalDate nextWorkingDay = LocalDate.of(2015, 1, 2);

	assertEquals(
		nextWorkingDay,
		LocalDate.of(2015, 1, 2).with(
			next(w -> w.getDayOfWeek().getValue() < 6)));
    }

    @Test
    public void testCal() {
	/*
	 * Not much can be tested for a void method that doesn't have side
	 * effects; just print the calendars for the current year.
	 */
	Year currentYear = Year.now();
	IntStream.rangeClosed(1, 12).forEach(
		month -> cal(currentYear.getValue(), month));
    }

    @Test
    public void testFridayTheThirteenth() {
	fridayTheThirteenth().forEach(
		date -> System.out.println(DateTimeFormatter.ISO_DATE
			.format(date)));
    }
}

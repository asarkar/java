/*******************************************************************************
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software, 
 * and is also available at http://www.gnu.org/licenses.
 *******************************************************************************/
package name.abhijitsarkar.java.java8impatient.datentime;

import static java.lang.System.out;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.ZoneId.getAvailableZoneIds;
import static java.time.temporal.TemporalAdjusters.firstDayOfNextMonth;
import static java.util.stream.Collectors.toList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh5 {
    public static final Logger LOGGER = LoggerFactory
	    .getLogger(PracticeQuestionsCh5.class);

    /**
     * Compute Porgrammer's Day without using {@code plusDays}.
     * 
     * @param year
     *            The year for which to calculate Programmer's Day.
     * @return A {@code LocalDate} instance representing the Programmer's Day
     *         for the given year.
     */
    public static LocalDate getProgrammersDayForYear(final int year) {
	return LocalDate.of(year, 1, 1).withDayOfYear(256);
    }

    /**
     * Q2: What happens when you add one year to
     * {@code LocalDate.of(2000, 2, 29)}? Four years? Four times one year?
     * <p>
     * <b>Ans:</b> Using method {@code plusYears}, adding 1 year too 2000-2-29
     * yields 2001-02-28. Adding 4 years and 4 times 1 year yield 2004-02-29.
     */
    public void existsOnlyToGlorifyJavadoc() {
    }

    /**
     * Q3: Implement a method {@code next} that takes a
     * {@code Predicate<LocalDate>} and returns an adjuster yielding the next
     * date fulfilling the predicate. For example,
     * <code>today.with(next(w -> getDayOfWeek().getValue() < 6)</code> computes
     * the next workday.
     * <p>
     * At first glance, it seems that the return type of the lambda expression
     * is incompatible with the method return type. The lambda expr. returns a
     * {@code Temporal} while the method returns a {@code TemporalAdjuster}.
     * However, what actually returned here is a lambda expr. that accepts a
     * {@code Temporal} and returns a {@code Temporal}. That's nothing but a
     * {@code TemporalAdjuster}. Another point to be noted is that the compiler
     * will supply the input {@code Temporal} based on the context of the call.
     * 
     * @param Predicate
     *            . .
     * @return TemporalAdjuster that fulfills the predicate.
     */
    public static TemporalAdjuster next(final Predicate<LocalDate> condition) {
	return (localDate) -> {
	    LocalDate input = LocalDate.from(localDate);
	    for (; !condition.test(input); input = input.plusDays(1L))
		;

	    return input;
	};
    }

    /**
     * Q4: Write an equivalent of the Unix {@code cal} program that displays a
     * calendar for a month. Show the weekend at the end of the week.
     * <p>
     * The output mirrors the Unix {@code cal} output, with 2 char day names and
     * right justified single digit days.
     * 
     * @param year
     *            The year.
     * @param month
     *            The month.
     */
    public static void cal(final int year, final int month) {
	final String monthYear = Month.of(month).toString() + " " + year;

	/*
	 * The month year is output approximately in the center; the number of
	 * leading spaces is calculated using the number of unused spaces on the
	 * row and then halving it.
	 */
	int width = (20 - monthYear.length()) / 2 + monthYear.length();

	out.printf("\n%" + width + "s\n", monthYear);

	for (DayOfWeek day : DayOfWeek.values()) {
	    /*
	     * Output 2 char day names. If Mon, use 2 spaces (means no leading
	     * spaces), else use 3 (means 1 space between names).
	     */
	    out.printf("%" + (day.getValue() > 1 ? 3 : 2) + "s", day.toString()
		    .substring(0, 2));
	}
	out.printf("\n");

	final LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
	final LocalDate firstDayOfNextMonth = firstDayOfMonth
		.with(firstDayOfNextMonth());

	DayOfWeek dayOfWeek = null;
	int dayOfMonth = 0;

	for (LocalDate runningDate = firstDayOfMonth; runningDate
		.isBefore(firstDayOfNextMonth); runningDate = runningDate
		.plusDays(1)) {
	    dayOfWeek = runningDate.getDayOfWeek();
	    dayOfMonth = runningDate.getDayOfMonth();

	    /*
	     * If Mon, use 2 spaces for output (means no leading space when the
	     * date is double digit and a leading space when the date is single
	     * digit).
	     */
	    if (dayOfWeek == MONDAY) {
		width = 2;
	    }
	    /*
	     * If first day of the month, calculate the starting position; add
	     * an additional space for right justification when the date is
	     * single digit (< 10).
	     */
	    else if (runningDate == firstDayOfMonth) {
		width = (dayOfWeek.getValue() - 1) * 3 + 1
			+ (dayOfMonth < 10 ? 1 : 0);
	    }
	    /*
	     * If not Mon or start of the month, use 3 spaces for output. This
	     * will output single digit dates right justified.
	     */
	    else {
		width = 3;
	    }

	    /* Output, breaking the line after Sun. */
	    out.printf("%" + width + (dayOfWeek != SUNDAY ? "s" : "s\n"),
		    dayOfMonth);
	}
    }

    /**
     * Q5: Write a program that prints how many days you have been alive.
     * 
     * @param year
     *            Birth year.
     * @param month
     *            Birth month.
     * @param dayOfMonth
     *            Birth day of month.
     * @return Age in days.
     */
    public static long ageInDays(final int year, final int month,
	    final int dayOfMonth) {
	final LocalDate dob = LocalDate.of(year, month, dayOfMonth);
	final LocalDate now = LocalDate.now();

	return ChronoUnit.DAYS.between(dob, now);
    }

    /**
     * List all Friday the 13th in the twentieth century.
     * 
     * @return List of all Friday the 13th in the twentieth century.
     */
    public static List<LocalDate> fridayTheThirteenth() {
	final LocalDate startDate = LocalDate.of(1900, 1, 13);

	return Stream.iterate(startDate, date -> date.plusMonths(1L))
		.limit(100).filter(date -> date.getDayOfWeek() == FRIDAY)
		.collect(toList());

	/* Alternative implementation */
	// final LocalDate startDate = LocalDate.of(1899, 12, 13);
	// final LocalDate endDate = LocalDate.of(2000, 1, 13);

	// final UnaryOperator<LocalDate> nextFriDayTheThirteenth = (input) -> {
	// LocalDate date = LocalDate.from(input).plusMonths(1);
	//
	// for (; date.getDayOfWeek() != FRIDAY; date = date.plusMonths(1))
	// ;
	//
	// return date;
	// };

	// final Stream.Builder<LocalDate> builder = Stream.builder();
	//
	// for (LocalDate runningDate = startDate
	// .with(ofDateAdjuster(nextFriDayTheThirteenth)); runningDate
	// .isBefore(endDate); runningDate = runningDate
	// .with(ofDateAdjuster(nextFriDayTheThirteenth))) {
	// builder.add(runningDate);
	// }
	//
	// return builder.build().collect(toList());
    }

    /**
     * Q8: Obtain the offsets of today's date in all supported time zones for
     * the current time instant, turning {@code ZoneId.getAvailableIds} into a
     * stream and using stream operations.
     * 
     * @return List of all offsets prefixed by zone ids.
     */
    public static List<String> getAllOffsets() {
	final LocalDateTime now = LocalDateTime.now();

	return getAvailableZoneIds()
		.stream()
		.map(zoneId -> {
		    final ZoneOffset offset = now.atZone(ZoneId.of(zoneId))
			    .getOffset();

		    return zoneId + " " + offset.toString();
		}).sorted().collect(toList());
    }

    /**
     * Q9: Again using stream operations, find all time zones whose offsets
     * aren't full hours.
     * 
     * @return List of all offsets that aren't full hours prefixed by zone ids.
     */
    public static List<String> getAllOffsetsWithFractionalHours() {
	final LocalDateTime now = LocalDateTime.now();

	return getAvailableZoneIds()
		.stream()
		.filter(zoneId -> {
		    final ZoneOffset offset = now.atZone(ZoneId.of(zoneId))
			    .getOffset();

		    return offset.getTotalSeconds() % 3600 != 0;
		})
		.map(zoneId -> {
		    final ZoneOffset offset = now.atZone(ZoneId.of(zoneId))
			    .getOffset();

		    return zoneId + " " + offset.toString();
		}).sorted().collect(toList());
    }
}

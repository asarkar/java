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
package name.abhijitsarkar.java.java8impatient.miscellaneous;

import static java.lang.Math.abs;
import static java.lang.Math.floorMod;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.lines;
import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.nullsFirst;
import static java.util.stream.Collectors.toList;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PracticeQuestionsCh8 {
    public static final Logger LOGGER = LoggerFactory
	    .getLogger(PracticeQuestionsCh8.class);

    public static final IntBinaryOperator REM_OPERATOR = (a, b) -> {
	final int result = a % b;

	LOGGER.info("{} % {} = {}.", a, b, result);

	return result;
    };
    public static final IntBinaryOperator FLOOR_MOD_OPERATOR = (a, b) -> {
	final int result = floorMod(a, b);

	LOGGER.info("floorMod({}, {}) = {}.", a, b, result);

	return result;
    };
    public static final IntBinaryOperator REM_FUNCTION_OPERATOR = (a, b) -> {
	final int result = abs(a % b);

	LOGGER.info("{} % {} = {}.", a, b, result);

	return result;
    };

    /**
     * Q3: Euclid's algorithm (which is over two thousand years old) computes
     * the greatest common divisor of two numbers as gcd(a,b) = a if b is zero,
     * and gcd(b, rem(a,b)) otherwise, where rem is the remainder. Clearly, the
     * gcd should not be negative, even if a or b are (since its negation would
     * then be a greater divisor). Implement the algorithm with {@code %},
     * {@code floorMod}, and a {@code rem} function that produces the
     * mathematical (non-negative) remainder. Which of the three gives you the
     * least trouble hassle with negative values?
     * <p>
     * <b>Ans:</b> Using {@code rem} gives the least trouble with negative
     * values.
     * 
     * @param a
     *            Dividend.
     * @param b
     *            Divisor.
     * @param remainderOperator
     *            The rem operator.
     * @return GCD.
     */
    public static int gcd(final int a, final int b,
	    final IntBinaryOperator remainderOperator) {
	return b == 0 ? a : gcd(b, remainderOperator.applyAsInt(a, b),
		remainderOperator);
    }

    /**
     * Q5: At the beginning of chapter 2, we counted long words in a list as
     * {@code words.stream().filter(w -> w.length() > 12).count()}. Do the same
     * with a lambda expression, but without using streams. Which operation is
     * faster for a long list?
     * <p>
     * <b>Ans:</b> The method without using streams is faster.
     * 
     * @param words
     *            List of words.
     * @param longWordMinLength
     *            Minimum length below which a word is removed from the list.
     * @return Count of all words that exceed the minimum length.
     */
    public static int countLongWordsWithoutUsingStreams(
	    final List<String> words, final int longWordMinLength) {
	final Predicate<String> p = s -> s.length() >= longWordMinLength;

	int count = 0;

	for (String aWord : words) {
	    if (p.test(aWord)) {
		++count;
	    }
	}

	return count;
    }

    public static int countLongWordsUsingStreams(final List<String> words,
	    final int longWordMinLength) {
	return (int) words.stream()
		.filter(w -> w.length() >= longWordMinLength).count();
    }

    /**
     * Q6: Using only methods of the {@code Comparator} class, define a
     * comparator for {@code Point2D} which is a total ordering (that is, the
     * comparator only returns zero for equal objects). Hint: First compute the
     * x-coordinates, then the y-coordinates. Do the same for
     * {@code Rectangle2D}.
     * 
     * @param p1
     *            First {@code Point2D}.
     * @param p2
     *            Second {@code Point2D}.
     * @return Positive integer if p1 > p2, negative integer if p1 < p2 and zero
     *         if they're equal.
     */
    public static int comparePoint2D(final Point2D p1, final Point2D p2) {
	return comparingDouble(Point2D::getX)
		.thenComparingDouble(Point2D::getY).compare(p1, p2);
    }

    /**
     * Q7: Express {@code nullsFirst(naturalOrder()).reversed()} without calling
     * {@code reversed}.
     * 
     * @return Comparator that compares by natural order reversed.
     */
    public static <T> Comparator<T> naturalOrderReversedWithNullsFirst() {
	return nullsFirst(Collections.reverseOrder());
    }

    /**
     * Using {@code Files.lines} and {@code Pattern.asPredicate}, write a
     * program that acts like the {@code grep} utility, printing all lines that
     * contain a match for a regular expression.
     * 
     * @param path
     *            File path.
     * @param regex
     *            Regular expression.
     * @return Matching lines.
     */
    public static List<String> grep(final Path path, final String regex) {
	if (isDirectory(path)) {
	    throw new IllegalArgumentException(
		    "Sorry, cannot handle directories.");
	}

	final Pattern p = Pattern.compile(regex);

	try {
	    return lines(path).filter(p.asPredicate()).collect(toList());
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}
    }
}

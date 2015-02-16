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

import static java.util.Arrays.sort;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.PracticeQuestionsCh8.FLOOR_MOD_OPERATOR;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.PracticeQuestionsCh8.REM_FUNCTION_OPERATOR;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.PracticeQuestionsCh8.REM_OPERATOR;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.PracticeQuestionsCh8.comparePoint2D;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.PracticeQuestionsCh8.gcd;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.PracticeQuestionsCh8.grep;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.PracticeQuestionsCh8.naturalOrderReversedWithNullsFirst;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh8Test {
    public static final Logger LOGGER = LoggerFactory
	    .getLogger(PracticeQuestionsCh8Test.class);

    @Test
    public void testGcdUsingRemOperator() {
	assertEquals(1, gcd(4, 3, REM_OPERATOR));
	/* Incorrect because if -1 is the gcd, so is 1 and 1 > -1 */
	assertEquals(-1, gcd(-4, 3, REM_OPERATOR));
	assertEquals(1, gcd(4, -3, REM_OPERATOR));
	/* Incorrect because if -1 is the gcd, so is 1 and 1 > -1 */
	assertEquals(-1, gcd(-4, -3, REM_OPERATOR));
    }

    @Test
    public void testGcdUsingFloorModOperator() {
	assertEquals(1, gcd(4, 3, FLOOR_MOD_OPERATOR));
	assertEquals(1, gcd(-4, 3, FLOOR_MOD_OPERATOR));
	/* Incorrect because if -1 is the gcd, so is 1 and 1 > -1 */
	assertEquals(-1, gcd(4, -3, FLOOR_MOD_OPERATOR));
	/* Incorrect because if -1 is the gcd, so is 1 and 1 > -1 */
	assertEquals(-1, gcd(-4, -3, FLOOR_MOD_OPERATOR));
    }

    @Test
    public void testGcdUsingRemFunctionOperator() {
	assertEquals(1, gcd(4, 3, REM_FUNCTION_OPERATOR));
	assertEquals(1, gcd(-4, 3, REM_FUNCTION_OPERATOR));
	assertEquals(1, gcd(4, -3, REM_FUNCTION_OPERATOR));
	assertEquals(1, gcd(-4, -3, REM_FUNCTION_OPERATOR));
    }

    @Test
    public void testComparePoint2D() {
	assertTrue(comparePoint2D(new Point(1, 1), new Point(1, 2)) < 0);
	assertTrue(comparePoint2D(new Point(1, 2), new Point(1, 1)) > 0);
	assertEquals(0, comparePoint2D(new Point(1, 1), new Point(1, 1)));
    }

    @Test
    public void testNaturalOrderReversed() {
	String[] words = new String[] { "a", "c", "b", null };
	String[] expected = new String[] { null, "c", "b", "a" };

	sort(words, naturalOrderReversedWithNullsFirst());

	assertArrayEquals(expected, words);
    }

    @Test
    public void testGrep() throws URISyntaxException {
	URI uri = getClass().getResource("/ch8/grep-test-fixture.txt").toURI();
	List<String> matchingLines = grep(Paths.get(uri), "^\\d+$");

	assertNotNull(matchingLines);
	assertEquals(1, matchingLines.size());
	assertEquals("123", matchingLines.get(0));
    }
}

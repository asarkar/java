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

import static java.lang.Math.floorMod;

import java.util.function.IntBinaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PracticeQuestionsCh8 {
    public static final Logger LOGGER = LoggerFactory
	    .getLogger(PracticeQuestionsCh8.class);

    public static int gcd(final int a, final int b,
	    final IntBinaryOperator remainderOperator) {
	return b > 0 ? remainderOperator.applyAsInt(a, b) : a;
    }

    static final IntBinaryOperator REM_OPERATOR = (a, b) -> a % b;
    static final IntBinaryOperator FLOOR_MOD_OPERATOR = (a, b) -> floorMod(a, b);
    static final IntBinaryOperator REM_FUNCTION_OPERATOR = (a, b) -> a % b;
}

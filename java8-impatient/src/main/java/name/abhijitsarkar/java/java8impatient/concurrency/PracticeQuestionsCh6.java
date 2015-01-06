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
package name.abhijitsarkar.java.java8impatient.concurrency;

import static java.util.function.BinaryOperator.maxBy;

import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PracticeQuestionsCh6 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh6.class);

	/**
	 * Q1: Write a program that keeps track of the longest string that is observed by a number of threads. Use an
	 * {@code AtomicReference} and an appropriate accumulator.
	 * 
	 * @param observed
	 *            Longest string.
	 * @param x
	 *            String value to be compared to the longest value.
	 * @return Longest string.
	 */
	public static String updateLongestString(final AtomicReference<String> observed, final String x) {
		LOGGER.info("Received observed: {}, x: {}", observed.get(), x);

		final String longestString = observed.accumulateAndGet(x,
				maxBy((currentValue, givenUpdate) -> currentValue.length() - givenUpdate.length()));

		LOGGER.info("New observed: {}.", observed.get());

		return longestString;
	}
}

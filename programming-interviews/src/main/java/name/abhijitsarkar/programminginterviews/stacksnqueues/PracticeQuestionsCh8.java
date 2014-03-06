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
package name.abhijitsarkar.programminginterviews.stacksnqueues;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.abhijitsarkar.algorithms.core.datastructure.Stack;

public class PracticeQuestionsCh8 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh8.class);

	/*
	 * Q8.2: Write a function that takes an arithmetical expression in RPN and returns the number that the expression
	 * evaluates to.
	 */
	public static int evalRpn(String expr) {
		final String basicRpnPattern = "-?\\d+";

		if (expr != null && expr.matches(basicRpnPattern)) {
			return Integer.valueOf(expr);
		}

		Stack<String> rpnStack = new Stack<String>();

		String[] tokens = expr.split(",");

		int value = 0;
		for (String token : tokens) {
			if (token.matches(basicRpnPattern)) {
				rpnStack.push(token);
			} else {
				/* 2nd operand pushed later, pop it 1st. */
				int operand2 = Integer.valueOf(rpnStack.pop());
				int operand1 = Integer.valueOf(rpnStack.pop());

				value = eval(operand1, operand2, token);

				rpnStack.push(String.valueOf(value));
			}
		}

		value = Integer.valueOf(rpnStack.pop());

		LOGGER.info("Expression \"{}\" evaluated to {}.", expr, value);

		return value;
	}

	private static int eval(int operand1, int operand2, String operator) {
		int result = 0;

		switch (operator) {
		case "+":
			result = operand1 + operand2;
			break;
		case "-":
			result = operand1 - operand2;
			break;
		case "x":
			result = operand1 * operand2;
			break;
		case "/":
			result = operand1 / operand2;
			break;
		default:
			throw new IllegalArgumentException("Illegal operator " + operator);
		}

		return result;
	}
}

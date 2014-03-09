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
package name.abhijitsarkar.programminginterviews.arraysnstrings;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh6 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh6.class);

	/*
	 * Q6.22: Given a cell phone keypad (specified by a mapping M that takes individual digits and returns the
	 * corresponding set of characters) and a number sequence, return all possible character sequences (not just legal
	 * words) that correspond to the number sequence.
	 */
	public static List<String> keypadPermutation(int num) {
		final List<String> perms = new ArrayList<>();

		final String numSeq = String.valueOf(num);

		return permute(numSeq, 0, perms);
	}

	private static List<String> permute(String numSeq, int idx, List<String> perms) {
		if (idx == numSeq.length()) {
			return perms;
		}

		final String charSeq = charSeq(numSeq.charAt(idx));

		final List<String> buffer = new ArrayList<>();

		if (perms.isEmpty()) {
			fillBufferWithPerms("", charSeq, buffer);
		} else {
			for (final String s : perms) {
				fillBufferWithPerms(s, charSeq, buffer);
			}
		}

		return permute(numSeq, ++idx, buffer);
	}

	private static String charSeq(char digit) {
		String charSeq = "";
		final int idxOfZeroInASCII = 48;
		int intVal = digit - idxOfZeroInASCII;

		switch (intVal) {
		case 2:
			charSeq = "ABC";
			break;
		case 3:
			charSeq = "DEF";
			break;
		case 4:
			charSeq = "GHI";
			break;
		case 5:
			charSeq = "JKL";
			break;
		case 6:
			charSeq = "MNO";
			break;
		case 7:
			charSeq = "PQRS";
			break;
		case 8:
			charSeq = "TUV";
			break;
		case 9:
			charSeq = "WXWZ";
			break;
		default:
			break;
		}

		return charSeq;
	}

	private static void fillBufferWithPerms(String s, String charSeq, List<String> buffer) {
		for (int i = 0; i < charSeq.length(); ++i) {
			buffer.add(s + charSeq.charAt(i));
		}
	}
}

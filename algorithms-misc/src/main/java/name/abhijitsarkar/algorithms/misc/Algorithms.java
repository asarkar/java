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
package name.abhijitsarkar.algorithms.misc;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Algorithms {
	public static final Logger LOGGER = LoggerFactory.getLogger(Algorithms.class);

	public static String longestRepeatedSubstring(String str) {
		final String[] suffixArray = buildSuffixArray(str);

		Arrays.sort(suffixArray);

		String temp = "";
		String lrs = "";
		String aSuffix = null;
		String anotherSuffix = null;
		int len = -1;

		for (int i = 0; i < suffixArray.length - 1; ++i) {
			aSuffix = suffixArray[i];
			anotherSuffix = suffixArray[i + 1];

			if (aSuffix.trim().equals("") || anotherSuffix.trim().equals("")) {
				continue;
			} else if (anotherSuffix.startsWith(aSuffix)) {
				LOGGER.debug("\"{}\" starts with \"{}\".", anotherSuffix, aSuffix);

				temp = aSuffix;
			} else {
				len = Math.min(aSuffix.length(), anotherSuffix.length());

				int j = 1;
				while (j <= len && aSuffix.regionMatches(0, anotherSuffix, 0, j)) {
					LOGGER.debug("Comparing \"{}\" and \"{}\".", aSuffix.substring(0, j), anotherSuffix.substring(0, j));

					++j;
				}

				temp = aSuffix.substring(0, --j);
			}

			lrs = temp.length() > lrs.length() ? temp : lrs;
		}

		return lrs;
	}

	/* http://en.wikipedia.org/wiki/Suffix_array */
	private static String[] buildSuffixArray(String str) {
		final String[] suffixArray = new String[str.length()];

		for (int i = 0; i < suffixArray.length; ++i) {
			suffixArray[i] = str.substring(i);
		}

		return suffixArray;
	}
}

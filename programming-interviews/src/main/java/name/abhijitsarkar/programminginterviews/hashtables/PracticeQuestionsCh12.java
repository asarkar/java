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
package name.abhijitsarkar.programminginterviews.hashtables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */

public class PracticeQuestionsCh12 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh12.class);

	/*
	 * Q12.7: Write a function that takes as input a dictionary of English words, and returns a partition of the
	 * dictionary into subsets of words that are all anagrams of each other.
	 */
	@SuppressWarnings("unchecked")
	public static List<List<String>> anagrams(final List<String> dictionary) {
		final MultiMap<Integer, Integer> anagramMap = new MultiValueMap<Integer, Integer>();

		char[] arr = null;
		int hashCode = -1;

		for (int i = 0; i < dictionary.size(); ++i) {
			arr = dictionary.get(i).toCharArray();
			Arrays.sort(arr);

			hashCode = String.valueOf(arr).hashCode();

			anagramMap.put(hashCode, i);
		}

		final List<List<String>> anagrams = new ArrayList<>();
		final Set<Entry<Integer, Object>> anagramIndices = anagramMap.entrySet();
		List<Integer> aSet = null;

		for (final Object o : anagramIndices) {
			aSet = (List<Integer>) ((Entry<Integer, Object>) o).getValue();

			if (aSet.size() > 1) {
				final List<String> an = new ArrayList<>();

				for (final int i : aSet) {
					an.add(dictionary.get(i));
				}
				anagrams.add(an);
			}
		}

		return anagrams;
	}
}

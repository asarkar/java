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
package name.abhijitsarkar.programminginterviews.sorting;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */

public class PracticeQuestionsCh13 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh13.class);

	public static Integer[] intersection(final int[] a, final int[] b) {
		Integer[] intersection = null;

		/*
		 * The time complexity is O(a.length * log(b.length)). Searching the larger array by is faster than searching
		 * the smaller array due to the log function.
		 */
		if (a.length < b.length) {
			intersection = findIntersection(a, b);
		} else {
			intersection = findIntersection(b, a);
		}

		return intersection;
	}

	private static Integer[] findIntersection(final int[] smaller, final int[] larger) {
		final int len1 = smaller.length - 1;
		final int len2 = larger.length - 1;

		int i = 0;
		int j = -1;

		for (; i <= len1; ++i) {
			int left = 0;
			int right = len2;
			int mid = left + ((right - left) >>> 1);
			
			/* The while loop can be replaced with Arrays.binarySearch() in reality. */
			for (; left <= right; mid = left + ((right - left) >>> 1)) {
				if (smaller[i] == larger[mid]) {
					break;
				} else if (larger[mid] > smaller[i]) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			}

			if (left <= right) {
				final List<Integer> intersection = new ArrayList<>();

				for (j = i; smaller[i] == larger[j]; ++i, ++j) {
					intersection.add(smaller[i]);
				}

				return intersection.toArray(new Integer[] {});
			}
		}

		return null;
	}
}

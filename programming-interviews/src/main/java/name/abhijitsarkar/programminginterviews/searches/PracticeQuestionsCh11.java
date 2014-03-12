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
package name.abhijitsarkar.programminginterviews.searches;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */

public class PracticeQuestionsCh11 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh11.class);

	/*
	 * Q11.2: Design an efficient algorithm that takes a sorted array A and a key k, and find the index of the first
	 * occurrence an element larger than k; return -1 if every element is less than or equal to k.
	 */
	public static int firstOccurrence(final int[] arr, final int key) {
		int index = -1;
		int left = 0;
		int right = arr.length - 1;

		if (right < 0 || arr[right] <= key) {
			return index;
		}

		int mid = left + ((right - left) >>> 1);

		for (; left <= right; mid = left + ((right - left) >>> 1)) {
			if (arr[mid] > key) {
				index = mid;
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}

		return index;
	}
}

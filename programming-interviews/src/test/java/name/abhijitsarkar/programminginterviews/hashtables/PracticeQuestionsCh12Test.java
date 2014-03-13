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

import static name.abhijitsarkar.programminginterviews.hashtables.PracticeQuestionsCh12.anagrams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsCh12Test {

	@Test
	public void testAnagrams() {
		List<String> dictionary = new ArrayList<>();

		dictionary.add("urn");
		dictionary.add("won");
		dictionary.add("bear");
		dictionary.add("bare");
		dictionary.add("run");
		dictionary.add("cafe");
		dictionary.add("now");

		List<List<String>> anagrams = anagrams(dictionary);

		Assert.assertEquals(3, anagrams.size());
		Assert.assertTrue(anagrams.contains(Arrays.asList(new String[] { "urn", "run" })));
		Assert.assertTrue(anagrams.contains(Arrays.asList(new String[] { "won", "now" })));
		Assert.assertTrue(anagrams.contains(Arrays.asList(new String[] { "bear", "bare" })));
	}
}

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

import static name.abhijitsarkar.java.java8impatient.concurrency.PracticeQuestionsCh6.updateLongestString;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh6Test {
	public static final Logger LOGGER = LoggerFactory
			.getLogger(PracticeQuestionsCh6.class);

	@Test
	public void testUpdateLongestString() {
		final String[] words = new String[] { "Java 8", "Java 8 is Awesome!",
				"Java 8 is the Best thing Since Sliced Bread!",
				"Java 8 Changes Everything!" };
		final int len = words.length;
		final int stopAfter = 100;

		final AtomicReference<String> longestString = new AtomicReference<>(
				words[0]);
		final AtomicInteger count = new AtomicInteger(0);
		final Random rand = new Random();

		class UpdateLongestStringTask extends RecursiveAction {
			private static final long serialVersionUID = -2288401002001447054L;

			@Override
			protected void compute() {
				final int c = count.getAndIncrement();

				LOGGER.info("Execution #: {}.", c);

				if (c >= stopAfter) {
					return;
				}

				final ForkJoinTask<Void> task = new UpdateLongestStringTask()
						.fork();

				updateLongestString(longestString, words[randomIndex()]);

				task.join();
			}

			private int randomIndex() {
				return rand.nextInt(len);
			}
		}

		new ForkJoinPool().invoke(new UpdateLongestStringTask());
	}
}

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
package name.abhijitsarkar.java.java8impatient.lambda;

import static java.lang.Character.isWhitespace;
import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh3 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh3.class);

	/**
	 *
	 * Q1: Enhance the lazy logging technique by providing conditional logging. A typical call would be
	 * {@code logIf(Level.FINEST, () -> i == 10, () -> "a[10] = " + a[10]). Don't evaluate the condition if the logger
	 * won't log the message.}
	 * 
	 * @param level
	 *            The level to log at.
	 * @param condition
	 *            The condition to evaluate before logging.
	 * @param message
	 *            The log message.
	 * 
	 * @return true if the message is logged, false otherwise.
	 */
	public static boolean logIf(final String level, Supplier<Boolean> condition, Supplier<String> message) {
		return isLoggingEnabledFor(level) && condition.get() && logAtLevel(level, message.get());
	}

	static boolean isLoggingEnabledFor(final String level) {
		Class<? extends Logger> loggerClass = LOGGER.getClass();
		boolean isEnabled = false;

		try {
			final String methodName = "is" + capitalize(level) + "Enabled";

			final Method isEnabledMethod = loggerClass.getDeclaredMethod(methodName);

			isEnabled = (boolean) isEnabledMethod.invoke(LOGGER);

			LOGGER.debug("Logging is {} for level {}.", isEnabled ? "enabled" : "disabled", level);
		} catch (Exception e) {
			LOGGER.error("An error occurred trying to determine if logging is enabled for level {}.", level);
		}

		return isEnabled;
	}

	static String capitalize(String level) {
		return Character.toUpperCase(level.charAt(0)) + level.substring(1);
	}

	static boolean logAtLevel(final String level, final String message) {
		Class<? extends Logger> loggerClass = LOGGER.getClass();
		boolean isLoggingSuccessful = false;

		try {
			final Method log = loggerClass.getDeclaredMethod(level, String.class);

			log.invoke(LOGGER, message);

			isLoggingSuccessful = true;
		} catch (Exception e) {
			LOGGER.error("An error occurred trying to log at level {}.", level);
		}

		return isLoggingSuccessful;
	}

	/**
	 * Q2: When you use a {@code ReentrantLock}, you are required to lock and unlock with the idiom
	 * 
	 * <pre>
	 * {@code
	 * myLock.lock();
	 * try {
	 *     some action
	 * } finally {
	 *     myLock.unlock();
	 * }
	 * </pre>
	 * 
	 * Provide a method withLock so that one can call {@code withLock(myLock,
	 * () -> some action })
	 * 
	 * @param lock
	 *            ReentrantLock.
	 * @param r
	 *            Action.
	 */
	public static void withLock(final ReentrantLock lock, final Runnable r) {
		lock.lock();

		try {
			r.run();
		} finally {
			lock.unlock();
		}
	}

	public enum ComparatorCustomizationOption {
		REVERSED, CASE_INSENSITIVE, SPACE_INSENSITIVE;
	}

	private static class SpaceInsensitiveComparator implements Comparator<String>, Serializable {
		private static final long serialVersionUID = 43977305580825595L;

		public int compare(final String s1, final String s2) {
			final int l1 = s1.length();
			final int l2 = s2.length();

			for (int i = 0, j = 0; i < l1 && j < l2;) {
				final char c1 = s1.charAt(i);
				final char c2 = s2.charAt(j);

				if (isWhitespace(c1)) {
					i++;

					continue;
				} else if (isWhitespace(c2)) {
					j++;

					continue;
				}

				if (c1 != c2) {
					return c1 - c2;
				}
			}

			return 0;
		}
	}

	/**
	 * Write a method that generates a {@code Comparator<String>} that can be normal or reversed, case-sensitive or
	 * case-insensitive, space-sensitive or space-insensitive, or any combination thereof. Your method should return a
	 * lambda expression.
	 * 
	 * @param customizationOptions
	 *            Comparator customization options.
	 * @return Comparator.
	 */
	public static Comparator<String> comparatorGenerator(final ComparatorCustomizationOption... customizationOptions) {

		final Map<ComparatorCustomizationOption, Comparator<String>> map = getComparatorCustomizationOptionsMap();

		return of(customizationOptions).map(map::get).reduce((c1, c2) -> c1.thenComparing(c2)).orElse(naturalOrder());
	}

	static Map<ComparatorCustomizationOption, Comparator<String>> getComparatorCustomizationOptionsMap() {
		final Map<ComparatorCustomizationOption, Comparator<String>> map = new HashMap<>();

		map.put(ComparatorCustomizationOption.REVERSED, reverseOrder());
		map.put(ComparatorCustomizationOption.CASE_INSENSITIVE, CASE_INSENSITIVE_ORDER);
		map.put(ComparatorCustomizationOption.SPACE_INSENSITIVE, new SpaceInsensitiveComparator());

		return map;
	}

	/**
	 * Q17: Implement a {@code doInParallelAsync(Runnable first, Runnable second, Consumer<Throwable> handler)} method
	 * that executes {@code first} and {@code second} in parallel, calling the handler if either method throws an
	 * exception.
	 * 
	 * @param first
	 *            First action.
	 * @param second
	 *            Second action.
	 * @param handler
	 *            Exception handler.
	 */
	public static void doInParallelAsync(final Runnable first, final Runnable second, final Consumer<Throwable> handler) {
		try {
			of(first, second).parallel().forEach(Runnable::run);
		} catch (Throwable t) {
			handler.accept(t);
		}
	}

	/**
	 * Q19: Look at the {@code Stream<T>} method
	 * {@code <U> U reduce (U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner)} . Should
	 * {@code U} be declared as {@code ? super U} in the first type argument to {@code BiFunction}? Why or why not?
	 * <p>
	 * <b>Ans:</b> Since {@code U} is the argument and the return type of {@code reduce}, the type does not vary. In
	 * effect, the contravariance and covariance cancel each other out.
	 */
	public void existsOnlyToGlorifyJavadoc() {
	}

	/**
	 * Supply a static method {@code <T, U> List<U> map(List<T>, Function<T, U>)}.
	 * 
	 * @param l
	 *            input list.
	 * @param f
	 *            Function.
	 * @return Output list.
	 */
	public static <T, U> List<U> map(List<T> l, Function<T, U> f) {
		return l.stream().map(f).collect(toList());
	}

	/**
	 * Q23: Define a {@code map} operation for a class {@code Pair<T>} that represents a pair of objects of type
	 * {@code T}.
	 * <p>
	 * Q24: Can you define a {@code flatMap} method for the {@code Pair<T>}? If so, what is it? If not, why not?
	 * <p>
	 * <b>Ans:</b> A {@code flatMap} operation replaces each element with a set of some other element, and then
	 * "flattens" the result to a single set of other elements. Since pair can only have 2 item, flattening is not
	 * applicable as it may result in more than 2 items. Consider a pair of lists of integers; flatMap may convert each
	 * item to a list of strings but if we flatten those, we're going to end up with more than 2 items.
	 * 
	 */
	static class Pair<T> {
		private T item1;
		private T item2;

		public Pair(T item1, T item2) {
			this.item1 = item1;
			this.item2 = item2;
		}

		public <U> Pair<U> map(final Function<? super T, ? extends U> mapper) {
			return new Pair<U>(mapper.apply(item1), mapper.apply(item2));
		}

		public Stream<T> stream() {
			return of(item1, item2);
		}
	}
}

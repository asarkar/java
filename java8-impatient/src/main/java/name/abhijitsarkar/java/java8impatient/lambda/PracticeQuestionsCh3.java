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

import static java.lang.Character.toUpperCase;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh3 {
    public static final Logger LOGGER = LoggerFactory
	    .getLogger(PracticeQuestionsCh3.class);

    /**
     *
     * Q1: Enhance the lazy logging technique by providing conditional logging.
     * A typical call would be
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
    public static boolean logIf(final String level,
	    Supplier<Boolean> condition, Supplier<String> message) {
	return isLoggingEnabledFor(level) && condition.get()
		&& logAtLevel(level, message.get());
    }

    static boolean isLoggingEnabledFor(final String level) {
	Class<? extends Logger> loggerClass = LOGGER.getClass();
	boolean isEnabled = false;

	try {
	    final String methodName = "is" + capitalize(level) + "Enabled";

	    final Method isEnabledMethod = loggerClass
		    .getDeclaredMethod(methodName);

	    isEnabled = (boolean) isEnabledMethod.invoke(LOGGER);

	    LOGGER.debug("Logging is {} for level {}.", isEnabled ? "enabled"
		    : "disabled", level);
	} catch (Exception e) {
	    LOGGER.error(
		    "An error occurred trying to determine if logging is enabled for level {}.",
		    level);
	}

	return isEnabled;
    }

    static String capitalize(String level) {
	return toUpperCase(level.charAt(0)) + level.substring(1);
    }

    static boolean logAtLevel(final String level, final String message) {
	Class<? extends Logger> loggerClass = LOGGER.getClass();
	boolean isLoggingSuccessful = false;

	try {
	    final Method log = loggerClass.getDeclaredMethod(level,
		    String.class);

	    log.invoke(LOGGER, message);

	    isLoggingSuccessful = true;
	} catch (Exception e) {
	    LOGGER.error("An error occurred trying to log at level {}.", level);
	}

	return isLoggingSuccessful;
    }

    /**
     * Q2: When you use a {@code ReentrantLock}, you are required to lock and
     * unlock with the idiom
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

    /**
     * Q17: Implement a
     * {@code doInParallelAsync(Runnable first, Runnable second, Consumer<Throwable> handler)}
     * method that executes {@code first} and {@code second} in parallel,
     * calling the handler if either method throws an exception.
     * 
     * @param first
     *            First action.
     * @param second
     *            Second action.
     * @param handler
     *            Exception handler.
     */
    public static void doInParallelAsync(final Runnable first,
	    final Runnable second, final Consumer<Throwable> handler) {
	try {
	    of(first, second).parallel().forEach(Runnable::run);
	} catch (Throwable t) {
	    handler.accept(t);
	}
    }

    /**
     * Q19: Look at the {@code Stream<T>} method
     * {@code <U> U reduce (U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner)}
     * . Should {@code U} be declared as {@code ? super U} in the first type
     * argument to {@code BiFunction}? Why or why not?
     * <p>
     * <b>Ans:</b> Since {@code U} is the argument and the return type of
     * {@code reduce}, the type does not vary. In effect, the contravariance and
     * covariance cancel each other out.
     */
    public void existsOnlyToGlorifyJavadoc() {
    }

    /**
     * Supply a static method
     * {@code <T, U> List<U> map(List<T>, Function<T, U>)}.
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
}

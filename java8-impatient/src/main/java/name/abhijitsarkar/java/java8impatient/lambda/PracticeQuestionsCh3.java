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

import java.lang.reflect.Method;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
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
	return Character.toUpperCase(level.charAt(0)) + level.substring(1);
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
    
    public static void withLock(final Consumer<ReentrantLock> lock, final IntSupplier action) {
    }
}

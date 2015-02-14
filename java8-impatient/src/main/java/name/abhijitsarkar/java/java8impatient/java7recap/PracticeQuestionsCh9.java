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
package name.abhijitsarkar.java.java8impatient.java7recap;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abhijit Sarkar
 */
public class PracticeQuestionsCh9 {
    public static final Logger LOGGER = LoggerFactory
	    .getLogger(PracticeQuestionsCh9.class);

    /**
     * Q1: Implement a code segment that constructs a {@code Scanner} and a
     * {@code PrintWriter}, without the try-with-resources statement. Bu sure to
     * close both objects, provided they have been properly constructed. You
     * need to consider the following conditions:
     * <p>
     * <ul>
     * <li>The {@code Scanner} constructor throws an exception.</li>
     * <li>The {@code PrintWriter} constructor throws an exception.</li>
     * <li>{@code hasNext}, {@code next}, or {@code println} throws an
     * exception.</li>
     * <li>{@code in.close()} throws an exception.</li>
     * <li>{@code out.close} throws an exception.</li>
     * </ul>
     * 
     * @param is
     *            {@code InputStream} on which the {@code Scanner} is
     *            constructed.
     * @param os
     *            {@code OutputStream} on which the {@code PrintWriter} is
     *            constructed.
     */
    public static void readAndWrite(final InputStream is, final OutputStream os) {
	Scanner in = null;
	PrintWriter out = null;

	try {
	    in = new Scanner(is);
	    out = new PrintWriter(os);

	    while (in.hasNext()) {
		out.println(in.next().toLowerCase());
	    }
	} finally {
	    try {
		if (!attemptToClose(in) || in.ioException() != null) {
		    /*
		     * Scanner could have an IOException from any of the method
		     * calls made on it or from a failure to close it. We don't
		     * care so in either case, we try to close the underlying
		     * InputStream. If it's already been closed as part of
		     * successfully closing the Scanner, this attempt has no
		     * effect.
		     */
		    attemptToClose(is);
		}
	    }
	    /* There may be an exception from closing the InputStream */
	    catch (final IOException ioe) {
		/* Log and suck it up; we got work to do. */
		LOGGER.error("There was a problem with closing the InputStream. Moving on.");
	    }

	    try {
		if (!attemptToClose(out) || out.checkError()) {
		    /*
		     * PrintWriter could have an IOException from any of the
		     * method calls made on it or from a failure to close it. We
		     * don't care so in either case, we try to close the
		     * underlying OutputStream. If it's already been closed as
		     * part of successfully closing the PrintWriter, this
		     * attempt has no effect.
		     */
		    attemptToClose(os);
		}
	    }
	    /* There may be an exception from closing the OutputStream */
	    catch (final IOException ioe) {
		/* Log and suck it up. */
		LOGGER.error("There was a problem with closing the OutputStream.");
	    }
	}
    }

    private static boolean attemptToClose(final Closeable resource)
	    throws IOException {
	if (resource != null) {
	    resource.close();

	    LOGGER.debug("Closed resource of type: {}.", resource.getClass()
		    .getName());

	    return true;
	}

	LOGGER.warn("Cannot close null resource.");

	return false;
    }

    /**
     * Q2: Improve on the preceding exercise by adding any exceptions thrown by
     * {@code in.close()} or {@code out.close()} as suppressed exceptions to the
     * original exception, if there was one.
     * 
     * @param is
     *            {@code InputStream} on which the {@code Scanner} is
     *            constructed.
     * @param os
     *            {@code OutputStream} on which the {@code PrintWriter} is
     *            constructed.
     * @throws Exception
     *             If there is any problem.
     */
    public static void improvedReadAndWrite(final InputStream is,
	    final OutputStream os) throws Exception {
	Exception ex = null;
	Scanner in = null;
	PrintWriter out = null;

	try {
	    in = new Scanner(is);
	    out = new PrintWriter(os);

	    while (in.hasNext()) {
		out.println(in.next().toLowerCase());
	    }
	} catch (final Exception e) {
	    ex = e;
	} finally {
	    try {
		if (!attemptToClose(in) || in.ioException() != null) {
		    attemptToClose(is);
		}
	    }
	    /*
	     * There may be an exception from closing the InputStream , try to
	     * close the OutputStream.
	     */
	    catch (final IOException ioe1) {
		try {
		    if (!attemptToClose(out) || out.checkError()) {
			attemptToClose(os);
		    }
		} catch (final IOException ioe2) {
		    /*
		     * There was a problem with closing the OutputStream, add
		     * the suppressed exception to the one thrown by InputStream
		     * close.
		     */
		    ioe1.addSuppressed(ioe2);
		}

		ex = addSuppressedOrAssignTo(ex, ioe1);
	    }

	    /*
	     * Try closing the OutputStream; if it's already closed, this
	     * attempt has no effect.
	     */
	    try {
		if (!attemptToClose(out)) {
		    attemptToClose(os);
		}
	    } catch (final IOException ioe) {
		ex = addSuppressedOrAssignTo(ex, ioe);
	    }

	    if (ex != null) {
		throw ex;
	    }
	}
    }

    private static Exception addSuppressedOrAssignTo(final Exception ex,
	    final IOException ioe) {
	Exception e = null;

	if (ex != null) {
	    e = ex;
	    e.addSuppressed(ioe);
	} else {
	    e = ioe;
	}

	return e;
    }
}

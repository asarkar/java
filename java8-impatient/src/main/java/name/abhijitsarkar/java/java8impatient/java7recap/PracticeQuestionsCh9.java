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

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.copy;
import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.write;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsFirst;
import static java.util.Objects.requireNonNull;
import static name.abhijitsarkar.java.java8impatient.webutil.URLContentReader.getContentAsStream;
import static name.abhijitsarkar.java.java8impatient.webutil.URLContentReader.openConnection;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
	    in = new Scanner(is, UTF_8.name());
	    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(os,
		    UTF_8)));

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
	    in = new Scanner(is, UTF_8.name());
	    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(os,
		    UTF_8)));

	    while (in.hasNext()) {
		out.println(in.next().toLowerCase());
	    }
	} catch (final Exception e) {
	    ex = e;
	} finally {
	    try {
		if (!attemptToClose(in) || in.ioException() != null) {
		    if (in.ioException() != null) {
			ex = addSuppressedOrAssignTo(ex, in.ioException());
		    }

		    attemptToClose(is);
		}
	    }
	    /*
	     * There may be an exception from closing the InputStream , try to
	     * close the OutputStream.
	     */
	    catch (final IOException ioe1) {
		ex = addSuppressedOrAssignTo(ex, ioe1);

		try {
		    if (!attemptToClose(out) || out.checkError()) {
			attemptToClose(os);
		    }
		} catch (final IOException ioe2) {
		    /*
		     * There was a problem with closing the OutputStream.
		     */
		    ex = addSuppressedOrAssignTo(ex, ioe2);
		}
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

    /**
     * Q3: When you rethrow an exception that you caught in a multi-{@code catch}
     * clause, how do you declare its type in the {@code throws} declaration of
     * the ambient method?
     * <p>
     * <b>Ans:</b> Declare the common superclass, which is {@code Exception}, if
     * not anything else.
     * 
     * @throws Exception
     */
    public void process() throws Exception {
	try {
	    doSomeFileOperation();
	    doSomeNetworkOperation();
	} catch (URISyntaxException | FileNotFoundException e) {
	    throw e;
	}
    }

    private void doSomeNetworkOperation() throws URISyntaxException {
	throw new URISyntaxException(null, null);

    }

    private void doSomeFileOperation() throws FileNotFoundException {
	throw new FileNotFoundException();
    }

    /**
     * Q5: Write a program that reads all characters of a file and writes them
     * out in reverse order. Use {@code Files.readAllBytes} and
     * {@code Files.write}.
     * 
     * @param readFrom
     *            Path to read from.
     * @param writeTo
     *            Path to write to.
     */
    public static void writeBytesReversed(final Path readFrom,
	    final Path writeTo) {
	try {
	    write(writeTo, readAllBytesReversed(readFrom), CREATE, WRITE,
		    APPEND);
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}
    }

    static byte[] readAllBytesReversed(final Path readFrom) throws IOException {
	final byte[] arr = readAllBytes(readFrom);

	final int len = arr.length;
	final int mid = len >> 1;

	for (int i = 0; i < mid; i++) {
	    swap(arr, i, len - 1 - i);
	}

	return arr;
    }

    private static void swap(final byte[] arr, final int i, final int j) {
	final int len = arr.length;

	byte temp = arr[i];
	arr[i] = arr[len - i - 1];
	arr[len - i - 1] = temp;
    }

    /**
     * Q6: Write a program that reads all lines of a file and writes them out in
     * reverse order. Use {@code Files.readAllLines} and {@code Files.write}.
     * 
     * @param readFrom
     *            Path to read from.
     * @param writeTo
     *            Path to write to.
     */
    public static void writeLinesReversed(final Path readFrom,
	    final Path writeTo) {

	try {
	    /*
	     * Instead of getting a list and reversing it, we implement a
	     * reverse Iterable. It's more code but likely to perform better for
	     * long lists.
	     */
	    write(writeTo, new UnmodifiableLinesReversed(readFrom), UTF_8,
		    CREATE, WRITE, APPEND);
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}
    }

    static class UnmodifiableLinesReversed implements Iterable<String> {
	private final List<String> lines;

	UnmodifiableLinesReversed(final Path readFrom) {
	    try {
		lines = unmodifiableList(readAllLines(readFrom, UTF_8));
	    } catch (IOException e) {
		throw new UncheckedIOException(e);
	    }
	}

	@Override
	public Iterator<String> iterator() {
	    return new UnmodifiableReverseListIterator(lines);
	}
    }

    static class UnmodifiableReverseListIterator implements
	    ListIterator<String> {
	private final List<String> lines;
	private int index = 0;

	UnmodifiableReverseListIterator(final List<String> lines) {
	    this.lines = lines;
	    index = lines.size();
	}

	@Override
	public boolean hasNext() {
	    return hasPrevious();
	}

	@Override
	public String next() {
	    return previous();
	}

	@Override
	public boolean hasPrevious() {
	    return previousIndex() >= 0;
	}

	@Override
	public String previous() {
	    return lines.get(--index);
	}

	@Override
	public int nextIndex() {
	    return previousIndex();
	}

	@Override
	public int previousIndex() {
	    return index - 1;
	}

	@Override
	public void remove() {
	    throw new UnsupportedOperationException(
		    "This operation is not supported.");
	}

	@Override
	public void set(String e) {
	    throw new UnsupportedOperationException(
		    "This operation is not supported.");
	}

	@Override
	public void add(String e) {
	    throw new UnsupportedOperationException(
		    "This operation is not supported.");
	}
    }

    /**
     * Q7: Write a program that reads the contents of a web page and saves it to
     * a file. Use {@code URL.openStream} and {@code Files.copy}.
     * 
     * @param readFrom
     *            URL to read contents from.
     * @param writeTo
     *            Path to copy the contents to.
     * @return Number of bytes copied.
     */
    public static long writeContentTo(final URL readFrom, final Path writeTo) {
	try {
	    return copy(getContentAsStream(openConnection(readFrom, false)),
		    writeTo);
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}
    }

    /**
     * Q9: Given a class
     * 
     * <pre>
     * <code>
     * public static class LabeledPoint {
     *     private String label;
     *     private int x;
     *     private int y;
     *     ...
     * }
     * </pre>
     * 
     * </code> implement the {@code equals} and {@code hashCode} methods.
     * 
     * @author Abhijit Sarkar
     *
     */
    static class LabeledPoint implements Comparable<LabeledPoint> {
	private final String label;
	private final int x;
	private final int y;

	public LabeledPoint(final String label, final int x, final int y) {
	    this.label = label;
	    this.x = x;
	    this.y = y;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;

	    result = prime * result + Objects.hashCode(label);
	    result = prime * result + x;
	    result = prime * result + y;

	    return result;
	}

	@Override
	public boolean equals(final Object obj) {
	    if (this == obj) {
		return true;
	    }

	    if (obj == null) {
		return false;
	    }

	    if (!(obj instanceof LabeledPoint)) {
		return false;
	    }

	    final LabeledPoint otherPoint = (LabeledPoint) obj;

	    return Objects.equals(this.label, otherPoint.label)
		    && this.x == otherPoint.x && this.y == otherPoint.y;
	}

	/**
	 * Q10: Implement a {@code compareTo} method for the
	 * {@code LabeledPoint} class of the preceding exercise.
	 */
	@Override
	public int compareTo(final LabeledPoint otherPoint) {
	    int result = Objects.compare(this, otherPoint,
		    nullsFirst(naturalOrder()));

	    if (result == 0 && otherPoint != null) {
		result = Integer.compare(this.x, otherPoint.x);

		if (result == 0) {
		    result = Integer.compare(this.y, otherPoint.y);
		}
	    }

	    return result;
	}
    }

    /**
     * Q11: Using the {@code ProcessBuilder} class, write a program that calls
     * {@code grep -r} to look for credit card numbers in all files in any
     * subdirectory of the user's home directory. Collect the numbers that you
     * found in a file.
     * 
     * @param p
     *            Path where to start the search.
     * @return Path where the output file is located.
     * @throws Exception
     */
    public static Path findCreditCardNumbers(final Path p) throws Exception {
	requireNonNull(p);

	if (isWindows()) {
	    throw new UnsupportedOperationException(
		    "Sorry, this method only works in Unix OS.");
	}

	final String creditCardNumberRegex = getCreditCardNumberRegex();

	final Path output = createTempFile("q11-", ".tmp");

	LOGGER.info("Looking for credit card number recursively in: {}.",
		p.toString());

	/* 'r' for recursive, 'E' for extended regex. */
	ProcessBuilder builder = new ProcessBuilder("grep", "-rE",
		creditCardNumberRegex, p.toString());

	builder.redirectOutput(output.toFile());

	builder.start().waitFor(2, TimeUnit.SECONDS);

	return output;
    }

    private static String getCreditCardNumberRegex() {
	StringBuilder regex = new StringBuilder();

	/* c.f. http://www.regular-expressions.info/creditcard.html */
	regex.append("^(?:").append("(4[0-9]{12}(?:[0-9]{3})?)|") // visa, old
								  // ones may've
								  // 13
								  // characters,
								  // new ones 16
		.append("(5[1-5][0-9]{14})|") // mastercard
		.append("(6(?:011|5[0-9]{2})[0-9]{12})|") // discover
		.append("(3[47][0-9]{13})").append(")$"); // amex

	return regex.toString();
    }

    private static boolean isWindows() {
	return System.getProperty("os.name").toLowerCase().contains("win");
    }
}

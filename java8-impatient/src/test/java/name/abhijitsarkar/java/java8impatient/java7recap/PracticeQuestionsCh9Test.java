package name.abhijitsarkar.java.java8impatient.java7recap;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static name.abhijitsarkar.java.java8impatient.java7recap.PracticeQuestionsCh9.findCreditCardNumbers;
import static name.abhijitsarkar.java.java8impatient.java7recap.PracticeQuestionsCh9.improvedReadAndWrite;
import static name.abhijitsarkar.java.java8impatient.java7recap.PracticeQuestionsCh9.readAllBytesReversed;
import static name.abhijitsarkar.java.java8impatient.java7recap.PracticeQuestionsCh9.readAndWrite;
import static name.abhijitsarkar.java.java8impatient.java7recap.PracticeQuestionsCh9.writeContentTo;
import static name.abhijitsarkar.java.java8impatient.webutil.SimpleHttpServer.PORT;
import static name.abhijitsarkar.java.java8impatient.webutil.SimpleHttpServer.newInstance;
import static name.abhijitsarkar.java.java8impatient.webutil.URLContentReader.getContent;
import static name.abhijitsarkar.java.java8impatient.webutil.URLContentReader.openConnection;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import name.abhijitsarkar.java.java8impatient.java7recap.PracticeQuestionsCh9.UnmodifiableLinesReversed;

import org.junit.Test;

import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class PracticeQuestionsCh9Test {
    @Test(expected = NullPointerException.class)
    public void testReadAndWriteWhenScannerConstructorThrowsException() {
	readAndWrite(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testReadAndWriteWhenPrintWriterConstructorThrowsException(
	    @Mocked InputStream is) {
	readAndWrite(is, null);
    }

    @Test(expected = UncheckedIOException.class)
    public void testReadAndWriteWhenHasNextThrowsException(
	    @Mocked InputStream is, @Mocked OutputStream os, @Mocked Scanner sc) {

	new Expectations() {
	    {
		sc.hasNext();
		result = new UncheckedIOException("hasNext burped.",
			new IOException("hasNext burped"));
	    }
	};
	readAndWrite(is, os);
    }

    @Test(expected = UncheckedIOException.class)
    public void testReadAndWriteWhenNextThrowsException(@Mocked InputStream is,
	    @Mocked OutputStream os, @Mocked Scanner sc) {

	new Expectations() {
	    {
		sc.hasNext();
		returns(true);
		sc.next();
		result = new UncheckedIOException("next burped.",
			new IOException("next burped"));
	    }
	};
	readAndWrite(is, os);
    }

    @Test
    public void testReadAndWriteWhenScannerCloseThrowsException(
	    @Mocked InputStream is, @Mocked OutputStream os, @Mocked Scanner sc) {
	new Expectations(PrintWriter.class) {
	    {
		sc.hasNext();
		returns(false);

		sc.ioException();
		returns(new IOException("close burped."));
	    }
	};
	readAndWrite(is, os);
    }

    @Test
    public void testImprovedReadAndWriteWhenScannerHasNextAndCloseThrowExceptions(
	    @Mocked InputStream is, @Mocked OutputStream os, @Mocked Scanner sc) {
	new Expectations(PrintWriter.class) {
	    {
		sc.hasNext();
		result = new UncheckedIOException("hasNext burped.",
			new IOException("hasNext burped"));

		sc.ioException();
		returns(new IOException("close burped."));
	    }
	};

	try {
	    improvedReadAndWrite(is, os);
	} catch (Exception e) {
	    assertEquals("hasNext burped.", e.getMessage());
	    assertNotNull(e.getSuppressed());
	    assertEquals(1, e.getSuppressed().length);
	    assertEquals("close burped.", e.getSuppressed()[0].getMessage());
	}
    }

    @Test
    public void testImprovedReadAndWriteWhenScannerHasNextAndCloseAndInputStreamCloseThrowExceptions(
	    @Mocked InputStream is, @Mocked OutputStream os, @Mocked Scanner sc)
	    throws IOException {
	new Expectations(PrintWriter.class) {
	    {
		sc.hasNext();
		result = new UncheckedIOException("hasNext burped.",
			new IOException("hasNext burped"));

		sc.ioException();
		returns(new IOException("Scanner close burped."));

		is.close();
		result = new IOException("InputStream close burped.");
	    }
	};

	try {
	    improvedReadAndWrite(is, os);
	} catch (Exception e) {
	    assertEquals("hasNext burped.", e.getMessage());
	    assertNotNull(e.getSuppressed());
	    assertEquals(2, e.getSuppressed().length);
	    assertEquals("Scanner close burped.",
		    e.getSuppressed()[0].getMessage());
	    assertEquals("InputStream close burped.",
		    e.getSuppressed()[1].getMessage());
	}
    }

    @Test
    public void testImprovedReadAndWriteWhenScannerHasNextAndCloseAndInputStreamCloseAndOutputStreamCloseThrowExceptions(
	    @Mocked InputStream is, @Mocked OutputStream os, @Mocked Scanner sc)
	    throws IOException {
	new Expectations(PrintWriter.class) {
	    {
		sc.hasNext();
		result = new UncheckedIOException("hasNext burped.",
			new IOException("hasNext burped"));

		sc.ioException();
		returns(new IOException("Scanner close burped."));

		is.close();
		result = new IOException("InputStream close burped.");

		os.close();
		result = new IOException("OutputStream close burped.");
	    }
	};

	try {
	    improvedReadAndWrite(is, os);
	} catch (Exception e) {
	    assertEquals("hasNext burped.", e.getMessage());
	    assertNotNull(e.getSuppressed());
	    assertEquals(3, e.getSuppressed().length);
	    assertEquals("Scanner close burped.",
		    e.getSuppressed()[0].getMessage());
	    assertEquals("InputStream close burped.",
		    e.getSuppressed()[1].getMessage());
	    assertEquals("OutputStream close burped.",
		    e.getSuppressed()[2].getMessage());
	}
    }

    @Test
    public void testReadAllBytesReversed() throws IOException {
	new MockUp<Files>() {
	    @Mock
	    public byte[] readAllBytes(Path p) {
		return new byte[] { -1, 0, 1 };
	    }
	};

	assertArrayEquals(new byte[] { 1, 0, -1 }, readAllBytesReversed(null));
    }

    @Test
    public void testUnmodifiableLinesReversed() {
	new MockUp<Files>() {
	    @Mock
	    public List<String> readAllLines(Path p, Charset cs) {
		assertEquals(UTF_8, cs);

		return asList("line 1", "line 2", "line 3");
	    }
	};

	List<String> linesReversed = stream(
		new UnmodifiableLinesReversed(null).spliterator(), false)
		.collect(toList());

	assertEquals(3, linesReversed.size());
	assertEquals("line 3", linesReversed.get(0));
	assertEquals("line 2", linesReversed.get(1));
	assertEquals("line 1", linesReversed.get(2));
    }

    @Test
    public void testWriteContentTo(@Mocked Path writeTo)
	    throws MalformedURLException {
	HttpServer server = newInstance();
	server.start();

	URL readContentFrom = new URL("http://localhost:" + PORT + "/info");

	long bytesRead = getContent(openConnection(readContentFrom, false))
		.collect(toList()).get(0).getBytes(UTF_8).length;

	long bytesWritten = writeContentTo(readContentFrom, writeTo);

	assertEquals(bytesRead, bytesWritten);

	server.stop(0);
    }

    @Test
    public void testFindCreditCardNumbers() throws Exception {
	Path p = get(getClass().getResource("/ch9").toURI());

	p = findCreditCardNumbers(p);

	assertNotNull(p);
	assertTrue(exists(p));

	List<String> linesContainingCreditCardNumbers = readAllLines(p);

	assertNotNull(linesContainingCreditCardNumbers);

	assertEquals(2, linesContainingCreditCardNumbers.size());

	assertTrue(linesContainingCreditCardNumbers.get(0).contains(
		"4111111111111111"));
	assertTrue(linesContainingCreditCardNumbers.get(1).contains(
		"5111111111111111"));
    }
}

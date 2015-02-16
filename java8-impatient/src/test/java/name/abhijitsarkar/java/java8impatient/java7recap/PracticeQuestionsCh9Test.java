package name.abhijitsarkar.java.java8impatient.java7recap;

import static name.abhijitsarkar.java.java8impatient.java7recap.PracticeQuestionsCh9.improvedReadAndWrite;
import static name.abhijitsarkar.java.java8impatient.java7recap.PracticeQuestionsCh9.readAndWrite;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.Scanner;

import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;

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
}

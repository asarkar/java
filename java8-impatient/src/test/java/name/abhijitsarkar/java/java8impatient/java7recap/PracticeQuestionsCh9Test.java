package name.abhijitsarkar.java.java8impatient.java7recap;

import static name.abhijitsarkar.java.java8impatient.java7recap.PracticeQuestionsCh9.readAndWrite;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.Scanner;

import mockit.Expectations;
import mockit.Mocked;

import static org.junit.Assert.*;
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

    @Test(expected = UncheckedIOException.class)
    public void testReadAndWriteWhenScannerCloseThrowsException(
	    @Mocked InputStream is, @Mocked OutputStream os, @Mocked Scanner sc) {
	new Expectations(PrintWriter.class) {
	    {
		sc.hasNext();
		returns(false);

		sc.close();
		result = new UncheckedIOException("close burped.",
			new IOException("close burped"));
	    }
	};
	readAndWrite(is, os);
    }

    public void testImprovedReadAndWriteWhenScannerHasNextAndCloseThrowExceptions(
	    @Mocked InputStream is, @Mocked OutputStream os, @Mocked Scanner sc) {
	new Expectations(PrintWriter.class) {
	    {
		sc.hasNext();
		result = new UncheckedIOException("hasNext burped.",
			new IOException("hasNext burped"));

		sc.close();
		result = new UncheckedIOException("close burped.",
			new IOException("close burped"));
	    }
	};
	
	try {
	    readAndWrite(is, os);
	} catch (Exception e) {
	    assertEquals("hasNext burped.", e.getMessage());
	    assertNotNull();
	    assertEquals("close burped.", e.getSuppressed().getMessage());
	}	
    }
}

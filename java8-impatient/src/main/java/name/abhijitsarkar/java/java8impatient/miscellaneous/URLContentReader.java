package name.abhijitsarkar.java.java8impatient.miscellaneous;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getEncoder;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.ScannerDecorator.lines;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.SimpleHttpServer.PASSWORD;
import static name.abhijitsarkar.java.java8impatient.miscellaneous.SimpleHttpServer.USERNAME;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Q11: Write a program that gets the content of a password-protected web page.
 * 
 * @author Abhijit Sarkar
 *
 */
public class URLContentReader {
    public static URLConnection openConnection(final URL url,
	    final boolean isProtected) {
	try {
	    final URLConnection connection = url.openConnection();

	    if (isProtected) {
		setAuthInfo(connection);
	    }

	    return connection;
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}
    }

    public static Stream<String> getContent(final URLConnection connection) {
	try {
	    connection.connect();

	    return lines(new Scanner(connection.getInputStream(), UTF_8.name()));
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}
    }

    private static void setAuthInfo(final URLConnection connection) {
	connection
		.setRequestProperty("Authorization", "Basic " + getAuthInfo());
    }

    private static String getAuthInfo() {
	return encodeAuthInfo(String.join(":", USERNAME, PASSWORD));
    }

    private static String encodeAuthInfo(final String authInfo) {
	return getEncoder().encodeToString(authInfo.getBytes(UTF_8));
    }
}

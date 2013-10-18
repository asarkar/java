package com.github.mkalin.jwsur2.ch1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/*
 * *****************************************************************************
 * Code usage and distribution policy is copied from the Preface of the book:
 * "Java Web Services: Up and Running, Second Edition, by Martin Kalin. Copyright 2013 Martin Kalin, 978-1-449-36511-0."
 * *****************************************************************************
 * This book is here to help you get your job done. In general, if this book 
 * includes code examples, you may use the code in your programs and 
 * documentation. You do not need to contact us for permission unless you’re 
 * reproducing a significant portion of the code. For example, writing a program 
 * that uses several chunks of code from this book does not require permission. 
 * Selling or distributing a CD-ROM of examples from O’Reilly books does 
 * require permission. Answering a question by citing this book and quoting 
 * example code does not require permission. Incorporating a significant amount 
 * of example code from this book into your product’s documentation 
 * does require permission.
 * We appreciate, but do not require, attribution. 
 * An attribution usually includes the title, author, publisher, and ISBN. 
 * For example: “Java Web Services: Up and Running, Second Edition, by Martin Kalin. Copyright 2013 Martin Kalin, 978-1-449-36511-0.”
 * If you feel your use of code examples falls outside fair use or the 
 * permission given above, feel free to contact us at permissions@oreilly.com.
 * *****************************************************************************
 */

public class UrlConnectionClient {
	public static void main(String[] args) {
		// usage
		if (args.length < 1) {
			System.err.println("Usage: URLConnectionClient <url>");
			return;
		}

		try {
			// Parse the URL.
			URL url = new URL(args[0].trim());

			// Connect.
			URLConnection sock = url.openConnection();

			// Read and print.
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));
			String next_record = null;
			while ((next_record = reader.readLine()) != null)
				System.out.println(next_record);

			// Close.
			reader.close();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

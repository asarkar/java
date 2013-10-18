package com.github.mkalin.jwsur2.ch1.predictions;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

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

public class Predictions {
	private int n = 32;
	private Prediction[] predictions;
	private ServletContext sctx;

	public Predictions() {
	}

	// The ServletContext is required to read the data from
	// a text file packaged inside the WAR file
	public void setServletContext(ServletContext sctx) {
		this.sctx = sctx;
	}

	public ServletContext getServletContext() {
		return this.sctx;
	}

	// getPredictions returns an XML representation of
	// the Predictions array
	public void setPredictions(String ps) {
	} // no-op

	public String getPredictions() {
		// Has the ServletContext been set?
		if (getServletContext() == null)
			return null;

		// Have the data been read already?
		if (predictions == null)
			populate();

		// Convert the Predictions array into an XML document
		return toXML();
	}

	// ** utilities
	private void populate() {
		String filename = "/WEB-INF/data/predictions.db";
		InputStream in = sctx.getResourceAsStream(filename);

		assert in != null : "Can't find data file";

		// Read the data into the array of Predictions.
		if (in != null) {
			try {
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(isr);

				predictions = new Prediction[n];
				int i = 0;
				String record = null;
				while ((record = reader.readLine()) != null) {
					String[] parts = record.split("!");
					Prediction p = new Prediction();
					p.setWho(parts[0]);
					p.setWhat(parts[1]);

					predictions[i++] = p;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String toXML() {
		String xml = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			XMLEncoder encoder = new XMLEncoder(out);
			encoder.writeObject(predictions); // serialize to XML
			encoder.close();
			xml = out.toString(); // stringify
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}
}

package com.github.mkalin.jwsur2.ch2.predictions2;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;

public class Predictions {
	private ConcurrentMap<Integer, Prediction> predictions;
	private ServletContext sctx;
	private AtomicInteger mapKey;

	public Predictions() {
		predictions = new ConcurrentHashMap<Integer, Prediction>();
		mapKey = new AtomicInteger();
	}

	// ** properties

	// The ServletContext is required to read the data from
	// a text file packaged inside the WAR file
	public void setServletContext(ServletContext sctx) {
		this.sctx = sctx;
	}

	public ServletContext getServletContext() {
		return this.sctx;
	}

	public void setMap(ConcurrentMap<String, Prediction> predictions) {
		// no-op for now
	}

	public ConcurrentMap<Integer, Prediction> getMap() {
		// Has the ServletContext been set?
		if (getServletContext() == null)
			return null;

		// Have the data been read already?
		if (predictions.size() < 1)
			populate();

		return this.predictions;
	}

	public String toXML(Object obj) {
		String xml = null;

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			XMLEncoder encoder = new XMLEncoder(out);
			encoder.writeObject(obj); // serialize to XML
			encoder.close();
			xml = out.toString(); // stringify
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

	public int addPrediction(Prediction p) {
		int id = mapKey.incrementAndGet();
		p.setId(id);
		predictions.put(id, p);
		return id;
	}

	// ** utility
	private void populate() {
		String filename = "/WEB-INF/data/predictions.db";
		InputStream in = sctx.getResourceAsStream(filename);

		// Read the data into the array of Predictions.
		if (in != null) {
			try {
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(isr);

				int i = 0;
				String record = null;
				while ((record = reader.readLine()) != null) {
					String[] parts = record.split("!");
					Prediction p = new Prediction();
					p.setWho(parts[0]);
					p.setWhat(parts[1]);
					addPrediction(p);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

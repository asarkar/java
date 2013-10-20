package com.github.mkalin.jwsur2.ch2.jaxws.aphorism3;

import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

@WebServiceProvider
// generic service provider
@ServiceMode(javax.xml.ws.Service.Mode.MESSAGE)
// entire message available
@BindingType(HTTPBinding.HTTP_BINDING)
// versus SOAP binding
public class AdagesProvider implements Provider<Source> {
	@Resource
	protected WebServiceContext wctx; // dependency injection

	public AdagesProvider() {
	}

	// Implement the Provider interface by defining invoke, which expects an XML
	// source (perhaps null) and returns an XML source (perhaps null).
	public Source invoke(Source request) {
		if (wctx == null)
			throw new RuntimeException("Injection failed on wctx.");

		// Grab the message context and extract the request verb.
		MessageContext mctx = wctx.getMessageContext();
		String httpVerb = (String) mctx.get(MessageContext.HTTP_REQUEST_METHOD);
		httpVerb = httpVerb.trim().toUpperCase();

		// Dispatch on verb to the handler method. POST and PUT have non-null
		// requests so only these two get the Source request.
		if (httpVerb.equals("GET"))
			return doGet(mctx);
		else if (httpVerb.equals("POST"))
			return doPost(request);
		else if (httpVerb.equals("PUT"))
			return doPut(request);
		else if (httpVerb.equals("DELETE"))
			return doDelete(mctx);
		else
			throw new HTTPException(405); // bad verb
	}

	private Source doGet(MessageContext mctx) {
		// Parse the query string.
		String qs = (String) mctx.get(MessageContext.QUERY_STRING);

		// Get all Adages.
		if (qs == null)
			return adages2Xml();
		// Get a specified Adage.
		else {
			int id = getId(qs);
			if (id < 0)
				throw new HTTPException(400); // bad request

			Adage adage = Adages.find(id);
			if (adage == null)
				throw new HTTPException(404); // not found

			return adage2Xml(adage);
		}
	}

	private Source doPost(Source request) {
		if (request == null)
			throw new HTTPException(400); // bad request

		InputSource in = toInputSource(request);

		String pattern = "//words/text()"; // find the Adage's "words"
		String words = findElement(pattern, in);
		if (words == null)
			throw new HTTPException(400); // bad request

		Adages.add(words);
		String msg = "The adage '" + words + "' has been created.";
		return toSource(toXml(msg));
	}

	private Source doPut(Source request) {
		if (request == null)
			throw new HTTPException(400); // bad request

		InputSource in = toInputSource(request);
		String pattern = "//words/text()"; // find the Adage's "words"
		String words = findElement(pattern, in);
		if (words == null)
			throw new HTTPException(400); // bad request

		// Format in XML is: <words>!<id>
		String[] parts = words.split("!");
		if (parts[0].length() < 1 || parts[1].length() < 1)
			throw new HTTPException(400); // bad request

		int id = -1;
		try {
			id = Integer.parseInt(parts[1].trim());
		} catch (Exception e) {
			throw new HTTPException(400);
		} // bad request

		// Find and edit.
		Adage adage = Adages.find(id);
		if (adage == null)
			throw new HTTPException(404); // not found

		adage.setWords(parts[0]);
		String msg = "Adage " + adage.getId() + " has been updated.";
		return toSource(toXml(msg));
	}

	private Source doDelete(MessageContext mctx) {
		String qs = (String) mctx.get(MessageContext.QUERY_STRING);

		// Disallow the deletion of all teams at once.
		if (qs == null)
			throw new HTTPException(403); // illegal operation
		else {
			int id = getId(qs);
			if (id < 0)
				throw new HTTPException(400); // bad request

			Adage adage = Adages.find(id);
			if (adage == null)
				throw new HTTPException(404); // not found
			Adages.remove(adage);

			String msg = "Adage " + id + " removed.";
			return toSource(toXml(msg));
		}
	}

	private int getId(String qs) {
		int retval = -1; // bad ID
		String[] parts = qs.split("=");

		if (!parts[0].toLowerCase().trim().equals("id"))
			return retval;
		try {
			retval = Integer.parseInt(parts[1].trim());
		} catch (Exception e) {
			return retval;
		}
		return retval;
	}

	private StreamSource adages2Xml() {
		String str = toXml(Adages.getListAsArray());
		return toSource(str);
	}

	private StreamSource adage2Xml(Adage adage) {
		String str = toXml(adage);
		return toSource(str);
	}

	private String toXml(Object obj) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLEncoder enc = new XMLEncoder(out);
		enc.writeObject(obj);
		enc.close();
		return out.toString();
	}

	private StreamSource toSource(String str) {
		return new StreamSource(new StringReader(str));
	}

	private InputSource toInputSource(Source source) {
		InputSource input = null;
		try {
			Transformer trans = TransformerFactory.newInstance()
					.newTransformer();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(bos);
			trans.transform(source, result);
			input = new InputSource(new ByteArrayInputStream(bos.toByteArray()));
		} catch (Exception e) {
			throw new HTTPException(500);
		} // internal server error
		return input;
	}

	private String findElement(String expression, InputSource source) {
		XPath xpath = XPathFactory.newInstance().newXPath();
		String retval = null;
		try {
			retval = (String) xpath.evaluate(expression, source,
					XPathConstants.STRING);
		} catch (Exception e) {
			throw new HTTPException(400);
		} // bad request
		return retval;
	}
}
package com.github.mkalin.jwsur2.ch2.jaxws.aphorism3;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;

public class Client {
	private Service service;
	private QName serviceName = new QName("http://sample.org",
			"VerySimpleService");
	private QName portName = new QName("http://sample.org", "PortName");

	public static void main(String[] args) throws Exception {
		new Client().testProvider();
	}

	private void init() throws Exception {
		String url = "http://localhost:8888/";
		service = Service.create(serviceName);
		service.addPort(portName, HTTPBinding.HTTP_BINDING, url);
		System.out.println("Setup done.");
	}

	private Source invoke(Dispatch<Source> dispatch, String verb, String request) {
		Map<String, Object> requestContext = dispatch.getRequestContext();
		requestContext.put(MessageContext.HTTP_REQUEST_METHOD, verb);
		return dispatch.invoke(new StreamSource(new StringReader(request)));
	}

	public void testProvider() throws Exception {
		init();
		Dispatch<Source> dispatch = service.createDispatch(portName,
				Source.class, Service.Mode.MESSAGE);

		// POST test
		String request = "<ns1:foo xmlns:ns1='http://sample.org'><words>This is the way the world ends.</words></ns1:foo>";
		System.out.println("\nInvoking xml request: " + request);
		Source result = this.invoke(dispatch, "POST", request);
		System.out.println("Response ==> " + toXmlString(result));
	}

	private String toXmlString(Source result) {
		String xmlResult = null;
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			OutputStream out = new ByteArrayOutputStream();
			StreamResult streamResult = new StreamResult();
			streamResult.setOutputStream(out);
			transformer.transform(result, streamResult);
			xmlResult = streamResult.getOutputStream().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlResult;
	}
}

package name.abhijitsarkar.xml.jaxp.sax;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import name.abhijitsarkar.xml.jaxp.MyErrorHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class MySAXParser {
	private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	public static void main(String[] args) throws Exception {
		MySAXParser myParser = new MySAXParser();
		myParser.parseWithoutValidation();
		myParser.parseWithValidation();
	}

	public void parseWithoutValidation() throws Exception {
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
		xmlReader.setContentHandler(new MyContentHandler());

		InputStream is = MySAXParser.class.getResourceAsStream("/po_good.xml");
		xmlReader.parse(new InputSource(is));
		is.close();
	}

	public void parseWithValidation() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		SAXParser parser = factory.newSAXParser();
		parser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

		URL schema = MySAXParser.class.getResource("/po.xsd");
		parser.setProperty(JAXP_SCHEMA_SOURCE, new File(schema.toURI()));

		XMLReader xmlReader = parser.getXMLReader();
		xmlReader.setContentHandler(new MyContentHandler());
		xmlReader.setErrorHandler(new MyErrorHandler());

		InputStream is = MySAXParser.class.getResourceAsStream("/po_bad.xml");
		xmlReader.parse(new InputSource(is));
		is.close();
	}
}

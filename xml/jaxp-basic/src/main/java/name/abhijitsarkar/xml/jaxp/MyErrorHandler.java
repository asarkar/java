package name.abhijitsarkar.xml.jaxp;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MyErrorHandler implements ErrorHandler {

	@Override
	public void warning(SAXParseException e) throws SAXException {
		System.err.println("Warning: " + e.getMessage());
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		System.err.println("Error: " + e.getMessage());
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		System.err.println("Fatal error: " + e.getMessage());
	}
}

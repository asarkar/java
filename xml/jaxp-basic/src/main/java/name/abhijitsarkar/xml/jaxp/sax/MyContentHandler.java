package name.abhijitsarkar.xml.jaxp.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyContentHandler extends DefaultHandler {

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Start document");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("End document");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		System.out.println("\nStart element");

		int numAttr = attributes.getLength();

		System.out.printf("Element %s has %d attributes\n", localName, numAttr);

		for (int i = 0; i < numAttr; i++) {
			System.out.printf("Attribute[%d] -> [name=%s, value=%s]\n", i,
					attributes.getLocalName(i), attributes.getValue(i));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.println("End element");
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String text = String.valueOf(ch).substring(start, start + length)
				.trim();

		System.out.println("Characters: \"" + text + "\"");
	}
}

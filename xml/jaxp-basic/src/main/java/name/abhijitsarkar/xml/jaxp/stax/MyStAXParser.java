package name.abhijitsarkar.xml.jaxp.stax;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.EventReaderDelegate;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import name.abhijitsarkar.xml.jaxp.MyErrorHandler;
import name.abhijitsarkar.xml.jaxp.sax.MySAXParser;

/*
 * This class uses the iterator Stax API
 */
public class MyStAXParser {
	public static void main(String[] args) throws Exception {
		MyStAXParser myParser = new MyStAXParser();
		myParser.parseWithoutValidation();
		myParser.parseWithValidation();
	}

	public void parseWithoutValidation() throws XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = MyStAXParser.class.getResourceAsStream("/po_good.xml");
		XMLEventReader r = factory.createXMLEventReader(is);

		while (r.hasNext()) {
			XMLEvent e = r.nextEvent();

			handleEvent(e);
		}
	}

	public void parseWithValidation() throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = MyStAXParser.class.getResourceAsStream("/po_bad.xml");
		XMLEventReader r = factory.createXMLEventReader(is);

		r = new EventReaderDelegate(r) {
			@Override
			public XMLEvent nextEvent() throws XMLStreamException {
				XMLEvent e = super.nextEvent();

				handleEvent(e);
				return e;
			}
		};

		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		URL schemaURL = MySAXParser.class.getResource("/po.xsd");
		Schema schema = schemaFactory.newSchema(new File(schemaURL.toURI()));

		Validator validator = schema.newValidator();
		validator.setErrorHandler(new MyErrorHandler());
		// validator calls the EventReaderDelegate nextEvent() method
		validator.validate(new StAXSource(r));
	}

	private void handleEvent(XMLEvent e) {
		int eventType = e.getEventType();
		switch (eventType) {
		case XMLEvent.START_ELEMENT:
			System.out.println("\nStart element");
			StartElement startElem = e.asStartElement();
			String localName = startElem.getName().getLocalPart();

			System.out.printf("Element %s\n", localName);

			@SuppressWarnings("unchecked")
			Iterator<Attribute> attributes = startElem.getAttributes();

			Attribute attr = null;
			while (attributes.hasNext()) {
				attr = attributes.next();
				localName = attr.getName().getLocalPart();

				System.out.printf("Attribute -> [name=%s, value=%s]\n",
						localName, attr.getValue());
			}
			break;
		case XMLEvent.END_ELEMENT:
			System.out.println("End element");
			break;
		case XMLEvent.CHARACTERS:
			Characters ch = e.asCharacters();
			System.out.println("Characters: \"" + ch.getData().trim() + "\"");
			break;
		case XMLEvent.START_DOCUMENT:
			System.out.println("Start document");
			break;
		case XMLEvent.END_DOCUMENT:
			System.out.println("End document");
			break;
		default:
			break;
		}
	}
}

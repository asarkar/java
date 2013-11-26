package name.abhijitsarkar.xml.jaxp.dom;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import name.abhijitsarkar.xml.jaxp.MyErrorHandler;
import name.abhijitsarkar.xml.jaxp.sax.MySAXParser;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MyDOMParser {
	private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	public static void main(String[] args) throws Exception {
		MyDOMParser myParser = new MyDOMParser();
		myParser.parseWithoutValidation();
		myParser.parseWithValidation();
	}

	public void parseWithoutValidation() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		InputStream is = MyDOMParser.class.getResourceAsStream("/po_good.xml");
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(is);

		printNodeInfo(doc.getDocumentElement());

		is.close();
	}

	public void parseWithValidation() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setValidating(true);

		dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

		URL schema = MySAXParser.class.getResource("/po.xsd");
		dbf.setAttribute(JAXP_SCHEMA_SOURCE, new File(schema.toURI()));

		InputStream is = MyDOMParser.class.getResourceAsStream("/po_bad.xml");
		DocumentBuilder db = dbf.newDocumentBuilder();
		db.setErrorHandler(new MyErrorHandler());
		
		Document doc = db.parse(is);

		printNodeInfo(doc.getDocumentElement());

		is.close();
	}

	private void printNodeInfo(Node node) {
		short nodeType = node.getNodeType();

		switch (nodeType) {
		case Node.ELEMENT_NODE:
			Element elem = (Element) node;

			NamedNodeMap attributeMap = elem.getAttributes();
			int numAttr = attributeMap.getLength();

			System.out.printf("Element %s has %d attributes\n",
					elem.getNodeName(), numAttr);

			Attr attr = null;
			for (int i = 0; i < numAttr; i++) {
				attr = (Attr) attributeMap.item(i);

				System.out.printf("Attribute[%d] -> [name=%s, value=%s]\n", i,
						attr.getName(), attr.getValue());
			}

			NodeList childNodes = elem.getChildNodes();

			int numChildren = childNodes.getLength();

			for (int i = 0; i < numChildren; i++) {
				printNodeInfo(childNodes.item(i));
			}

			break;
		case Node.TEXT_NODE:
			System.out.println("text: \"" + node.getNodeValue().trim() + "\"");
			break;
		default:
		}
	}
}

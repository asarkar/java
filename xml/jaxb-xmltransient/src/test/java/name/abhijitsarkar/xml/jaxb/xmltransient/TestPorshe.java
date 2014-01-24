package name.abhijitsarkar.xml.jaxb.xmltransient;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import name.abhijitsarkar.xml.jaxb.xmltransient.Car;
import name.abhijitsarkar.xml.jaxb.xmltransient.Porshe;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPorshe {
	private static JAXBContext jc;

	@BeforeClass
	public static void oneTimeSetUp() throws JAXBException {
		jc = JAXBContext.newInstance(Porshe.class);
	}

	@Test
	public void testXMLTransient() throws JAXBException, IOException {
		Car porshe = new Porshe("porshe", "911", 2014);

		StringWriter w = new StringWriter();

		jc.createMarshaller().marshal(porshe, w);

		String xml = w.getBuffer().toString();

		w.close();

		System.out.println(xml);

		Assert.assertFalse("Make shouldn't be marshalled.",
				xml.contains("make"));
		Assert.assertFalse(
				"Cool stuff found only in a Porshe shouldn't be marshalled.",
				xml.contains("coolStuffFoundOnlyInAPorshe"));
	}
}

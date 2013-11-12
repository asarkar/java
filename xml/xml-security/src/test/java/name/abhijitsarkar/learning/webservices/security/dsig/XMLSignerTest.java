package name.abhijitsarkar.learning.webservices.security.dsig;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class XMLSignerTest {

	@Test
	public void testSign() {
		String inputFile = XMLSignerTest.class.getResource("/" + INPUT_FILE)
				.getPath();
		String outputFile = new File(inputFile).getParent()
				+ File.separator + OUTPUT_FILE;
		try {
			XMLSigner.sign(inputFile, outputFile, REFERENCE_URI);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Shouldn't be here.");
		}
	}

	@Test
	public void testValidate() {
		String inputFile = XMLSignerTest.class.getResource("/" + INPUT_FILE)
				.getPath();
		String outputFile = new File(inputFile).getParent()
				+ File.separator + OUTPUT_FILE;
		try {
			XMLSigner.validate(outputFile);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Shouldn't be here.");
		}
	}

	private final XMLSigner XMLSigner = new XMLSigner();
	private static final String REFERENCE_URI = "PurchaseOrder";
	private static final String INPUT_FILE = "PurchaseOrder.xml";
	private static final String OUTPUT_FILE = "SignedPurchaseOrder.xml";
}

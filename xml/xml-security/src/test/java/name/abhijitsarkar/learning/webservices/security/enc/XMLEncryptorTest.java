package name.abhijitsarkar.learning.webservices.security.enc;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class XMLEncryptorTest {

	@Test
	public void testEncrypt() {
		String inputFile = XMLEncryptorTest.class.getResource("/" + INPUT_FILE)
				.getPath();
		String outputFile = new File(inputFile).getParent()
				+ File.separator + OUTPUT_FILE;
		
		try {
			XMLEncryptor.encrypt(inputFile, outputFile);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Shouldn't be here.");
		}
	}
	
	@Test
	public void testDecrypt() {
		String inputFile = XMLEncryptorTest.class.getResource("/" + INPUT_FILE)
				.getPath();
		String outputFile = new File(inputFile).getParent()
				+ File.separator + OUTPUT_FILE;
		
		try {
			XMLEncryptor.decrypt(outputFile);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Shouldn't be here.");
		}
	}

	private final XMLEncryptor XMLEncryptor = new XMLEncryptor();
	private static final String INPUT_FILE = "PurchaseOrder.xml";
	private static final String OUTPUT_FILE = "EncryptedPurchaseOrder.xml";
}

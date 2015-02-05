package name.abhijitsarkar.xml.security.enc;

import java.io.File;

import org.junit.Test;

public class XMLEncryptorTest {
    private final XMLEncryptor XMLEncryptor = new XMLEncryptor();
    private static final String INPUT_FILE = "PurchaseOrder.xml";
    private static final String OUTPUT_FILE = "EncryptedPurchaseOrder.xml";

    @Test
    public void testEncrypt() throws Exception {
	String inputFile = XMLEncryptorTest.class.getResource("/" + INPUT_FILE)
		.getPath();
	String outputFile = new File(inputFile).getParent() + File.separator
		+ OUTPUT_FILE;

	XMLEncryptor.encrypt(inputFile, outputFile);
    }

    @Test
    public void testDecrypt() throws Exception {
	String inputFile = XMLEncryptorTest.class.getResource("/" + INPUT_FILE)
		.getPath();
	String outputFile = new File(inputFile).getParent() + File.separator
		+ OUTPUT_FILE;

	XMLEncryptor.decrypt(outputFile);
    }
}

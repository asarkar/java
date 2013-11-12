package name.abhijitsarkar.learning.webservices.security.enc;

import java.io.File;

import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.security.Init;
import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.keys.KeyInfo;
import org.w3c.dom.Document;

public class EncryptionManager {
	static {
		Init.init();
	}

	public EncryptionManager(SecretKey secKey) {
		this.secKey = secKey;
	}

	// <EncryptedData Id? Type?>
	// <EncryptionMethod/>?
	// <ds:KeyInfo>
	// <EncryptedKey>?
	// <AgreementMethod>?
	// <ds:KeyName>?
	// <ds:RetrievalMethod>?
	// <ds:*>?
	// </ds:KeyInfo>?
	// <CipherData>
	// <CipherValue>?
	// <CipherReference URI?>?
	// </CipherData>
	// <EncryptionProperties>?
	// <EncryptedData>
	public void encrypt(File inputFile, File outputFile) throws Exception {
		Document XML = parseFile(inputFile);

		// Encrypt the security key itself
		XMLCipher cipher = getCipher(XMLCipher.WRAP_MODE, secKey);
		EncryptedKey encryptedKey = cipher.encryptKey(XML, secKey);

		KeyInfo keyInfo = new KeyInfo(XML);
		keyInfo.add(encryptedKey);

		// Encrypt the data
		cipher = getCipher(XMLCipher.ENCRYPT_MODE, secKey);
		EncryptedData encryptedData = cipher.encryptData(XML,
				XML.getDocumentElement());

		encryptedData.setKeyInfo(keyInfo);

		// do the actual encryption
		Document doc = cipher.doFinal(XML, XML.getDocumentElement());

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(outputFile));
	}

	private Document parseFile(File inputFile) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		return dbf.newDocumentBuilder().parse(inputFile);
	}

	public void decrypt(File encryptedFile) throws Exception {
		XMLCipher cipher = getCipher(XMLCipher.DECRYPT_MODE, secKey);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document XML = dbf.newDocumentBuilder().parse(encryptedFile);

		Document doc = cipher.doFinal(XML, XML.getDocumentElement());

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(System.out));
	}

	private XMLCipher getCipher(int mode, SecretKey key) throws Exception {
		// Must use the same signing algorithm that was used when creating the
		// seckey
		XMLCipher cipher = XMLCipher.getInstance(XMLCipher.AES_128);
		cipher.init(mode, key);

		return cipher;
	}

	private final SecretKey secKey;
}

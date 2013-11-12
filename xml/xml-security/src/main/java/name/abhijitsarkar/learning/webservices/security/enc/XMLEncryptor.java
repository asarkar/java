package name.abhijitsarkar.learning.webservices.security.enc;

import java.io.File;

import name.abhijitsarkar.learning.webservices.security.KeyStoreManager;
import name.abhijitsarkar.learning.webservices.security.SecurityUtil;

public class XMLEncryptor {

	public void encrypt(String inputFile, String outputFile) throws Exception {
		File ipFile = new File(inputFile);
		File opFile = new File(outputFile);

		SecurityUtil.print(ipFile);

		EncryptionManager encMgr = new EncryptionManager(
				KeyStoreManager
						.getSecKey(
								XMLEncryptor.class.getResource("/" + KEYSTORE)
										.getPath(), STORE_PASSWORD,
								KEY_PASSWORD, ALIAS));
		encMgr.encrypt(ipFile, opFile);

		SecurityUtil.print(opFile);
	}

	public void decrypt(String inputFile) throws Exception {
		File ipFile = new File(inputFile);

		SecurityUtil.print(ipFile);

		EncryptionManager encMgr = new EncryptionManager(
				KeyStoreManager
						.getSecKey(
								XMLEncryptor.class.getResource("/" + KEYSTORE)
										.getPath(), STORE_PASSWORD,
								KEY_PASSWORD, ALIAS));
		encMgr.decrypt(ipFile);
	}

	private static final String KEYSTORE = ".ocewsdkeystore";
	private static final String STORE_PASSWORD = "abhijitsarkar";
	private static final String KEY_PASSWORD = "abhijitsarkar";
	private static final String ALIAS = "xmlenc";

}

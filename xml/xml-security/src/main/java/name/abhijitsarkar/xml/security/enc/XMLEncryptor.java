package name.abhijitsarkar.xml.security.enc;

import java.io.File;

import javax.crypto.SecretKey;

import name.abhijitsarkar.xml.security.KeyStoreManager;
import name.abhijitsarkar.xml.security.SecurityUtil;

public class XMLEncryptor {
    private static final String KEYSTORE = ".ocewsdkeystore";
    private static final String STORE_PASSWORD = "abhijitsarkar";
    private static final String KEY_PASSWORD = "abhijitsarkar";
    private static final String ALIAS = "xmlenc";

    private static final EncryptionManager encMgr;

    static {
	try {
	    SecretKey secretKey = KeyStoreManager.getSecKey(XMLEncryptor.class
		    .getResource("/" + KEYSTORE).getPath(), STORE_PASSWORD,
		    KEY_PASSWORD, ALIAS);

	    encMgr = new EncryptionManager(secretKey);
	} catch (Exception e) {
	    throw new IllegalStateException(
		    "Failed to instantiate secret key, cannot proceed.", e);
	}
    }

    public void encrypt(String inputFile, String outputFile) throws Exception {
	File ipFile = new File(inputFile);
	File opFile = new File(outputFile);

	SecurityUtil.print(ipFile);

	encMgr.encrypt(ipFile, opFile);

	SecurityUtil.print(opFile);
    }

    public void decrypt(String inputFile) throws Exception {
	File ipFile = new File(inputFile);

	SecurityUtil.print(ipFile);

	encMgr.decrypt(ipFile);
    }
}

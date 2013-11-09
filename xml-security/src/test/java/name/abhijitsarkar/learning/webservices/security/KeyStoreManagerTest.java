package name.abhijitsarkar.learning.webservices.security;

import java.security.KeyPair;

import name.abhijitsarkar.learning.webservices.security.KeyStoreManager;

import org.junit.Assert;
import org.junit.Test;

public class KeyStoreManagerTest {
	@Test
	public void testPublicKey() {
		try {
			KeyPair keyPair = KeyStoreManager.getKeyPair(
					KeyStoreManagerTest.class.getResource("/" + KEYSTORE)
							.getPath(), STORE_PASSWORD, KEY_PASSWORD, ALIAS);

			Assert.assertNotNull("Public Key must not be null",
					keyPair.getPublic());
			Assert.assertNotNull("Private Key must not be null",
					keyPair.getPrivate());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Shouldn't be here.");
		}
	}

	private static final String KEYSTORE = ".ocewsdkeystore";
	private static final String STORE_PASSWORD = "abhijitsarkar";
	private static final String KEY_PASSWORD = "abhijitsarkar";
	private static final String ALIAS = "xmlsig";

}

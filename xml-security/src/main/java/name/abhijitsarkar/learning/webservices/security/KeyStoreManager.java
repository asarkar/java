package name.abhijitsarkar.learning.webservices.security;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collections;

import javax.crypto.SecretKey;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;

import name.abhijitsarkar.learning.webservices.security.exception.NoSuchKeyException;

public class KeyStoreManager {

	public static KeyPair getKeyPair(String keystore, String storePassword,
			String keyPassword, String alias) throws Exception {
		KeyStore ks = loadKeyStore(keystore, storePassword);
		Key key = getKey(keystore, storePassword, keyPassword, alias);

		PublicKey publicKey = null;
		PrivateKey privateKey = null;

		key = ks.getKey(alias, keyPassword.toCharArray());

		if (key instanceof PrivateKey) {
			Certificate cert = ks.getCertificate(alias);
			publicKey = cert.getPublicKey();
			privateKey = (PrivateKey) key;

			return new KeyPair(publicKey, privateKey);
		}

		throw new NoSuchKeyException("Alias '" + alias
				+ "' does not exist in keystore '" + keystore + "'.");
	}

	public static SecretKey getSecKey(String keystore, String storePassword,
			String keyPassword, String alias) throws Exception {
		KeyStore ks = loadKeyStore(keystore, storePassword);
		Key key = getKey(keystore, storePassword, keyPassword, alias);

		key = ks.getKey(alias, keyPassword.toCharArray());

		if (key instanceof SecretKey) {

			return (SecretKey) key;
		}

		throw new NoSuchKeyException("Alias '" + alias
				+ "' does not exist in keystore '" + keystore + "'.");
	}

	public static KeyInfo createKeyInfo(final XMLSignatureFactory fac,
			final String certificateFile) throws Exception {
		KeyInfoFactory kif = fac.getKeyInfoFactory();

		Certificate cert = getCertificate(certificateFile);

		X509Data x509d = kif.newX509Data(Collections.singletonList(cert));

		return kif.newKeyInfo(Collections.singletonList(x509d));
	}

	public static Certificate getCertificate(final String certificateFile)
			throws Exception {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream fis = new FileInputStream(certificateFile);
		Certificate cert = cf.generateCertificate(fis);
		fis.close();

		return cert;
	}

	private static Key getKey(String keystore, String storePassword,
			String keyPassword, String alias) throws Exception {
		KeyStore ks = loadKeyStore(keystore, storePassword);

		if (ks.containsAlias(alias)) {
			return ks.getKey(alias, keyPassword.toCharArray());
		}

		throw new NoSuchKeyException("Alias '" + alias
				+ "' does not exist in keystore '" + keystore + "'.");
	}

	private static KeyStore loadKeyStore(String keystore, String storePassword)
			throws Exception {
		// Load the KeyStore and get the signing key and certificate.
		KeyStore ks = KeyStore.getInstance("JCEKS");
		ks.load(new FileInputStream(keystore), storePassword.toCharArray());
		return ks;
	}
}

package name.abhijitsarkar.learning.webservices.security.dsig;

import java.io.File;
import java.security.KeyPair;
import java.util.Collections;
import java.util.Iterator;

import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import name.abhijitsarkar.learning.webservices.security.KeyStoreManager;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

class SignatureManager {

	SignatureManager(final KeyPair keyPair, final String certificateFile,
			final String referenceURI) {
		// Create a DOM XMLSignatureFactory that will be used to
		// generate the enveloped signature.
		this.fac = XMLSignatureFactory.getInstance("DOM");
		this.keyPair = keyPair;
		this.certificateFile = new File(certificateFile);
		if (referenceURI != null) {
			this.referenceURI = referenceURI;
		} else {
			this.referenceURI = "";
		}

		if (this.keyPair == null || this.certificateFile == null) {
			throw new IllegalArgumentException(
					"Key pair and/or certificate file must not be null.");
		}

		if (this.fac == null) {
			throw new IllegalStateException(
					"Failed to instantiate XMLSignatureFactory.");
		}
	}

	void sign(File inputFile, File outputFile) throws Exception {
		// Instantiate the document to be signed.
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document XML = dbf.newDocumentBuilder().parse(inputFile);
		XMLStructure content = new DOMStructure(XML.getDocumentElement());
		XMLObject obj = fac.newXMLObject(Collections.singletonList(content),
				referenceURI, null, null);

		// Create the XMLSignature, but don't sign it yet.
		// <Signature ID?>
		// <SignedInfo/>
		// <SignatureValue/>
		// (<KeyInfo>)?
		// (<Object ID?>)*
		// </Signature>
		XMLSignature signature = fac.newXMLSignature(createSignedInfo(),
				KeyStoreManager.createKeyInfo(fac, certificateFile.getPath()),
				Collections.singletonList(obj), null, null);

		// Create a DOMSignContext and specify the DSA PrivateKey and
		// location of the resulting XMLSignature's parent element.
		Document doc = dbf.newDocumentBuilder().newDocument();
		DOMSignContext dsc = new DOMSignContext(keyPair.getPrivate(), doc);

		// Marshal, generate, and sign the enveloped signature.
		signature.sign(dsc);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(outputFile));
	}

	void validate(File signedFile) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document XML = dbf.newDocumentBuilder().parse(signedFile);

		NodeList nl = XML.getElementsByTagNameNS(XMLSignature.XMLNS,
				"Signature");

		if (nl.getLength() == 0) {
			throw new XMLSignatureException(
					"Unable to not find Signature element!");
		}

		DOMValidateContext valContext = new DOMValidateContext(
				keyPair.getPublic(), nl.item(0));

		XMLSignature signature = fac.unmarshalXMLSignature(valContext);

		boolean validity = signature.validate(valContext);

		System.out.println("Signature " + (validity ? "passed" : "failed")
				+ " core validation!");

		validity = signature.getSignatureValue().validate(valContext);

		System.out.println("Signature " + (validity ? "passed" : "failed")
				+ " cryptographic validation!");

		// Check the validation status of each Reference.
		@SuppressWarnings("unchecked")
		Iterator<Reference> it = signature.getSignedInfo().getReferences()
				.iterator();
		for (int i = 0; it.hasNext(); i++) {
			validity = it.next().validate(valContext);
			System.out.println("Reference[" + i + "] "
					+ (validity ? "passed" : "failed") + " validation!");
		}
	}

	private SignedInfo createSignedInfo() throws Exception {
		// <complexType name= "SignedInfoType">
		// <sequence>
		// <element ref="ds:CanonicalizationMethod"/>
		// <element ref="ds:SignatureMethod"/>
		// <element ref="ds:Reference" maxOccurs="unbounded"/>
		// </sequence>
		// <attribute name="Id" type="ID" use="optional"/>

		// public abstract SignatureMethod newSignatureMethod(String algorithm,
		// SignatureMethodParameterSpec params) throws NoSuchAlgorithmException,
		// InvalidAlgorithmParameterException

		// Must use the same signing algorithm that was used when creating the
		// keypair

		return fac.newSignedInfo(fac.newCanonicalizationMethod(
				CanonicalizationMethod.INCLUSIVE,
				(C14NMethodParameterSpec) null), fac.newSignatureMethod(
				SignatureMethod.DSA_SHA1, null), Collections
				.singletonList(createReference()));
	}

	private Reference createReference() throws Exception {
		// <complexType name= "ReferenceType">
		// <sequence>
		// <element ref="ds:Transforms" minOccurs="0"/>
		// <element ref="ds:DigestMethod"/>
		// <element ref="ds:DigestValue"/>
		// </sequence>
		// <attribute name="Id" type="ID" use="optional"/>
		// <attribute name="URI" type="anyURI" use="optional"/>
		// <attribute name="Type" type="anyURI" use="optional"/>

		// public abstract Reference newReference(String uri, DigestMethod dm,
		// List transforms, String type, String id)

		// URI "" means the document root
		return fac.newReference("#" + referenceURI, fac.newDigestMethod(
				DigestMethod.SHA1, null), Collections.singletonList(fac
				.newTransform(Transform.ENVELOPED,
						(TransformParameterSpec) null)), null, null);
	}

	private final XMLSignatureFactory fac;
	private final KeyPair keyPair;
	private final File certificateFile;
	private final String referenceURI;
}

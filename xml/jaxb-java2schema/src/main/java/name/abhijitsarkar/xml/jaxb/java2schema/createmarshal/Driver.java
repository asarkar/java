package name.abhijitsarkar.xml.jaxb.java2schema.createmarshal;

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import name.abhijitsarkar.xml.jaxb.java2schema.createmarshal.cardfile.Address;
import name.abhijitsarkar.xml.jaxb.java2schema.createmarshal.cardfile.BusinessCard;

import org.xml.sax.SAXException;

public class Driver {
	public static void main(String[] args) throws Exception {
		String packageName = BusinessCard.class.getPackage().getName();
		String codeDir = new File(BusinessCard.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath()).getParent();
		String pwd = codeDir + File.separator
				+ packageName.replaceAll("\\.", File.separator);

		// Illustrate two methods to create JAXBContext for j2s binding.
		// (1) by root classes newInstance(Class ...)
		JAXBContext context1 = JAXBContext.newInstance(BusinessCard.class);

		// (2) by package, requires jaxb.index file in package cardfile.
		// newInstance(String packageNames)
		JAXBContext context2 = JAXBContext.newInstance(packageName);

		Marshaller m = context1.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(getCard(), System.out);

		File f = new File(pwd);
		f.mkdirs();

		f = new File(f.getPath() + File.separator + "bcard.xml");

		OutputStream os = new FileOutputStream(f);
		XMLStreamWriter w = XMLOutputFactory.newInstance()
				.createXMLStreamWriter(os, "UTF-8");

		Marshaller m2 = context1.createMarshaller();
		// m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m2.marshal(getCard(), w);

		os.close();

		// illustrate optional unmarshal validation.
		Unmarshaller um = context2.createUnmarshaller();
		um.setSchema(getSchema("generated/schema1.xsd"));

		InputStream is = new FileInputStream(f);
		XMLStreamReader r = XMLInputFactory.newInstance()
				.createXMLStreamReader(is, "UTF-8");
		Object bce = um.unmarshal(r);
		m.marshal(bce, System.out);
	}

	static Schema getSchema(String schemaResourceName) throws SAXException {
		SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
		try {
			return sf.newSchema(Driver.class.getResource(schemaResourceName));
		} catch (SAXException se) {
			// this can only happen if there's a deployment error and the
			// resource is missing.
			throw se;
		}
	}

	private static BusinessCard getCard() {
		return new BusinessCard("John Doe", "Sr. Widget Designer",
				"Acme, Inc.", new Address(null, "123 Widget Way", "Anytown",
						"MA", (short) 12345), "123.456.7890", null,
				"123.456.7891", "John.Doe@Acme.ORG");
	}
}

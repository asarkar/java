package name.abhijitsarkar.learning.xml.jaxb.customize.external;

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

import java.math.BigDecimal;
import java.util.ListIterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import name.abhijitsarkar.learning.xml.jaxb.customize.external.generated.Items;
import name.abhijitsarkar.learning.xml.jaxb.customize.external.generated.Items.Item;
import name.abhijitsarkar.learning.xml.jaxb.customize.external.generated.ObjectFactory;
import name.abhijitsarkar.learning.xml.jaxb.customize.external.generated.POType;
import name.abhijitsarkar.learning.xml.jaxb.customize.external.generated.USAddress;
import name.abhijitsarkar.learning.xml.jaxb.customize.external.generated.USState;

public class Driver {

	// This sample application demonstrates how to modify a java content
	// tree and marshal it back to a xml data. This example demonstrates
	// customiation within the schema file, po.xsd, and the impact that these
	// customizations have on the schema derived Java representation.

	public static void main(String[] args) {
		try {
			// create a JAXBContext capable of handling classes generated into
			// the primer.po package
			JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class
					.getPackage().getName());

			// create an Unmarshaller
			Unmarshaller u = jc.createUnmarshaller();

			// unmarshal a po instance document into a tree of Java content
			// objects composed of classes from the primer.myPo package.
			StreamSource src = new StreamSource(
					Driver.class.getResourceAsStream("/po.xml"));
			JAXBElement<POType> poe = u.unmarshal(src, POType.class);
			POType po = poe.getValue();

			// change the billto address
			USAddress address = po.getBillTo();
			address.setToName("John Bob");
			address.setStreet("242 Driver Street");
			address.setCity("Beverly Hills");
			address.setState(USState.CA);
			address.setZipCode(90210);

			USState purchaseState = address.getState();
			ListIterator<Item> iter = po.getItems().getItem().listIterator();
			while (iter.hasNext()) {

				// update to 20% off sale price
				Items.Item item = (Items.Item) iter.next();
				item.setPrice(item.getPrice().multiply(new BigDecimal("0.80")));

				// Calculate sales tax for specific states
				if (purchaseState == USState.MA) {
					item.setPrice(item.getPrice().multiply(
							new BigDecimal("1.05")));
				} else if (purchaseState == USState.CA) {
					item.setPrice(item.getPrice().multiply(
							new BigDecimal("1.06")));
				}
				item.setPrice(item.getPrice()
						.setScale(2, BigDecimal.ROUND_DOWN));
			}

			// create a Marshaller and marshal to a file
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(poe, System.out);

		} catch (JAXBException je) {
			je.printStackTrace();
		}
	}
}

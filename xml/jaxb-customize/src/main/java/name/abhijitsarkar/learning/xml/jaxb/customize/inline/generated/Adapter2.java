//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.11.12 at 08:04:31 PM EST 
//


package name.abhijitsarkar.learning.xml.jaxb.customize.inline.generated;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import name.abhijitsarkar.learning.xml.jaxb.customize.inline.MyDatatypeConverter;

public class Adapter2
    extends XmlAdapter<String, Short>
{


    public Short unmarshal(String value) {
        return (MyDatatypeConverter.parseIntegerToShort(value));
    }

    public String marshal(Short value) {
        return (MyDatatypeConverter.printShortToInteger(value));
    }

}

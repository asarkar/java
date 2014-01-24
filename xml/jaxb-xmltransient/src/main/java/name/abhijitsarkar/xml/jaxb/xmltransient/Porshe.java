package name.abhijitsarkar.xml.jaxb.xmltransient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "porshe")
@XmlType(name = "porshe")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Porshe extends Car {
	@XmlTransient
	private String coolStuffFoundOnlyInAPorshe;

	public Porshe() {

	}

	public Porshe(String make, String model, int year) {
		super(make, model, year);
	}

	@XmlTransient
	@Override
	public String getMake() {
		return super.getMake();
	}

	@Override
	public String getModel() {
		return "porshe";
	}

	public String getCoolStuffFoundOnlyInAPorshe() {
		return coolStuffFoundOnlyInAPorshe;
	}

	public void setCoolStuffFoundOnlyInAPorshe(
			String coolStuffFoundOnlyInAPorshe) {
		this.coolStuffFoundOnlyInAPorshe = coolStuffFoundOnlyInAPorshe;
	}
}

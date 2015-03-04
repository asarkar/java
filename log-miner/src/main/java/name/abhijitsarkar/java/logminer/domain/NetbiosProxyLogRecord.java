package name.abhijitsarkar.java.logminer.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "rb")
public class NetbiosProxyLogRecord extends AbstractLogRecord {
    private static final long serialVersionUID = 4824800845216215757L;

    @Override
    public void readLine(String line) {
	throw new UnsupportedOperationException(
		"If I implement everything, what're you gonna do?");
    }
}

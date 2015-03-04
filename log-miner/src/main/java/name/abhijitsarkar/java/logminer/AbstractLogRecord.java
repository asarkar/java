package name.abhijitsarkar.java.logminer;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;

@Entity
@Table(name = "log")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class AbstractLogRecord implements SkippableLogRecord,
	Serializable {
    private static final long serialVersionUID = 4720873878287976746L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    private String jvm;
    private int day;
    private int month;
    private int year;
    private String root;
    private String filename;
    private String path;
    private int status;
    private long size;

    public AbstractLogRecord() {
	// JPA needs a no-arg constructor
    }

    public AbstractLogRecord(String line) {
	readLine(line.toString());
    }

    @Override
    public String getJvm() {
	return jvm;
    }

    @Override
    public int getDay() {
	return day;
    }

    @Override
    public int getMonth() {
	return month;
    }

    @Override
    public int getYear() {
	return year;
    }

    @Override
    public String getRoot() {
	return root;
    }

    @Override
    public String getFilename() {
	return filename;
    }

    @Override
    public String getPath() {
	return path;
    }

    @Override
    public int getStatus() {
	return status;
    }

    @Override
    public long getSize() {
	return size;
    }

    protected void setJvm(String jvm) {
	this.jvm = jvm;
    }

    protected void setDay(int day) {
	this.day = day;
    }

    protected void setMonth(int month) {
	this.month = month;
    }

    protected void setYear(int year) {
	this.year = year;
    }

    protected void setRoot(String root) {
	this.root = root;
    }

    protected void setFilename(String filename) {
	this.filename = filename;
    }

    protected void setPath(String path) {
	this.path = path;
    }

    protected void setStatus(int status) {
	this.status = status;
    }

    protected void setSize(long size) {
	this.size = size;
    }

    @Override
    public boolean isSkipped() {
	return jvm == null;
    }

    public abstract void readLine(String line);

    @Override
    public int hashCode() {
	return Objects.hash(path, day, month, year);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!AbstractLogRecord.class.isAssignableFrom(obj.getClass())) {
	    return false;
	}

	AbstractLogRecord other = (AbstractLogRecord) obj;

	return Objects.equals(path, other.path) && day == other.day
		&& month == other.month && year == other.year;
    }
}

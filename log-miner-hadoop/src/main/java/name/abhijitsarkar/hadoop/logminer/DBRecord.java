package name.abhijitsarkar.hadoop.logminer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

public class DBRecord implements Writable, DBWritable {
    private String jvm;
    private int day;
    private int month;
    private int year;
    private String root;
    private String filename;
    private String path;
    private int status;
    private long size;

    public DBRecord(SkippableLogRecord logRecord) {
	jvm = logRecord.getJvm().toString();
	day = logRecord.getDay().get();
	month = logRecord.getMonth().get();
	year = logRecord.getYear().get();
	root = logRecord.getRoot().toString();
	filename = logRecord.getFilename().toString();
	path = logRecord.getPath().toString();
	status = logRecord.getStatus().get();
	size = logRecord.getSize().get();
    }

    @Override
    public void readFields(ResultSet rs) throws SQLException {
	jvm = rs.getString("jvm");
	day = rs.getInt("day");
	month = rs.getInt("month");
	year = rs.getInt("year");
	root = rs.getString("root");
	filename = rs.getString("filename");
	path = rs.getString("path");
	status = rs.getInt("status");
	size = rs.getLong("size");
    }

    @Override
    public void write(PreparedStatement ps) throws SQLException {
	ps.setString(1, jvm);
	ps.setInt(2, day);
	ps.setInt(3, month);
	ps.setInt(4, year);
	ps.setString(5, root);
	ps.setString(6, filename);
	ps.setString(7, path);
	ps.setInt(8, status);
	ps.setLong(9, size);
    }

    @Override
    public void write(DataOutput out) throws IOException {
	// TODO Auto-generated method stub
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	// TODO Auto-generated method stub
    }
}

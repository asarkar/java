package name.abhijitsarkar.java.logminer;

public interface SkippableLogRecord {

    public void readLine(String line);

    public String getJvm();

    public int getDay();

    public int getMonth();

    public int getYear();

    public String getRoot();

    public String getFilename();

    public String getPath();

    public int getStatus();

    public long getSize();
    
    public boolean isSkipped();
}

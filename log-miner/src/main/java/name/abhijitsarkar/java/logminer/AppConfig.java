package name.abhijitsarkar.java.logminer;

import name.abhijitsarkar.java.logminer.domain.NetbiosProxyLogRecord;
import name.abhijitsarkar.java.logminer.domain.ReverseProxyLogRecord;
import name.abhijitsarkar.java.logminer.domain.SkippableLogRecord;
import name.abhijitsarkar.java.logminer.repo.LogMiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${type}")
    private String logRecordType;

    @Bean
    public LogMiner miner() {
	return new LogMiner(getLogRecordType(logRecordType));
    }

    private Class<? extends SkippableLogRecord> getLogRecordType(
	    String logRecordType) {
	switch (logRecordType) {
	case "rp":
	    return ReverseProxyLogRecord.class;
	case "nbp":
	    return NetbiosProxyLogRecord.class;
	}

	throw new IllegalArgumentException(
		"Log record type can only be 'rp' or 'nbp'.");
    }
}

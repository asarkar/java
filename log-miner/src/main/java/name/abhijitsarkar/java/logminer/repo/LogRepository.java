package name.abhijitsarkar.java.logminer.repo;

import name.abhijitsarkar.java.logminer.domain.AbstractLogRecord;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository<T extends AbstractLogRecord> extends
	JpaRepository<T, Long> {

}

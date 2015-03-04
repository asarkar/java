package name.abhijitsarkar.java.logminer.repo;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.lines;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import name.abhijitsarkar.java.logminer.domain.SkippableLogRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LogMiner {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(LogMiner.class);

    @SuppressWarnings("rawtypes")
    @Autowired
    private LogRepository logRepo;

    private final Class<? extends SkippableLogRecord> clazz;

    public LogMiner() {
	this(null);
    }

    public LogMiner(Class<? extends SkippableLogRecord> clazz) {
	this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public boolean mine(Path path) {
	boolean isSuccessful = true;

	try (Stream<String> s = lines(path, UTF_8)) {
	    s.parallel().map(this::newLogInstance)
		    .filter(rec -> !rec.isSkipped()).forEach(logRepo::save);
	} catch (IOException e) {
	    LOGGER.error("There was an error processing path: {}.", path);

	    isSuccessful = false;
	}

	return isSuccessful;
    }

    private SkippableLogRecord newLogInstance(String line) {
	try {
	    return clazz.getConstructor(String.class).newInstance(line);
	} catch (ReflectiveOperationException e) {
	    throw new RuntimeException("There was an error instantiating type "
		    + clazz.getName());
	}
    }
}

package name.abhijitsarkar.java.logminer;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.lines;
import static javax.persistence.Persistence.createEntityManagerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogMiner {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(LogMiner.class);

    private final EntityManagerFactory emf;
    private final EntityManager em;

    private final Class<? extends SkippableLogRecord> clazz;

    public LogMiner(Class<? extends SkippableLogRecord> clazz) {
	emf = createEntityManagerFactory("log-miner-pu");
	em = emf.createEntityManager();

	this.clazz = clazz;
    }

    public boolean mine(Path path) {
	EntityTransaction tx = em.getTransaction();
	tx.begin();

	boolean isSuccessful = true;

	try (Stream<String> s = lines(path, UTF_8)) {
	    s.parallel().map(this::newLogInstance)
		    .filter(rec -> !rec.isSkipped()).forEach(this::persist);
	} catch (IOException e) {
	    LOGGER.error("There was an error processing path: {}.", path);

	    isSuccessful = false;
	} finally {
	    tx.commit();
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

    private <T extends SkippableLogRecord> void persist(T rec) {
	LOGGER.debug("Persisting {}.", rec.getPath());

	em.persist(rec);
    }
}

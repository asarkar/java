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
    private static final Logger LOGGER = LoggerFactory.getLogger(LogMiner.class);

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public LogMiner() {
	emf = createEntityManagerFactory("log-miner-pu");
	em = emf.createEntityManager();
    }

    public void mine(Path path) {
	EntityTransaction tx = em.getTransaction();

	tx.begin();

	try (Stream<String> s = lines(path, UTF_8)) {
	    s.parallel().map(SkippableLogRecord::new)
		    .filter(rec -> !rec.isSkipped()).forEach(this::persist);
	} catch (IOException e) {
	    LOGGER.error("There was an error processing path: {}.", path);
	} finally {
	    tx.commit();
	}
    }

    private void persist(SkippableLogRecord rec) {
	LOGGER.debug("Persisting {}.", rec.getPath());

	em.persist(rec);
    }
}

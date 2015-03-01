package name.abhijitsarkar.java.logminer;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import name.abhijitsarkar.java.logminer.SkippableLogRecord;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SkippableLogRecordIntegrationTest {
    private static String LINE;

    private static EntityManagerFactory emf;
    private static EntityManager em;

    private EntityTransaction tx;

    @BeforeClass
    public static void init() throws IOException, URISyntaxException {
	emf = Persistence.createEntityManagerFactory("test-pu");
	em = emf.createEntityManager();

	Path logFile = get(SkippableLogRecordIntegrationTest.class.getResource(
		"/access.log").toURI());

	LINE = lines(logFile, UTF_8).findFirst().get();
    }

    @AfterClass
    public static void cleanUp() {
	em.close();
	emf.close();
    }

    @Before
    public void startTransaction() {
	tx = em.getTransaction();

	tx.begin();
    }

    @After
    public void endTransaction() {
	tx.rollback();
    }

    @Test
    public void testInsert() {
	SkippableLogRecord rec = new SkippableLogRecord(LINE);

	assertFalse(rec.isSkipped());

	em.persist(rec);

	/* Need active transaction in order to flush */
	em.flush();

	CriteriaBuilder cb = em.getCriteriaBuilder();

	CriteriaQuery<SkippableLogRecord> cq = cb
		.createQuery(SkippableLogRecord.class);
	Root<SkippableLogRecord> e = cq.from(SkippableLogRecord.class);
	cq.where(cb.equal(e.get("root"), "PersonalLines"));

	rec = em.createQuery(cq).getSingleResult();

	assertNotNull(rec);
	assertEquals("startquote.cfm?ratingAction=yourQuoteAndFeature",
		rec.getFilename());
    }
}

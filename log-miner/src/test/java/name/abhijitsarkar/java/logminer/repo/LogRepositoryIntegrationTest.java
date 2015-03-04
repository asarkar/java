package name.abhijitsarkar.java.logminer.repo;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.annotation.PostConstruct;

import name.abhijitsarkar.java.logminer.LogMinerApp;
import name.abhijitsarkar.java.logminer.domain.ReverseProxyLogRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LogMinerApp.class)
@ActiveProfiles("test")
public class LogRepositoryIntegrationTest {
    private String line;

    @Autowired
    private LogRepository<ReverseProxyLogRecord> repo;

    @Value("classpath:/rp/rp.txt")
    private Resource logFile;

    @PostConstruct
    public void init() throws IOException {
	assertNotNull(logFile);
	line = lines(get(logFile.getURI()), UTF_8).findFirst().get();
    }

    @Test
    public void testInsert() {
	ReverseProxyLogRecord rec = new ReverseProxyLogRecord(line);

	assertFalse(rec.isSkipped());

	repo.save(rec);

	repo.findByRoot("PersonalLines").stream()
		.filter(r -> "PersonalLines".equals(r.getRoot())).findFirst()
		.get();
    }
}

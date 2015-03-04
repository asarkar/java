package name.abhijitsarkar.java.logminer;

import static java.nio.file.Files.find;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import name.abhijitsarkar.java.logminer.repo.LogMiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogMinerApp implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(LogMinerApp.class);

    @Autowired
    private LogMiner miner;

    @Value("${path}")
    private String path;

    public static void main(String[] args) throws InterruptedException {
	SpringApplication.run(LogMinerApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
	findAllFilesInDirectory(get(path)).forEach(miner::mine);
    }

    private Stream<Path> findAllFilesInDirectory(final Path path) {
	try {
	    return find(path, 1,
		    (p, fileAttributes) -> fileAttributes.isRegularFile());
	} catch (IOException e) {
	    LOGGER.error("There was an error processing path: {}.", path);

	    return Stream.empty();
	}
    }
}

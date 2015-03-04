package name.abhijitsarkar.java.logminer;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.find;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Driver.class);

    private final LogMiner miner;

    public Driver(String logRecordType) {
	miner = new LogMiner(getLogRecordType(logRecordType));
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

    public static void main(String[] args) {
	Path path = null;

	if (args.length < 2 || !exists(path = get(args[0]))
		|| !isDirectory(path)) {
	    System.err
		    .println("Usage: java -jar <jar-name> <log-directory> [rp|nbp]");
	} else {
	    new Driver(args[1]).run(path);
	}
    }

    private void run(Path path) {
	findAllFilesInDirectory(path).forEach(
		p -> ForkJoinTask.adapt(() -> miner.mine(p)).invoke());
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

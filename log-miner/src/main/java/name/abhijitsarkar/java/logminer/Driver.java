package name.abhijitsarkar.java.logminer;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.find;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Paths.get;
import static java.util.concurrent.ForkJoinPool.commonPool;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Driver.class);

    private final LogMiner miner;

    public Driver() {
	miner = new LogMiner();
    }

    public static void main(String[] args) {
	Path path = null;

	if (args.length == 0 || !exists(path = get(args[0]))
		|| !isDirectory(path)) {
	    System.err.println("Usage: java -jar <jar-name> <log-directory>");
	} else {
	    new Driver().run(path);
	}
    }

    private void run(Path path) {
	ForkJoinPool pool = commonPool();
	pool.awaitQuiescence(1, TimeUnit.HOURS);

	pool.invokeAll(findAllFilesInDirectory(path).map(Mine::new).collect(
		toList()));
    }

    private Stream<Path> findAllFilesInDirectory(final Path path) {
	try {
	    Stream<Path> files = find(path, 1,
		    (p, fileAttributes) -> fileAttributes.isRegularFile());

	    return files;
	} catch (IOException e) {
	    LOGGER.error("There was an error processing path: {}.", path);

	    return Stream.empty();
	}
    }

    private class Mine implements Callable<Void> {
	private final Path path;

	Mine(Path p) {
	    this.path = p;
	}

	@Override
	public Void call() throws Exception {
	    miner.mine(path);

	    return null;
	}
    }
}

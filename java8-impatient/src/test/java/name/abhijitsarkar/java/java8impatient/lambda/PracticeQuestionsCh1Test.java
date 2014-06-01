package name.abhijitsarkar.java.java8impatient.lambda;

import static name.abhijitsarkar.java.java8impatient.lambda.PracticeQuestionsCh1.andThen;
import static name.abhijitsarkar.java.java8impatient.lambda.PracticeQuestionsCh1.lambdaAndEnhancedFor;
import static name.abhijitsarkar.java.java8impatient.lambda.PracticeQuestionsCh1.sortFiles;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Test;

public class PracticeQuestionsCh1Test {

	@Test
	public void testListAllSubdirectoriesUsingLambda() {
		testListAllSubdirectories(PracticeQuestionsCh1::listAllSubdirectoriesUsingLambda, "/dir");
	}

	@Test
	public void testlistAllSubdirectoriesUsingMethodExpr() {
		testListAllSubdirectories(PracticeQuestionsCh1::listAllSubdirectoriesUsingMethodExpr, "/dir");
	}

	private void testListAllSubdirectories(Function<String, List<String>> func, String dir) {
		List<String> subdirectories = func.apply(dir);

		assertNotNull(subdirectories);
		assertEquals(2, subdirectories.size());

		Collections.sort(subdirectories);

		assertEquals("subdir1", subdirectories.get(0));
		assertEquals("subdir2", subdirectories.get(1));

		/* Ideally this should be a separate test */
		subdirectories = func.apply("junk");

		assertNotNull(subdirectories);

		assertTrue(subdirectories.isEmpty());
	}

	@Test
	public void testSortFiles() throws URISyntaxException {
		URL dirURL = PracticeQuestionsCh1.class.getResource("/dir");

		assertNotNull(dirURL);

		try (Stream<File> stream = listFilesRecursively(Paths.get(dirURL.toURI())).stream()) {
			File[] files = stream.toArray(File[]::new);

			assertEquals(4, files.length);

			sortFiles(files);

			assertEquals(4, files.length);

			assertEquals("subdir1", files[0].getName());
			assertEquals("subdir2", files[1].getName());
			assertEquals("f1.txt", files[2].getName());
			assertEquals("f2.txt", files[3].getName());
		}
	}

	private List<File> listFilesRecursively(Path dir) {
		List<File> files = new ArrayList<>();

		try (Stream<Path> paths = Files.list(dir)) {
			paths.forEach((path) -> {
				if (path.toFile().isDirectory()) {
					files.addAll(listFilesRecursively(path));
				}
				files.add(path.toFile());
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return files;
	}

	@Test
	public void testAndThen() throws IOException {
		String charset = StandardCharsets.UTF_8.name();

		PrintStream originalStdout = System.out;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try (PrintStream newStdout = new PrintStream(baos, true, charset)) {
			System.setOut(newStdout);

			Object o = andThen(() -> {
				System.out.print("Hello ");
			}, () -> {
				System.out.print("Abhijit");
			});

			assertTrue(o instanceof Runnable);

			((Runnable) o).run();

			assertEquals("Hello Abhijit", baos.toString(charset));
		} finally {
			System.setOut(originalStdout);
		}
	}

	@Test
	public void testLambdaAndEnhancedFor() {
		List<Runnable> runners = lambdaAndEnhancedFor();

		runners.forEach(Runnable::run);
	}
}

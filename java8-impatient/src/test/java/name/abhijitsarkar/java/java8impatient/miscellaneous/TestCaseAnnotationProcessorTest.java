package name.abhijitsarkar.java.java8impatient.miscellaneous;

import static java.util.Arrays.asList;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.abhijitsarkar.java.java8impatient.miscellaneous.TestCaseAnnotationProcessor.Pair;

import org.junit.Test;

public class TestCaseAnnotationProcessorTest {
    @Test
    public void testGenerateTestCasesRunner() {
	Map<String, List<Pair>> methodMap = new HashMap<>();

	methodMap.put("method1", asList(new Pair(1, 1)));
	methodMap.put("method2", asList(new Pair(2, 4), new Pair(4, 16)));

	PrintWriter out = new PrintWriter(System.out);

	new TestCaseAnnotationProcessor().generateTestCasesRunner(methodMap,
		out);
	out.flush();
    }
}

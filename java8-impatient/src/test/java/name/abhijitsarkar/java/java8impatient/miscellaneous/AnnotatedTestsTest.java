package name.abhijitsarkar.java.java8impatient.miscellaneous;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

public class AnnotatedTestsTest {
    @Test
    public void testSquare() {
	Map<Method, TestCase[]> methodMap = Stream
		.of(AnnotatedTests.class.getDeclaredMethods())
		.filter(method -> isTestCase(method))
		.collect(
			toMap(identity(), method -> method
				.getDeclaredAnnotationsByType(TestCase.class)));

	assertEquals(2, methodMap.size());

	methodMap.forEach((method, testCases) -> {
	    Stream.of(testCases).forEach(
		    testCase -> {
			System.out.println("Invoking method: "
				+ method.getName() + " with param: "
				+ testCase.param());

			try {
			    assertEquals(testCase.expected(),
				    method.invoke(null, testCase.param()));
			} catch (Exception e) {
			    fail(e.getMessage());
			}
		    });
	});
    }

    private boolean isTestCase(Method method) {
	/* Only byType methods can handle repeated annotations. */
	return method.getDeclaredAnnotationsByType(TestCase.class).length > 0;
    }
}

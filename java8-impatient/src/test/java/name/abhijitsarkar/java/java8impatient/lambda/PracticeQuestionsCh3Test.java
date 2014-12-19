package name.abhijitsarkar.java.java8impatient.lambda;

import static name.abhijitsarkar.java.java8impatient.lambda.PracticeQuestionsCh3.capitalize;
import static name.abhijitsarkar.java.java8impatient.lambda.PracticeQuestionsCh3.isLoggingEnabledFor;
import static name.abhijitsarkar.java.java8impatient.lambda.PracticeQuestionsCh3.logIf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PracticeQuestionsCh3Test {

    @Test
    public void testCapitalize() {
	assertEquals("Java", capitalize("java"));
	assertEquals("JAVA", capitalize("jAVA"));
    }

    @Test
    public void testIsLoggingEnabledFor() {
	assertTrue(isLoggingEnabledFor("error"));
    }

    @Test
    public void testLogIfWhenTrue() {
	final boolean isEnabled = isLoggingEnabledFor("error");

	assertEquals(isEnabled,
		logIf("error", () -> true, () -> "This should be logged."));
    }

    @Test
    public void testLogIfWhenFalse() {
	assertFalse(logIf("error", () -> false,
		() -> "This shouldn't be logged."));
    }
}

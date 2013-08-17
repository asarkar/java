package name.abhijitsarkar.learning.cascading.citation;

import java.util.regex.Pattern;

import name.abhijitsarkar.learning.cascading.citation.CitationCombiner;

import org.junit.Test;

public class InputFileFormatTest {
    @Test
    public void testInputFormat() {
        Pattern.matches(CitationCombiner.INPUT_FORMAT_REGEX, "3858241,956203");
    }
}

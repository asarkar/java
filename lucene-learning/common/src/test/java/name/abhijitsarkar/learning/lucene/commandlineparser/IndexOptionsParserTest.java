package name.abhijitsarkar.learning.lucene.commandlineparser;

import java.io.File;

import name.abhijitsarkar.learning.lucene.commandlineparser.IndexOptionsParser;

import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class IndexOptionsParserTest {
    @Test(expected = MissingOptionException.class)
    public void testNoArgs() throws ParseException {
        String[] args = new String[] {};
        IndexOptionsParser parser = new IndexOptionsParser(args);

        Assert.assertEquals("Wrong index directory path",
                new File(".").getPath(), parser.getIndexDir());
        Assert.assertNull("Wrong query string", parser.getDoc());
    }

    @Test
    public void testWithShortIndexAndDocArgs() throws ParseException {
        String[] args = new String[] { "-i",
                "learning/lucene/commandlineparser", "-d",
                "learning/lucene/commandlineparser" };
        IndexOptionsParser parser = new IndexOptionsParser(args);

        Assert.assertEquals("Wrong index directory path",
                "learning/lucene/commandlineparser", parser.getIndexDir());
        Assert.assertEquals("Wrong doc path",
                "learning/lucene/commandlineparser", parser.getDoc());
    }

    @Test
    public void testWithLongIndexAndDocArgs() throws ParseException {
        String[] args = new String[] { "-index",
                "learning/lucene/commandlineparser", "-doc",
                "learning/lucene/commandlineparser" };
        IndexOptionsParser parser = new IndexOptionsParser(args);

        Assert.assertEquals("Wrong index directory path",
                "learning/lucene/commandlineparser", parser.getIndexDir());
        Assert.assertEquals("Wrong doc path",
                "learning/lucene/commandlineparser", parser.getDoc());
    }
}

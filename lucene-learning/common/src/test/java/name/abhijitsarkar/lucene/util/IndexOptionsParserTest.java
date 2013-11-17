package name.abhijitsarkar.lucene.util;

import java.io.File;

import name.abhijitsarkar.lucene.util.IndexOptionsParser;

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
                "/lucene/commandlineparser", "-d",
                "/lucene/commandlineparser" };
        IndexOptionsParser parser = new IndexOptionsParser(args);

        Assert.assertEquals("Wrong index directory path",
                "/lucene/commandlineparser", parser.getIndexDir());
        Assert.assertEquals("Wrong doc path",
                "/lucene/commandlineparser", parser.getDoc());
    }

    @Test
    public void testWithLongIndexAndDocArgs() throws ParseException {
        String[] args = new String[] { "-index",
                "/lucene/commandlineparser", "-doc",
                "/lucene/commandlineparser" };
        IndexOptionsParser parser = new IndexOptionsParser(args);

        Assert.assertEquals("Wrong index directory path",
                "/lucene/commandlineparser", parser.getIndexDir());
        Assert.assertEquals("Wrong doc path",
                "/lucene/commandlineparser", parser.getDoc());
    }
}

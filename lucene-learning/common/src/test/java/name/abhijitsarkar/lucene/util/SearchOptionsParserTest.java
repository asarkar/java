package name.abhijitsarkar.lucene.util;

import java.io.File;

import name.abhijitsarkar.lucene.util.SearchOptionsParser;

import org.apache.commons.cli.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class SearchOptionsParserTest {
	@Test
	public void testNoArgs() throws ParseException {
		String[] args = new String[] { "-q", "some query" };
		SearchOptionsParser parser = new SearchOptionsParser(args);

		Assert.assertEquals("Wrong index directory path",
				new File(".").getPath(), parser.getIndexDir());
		Assert.assertEquals("Wrong query string", "some query",
				parser.getQueryStr());
	}

	@Test
	public void testWithShortIndexArg() throws ParseException {
		String[] args = new String[] { "-i",
				"/lucene/commandlineparser", "-q", "some query" };
		SearchOptionsParser parser = new SearchOptionsParser(args);

		Assert.assertEquals("Wrong index directory path",
				"/lucene/commandlineparser", parser.getIndexDir());
		Assert.assertEquals("Wrong query string", "some query",
				parser.getQueryStr());
	}

	@Test
	public void testWithLongIndexArg() throws ParseException {
		String[] args = new String[] { "-index",
				"/lucene/commandlineparser", "-q", "some query" };
		SearchOptionsParser parser = new SearchOptionsParser(args);

		Assert.assertEquals("Wrong index directory path",
				"/lucene/commandlineparser", parser.getIndexDir());
		Assert.assertEquals("Wrong query string", "some query",
				parser.getQueryStr());
	}

	@Test
	public void testWithShortQueryArg() throws ParseException {
		String[] args = new String[] { "-q", "some query" };
		SearchOptionsParser parser = new SearchOptionsParser(args);

		Assert.assertEquals("Wrong index directory path",
				new File(".").getPath(), parser.getIndexDir());
		Assert.assertEquals("Wrong query string", "some query",
				parser.getQueryStr());
	}

	@Test
	public void testWithLongQueryArg() throws ParseException {
		String[] args = new String[] { "-query", "some query" };
		SearchOptionsParser parser = new SearchOptionsParser(args);

		Assert.assertEquals("Wrong index directory path",
				new File(".").getPath(), parser.getIndexDir());
		Assert.assertEquals("Wrong query string", "some query",
				parser.getQueryStr());
	}

	@Test
	public void testWithAllArgs() throws ParseException {
		String[] args = new String[] { "-i",
				"/lucene/commandlineparser", "-query", "some query" };
		SearchOptionsParser parser = new SearchOptionsParser(args);

		Assert.assertEquals("Wrong index directory path",
				"/lucene/commandlineparser", parser.getIndexDir());
		Assert.assertEquals("Wrong query string", "some query",
				parser.getQueryStr());
	}

	@Test(expected = ParseException.class)
	public void testWithWrongArg() throws ParseException {
		String[] args = new String[] { "-unknown" };
		SearchOptionsParser parser = new SearchOptionsParser(args);

		Assert.assertEquals("Wrong index directory path",
				new File(".").getPath(), parser.getIndexDir());
		Assert.assertNull("Wrong query string", parser.getQueryStr());
	}
}

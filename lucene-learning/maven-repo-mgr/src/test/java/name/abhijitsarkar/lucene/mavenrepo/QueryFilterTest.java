package name.abhijitsarkar.lucene.mavenrepo;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.sandbox.queries.regex.RegexQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueryFilterTest {
	private StandardQueryParser qpHelper;
	private IndexSearcher searcher;
	private final int hitsPerPage = 10;

	@Before
	public void setUp() throws IOException {
		Directory indexDir = new RAMDirectory();
		final IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40,
				new StandardAnalyzer(Version.LUCENE_40));
		IndexWriter writer = new IndexWriter(indexDir, iwc);
		Document doc = new Document();
		doc.add(new StringField("path",
				"/Users/Abhijit/Repositories/maven/name/abhijitsarkar"
						+ "/lucene/lucene-common/1.0-SNAPSHOT"
						+ "/lucene-common-1.0-SNAPSHOT.pom", Field.Store.YES));
		doc.add(new TextField(
				"content",
				"<groupId>name.abhijitsarkar.lucene</groupId><artifactId>lucene-common</artifactId>",
				Field.Store.NO));
		writer.addDocument(doc);
		writer.close();

		IndexReader reader = DirectoryReader.open(indexDir);
		searcher = new IndexSearcher(reader);

		qpHelper = new StandardQueryParser(new StandardAnalyzer(
				Version.LUCENE_40));
	}

	@Test
	public void testSimpleQuery() throws QueryNodeException, IOException {
		Query query = qpHelper.parse("lucene", "content");
		TopDocs results = searcher.search(query, hitsPerPage);

		Assert.assertEquals("There should be only 1 hit", 1, results.totalHits);
	}

	@Test
	public void testRegexQueryFilterHit() throws QueryNodeException,
			IOException {
		String queryStr = "lucene";
		Query query = qpHelper.parse(queryStr, "content");
		final Query pathQuery = new RegexQuery(new Term("path", "(/.+)+"
				+ queryStr + "(/.+)+"));
		final Filter filter = new QueryWrapperFilter(pathQuery);
		TopDocs results = searcher.search(query, filter, hitsPerPage);

		Assert.assertEquals("There should be only 1 hit", 1, results.totalHits);
	}

	@Test
	public void testRegexQueryFilterNoHit() throws QueryNodeException,
			IOException {
		String queryStr = "something";
		Query query = qpHelper.parse(queryStr, "content");
		final Query pathQuery = new RegexQuery(new Term("path", "(/.+)+"
				+ queryStr + "(/.+)+"));
		final Filter filter = new QueryWrapperFilter(pathQuery);
		TopDocs results = searcher.search(query, filter, hitsPerPage);

		Assert.assertEquals("There shouldn't be any hit", 0, results.totalHits);
	}
}

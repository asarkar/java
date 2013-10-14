package name.abhijitsarkar.learning.lucene.scoring;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogSimilarityTest {
	private static final String[] bondCliches = new String[] {
			"My name is Bond", "My name is Bond, James Bond",
			"James Bond's code name is 007" };
	private static final String CONTENT_FIELD = "content";
	private static final int NUM_RESULTS = 10;

	private static IndexSearcher searcher;
	private static StandardQueryParser queryParser;

	@BeforeClass
	public static void oneTimeSetUp() throws IOException {
		Analyzer analyzer = new EnglishAnalyzer(Version.LUCENE_40);
		Similarity similarity = new LogSimilarity();

		Directory indexDir = new RAMDirectory();
		final IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40,
				analyzer);
		iwc.setSimilarity(similarity);
		IndexWriter writer = new IndexWriter(indexDir, iwc);

		for (String cliche : bondCliches) {
			Document doc = new Document();
			doc.add(new TextField(CONTENT_FIELD, cliche, Store.NO));
			writer.addDocument(doc);
		}

		writer.close();

		IndexReader reader = DirectoryReader.open(indexDir);
		searcher = new IndexSearcher(reader);
		searcher.setSimilarity(similarity);

		queryParser = new StandardQueryParser(analyzer);
	}

	@AfterClass
	public static void oneTimeCleanUp() throws IOException {
		searcher.getIndexReader().close();
	}

	@Test
	public void testTermQuery() throws IOException, QueryNodeException {
		Query query = queryParser.parse("bond", CONTENT_FIELD);
		float queryBoost = 1.0f;
		float precision = 10 ^ 6;

		Assert.assertTrue("Wrong query type", query instanceof TermQuery);

		Assert.assertEquals("Wrong query boost", queryBoost, query.getBoost(),
				queryBoost / precision);

		TopDocs topDocs = searcher.search(query, NUM_RESULTS);

		ScoreDoc[] scoreDocs = topDocs.scoreDocs;

		Assert.assertEquals("Wrong number of documents that hit", 3,
				scoreDocs.length);

		/*
		 * By default, Lucene sorts by descending score so 2nd indexed doc is
		 * 1st
		 */
		Assert.assertEquals("Wrong score for document 1", 1.69,
				scoreDocs[0].score, 1.69 / precision);

		Assert.assertEquals("Wrong score for document 3", 1.0,
				scoreDocs[1].score, 1.0 / precision);

		Assert.assertEquals("Wrong score for document 3", 1.0,
				scoreDocs[2].score, 1.0 / precision);
	}

	@Test
	public void testTermQueryWithBoost() throws IOException, QueryNodeException {
		Query query = queryParser.parse("bond^2.0", CONTENT_FIELD);
		float queryBoost = 2.0f;
		float precision = 10 ^ 6;

		Assert.assertTrue("Wrong query type", query instanceof TermQuery);

		Assert.assertEquals("Wrong query boost", queryBoost, query.getBoost(),
				queryBoost / precision);

		TopDocs topDocs = searcher.search(query, NUM_RESULTS);

		ScoreDoc[] scoreDocs = topDocs.scoreDocs;

		Assert.assertEquals("Wrong number of documents that hit", 3,
				scoreDocs.length);

		/*
		 * By default, Lucene sorts by descending score so 2nd indexed doc is
		 * 1st
		 */
		Assert.assertEquals("Wrong score for document 1", 3.39,
				scoreDocs[0].score, 3.39 / precision);

		Assert.assertEquals("Wrong score for document 3", 2.0,
				scoreDocs[1].score, 2.0 / precision);

		Assert.assertEquals("Wrong score for document 3", 2.0,
				scoreDocs[2].score, 2.0 / precision);
	}
}
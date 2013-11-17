package name.abhijitsarkar.lucene.mavenrepo;

import java.io.IOException;

import name.abhijitsarkar.lucene.util.SearchOptionsParser;
import name.abhijitsarkar.lucene.util.SearchUtil;

import org.apache.commons.cli.ParseException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.sandbox.queries.regex.RegexQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.util.Version;

/**
 * This class, as the name suggests, searches the Lucene index. It uses
 * {@link SearchOptionsParser SearchOptionsParser} for parsing command line
 * arguments.
 * 
 */
public class SearcherUsingFilter {
	public static final void main(String[] args) throws ParseException,
			IOException, org.apache.lucene.queryparser.classic.ParseException,
			QueryNodeException {
		new SearcherUsingFilter().search(args);
	}

	private void search(String[] args) throws ParseException, IOException,
			org.apache.lucene.queryparser.classic.ParseException,
			QueryNodeException {
		final SearchOptionsParser parser = new SearchOptionsParser(args);
		final String indexDir = parser.getIndexDir();
		/* The default search field is 'content' */
		final String field = (parser.getField() != null ? parser.getField()
				: "content");
		final String queryStr = parser.getQueryStr();

		final IndexReader reader = SearchUtil.getFSIndexReader(indexDir);
		final IndexSearcher searcher = new IndexSearcher(reader);

		/* Using the new, recommended parser */
		StandardQueryParser qpHelper = new StandardQueryParser(
				new StandardAnalyzer(Version.LUCENE_40));
		// config.setAllowLeadingWildcard(true);
		Query query = qpHelper.parse(queryStr, field);

		final Query pathQuery = new RegexQuery(new Term("path", "(/.+)+"
				+ query + "(/.+)+"));
		final Filter filter = new QueryWrapperFilter(pathQuery);

		SearchUtil.searchDocs(searcher, filter, query, 10);
		reader.close();
	}
}

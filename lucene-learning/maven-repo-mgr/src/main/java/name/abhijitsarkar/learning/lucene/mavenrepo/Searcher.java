package name.abhijitsarkar.learning.lucene.mavenrepo;

import java.io.Console;
import java.io.File;
import java.io.IOException;

import name.abhijitsarkar.learning.lucene.commandlineparser.SearchOptionsParser;
import name.abhijitsarkar.learning.lucene.pagination.Window;

import org.apache.commons.cli.ParseException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * This class, as the name suggests, searches the Lucene index. It uses
 * {@link SearchOptionsParser SearchOptionsParser} for parsing command line
 * arguments. It also uses {@link Window Window} for paginating the search
 * results.
 * 
 */
public class Searcher {
    public static final void main(String[] args) throws ParseException,
            IOException, org.apache.lucene.queryparser.classic.ParseException,
            QueryNodeException {
        new Searcher().search(args);
    }

    private void search(String[] args) throws ParseException, IOException,
            org.apache.lucene.queryparser.classic.ParseException,
            QueryNodeException {
        final SearchOptionsParser parser = new SearchOptionsParser(args);
        final File indexDir = new File(parser.getIndexDir());
        /* The default search field is 'content' */
        final String field = (parser.getField() != null ? parser.getField()
                : "content");
        final String queryStr = parser.getQueryStr();

        final IndexReader reader = DirectoryReader.open(FSDirectory
                .open(indexDir));
        final IndexSearcher searcher = new IndexSearcher(reader);

        /* Using the new, recommended parser */
        StandardQueryParser qpHelper = new StandardQueryParser(
                new StandardAnalyzer(Version.LUCENE_40));
        // config.setAllowLeadingWildcard(true);
        Query query = qpHelper.parse(queryStr, field);

        searchDocs(searcher, query);
        reader.close();
    }

    private void searchDocs(IndexSearcher searcher, Query query)
            throws IOException {
        final int hitsPerPage = 10;

        TopDocs results = searcher.search(query, hitsPerPage);

        ScoreDoc[] hits = results.scoreDocs;

        final int numTotalHits = results.totalHits;
        System.out.println("Total " + numTotalHits
                + " result(s) found for query: " + query);

        if (numTotalHits == 0) {
            return;
        }

        final Console console = System.console();
        String input = null;

        /*
         * Create a window that'll slide left and right to provide pagination.
         * Don't bother sliding it now, the Constructor takes care of that.
         */
        final Window window = new Window(hitsPerPage, hits.length);
        /*
         * Now that the window is created and initially, correctly, positioned
         * by the Constructor, display the results that fall within the window.
         * Displaying the window always follows sliding.
         */
        window.displayResults(searcher, hits);

        search: while (true) {
            System.out.println("\n\nAt anytime, press 'p' to see previous "
                    + hitsPerPage + " results and 'n' to see the next "
                    + hitsPerPage + ". Press any other key to quit.");
            input = console.readLine();

            if (input == null || input.trim().isEmpty()) {
                break;
            }

            switch (input.toLowerCase().charAt(0)) {
            case 'n':
                /*
                 * User wants to go to the next page but we haven't fetched the
                 * results yet. Go fetch the results. Also set the window
                 * sliding area to the width of the new results.
                 */
                if (hits.length < numTotalHits) {
                    results = searcher.search(query,
                            Math.min(numTotalHits, hits.length + hitsPerPage));
                    hits = results.scoreDocs;
                    window.setMaxSlidingArea(hits.length);
                }

                /*
                 * Search or not, user wants to go to the next page so slide the
                 * window to the right
                 */
                window.slideRight();
                window.displayResults(searcher, hits);

                break;
            case 'p':
                /*
                 * User wants to go to the previous page so slide the window to
                 * the right
                 */
                window.slideLeft();
                window.displayResults(searcher, hits);

                break;
            case 'q':
                break search;
            default:
                break search;
            }
        }
    }
}

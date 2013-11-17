package name.abhijitsarkar.lucene.util;

import java.io.Console;
import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SearchUtil {
	public static IndexReader getFSIndexReader(final String indexDir)
			throws IOException {
		return DirectoryReader.open(FSDirectory.open(new File(indexDir)));
	}

	public static void searchDocs(final IndexSearcher searcher,
			final Filter filter, final Query query, final int hitsPerPage)
			throws IOException {
		TopDocs results = searcher.search(query, filter, hitsPerPage);

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
					results = searcher.search(query, filter,
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

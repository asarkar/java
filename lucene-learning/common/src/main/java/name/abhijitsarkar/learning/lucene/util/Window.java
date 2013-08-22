package name.abhijitsarkar.learning.lucene.util;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;

/**
 * This class provides pagination support to to any class that does Lucene
 * searches. Lucene does not have a built-in pagination support and it is left
 * to the client code. Lucene, however, does have support for fetching the
 * results in installments where each subsequent installment also contains the
 * records from the previous one; that is where this class comes in. It acts as
 * a sliding window over the set of all results fetched up to that point. Only
 * the results that fall within the window are going to be visible.
 * 
 * Calling code uses the {@link #slideLeft() slideLeft} and
 * {@link #slideRight() slideRight} methods to slide the window. The results,
 * however, are not displayed on the console until a call is made to the
 * {@link #displayResults(IndexSearcher, ScoreDoc[]) displayResults} method.
 * 
 * <STRONG>Note that it is the callers responsibility to set the
 * {@link #maxSlidingArea maxSlidingArea} correctly each time it changes, which
 * is usually each time a new search is made</STRONG>
 * 
 */
public class Window {
    private final int windowSize;
    private int leftIndex;
    private int rightIndex;
    private int maxSlidingArea;

    public Window(final int windowSize, final int maxSlidingArea) {
        this.windowSize = windowSize;
        this.maxSlidingArea = maxSlidingArea;

        /*
         * Set up the initial position such that when slid right, the window
         * would encompass the first 'windowSize' elements.
         */
        leftIndex = -windowSize;
        rightIndex = 0;

        slideRight();
    }

    /**
     * As the name suggests, this method "attempts to" slides the window to the
     * right by the predefined constant {@link #windowSize windowSize}. It
     * "attempts to" because if sliding causes the window to go over the
     * {@link #maxSlidingArea maxSlidingArea}, it does not slide the full
     * {@link #windowSize windowSize} but adjusts accordingly
     */
    public void slideRight() {
        /*
         * If sliding right maintains the right index within the limit, we're
         * good
         */
        if ((rightIndex + windowSize) < maxSlidingArea) {
            rightIndex += windowSize;
            leftIndex = rightIndex - windowSize + 1;
        }
        /*
         * Sliding right causes right index to go over over the limit, but left
         * could be still in
         */
        else if ((leftIndex + windowSize) <= maxSlidingArea) {
            leftIndex += windowSize;
            rightIndex = maxSlidingArea - 1;
        }
        /*
         * Sliding right causes the window to go over the limits, throw up the
         * hands
         */
        else {
            System.out.println("Can't slide to right anymore. "
                    + "No more elements.");
        }
    }

    /**
     * As the name suggests, this method "attempts to" slides the window to the
     * left by the predefined constant {@link #windowSize windowSize}. It
     * "attempts to" because if sliding causes the window to go over the
     * {@link #maxSlidingArea maxSlidingArea}, it does not slide the full
     * {@link #windowSize windowSize} but adjusts accordingly
     */
    public void slideLeft() {
        /*
         * If sliding left maintains the left index within the limit, we're good
         */
        if ((leftIndex - windowSize) >= 0) {
            leftIndex -= windowSize;
            rightIndex = leftIndex + windowSize - 1;
        }
        /*
         * Sliding left causes left index to go over over the limit, but right
         * could be still in
         */
        else if ((rightIndex - windowSize) >= 0) {
            leftIndex = 0;
            rightIndex -= windowSize;
        }
        /*
         * Sliding left causes the window to go over the limits, throw up the
         * hands
         */
        else {
            System.out.println("Can't slide to left anymore. "
                    + "No more elements.");
        }
    }

    /**
     * This method display on console all the results that fall within the
     * window
     * 
     * @param searcher
     *            Lucene IndexSearcher
     * @param hits
     *            Number of hits from the last search
     * @throws IOException
     */
    public void displayResults(final IndexSearcher searcher,
            final ScoreDoc[] hits) throws IOException {

        System.out.println("\nResults " + (leftIndex + 1) + " through "
                + (rightIndex + 1) + " are displayed.\n");

        Document doc = null;
        String path = null;
        String content = null;

        System.out.printf("%-120s %-6s\n", "Path", "Score");
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < 127; i++) {
            padding.append('*');
        }
        System.out.printf("%-127s\n", padding);

        for (int counter = leftIndex; counter <= rightIndex; counter++) {
            doc = searcher.doc(hits[counter].doc);
            path = doc.get("path");
            if (path != null) {
                System.out.printf("%-120s %-2.4f\n", path, hits[counter].score);
                content = doc.get("content");
                if (content != null) {
                    System.out.println("Content: " + content);
                }
            } else {
                System.out.println("No information found for this document");
            }
        }
    }

    public int getWindowSize() {
        return windowSize;
    }

    public int getLeftIndex() {
        return leftIndex;
    }

    public int getRightIndex() {
        return rightIndex;
    }

    public int getMaxSlidingArea() {
        return maxSlidingArea;
    }

    public void setMaxSlidingArea(int maxSlidingArea) {
        this.maxSlidingArea = maxSlidingArea;
    }
}

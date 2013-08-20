package name.abhijitsarkar.learning.lucene.mavenrepo;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

/**
 * This class splits the incoming TokenStream by whitespace.
 * 
 */
public class WhitespaceFilter extends TokenFilter {
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private final PositionIncrementAttribute posIncAttr = addAttribute(PositionIncrementAttribute.class);
    private final Queue<String> queue;
    private int offset;

    public WhitespaceFilter(TokenStream in) {
        super(in);
        queue = new ArrayDeque<String>();
        offset = 0;
    }

    @Override
    public final boolean incrementToken() throws IOException {
        /*
         * The flow would not reach the input.incrementToken() call until the
         * queue is empty. If you're going to inject terms in the TokenStream,
         * you need some kind of buffering, like the queue here
         */
        if (!queue.isEmpty()) {
            removeFromQueue();
            return true;
        }
        if (!input.incrementToken()) {
            return false;
        }

        final String[] tokens = termAtt.toString().split("\\s");
        /*
         * Adding to the queue and immediately removing may seem odd but
         * remember, the input term is split into multiple terms. If the top
         * term is not returned, the current term value is going to be returned,
         * followed by the return of the split terms in subsequent calls. We
         * don't want that to happen.
         */
        addToQueue(tokens);

        removeFromQueue();

        return true;
    }

    private void addToQueue(String[] tokens) {
        for (String token : tokens) {
            queue.add(token.trim());
        }
    }

    /*
     * Remove the first element from the queue and set that as the value if the
     * term attribute. Anytime a term attribute value is changed, the offset
     * need to be adjusted too
     */
    private void removeFromQueue() {
        final String token = queue.remove();
        termAtt.setEmpty().append(token);
        offsetAtt.setOffset(offset, offset + termAtt.length());
        offset = offsetAtt.endOffset() + posIncAttr.getPositionIncrement();
    }

    @Override
    public void reset() throws IOException {
        offset = 0;
        // input.reset();
    }
}

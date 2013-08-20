package name.abhijitsarkar.learning.lucene.mavenrepo;

import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;

/**
 * This class fishes out the groupId and artifactId out of the input stream and
 * then concatenates them using a whitespace
 * 
 */
public class MavenPOMTokenizer extends Tokenizer {
    private static final String MAVEN_TYPE = "MAVEN";

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private final TypeAttribute typeAttr = addAttribute(TypeAttribute.class);
    private final PositionIncrementAttribute posIncAttr = addAttribute(PositionIncrementAttribute.class);
    private final String[] stopWords;

    private Pattern pattern = null;
    private Matcher matcher = null;

    private final StringBuilder str = new StringBuilder();
    private StringBuilder regex = null;

    private int offset = 0;
    private int i = 0;

    public MavenPOMTokenizer(Version matchVersion, Reader input,
            String[] stopWords) {
        super(input);
        this.stopWords = stopWords;
        try {
            fillBuffer(str, input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method has to ensure that it returns false for all but the first
     * call because it processes all the input stream at once and gets out what
     * it needs. If subsequent calls do not return false, that'd throw the
     * method in an infinite loop. It also has to correct the offsets because it
     * messes with the term attribute value
     */
    @Override
    public final boolean incrementToken() throws IOException {
        if (i > 0) {
            return false;
        }

        regex = new StringBuilder();

        for (; i < stopWords.length; i++) {
            regex.setLength(0);

            regex.append(xmlStartTag(stopWords[i])).append("(.+)")
                    .append(xmlStopTag(stopWords[i]));
            pattern = Pattern.compile(regex.toString());
            matcher = pattern.matcher(str);

            if (matcher.find() && matcher.groupCount() >= 1) {

                if (i == 0) {
                    termAtt.setEmpty();
                }

                termAtt.append(matcher.group(1));

                if (i < (stopWords.length - 1)) {
                    termAtt.append(" ");

                }

                offsetAtt.setOffset(correctOffset(offset),
                        correctOffset(termAtt.length()));

                offset = offsetAtt.endOffset()
                        + posIncAttr.getPositionIncrement();

                typeAttr.setType(MAVEN_TYPE);
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    public void reset() {
        regex = null;
        pattern = null;
        matcher = null;
    }

    final char[] ioBuffer = new char[8192];

    private void fillBuffer(StringBuilder sb, Reader input) throws IOException {
        int len;
        sb.setLength(0);
        while ((len = input.read(ioBuffer)) > 0) {
            sb.append(ioBuffer, 0, len);
        }
    }

    private String xmlStartTag(String stopWord) {
        return "<" + stopWord + ">";
    }

    private String xmlStopTag(String stopWord) {
        return "</" + stopWord + ">";
    }

    @Override
    public void end() {
        final int ofs = correctOffset(str.length());
        offsetAtt.setOffset(ofs, ofs);
    }
}

package name.abhijitsarkar.lucene.mavenrepo;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Assert;

/**
 * This is an utility class to help testing and debugging
 * 
 */
public class AnalyzerUtils {

    public static void assertAnalyzesTo(Analyzer analyzer, String input,
            String[] output) throws IOException {
        TokenStream stream = analyzer.tokenStream("field", new StringReader(
                input));
        CharTermAttribute termAttr = stream
                .addAttribute(CharTermAttribute.class);
        /*
         * It is necessary to call reset on the TokenStream before calling
         * incrementToken(), else ArrayIndexOutOfBoundsException
         */
        stream.reset();
        for (String expected : output) {
            Assert.assertTrue(stream.incrementToken());
            Assert.assertEquals(expected, termAttr.toString());
        }
        Assert.assertFalse(stream.incrementToken());
        stream.close();
    }

    public static void dumpTokenStream(Analyzer analyzer, String input,
            PrintStream out) throws IOException {
        TokenStream stream = analyzer.tokenStream("field", new StringReader(
                input));
        CharTermAttribute termAttr = stream
                .addAttribute(CharTermAttribute.class);
        PositionIncrementAttribute posIncr = stream
                .addAttribute(PositionIncrementAttribute.class);
        OffsetAttribute offset = stream.addAttribute(OffsetAttribute.class);
        TypeAttribute type = stream.addAttribute(TypeAttribute.class);
        /*
         * It is necessary to call reset on the TokenStream before calling
         * incrementToken(), else ArrayIndexOutOfBoundsException
         */
        stream.reset();
        int position = 0;
        while (stream.incrementToken()) {
            int increment = posIncr.getPositionIncrement();
            if (increment > 0) {
                position = position + increment;
                out.println();
                out.print(position + ": ");
            }
            out.print("[" + termAttr.toString() + ":" + offset.startOffset()
                    + "->" + offset.endOffset() + ":" + type.type() + "]\n");
            stream.close();
        }
    }
}

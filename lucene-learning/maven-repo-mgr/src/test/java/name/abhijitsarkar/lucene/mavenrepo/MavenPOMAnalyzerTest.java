package name.abhijitsarkar.lucene.mavenrepo;

import java.io.IOException;
import java.io.StringReader;

import name.abhijitsarkar.lucene.mavenrepo.MavenPOMAnalyzer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Version;
import org.junit.Assert;
import org.junit.Test;

public class MavenPOMAnalyzerTest {
    private MavenPOMAnalyzer analyzer = new MavenPOMAnalyzer(Version.LUCENE_40);

    @Test
    public void testMavenPOMAnalyzer() throws IOException {
        String input = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + "xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 "
                + "http://maven.apache.org/xsd/maven-4.0.0.xsd\">"
                + "<modelVersion>4.0.0</modelVersion>"

                + "<parent>\n"
                + "<groupId>name.abhijitsarkar.lucene</groupId>\n"
                + "<artifactId>lucene-learning</artifactId>\n"
                + "<version>1.0-SNAPSHOT</version>\n" + "</parent>\n"

                + "<artifactId>maven-repo-mgr</artifactId>\n"
                + "<packaging>jar</packaging>\n"

                + "<name>hadoop-examples</name>\n";
        String[] output = new String[] { "name.abhijitsarkar.lucene",
                "lucene-learning" };

        TokenStream stream = analyzer.tokenStream("field", new StringReader(
                input));
        CharTermAttribute termAttr = stream
                .addAttribute(CharTermAttribute.class);
        OffsetAttribute offsetAtt = stream.addAttribute(OffsetAttribute.class);
        /*
         * It is necessary to call reset on the TokenStream before calling
         * incrementToken(), else ArrayIndexOutOfBoundsException
         */
        stream.reset();

        Assert.assertTrue("There should be more tokens",
                stream.incrementToken());
        Assert.assertEquals("Wrong token", output[0], termAttr.toString());
        Assert.assertEquals("Wrong start offset", 0, offsetAtt.startOffset());
        Assert.assertEquals("Wrong end offset", 34, offsetAtt.endOffset());

        Assert.assertTrue("There should be more tokens",
                stream.incrementToken());
        Assert.assertEquals("Wrong token", output[1], termAttr.toString());
        Assert.assertEquals("Wrong start offset", 35, offsetAtt.startOffset());
        Assert.assertEquals("Wrong end offset", 50, offsetAtt.endOffset());

        Assert.assertFalse(stream.incrementToken());
        stream.close();
    }
}

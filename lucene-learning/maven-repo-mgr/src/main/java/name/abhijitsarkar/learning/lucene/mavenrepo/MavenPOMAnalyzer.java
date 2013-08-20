package name.abhijitsarkar.learning.lucene.mavenrepo;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.util.Version;

/**
 * 
 * This class delegates to the {@link MavenPOMTokenizer MavenPOMTokenizer} for
 * tokenizing the input stream. It then applies the {@link WhitespaceFilter
 * WhitespaceFilter} for further splitting the terms by whitespace. Note that
 * the input stream has already been modified by {@link MavenPOMCharFilter
 * MavenPOMCharFilter}
 * 
 */
public class MavenPOMAnalyzer extends Analyzer {
    private final Version matchVersion;
    private final String[] xmlTags;
    public static final String[] MAVEN_XML_TAG_SET;

    static {
        MAVEN_XML_TAG_SET = new String[] { "groupId", "artifactId" };
    }

    public MavenPOMAnalyzer(Version matchVersion) {
        this(matchVersion, MAVEN_XML_TAG_SET);
    }

    public MavenPOMAnalyzer(Version matchVersion, String[] stopWords) {
        this.matchVersion = matchVersion;
        this.xmlTags = stopWords == null ? MAVEN_XML_TAG_SET : stopWords;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName,
            Reader reader) {
        final Tokenizer source = new MavenPOMTokenizer(matchVersion, reader,
                xmlTags);
        TokenStream tok = new WhitespaceFilter(source);
        return new TokenStreamComponents(source, tok);
    }

    @Override
    protected Reader initReader(String fieldName, Reader reader) {
        return new MavenPOMCharFilter(reader);
    }
}

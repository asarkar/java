package name.abhijitsarkar.lucene.mavenrepo;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.charfilter.BaseCharFilter;

/**
 * The purpose of this class is to truncate the input stream after the
 * artifactId tag. A pom.xml file contains many groupId and artifactId pair, all
 * but one of which are for dependencies. We do not want the dependency groupId
 * and artifactId pairs. Thus we read the input stream until the first groupId
 * and artifactId pair is found and then discard the rest of the stream. The
 * groupId and artifactId detection could be more sophisticated in reality but
 * this serves the purposes of learning.
 * 
 */
public class MavenPOMCharFilter extends BaseCharFilter {
    static final Pattern groupId = Pattern.compile("<groupId>(.+)</groupId>");
    static final Pattern artifactId = Pattern
            .compile("<artifactId>(.+)</artifactId>");

    private Reader reader;
    private Matcher matcher;

    public MavenPOMCharFilter(Reader in) {
        super(in);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (reader == null) {
            fillUntilArtifactId();
        }

        return reader.read(cbuf, off, len);
    }

    private void fillUntilArtifactId() throws IOException {
        StringBuilder transformedInput = new StringBuilder();
        final char[] ioBuffer = new char[1024];
        final StringBuilder temp = new StringBuilder();

        for (int cnt = input.read(ioBuffer); cnt > 0; cnt = input
                .read(ioBuffer)) {
            temp.delete(0, temp.length()).append(ioBuffer);
            matcher = groupId.matcher(temp);

            if (matcher.find()) {
                matcher = artifactId.matcher(temp);

                if (matcher.find()) {
                    transformedInput.append(ioBuffer, 0, matcher.end());
                    break;
                }
            }
            transformedInput.append(ioBuffer, 0, cnt);
        }

        reader = new StringReader(transformedInput.toString());
    }
}

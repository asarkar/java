package name.abhijitsarkar.lucene.mavenrepo;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.util.BytesRef;

/**
 * This class overrides the
 * {@link org.apache.lucene.queries.CustomScoreProvider#customScore(int, float, float)
 * customScore(int, float, float)} method to boost those results that have the
 * query term in the provided field}
 * 
 */
public class PathBooster extends CustomScoreProvider {
	private final FieldCache.DocTerms paths;
	private final Pattern p;
	private Matcher m;

	public PathBooster(AtomicReaderContext context, String field,
			String queryStr) throws IOException {
		super(context);
		/*
		 * This occupies some RAM until the reader is closed, but I've not found
		 * any other way to retrieve field values from documents
		 */
		this.paths = FieldCache.DEFAULT.getTerms(context.reader(), field);
		this.p = Pattern.compile("(/.+)+" + queryStr + "(/.+)+");
	}

	@Override
	public float customScore(int docId, float subQueryScore, float valSrcScore) {
		/*
		 * Use the utf8ToString() method, not new String(bytes, offset, length)
		 * as it does not respect the correct character set and may return wrong
		 * results (depending on the platform's defaults)
		 */
		final String path = paths.getTerm(docId, new BytesRef()).utf8ToString();
		int termFreq = 0;

		if (path != null && !path.isEmpty()) {
			m = p.matcher(path);

			/* Everything is in the for-loop construct, do not need a body */
			for (; m.find(); termFreq++)
				;
		}

		/*
		 * Add, rather than multiply, because we don't know what the
		 * subQueryScore is; it could be very low in which case the boost would
		 * still not put the result on top
		 */
		return subQueryScore + termFreq;
	}
}

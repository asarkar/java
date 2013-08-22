package name.abhijitsarkar.learning.lucene.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexUtil {

	public static IndexWriter getFSIndexWriter(final Version version,
			final String indexDir, final Analyzer analyzer,
			final OpenMode openMode) throws IOException {
		final Directory dir = FSDirectory.open(new File(indexDir));
		final IndexWriterConfig iwc = new IndexWriterConfig(version, analyzer);
		iwc.setOpenMode(openMode);
		return new IndexWriter(dir, iwc);
	}

	public static FilenameFilter getFilenameFilter(final String extension) {
		return new FilenameFilter() {

			@Override
			public boolean accept(File file, String name) {
				return file.isFile() && name.endsWith(extension);
			}
		};
	}
}

package name.abhijitsarkar.lucene.mavenrepo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import name.abhijitsarkar.lucene.util.IndexOptionsParser;
import name.abhijitsarkar.lucene.util.IndexUtil;

import org.apache.commons.cli.ParseException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.util.Version;

/**
 * This class creates a Lucene index. It uses {@link IndexOptionsParser
 * IndexOptionsParser} for parsing command line arguments.
 * 
 */
public class Indexer {

	public static void main(String[] args) throws ParseException, IOException {
		new Indexer().index(args);
	}

	private void index(String[] args) throws ParseException, IOException {
		final IndexOptionsParser parser = new IndexOptionsParser(args);
		final String indexDir = parser.getIndexDir();
		final File doc = new File(parser.getDoc());
		final String includeExtension = parser.getFileExtension();

		System.out.println("Indexing to directory '" + indexDir
				+ "', files with extension '" + includeExtension + "'\n");

		final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		final IndexWriter writer = IndexUtil.getFSIndexWriter(
				Version.LUCENE_40, indexDir, analyzer, OpenMode.CREATE);
		FilenameFilter filenameFilter = IndexUtil
				.getFilenameFilter(includeExtension);
		indexDocs(writer, doc, filenameFilter);
		writer.close();
	}

	private void indexDocs(IndexWriter writer, File file,
			final FilenameFilter filenameFilter) throws IOException {
		Reader reader = null;

		/* Do not try to index files that cannot be read */
		if (file.canRead()) {
			if (file.isDirectory()) {
				String[] files = file.list();
				/* An IO error could occur */
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						indexDocs(writer, new File(file, files[i]),
								filenameFilter);
					}
				}
			} else if (filenameFilter.accept(file, file.getName())) {
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(file)));

				/* Make a new, empty document */
				Document doc = new Document();

				/*
				 * Add the path of the file as a field named "path". Use a field
				 * that is indexed (i.e. searchable), but don't tokenize the
				 * field into separate words
				 */
				Field pathField = new StringField("path", file.getPath(),
						Field.Store.YES);
				doc.add(pathField);

				/*
				 * Add the last modified date of the file a field named
				 * "modified". Use a LongField that is indexed (i.e. efficiently
				 * filterable with NumericRangeFilter). This indexes to
				 * milli-second resolution, which is often too fine. You could
				 * instead create a number based on
				 * year/month/day/hour/minutes/seconds, down the resolution you
				 * require.
				 */
				doc.add(new LongField("modified", file.lastModified(),
						Field.Store.NO));

				/*
				 * Add the contents of the file to a field named "contents".
				 * Specify a Reader, so that the text of the file is tokenized
				 * and indexed, but not stored. Note that FileReader expects the
				 * file to be in UTF-8 encoding. If that's not the case
				 * searching for special characters will fail.
				 */
				doc.add(new TextField("content", reader));

				if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
					/*
					 * New index, so we just add the document (no old document
					 * can be there)
					 */
					System.out.println("adding " + file);
					writer.addDocument(doc);
				} else {
					/*
					 * Existing index (an old copy of this document may have
					 * been indexed) so we use updateDocument instead to replace
					 * the old one matching the exact path, if present
					 */
					System.out.println("updating " + file);
					writer.updateDocument(new Term("path", file.getPath()), doc);
				}

				reader.close();
			}
		}
	}
}

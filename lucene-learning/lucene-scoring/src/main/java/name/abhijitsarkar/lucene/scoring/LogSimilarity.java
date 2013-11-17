package name.abhijitsarkar.lucene.scoring;

import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.SimilarityBase;

public class LogSimilarity extends SimilarityBase {

	@Override
	protected float score(BasicStats stats, float freq, float docLen) {
		printStats(stats);
		System.out.println("Freq: " + freq);
		return (float) (1.0d + Math.log(freq)) * stats.getTotalBoost();
	}

	protected void printStats(final BasicStats stats) {
		System.out.println("************************************");
		System.out.println("Document freq: " + stats.getDocFreq());
		System.out.println("Number of doc: " + stats.getNumberOfDocuments());
		System.out.println("Number of field tokens: "
				+ stats.getNumberOfFieldTokens());
		System.out.println("Total boost: " + stats.getTotalBoost());
		System.out.println("Total term freq: " + stats.getTotalTermFreq());
		System.out.println("Value for norm: "
				+ stats.getValueForNormalization());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}

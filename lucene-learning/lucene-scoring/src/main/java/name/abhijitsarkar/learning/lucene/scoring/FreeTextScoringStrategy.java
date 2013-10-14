package name.abhijitsarkar.learning.lucene.scoring;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;

public interface FreeTextScoringStrategy {
    public void init(Weight weight, List<Scorer> childScorers,
            AtomicReaderContext context);

    public float score(int docId) throws IOException;
}
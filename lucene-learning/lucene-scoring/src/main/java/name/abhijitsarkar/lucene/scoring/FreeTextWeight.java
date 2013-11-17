package name.abhijitsarkar.lucene.scoring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.FreeTextScorer;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.Bits;

public class FreeTextWeight extends Weight {
    Query myQuery;
    List<Weight> myChildWeights;
    FreeTextScoringStrategy myScoringStrategy;
    IndexSearcher myIndexSearcher; // Normally would toss this, but saving for
                                   // {@link FreeTextScoringStrategy}

    public FreeTextWeight(Query query, List<Query> childQueries,
            IndexSearcher searcher, FreeTextScoringStrategy scoringStrategy)
            throws IOException {
        myQuery = query;
        myScoringStrategy = scoringStrategy;

        myChildWeights = new ArrayList<Weight>();
        for (Query childQuery : childQueries) {
            myChildWeights.add(childQuery.createWeight(searcher));
        }
    }

    @Override
    public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder,
            boolean topScorer, Bits acceptDocs) throws IOException {
        List<Scorer> childScorers = new ArrayList<Scorer>();
        for (Weight w : myChildWeights) {
            childScorers.add(w.scorer(context, scoreDocsInOrder, false,
                    acceptDocs));
        }

        return new FreeTextScorer(this, childScorers, context,
                myScoringStrategy);
    }

    @Override
    public Explanation explain(AtomicReaderContext context, int doc)
            throws IOException {
        return new Explanation(0.0f, "Exercise for the reader");
    }

    @Override
    public Query getQuery() {
        return myQuery;
    }

    @Override
    public float getValueForNormalization() throws IOException {
        float sum = 0.0f;
        for (int i = 0; i < myChildWeights.size(); i++) {
            float s = myChildWeights.get(i).getValueForNormalization();
            sum += s;
        }

        sum *= myQuery.getBoost() * myQuery.getBoost();

        return sum;
    }

    @Override
    public void normalize(float norm, float topLevelBoost) {
        topLevelBoost *= myQuery.getBoost();

        for (Weight w : myChildWeights) {
            w.normalize(norm, topLevelBoost);
        }
    }
}
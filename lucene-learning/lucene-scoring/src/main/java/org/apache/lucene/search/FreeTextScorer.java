package org.apache.lucene.search;

import java.io.IOException;
import java.util.List;

import name.abhijitsarkar.learning.lucene.scoring.FreeTextScoringStrategy;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.search.DisjunctionSumScorer;

public class FreeTextScorer extends DisjunctionSumScorer {
    FreeTextScoringStrategy myScoringStrategy;

    public FreeTextScorer(Weight weight, List<Scorer> childScorers,
            AtomicReaderContext context, FreeTextScoringStrategy scoringStrategy)
            throws IOException {
        super(weight, childScorers);
        myScoringStrategy = scoringStrategy;
        myScoringStrategy.init(weight, childScorers, context);
    }

    @Override
    public float score() throws IOException {
        return myScoringStrategy.score(this.docID());
    }
}
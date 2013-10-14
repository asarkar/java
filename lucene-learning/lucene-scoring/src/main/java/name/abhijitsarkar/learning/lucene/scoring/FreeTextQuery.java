package name.abhijitsarkar.learning.lucene.scoring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Weight;

/**
 * Example of a hand-made query.
 * 
 * FreeTextQuery represents a collection of terms (ie. the clauses) which we
 * wish to run a scoring algorithm across. We're not especially interested in
 * boolean or proximity relationships between the terms... each term is simply
 * fed into the FreeTextScoringStrategy for every document we wish to score.
 * 
 * @author Greg Dearing
 * 
 */
public class FreeTextQuery extends Query {
    private List<Query> myClauses;
    private FreeTextScoringStrategy myScoringStrategy;

    public FreeTextQuery(List<Query> clauses,
            FreeTextScoringStrategy scoringStrategy) {
        myClauses = clauses;
        myScoringStrategy = scoringStrategy;
    }

    @Override
    public Weight createWeight(IndexSearcher searcher) throws IOException {
        return new FreeTextWeight(this, myClauses, searcher, myScoringStrategy);
    }

    public Query rewrite(IndexReader reader) throws IOException {
        List<Query> myRewrittenClauses = new ArrayList<Query>();

        for (Query q : myClauses)
            myRewrittenClauses.add(q.rewrite(reader));

        myClauses = myRewrittenClauses;
        return this;
    }

    @Override
    public String toString(String field) {
        StringBuilder sb = new StringBuilder("(freetext: ");
        sb.append(myClauses.get(0).toString(field)).append(", ");
        for (int i = 1; i < myClauses.size(); i++) {
            sb.append(myClauses.get(i).toString(field)).append(", ");
        }
        sb.append("(");
        return sb.toString();
    }
}

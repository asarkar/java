package name.abhijitsarkar.lucene.scoring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import name.abhijitsarkar.lucene.scoring.FreeTextQuery;
import name.abhijitsarkar.lucene.scoring.FreeTextScoringStrategy;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.queries.function.FunctionQuery;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.valuesource.FloatFieldSource;
import org.apache.lucene.queries.function.valuesource.MaxFloatFunction;
import org.apache.lucene.queries.function.valuesource.QueryValueSource;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.Weight;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LuceneScoringTest {
    private IndexSearcher mySearcher;
    private IndexReader myReader;

    private String[] myTitles = new String[] { "see spot run", "spot run see",
            "run spot run" };
    private float[] myCiteRanks = new float[] { 0.0f, 1.1f, 2.2f };

    // private static Logger MyLogger = LoggerFactory
    // .getLogger(LuceneScoringTest.class);

    @Before
    public void setUp() throws Exception {
        /*
         * Build an in-memory index, using the values of myTitles and
         * myCiteRanks
         */
        Directory dir = new RAMDirectory();
        IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(
                Version.LUCENE_40, new WhitespaceAnalyzer(Version.LUCENE_40)));

        FieldType titleFieldType = new FieldType(StringField.TYPE_NOT_STORED);
        titleFieldType.setTokenized(true);
        titleFieldType
                .setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);

        for (int i = 0; i < myTitles.length; i++) {
            Document doc = new Document();
            doc.add(new Field("TI", myTitles[i], titleFieldType));
            doc.add(new Field("RANK", Float.toString(myCiteRanks[i]),
                    titleFieldType));
            writer.addDocument(doc);
        }

        writer.close();

        mySearcher = new IndexSearcher(DirectoryReader.open(dir));
        myReader = mySearcher.getIndexReader();

        // Our index only has one segment, so this is reasonable
        Assert.assertEquals("Index should only have one segment", 1, myReader
                .leaves().size());
    }

    @Test
    public void testTermSearchWorkflow() throws Exception {
        TermQuery termQuery = new TermQuery(new Term("TI", "run"));

        Weight weight = mySearcher.createNormalizedWeight(termQuery);
        AtomicReaderContext segmentContext = myReader.leaves().get(0);

        Scorer termScorer = weight.scorer(segmentContext, false, true, null);

        // Run search manually
        // Floating point calculations are not exact - there is often
        // round-off errors, and errors due to representation. Because of
        // this, directly comparing two floating point values for equality
        // is usually not a good idea, because they can be different by a
        // small amount, depending upon how they were computed.The "delta",
        // describes the amount of difference that can be toleratd in the
        // values for them to be still considered equal. The size of this
        // value is entirely dependent on the values compared.
        Assert.assertEquals(0, termScorer.nextDoc());
        Assert.assertEquals(1.0f, termScorer.freq(), 1.0 / (10 ^ 6));
        Assert.assertEquals(0.71231794f, termScorer.score(),
                0.71231794f / (10 ^ 6));
        // MyLogger.trace(weight.explain(segmentContext, 0).toString());

        Assert.assertEquals(1, termScorer.nextDoc());
        Assert.assertEquals(1.0f, termScorer.freq(), 1.0 / (10 ^ 6));
        Assert.assertEquals(0.71231794f, termScorer.score(),
                0.71231794f / (10 ^ 6));

        Assert.assertEquals(2, termScorer.nextDoc());
        Assert.assertEquals(2.0f, termScorer.freq(), 2.0 / (10 ^ 6));
        Assert.assertEquals(1.0073696f, termScorer.score(),
                1.0073696f / (10 ^ 6));

        Assert.assertEquals(Scorer.NO_MORE_DOCS, termScorer.nextDoc());
    }

    /**
     * Demonstrates searching using a custom-made query
     */
    @Test
    public void testFreeTextQuery() throws Exception {
        List<Query> clauses = new ArrayList<Query>();
        clauses.add(new TermQuery(new Term("TI", "see")));
        clauses.add(new TermQuery(new Term("TI", "spot")));

        Query freeTextQuery = new FreeTextQuery(clauses,
                new MikeScoringStrategy());

        Weight freeTextWeight = mySearcher
                .createNormalizedWeight(freeTextQuery);

        AtomicReaderContext segmentContext = myReader.leaves().get(0);
        Scorer freeTextScorer = freeTextWeight.scorer(segmentContext, false,
                true, null);

        // Run search manually
        // Floating point calculations are not exact - there is often
        // round-off errors, and errors due to representation. Because of
        // this, directly comparing two floating point values for equality
        // is usually not a good idea, because they can be different by a
        // small amount, depending upon how they were computed.The "delta",
        // describes the amount of difference that can be toleratd in the
        // values for them to be still considered equal. The size of this
        // value is entirely dependent on the values compared.
        Assert.assertEquals(0, freeTextScorer.nextDoc());
        Assert.assertEquals(2.0f, freeTextScorer.score(), 2.0 / (10 ^ 6));

        Assert.assertEquals(1, freeTextScorer.nextDoc());
        Assert.assertEquals(2.0f, freeTextScorer.score(), 2.0 / (10 ^ 6));

        Assert.assertEquals(2, freeTextScorer.nextDoc());
        Assert.assertEquals(1.0f, freeTextScorer.score(), 1.0 / (10 ^ 6));

        Assert.assertEquals(Scorer.NO_MORE_DOCS, freeTextScorer.nextDoc());
    }

    /**
     * Just making sure that FreeTextQuery knows how to skip documents that
     * didn't match
     */
    @Test
    public void testFreeTextQuery2() throws Exception {
        List<Query> clauses = new ArrayList<Query>();
        clauses.add(new TermQuery(new Term("TI", "see")));
        clauses.add(new TermQuery(new Term("TI", "see")));

        Query q = new FreeTextQuery(clauses, new MikeScoringStrategy());

        Weight w = mySearcher.createNormalizedWeight(q);

        AtomicReaderContext segmentContext = myReader.leaves().get(0);
        Scorer s = w.scorer(segmentContext, false, true, null);

        Assert.assertEquals(0, s.nextDoc());
        Assert.assertEquals(1, s.nextDoc());
        Assert.assertEquals(Scorer.NO_MORE_DOCS, s.nextDoc());
    }

    /**
     * Uses a function query to force the score of each doc to be the value of
     * it's "RANK" field
     */
    @Test
    public void testConstantFunctionQuery() throws Exception {
        ValueSource vs = new FloatFieldSource("RANK");
        FunctionQuery fq = new FunctionQuery(vs);

        Scorer s = createScorer(fq);

        for (int docId = 0; docId < myCiteRanks.length; docId++) {
            Assert.assertEquals(docId, s.nextDoc());
            // Floating point calculations are not exact - there is often
            // round-off errors, and errors due to representation. Because of
            // this, directly comparing two floating point values for equality
            // is usually not a good idea, because they can be different by a
            // small amount, depending upon how they were computed.The "delta",
            // describes the amount of difference that can be toleratd in the
            // values for them to be still considered equal. The size of this
            // value is entirely dependent on the values compared.
            Assert.assertEquals(myCiteRanks[docId], s.score(),
                    myCiteRanks[docId] / (10 ^ 6));
        }
    }

    /**
     * Combines multiple value sources, so that the
     */
    @Test
    public void testNestedFunctionsQuery() throws Exception {
        TermQuery termQuery = new TermQuery(new Term("TI", "run"));

        // The natural relevancy score of the query. Returns 0.0f if query
        // doesn't match a doc
        ValueSource queryValue = new QueryValueSource(termQuery, 0.0f);

        // CiteRank for the doc
        ValueSource citeRankValue = new FloatFieldSource("RANK");

        // The max of score and CiteRank
        ValueSource maxValue = new MaxFloatFunction(new ValueSource[] {
                queryValue, citeRankValue });
        FunctionQuery q = new FunctionQuery(maxValue);

        Scorer s = createScorer(q);

        Assert.assertEquals(0, s.nextDoc());
        // Floating point calculations are not exact - there is often
        // round-off errors, and errors due to representation. Because of
        // this, directly comparing two floating point values for equality
        // is usually not a good idea, because they can be different by a
        // small amount, depending upon how they were computed.The "delta",
        // describes the amount of difference that can be toleratd in the
        // values for them to be still considered equal. The size of this
        // value is entirely dependent on the values compared.
        Assert.assertEquals(0.71231794f, s.score(), 0.71231794 / (10 ^ 6));

        Assert.assertEquals(1, s.nextDoc());
        Assert.assertEquals(1.1f, s.score(), 1.1 / (10 ^ 6));

        Assert.assertEquals(2, s.nextDoc());
        Assert.assertEquals(2.2f, s.score(), 2.2 / (10 ^ 6));

        Assert.assertEquals(Scorer.NO_MORE_DOCS, s.nextDoc());
    }

    /**
     * Runs the query "TI:see", but forces the score to always be the value of
     * CiteRank
     */
    @Test
    public void testCustomScoreQuery() throws Exception {
        TermQuery termQuery = new TermQuery(new Term("TI", "see"));
        FunctionQuery rankScoringQuery = new FunctionQuery(
                new FloatFieldSource("RANK"));

        CustomScoreQuery q = new CustomScoreQuery(termQuery, rankScoringQuery) {
            protected CustomScoreProvider getCustomScoreProvider(
                    AtomicReaderContext context) throws IOException {
                return new CiteRankScoreProvider(context);
            }
        };
        q.setStrict(true); // Don't normalize the valueSource (cite rank)
                           // portion of the query

        Scorer s = createScorer(q);

        Assert.assertEquals(0, s.nextDoc());
        Assert.assertEquals(0.0f, s.score(), 0.0);

        // Floating point calculations are not exact - there is often
        // round-off errors, and errors due to representation. Because of
        // this, directly comparing two floating point values for equality
        // is usually not a good idea, because they can be different by a
        // small amount, depending upon how they were computed.The "delta",
        // describes the amount of difference that can be toleratd in the
        // values for them to be still considered equal. The size of this
        // value is entirely dependent on the values compared.
        Assert.assertEquals(1, s.nextDoc());
        Assert.assertEquals(1.1f, s.score(), 1.1 / (10 ^ 6));

        Assert.assertEquals(Scorer.NO_MORE_DOCS, s.nextDoc());
    }

    // Helper method
    private Scorer createScorer(Query query) throws Exception {
        Weight w = mySearcher.createNormalizedWeight(query);
        AtomicReaderContext segmentContext = myReader.leaves().get(0);
        return (w.scorer(segmentContext, false, true, null));
    }

    private class CiteRankScoreProvider extends CustomScoreProvider {
        public CiteRankScoreProvider(AtomicReaderContext context) {
            super(context);
        }

        public float customScore(int doc, float subQueryScore, float valSrcScore)
                throws IOException {
            return valSrcScore;
        }
    }

    private class MikeScoringStrategy implements FreeTextScoringStrategy {
        List<Scorer> myChildScorers;

        public void init(Weight weight, List<Scorer> childScorers,
                AtomicReaderContext context) {
            myChildScorers = childScorers;
            // This algorithm doesn't use the weight or context objects, but
            // don't forget they exist.t;
        }

        /**
         * Every child who 'matches' the document adds +1 to its score.
         */
        public float score(int docId) throws IOException {
            float score = 0.0f;

            for (Scorer s : myChildScorers) {
                if (docId == s.docID()) {
                    score += 1.0f;
                }

                // Note that a lot of data is available to your algorithm here
                // s.getWeight(). You could use this to re-implement your
                // child's scorer. (not recommended)
                // s.getWeight().getQuery()
                // s.freq()
                // s.score()
                // myWeight (Has access to the whole index)
                // myContext (The index segment currently being worked on)
            }

            return score;
        }
    }
}

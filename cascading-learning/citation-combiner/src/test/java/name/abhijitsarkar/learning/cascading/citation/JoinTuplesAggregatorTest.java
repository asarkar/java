package name.abhijitsarkar.learning.cascading.citation;

import java.util.ArrayList;
import java.util.List;

import name.abhijitsarkar.learning.cascading.citation.JoinTuplesAggregator;

import org.junit.Assert;
import org.junit.Test;

import cascading.CascadingTestCase;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleListCollector;

public class JoinTuplesAggregatorTest extends CascadingTestCase {

    private static final long serialVersionUID = -8354904330407776457L;

    @Test
    public void testAggregateUsingDefaultConstructor() {
        final Tuple arg1 = new Tuple("1", "101");
        final Tuple arg2 = new Tuple("2", "101");
        Tuple[] args = new Tuple[] { arg1, arg2 };
        TupleListCollector collector = CascadingTestCase.invokeAggregator(
                new JoinTuplesAggregator(), args, Fields.ALL);

        List<Tuple> result = asList(collector);

        Assert.assertEquals("Wrong result size", 1, result.size());
        Assert.assertEquals("Wrong result", new Tuple("1,2"), result.get(0));
    }

    private List<Tuple> asList(Iterable<Tuple> it) {
        List<Tuple> list = new ArrayList<Tuple>();

        for (Tuple t : it) {
            list.add(t);
        }

        return list;
    }
}

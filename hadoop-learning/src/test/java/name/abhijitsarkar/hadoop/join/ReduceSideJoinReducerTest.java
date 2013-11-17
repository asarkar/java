package name.abhijitsarkar.hadoop.join;

import java.util.ArrayList;
import java.util.List;

import name.abhijitsarkar.hadoop.join.ReduceSideJoinReducer;
import name.abhijitsarkar.hadoop.join.TaggedKey;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

public class ReduceSideJoinReducerTest {
    private final ReduceSideJoinReducer reducer = new ReduceSideJoinReducer();
    private final ReduceDriver<TaggedKey, Text, IntWritable, Text> reduceDriver = new ReduceDriver<TaggedKey, Text, IntWritable, Text>();

    @Test
    public void testReduce() {
        TaggedKey key = new TaggedKey(1, 1);
        List<Text> values = new ArrayList<Text>();
        values.add(new Text(
                "Stephanie Leung,555-555-5555,123 No Such St,Fantasyland,CA 99999"));
        values.add(new Text("A,99.99,01/01/2013"));
        Text expectedValue = new Text(
                "Stephanie Leung,555-555-5555,123 No Such St,Fantasyland,CA 99999|A,99.99,01/01/2013");

        reduceDriver.withReducer(reducer).withInput(key, values)
                .withOutput(new IntWritable(1), expectedValue).runTest();
    }
}

package name.abhijitsarkar.learning.hadoop.citation;

import java.util.ArrayList;
import java.util.List;

import name.abhijitsarkar.learning.hadoop.citation.CitationCombiner;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.junit.Ignore;
import org.junit.Test;

public class CitationCombinerTest {
    private final CitationCombiner.CitationMapper mapper = new CitationCombiner.CitationMapper();
    private final MapDriver<Text, Text, Text, Text> mapDriver = new MapDriver<Text, Text, Text, Text>();
    private final CitationCombiner.CitationReducer reducer = new CitationCombiner.CitationReducer();
    private final ReduceDriver<Text, Text, Text, Text> reduceDriver = new ReduceDriver<Text, Text, Text, Text>();

    @Test
    public void testMapSkipHeader() {
        mapDriver.withMapper(mapper)
                .withInput(new Text("Key"), new Text("Value")).runTest();
    }

    @Test
    public void testMapValidRecord() {
        Text key = new Text("1");
        Text value = new Text("2");
        testMapUtility(key, value).runTest();
    }

    @Ignore
    private MapDriver<Text, Text, Text, Text> testMapUtility(Text key,
            Text value) {
        return mapDriver.withMapper(mapper).withInput(key, value)
                .withOutput(value, key);
    }

    @Test
    public void testReduce() {
        Text key = new Text("1");
        List<Text> values = new ArrayList<Text>();
        values.add(new Text("2"));
        values.add(new Text("3"));
        values.add(new Text("4"));
        testReduceUtility(key, values).runTest();
    }

    @Ignore
    private ReduceDriver<Text, Text, Text, Text> testReduceUtility(Text key,
            List<Text> values) {
        StringBuilder expectedValue = new StringBuilder();

        for (Text v : values) {
            expectedValue.append(v.toString()).append(",");
        }
        expectedValue.deleteCharAt(expectedValue.length() - 1);
        return reduceDriver.withReducer(reducer).withInput(key, values)
                .withOutput(new Text("1"), new Text(expectedValue.toString()));
    }
}

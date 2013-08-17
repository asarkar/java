package name.abhijitsarkar.learning.hadoop.join;

import name.abhijitsarkar.learning.hadoop.join.OrderMapper;
import name.abhijitsarkar.learning.hadoop.join.TaggedKey;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

public class OrderMapperTest {
    private final OrderMapper mapper = new OrderMapper();
    private final MapDriver<LongWritable, Text, TaggedKey, Text> mapDriver = new MapDriver<LongWritable, Text, TaggedKey, Text>();

    @Test
    public void testMapValidRecord() {
        mapDriver
                .withMapper(mapper)
                .withInput(new LongWritable(1L),
                        new Text("A|1|99.99|01/01/2013"))
                .withOutput(new TaggedKey(1, OrderMapper.SORT_ORDER),
                        new Text("A,99.99,01/01/2013")).runTest();
    }

    @Test
    public void testMapSkipHeader() {
        mapDriver
                .withMapper(mapper)
                .withInput(new LongWritable(1L),
                        new Text("ORD_ID|CUST_ID|ORD_TOTAL|ORD_DT")).runTest();
    }

}

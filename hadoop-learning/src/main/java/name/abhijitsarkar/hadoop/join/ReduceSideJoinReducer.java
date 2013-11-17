package name.abhijitsarkar.hadoop.join;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * This class reduces the output from {@link CustomerMapper CustomerMapper} and
 * from {@link OrderMapper OrderMapper}. Thanks to {@link KeyGroupingComparator
 * KeyGroupingComparator} and {@link TaggedKey#compareTo(TaggedKey)}, all the
 * records for the same key (customer ID) are sent to the Reducer in one
 * invocation and customer records are the first ones to arrive.
 */
public class ReduceSideJoinReducer extends
        Reducer<TaggedKey, Text, IntWritable, Text> {

    @Override
    protected void reduce(TaggedKey key, Iterable<Text> values, Context ctx)
            throws IOException, InterruptedException {

        StringBuilder value = new StringBuilder();

        System.out.println("Entering reduce");
        for (Text v : values) {
            System.out.println("Loop - Key: " + key + ", Value: " + value);
            value.append(v.toString()).append("|");
        }
        value.delete(value.length() - 1, value.length());
        System.out.println("Key: " + key.getJoinKey() + ", Value: " + value);

        ctx.write(key.getJoinKey(), new Text(value.toString()));
    }
}

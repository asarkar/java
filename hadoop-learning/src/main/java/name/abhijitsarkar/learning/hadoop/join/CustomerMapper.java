package name.abhijitsarkar.learning.hadoop.join;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * This class works on the customers.txt. It writes out a composite key, the
 * sort order of which is used for secondary sorting.
 */
public class CustomerMapper extends Mapper<LongWritable, Text, TaggedKey, Text> {
    public static final String COMMA = ",";
    public static final int SORT_ORDER = 1;

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        final String[] lineSplit = value.toString().split(COMMA, 2);

        if (lineSplit.length == 2) {
            /* Skip the header row */
            try {
                context.write(new TaggedKey(Integer.parseInt(lineSplit[0]),
                        SORT_ORDER), new Text(lineSplit[1]));
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
            System.out.println("Key: " + lineSplit[0] + ", Value: "
                    + lineSplit[1]);
        }
    }
}

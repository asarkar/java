package name.abhijitsarkar.learning.hadoop.join;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * This class works on the orders.txt. It writes out a composite key, the sort
 * order of which is used for secondary sorting.
 */
public class OrderMapper extends Mapper<LongWritable, Text, TaggedKey, Text> {
    public static final String PIPE = "\\|";
    public static final String COMMA = ",";
    public static final int SORT_ORDER = 2;

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        final String[] lineSplit = value.toString().split(PIPE, 3);

        if (lineSplit.length == 3) {
            /* Skip the header row */
            try {
                context.write(new TaggedKey(Integer.parseInt(lineSplit[1]),
                        SORT_ORDER), new Text(lineSplit[0] + COMMA
                        + lineSplit[2].replaceAll(PIPE, COMMA)));
                System.out.println("Key: " + lineSplit[1] + ", Value: "
                        + lineSplit[0] + COMMA
                        + lineSplit[2].replace(PIPE, COMMA));
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
    }
}

package name.abhijitsarkar.hadoop.join;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * This class partitions the output from {@link CustomerMapper CustomerMapper}
 * and from {@link OrderMapper OrderMapper} so that records with the same key
 * (customer ID) are sent to the same Reducer.
 */
public class KeyPartitioner extends Partitioner<TaggedKey, Text> {

    @Override
    public int getPartition(TaggedKey key, Text value, int numPartitions) {
        return key.getJoinKey().hashCode() % numPartitions;
    }
}

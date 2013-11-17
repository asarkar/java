package name.abhijitsarkar.hadoop.join;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * This class groups the output from {@link CustomerMapper CustomerMapper} and
 * from {@link OrderMapper OrderMapper} so that both customer and order records
 * with the same key (customer ID) are sent to the reducer in one invocation.
 * Without custom grouping, the records are sent separately to the Reducer which
 * forces it to save the records in memory until a match for the key is found in
 * a subsequent iteration. That is not a scalable solution and will run out of
 * memory for large data sets.
 */
public class KeyGroupingComparator extends WritableComparator {
    public KeyGroupingComparator() {
        super(TaggedKey.class, true);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        TaggedKey aKey = (TaggedKey) a;
        TaggedKey bKey = (TaggedKey) b;

        return aKey.getJoinKey().compareTo(bKey.getJoinKey());
    }
}

package name.abhijitsarkar.learning.hadoop.join;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

/**
 * This class acts as the composite key for the mapper classes
 */
public class TaggedKey implements WritableComparable<TaggedKey> {
    /**
     * The key on which customer and order records are joined (customer ID)
     */
    private IntWritable joinKey = new IntWritable();
    /**
     * The tag is used for secondary sort (c.f. {@link #compareTo(TaggedKey)})
     */
    private IntWritable tag = new IntWritable();

    public TaggedKey() {
    }

    public TaggedKey(int key, int tag) {
        this.joinKey.set(key);
        this.tag.set(tag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        joinKey.readFields(in);
        tag.readFields(in);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        joinKey.write(out);
        tag.write(out);
    }

    /**
     * This method employs a secondary sort based on the tag for equal keys. The
     * tag value is set by the mappers and is set to 1 for
     * {@link CustomerMapper CustomerMapper} and 2 for {@link OrderMapper
     * OrderMapper}. That guarantees that when output from both mappers are
     * grouped (c.f. {@link KeyGroupingComparator KeyGroupingComparator}),
     * customer record is the first one to reach the
     * {@link ReduceSideJoinReducer ReduceSideJoinReducer}. That way, the
     * reducer does not need to employ any logic to determine the kind of the
     * record and can concentrate solely on the join logic.
     * 
     */
    @Override
    public int compareTo(TaggedKey other) {
        int compareValue = this.joinKey.compareTo(other.getJoinKey());
        if (compareValue == 0) {
            compareValue = this.tag.compareTo(other.getTag());
        }
        return compareValue;
    }

    public IntWritable getJoinKey() {
        return joinKey;
    }

    public IntWritable getTag() {
        return tag;
    }

    /* Joshua Bloch's recommended hashCode impl. */
    @Override
    public int hashCode() {
        int result = 17;

        int c = joinKey.hashCode();
        result *= 37 + c;

        c = tag.hashCode();
        result *= 37 + c;

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TaggedKey)) {
            return false;
        }

        TaggedKey other = (TaggedKey) obj;

        return this.joinKey.equals(other.joinKey) && this.tag.equals(other.tag);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[joinkey: " + joinKey + ", tag: "
                + tag + "]";
    }
}

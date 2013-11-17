package name.abhijitsarkar.hadoop.join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ReduceSideJoinDriver extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        Job job = new Job(conf, "reduce-side-join");
        job.setJarByClass(getClass());

        job.setPartitionerClass(KeyPartitioner.class);
        job.setGroupingComparatorClass(KeyGroupingComparator.class);

        job.setReducerClass(ReduceSideJoinReducer.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        MultipleInputs.addInputPath(job, new Path(args[0], "customers.txt"),
                TextInputFormat.class, CustomerMapper.class);
        MultipleInputs.addInputPath(job, new Path(args[0], "orders.txt"),
                TextInputFormat.class, OrderMapper.class);

        job.setMapOutputKeyClass(TaggedKey.class);
        job.setMapOutputValueClass(Text.class);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        GenericOptionsParser parser = new GenericOptionsParser(
                new Configuration(), args);

        if (parser.getRemainingArgs().length < 2) {
            throw new IllegalArgumentException("Not enough arguments");
        }

        ToolRunner.run(new ReduceSideJoinDriver(), parser.getRemainingArgs());
    }
}

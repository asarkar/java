package name.abhijitsarkar.learning.hadoop.distributedcache;

import static name.abhijitsarkar.learning.hadoop.io.IOUtils.compressFile;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import name.abhijitsarkar.learning.hadoop.distributedcache.CitationRetriever;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

public class CitationRetrieverTest {
    private final CitationRetriever.CitationMapper mapper = new CitationRetriever.CitationMapper();
    private final MapDriver<LongWritable, Text, Text, Text> mapDriver = new MapDriver<LongWritable, Text, Text, Text>();
    private final CitationRetriever.CitationReducer reducer = new CitationRetriever.CitationReducer();
    private final ReduceDriver<Text, Text, Text, Text> reduceDriver = new ReduceDriver<Text, Text, Text, Text>();

    @Test
    public void testMapSkipHeader() {
        mapDriver.withMapper(mapper)
                .withInput(new LongWritable(1L), new Text("Value")).runTest();
    }

    @Test
    public void testMapWithoutCitationNumSet() {
        LongWritable key = new LongWritable(1L);
        Text value = new Text("3858241,956203");
        mapDriver.withMapper(mapper).withInput(key, value).runTest();
    }

    @Test
    public void testMapWithCitationNumSet() {
        LongWritable key = new LongWritable(1L);
        Text value = new Text("3858241,956203");
        Configuration conf = new Configuration();
        conf.set("citationNum", "3858241");
        mapDriver.withConfiguration(conf).withMapper(mapper)
                .withInput(key, value)
                .withOutput(new Text("3858241"), new Text("956203")).runTest();
    }

    /* Files don't seem to be put in distributed cache during Unit testing */
    // @Test(expected = RuntimeException.class)
    public void testReducerWithoutCacheArchive() {
        List<Text> values = new ArrayList<Text>();
        values.add(new Text("3146465"));
        values.add(new Text("3156927"));
        values.add(new Text("3221341"));
        values.add(new Text("3574238"));
        values.add(new Text("3681785"));
        values.add(new Text("3684611"));

        Text expectedOutputValue = new Text(
                "3858243 1975 5485 1973 \"FR\" \"\" 445095 3 12 2 6 63 7 2 0.8571 0 0.2778 6 8.1429 0 0 0 0");

        reduceDriver.withReducer(reducer)
                .withInput(new Text("3858243"), values)
                .withOutput(new Text("3858243"), expectedOutputValue).runTest();
    }

    /* Files don't seem to be put in distributed cache during Unit testing */
    // @Test
    public void testReducerWithCacheArchive() throws Exception {
        Configuration conf = new Configuration();
        final Path cachePath = new Path(getClass().getResource("/apat.txt")
                .getPath());

        final URI compressedFileURI = compressFile(cachePath.toUri(), "gzip",
                conf);
        DistributedCache.addCacheArchive(compressedFileURI, conf);

        List<Text> values = new ArrayList<Text>();
        values.add(new Text("3146465"));
        values.add(new Text("3156927"));
        values.add(new Text("3221341"));
        values.add(new Text("3574238"));
        values.add(new Text("3681785"));
        values.add(new Text("3684611"));

        Text expectedOutputValue = new Text(
                "3858243 1975 5485 1973 \"FR\" \"\" 445095 3 12 2 6 63 7 2 0.8571 0 0.2778 6 8.1429 0 0 0 0");

        reduceDriver.withConfiguration(conf).withReducer(reducer)
                .withInput(new Text("3858243"), values)
                .withOutput(new Text("3858243"), expectedOutputValue).runTest();
    }
}

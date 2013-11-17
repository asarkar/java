package name.abhijitsarkar.cascading.citation;

import java.util.Properties;

import cascading.flow.Flow;
import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.operation.Aggregator;
import cascading.operation.AssertionLevel;
import cascading.operation.DebugLevel;
import cascading.operation.assertion.AssertMatches;
import cascading.operation.regex.RegexSplitter;
import cascading.pipe.Checkpoint;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.property.AppProps;
import cascading.scheme.hadoop.TextDelimited;
import cascading.scheme.hadoop.TextLine;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;

/**
 * This class works on the cite.txt. It outputs a space separated key-value pair
 * where the key is a citation ID and the value is a comma-separated list of
 * citations that refer to the key.
 */
public class CitationCombiner {
    public static final String COMMA = ",";
    public static final String INPUT_FORMAT_REGEX = "\\d+,\\d+";

    public static void main(String[] args) {
        String srcPath = args[0];
        String outputPath = args[1];
        String checkpointPath = args[2];
        String trapPath = args[3];

        Properties properties = new Properties();
        AppProps.setApplicationJarClass(properties, CitationCombiner.class);
        HadoopFlowConnector flowConnector = new HadoopFlowConnector(properties);

        /* Create source and sink taps */
        Tap srcTap = new Hfs(new TextLine(new Fields("line")), srcPath);
        Tap citationCombinerTap = new Hfs(new TextDelimited(true, "\t"),
                outputPath);
        Tap trapTap = new Hfs(new TextDelimited(true, "\t"), trapPath);
        Tap checkpointTap = new Hfs(new TextDelimited(true, "\t"),
                checkpointPath);

        Pipe sourcePipe = new Pipe("sourcePipe");

        Checkpoint check = new Checkpoint("checkpoint", sourcePipe);

        AssertMatches assertMatches = new AssertMatches(INPUT_FORMAT_REGEX);
        sourcePipe = new Each(check, AssertionLevel.STRICT, assertMatches);

        Fields tokens = new Fields("citing", "cited");
        RegexSplitter splitter = new RegexSplitter(tokens, COMMA);
        Pipe pipe = new Each(sourcePipe, splitter);

        /* Group by the "cited" (referenced citations) */
        pipe = new GroupBy("callMeAnythingGroup", pipe, new Fields("cited"));

        /*
         * Custom aggregator will take the grouped Tuples and create a
         * comma-separated list
         */
        Aggregator join = new JoinTuplesAggregator(new Fields("join"), COMMA);

        pipe = new Every(pipe, join, Fields.ALL);

        /*
         * Connect the taps, pipes, etc., into a flow. The Trap must be set on
         * the pipe that is failing assertion otherwise the job will fail.
         */
        FlowDef flowDef = FlowDef.flowDef().setName("cc")
                .addSource(pipe, srcTap).addTailSink(pipe, citationCombinerTap)
                .addTrap(sourcePipe, trapTap)
                .addCheckpoint(check, checkpointTap);

        flowDef.setDebugLevel(DebugLevel.VERBOSE);

        Flow citationCombinerFlow = flowConnector.connect(flowDef);
        citationCombinerFlow.complete();
    }

}

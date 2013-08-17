package name.abhijitsarkar.learning.cascading.join;

import java.util.Properties;

import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.operation.Identity;
import cascading.pipe.CoGroup;
import cascading.pipe.Each;
import cascading.pipe.Pipe;
import cascading.pipe.joiner.LeftJoin;
import cascading.property.AppProps;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;

/**
 * This class takes 2 input files, customers.txt and orders.txt, performs a left
 * join, and then writes out the result to the output location. The join is
 * performed on customer ID but the output has it only once.
 */
public class Joiner {
    /* customers.txt record delimiter */
    public static final String COMMA = ",";
    /*
     * customers.txt also has records that use comma as valid data and not
     * delimiter; thus we need to escape those
     */
    public static final String ESCAPE_STR = "\"";
    /* orders.txt record delimiter */
    public static final String PIPE = "|";

    public static void main(String[] args) {
        final String lhsPath = args[0];
        final String rhsPath = args[1];
        final String outputPath = args[2];

        /* Define the fields in the customers.txt file */
        final Fields lhsFields = new Fields("custID", "custName", "custPhNum",
                "custAddr");
        /* Define the fields in the ordersr.txt file */
        final Fields rhsFields = new Fields("ordID", "custID", "ordTotal",
                "ordDate");

        /* Create source and sink taps */
        final Tap lhsTap = new Hfs(new TextDelimited(lhsFields, true, COMMA,
                ESCAPE_STR), lhsPath);
        final Tap rhsTap = new Hfs(new TextDelimited(rhsFields, true, PIPE),
                rhsPath);
        final Tap joinTap = new Hfs(new TextDelimited(true, PIPE), outputPath);

        final Pipe lhsPipe = new Pipe("lhsPipe");
        final Pipe rhsPipe = new Pipe("rhsPipe");

        /* This is the field we are going to perform the join on */
        final Fields joinField = new Fields("custID");
        /*
         * Output fields. Since both files have a field called custID, this is
         * necessary to avoid a name conflict in the output
         */
        Fields declaredFields = new Fields("custID", "custName", "custPhNum",
                "custAddr", "ordID", "custID2", "ordTotal", "ordDate");

        Pipe joinPipe = new CoGroup(lhsPipe, joinField, rhsPipe, joinField,
                declaredFields, new LeftJoin());

        /* Prevent the customer ID from appearing twice in the output */
        final Fields outputFields = new Fields("custID", "custName",
                "custPhNum", "custAddr", "ordID", "ordTotal", "ordDate");
        joinPipe = new Each(joinPipe, outputFields, new Identity(outputFields));

        /* Connect the taps, pipes, etc., into a flow */
        final FlowDef flowDef = FlowDef.flowDef().setName("join")
                .addSource(lhsPipe, lhsTap).addSource(rhsPipe, rhsTap)
                .addTailSink(joinPipe, joinTap);

        final Properties properties = new Properties();
        AppProps.setApplicationJarClass(properties, Joiner.class);
        final HadoopFlowConnector flowConnector = new HadoopFlowConnector(
                properties);
        flowConnector.connect(flowDef).complete();
    }
}

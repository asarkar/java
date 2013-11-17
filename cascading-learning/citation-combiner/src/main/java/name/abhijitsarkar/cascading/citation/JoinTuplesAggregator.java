package name.abhijitsarkar.cascading.citation;

import cascading.flow.FlowProcess;
import cascading.operation.Aggregator;
import cascading.operation.AggregatorCall;
import cascading.operation.BaseOperation;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntry;

public class JoinTuplesAggregator extends
        BaseOperation<JoinTuplesAggregator.Context> implements
        Aggregator<JoinTuplesAggregator.Context> {
    private static final long serialVersionUID = 3790369720116242139L;
    public static final String FIELD_NAME = "join";
    private String delimiter = ",";

    public static class Context {
        StringBuilder result = new StringBuilder();
    }

    /**
     * Constructs a new instance that returns a default delimiter separated list
     * of the values encountered in the field name "join".
     */
    public JoinTuplesAggregator() {
        super(new Fields(FIELD_NAME));
    }

    /**
     * Constructs a new instance that returns a default delimiter separated list
     * of the values encountered with the given fieldDeclaration name.
     */
    public JoinTuplesAggregator(Fields fieldDeclaration) {
        super(fieldDeclaration);
    }

    /**
     * Constructs a new instance that returns a given delimiter separated list
     * of the values encountered with the given fieldDeclaration name.
     */
    public JoinTuplesAggregator(Fields fieldDeclaration, String delimiter) {
        super(fieldDeclaration);
        this.delimiter = delimiter;
    }

    @Override
    public void start(FlowProcess flowProcess,
            AggregatorCall<Context> aggregatorCall) {
        aggregatorCall.setContext(new Context());
    }

    @Override
    public void aggregate(FlowProcess flowProcess,
            AggregatorCall<Context> aggregatorCall) {
        TupleEntry arguments = aggregatorCall.getArguments();
        Context context = aggregatorCall.getContext();

        /* Use the first field in the Tuple */
        context.result.append(arguments.getString(0)).append(delimiter);
    }

    @Override
    public void complete(FlowProcess flowProcess,
            AggregatorCall<Context> aggregatorCall) {
        Context context = aggregatorCall.getContext();

        Tuple result = new Tuple();

        /* Remove the extra delimiter at the end before adding to context */
        result.add(context.result.deleteCharAt(context.result.length() - 1)
                .toString());

        aggregatorCall.getOutputCollector().add(result);
    }
}

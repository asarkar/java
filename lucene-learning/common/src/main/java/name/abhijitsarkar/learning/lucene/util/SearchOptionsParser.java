package name.abhijitsarkar.learning.lucene.util;

import java.io.File;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * This class serves as an utility to any class that does Lucene searches. It
 * accepts parameters like index directory, field name and query string. It also
 * provides reasonable defaults for some parameters. To find out more about this
 * class, just run the Lucene searcher from command line.
 */
public class SearchOptionsParser {
    private CommandLine line = null;
    private final HelpFormatter formatter = new HelpFormatter();

    public SearchOptionsParser(final String[] args) throws ParseException {
        final Options options = new Options();

        final Option query = new Option("q", "query", true, "Query");
        query.setRequired(true);

        options.addOption("h", "help", false, "Print this message");
        options.addOption("i", "index", true, "Index directory");
        options.addOption("f", "field", true, "Field to search on");
        options.addOption(query);

        try {
            line = new BasicParser().parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp("<classname>", options);
            throw e;
        }

        if (line.hasOption("h")) {
            formatter.printHelp("<classname>", options);
        }
    }

    public String getIndexDir() {
        if (line.hasOption("index")) {
            return line.getOptionValue("index");
        }

        return new File(".").getPath();
    }

    public String getQueryStr() {
        return line.getOptionValue("query");
    }

    public String getField() {
        if (line.hasOption("field")) {
            return line.getOptionValue("field");
        }

        return null;
    }
}

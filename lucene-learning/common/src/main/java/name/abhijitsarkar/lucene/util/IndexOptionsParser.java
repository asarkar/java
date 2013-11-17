package name.abhijitsarkar.lucene.util;

import java.io.File;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * This class serves as an utility to any class that does Lucene indexing. It
 * accepts parameters like index directory, document path and file filter. It
 * also provides reasonable defaults for some parameters. To find out more about
 * this class, just run the Lucene indexer from command line.
 */
public class IndexOptionsParser {
    private CommandLine line = null;
    private final HelpFormatter formatter = new HelpFormatter();

    public IndexOptionsParser(final String[] args) throws ParseException {
        final Options options = new Options();

        Option doc = new Option("d", "doc", true, "Document file or directory");
        doc.setRequired(true);

        options.addOption("h", "help", false, "Print this message");
        options.addOption("i", "index", true,
                "Index directory, default is current working directory");
        options.addOption("in", "include", true,
                "File extension to include in indexing, default is .txt");
        options.addOption(doc);

        try {
            line = new BasicParser().parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp("<classname>", options);
            throw e;
        }

        if (line.hasOption("help")) {
            formatter.printHelp("<classname>", options);
        }
    }

    public String getIndexDir() {
        if (line.hasOption("index")) {
            return line.getOptionValue("index");
        }

        return new File(".").getPath();
    }

    public String getDoc() {
        return line.getOptionValue("doc");
    }

    public String getFileExtension() {
        if (line.hasOption("include")) {
            return line.getOptionValue("include");
        }

        return ".txt";
    }
}

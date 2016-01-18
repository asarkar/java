package name.abhijitsarkar.java.repository;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.util.logging.Level.INFO;

/**
 * @author Abhijit Sarkar
 */
public abstract class AbstractClientFactory {
    public static Client liveClient() {
        if (System.getProperty("java.util.logging.config.file") == null) {
            System.setProperty("java.util.logging.SimpleFormatter.format",
                    "%1$tF %1$tT [%4$.1s] %3$s - %5$s%6$s%n");
        }

        return ClientBuilder.newBuilder().
                register(ObjectMapperProvider.class).
                register(JacksonFeature.class).
                register(new LoggingFilter(consoleLogger(), true)).
                build();
    }

    private static Logger consoleLogger() {
        Logger logger = Logger.getLogger(NytBestSellersApiLiveClient.class.getName());
        logger.setLevel(INFO);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);

        return logger;
    }
}

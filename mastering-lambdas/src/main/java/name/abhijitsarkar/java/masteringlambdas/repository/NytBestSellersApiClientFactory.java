package name.abhijitsarkar.java.masteringlambdas.repository;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.util.logging.Level.INFO;

/**
 * @author Abhijit Sarkar
 */
public class NytBestSellersApiClientFactory {
    private static final String BASE_URI = "http://api.nytimes.com";
    private static final String API_KEY = "d63f2e638211e5293f4abfd08d96054d:6:73851692";

    public static NytBestSellersApiClient getInstance(boolean live) {
        if (live) {
            if (System.getProperty("java.util.logging.config.file") == null) {
                System.setProperty("java.util.logging.SimpleFormatter.format",
                        "%1$tF %1$tT [%4$.1s] %3$s - %5$s%6$s%n");
            }

            Client client = ClientBuilder.newBuilder().
                    register(ObjectMapperProvider.class).
                    register(JacksonFeature.class).
                    register(new LoggingFilter(consoleLogger(), true)).
                    build();

            WebTarget nytWebTarget = client.target(BASE_URI).queryParam("api-key", API_KEY);

            return new NytBestSellersApiLiveClient(nytWebTarget, client);
        }

        return new NytBestSellersApiStubClient();
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

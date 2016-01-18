package name.abhijitsarkar.java.repository;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import static name.abhijitsarkar.java.repository.AbstractClientFactory.liveClient;

/**
 * @author Abhijit Sarkar
 */
public class NytBestSellersApiClientFactory {
    private static final String BASE_URI = "http://api.nytimes.com";
    private static final String API_KEY = "d63f2e638211e5293f4abfd08d96054d:6:73851692";

    public static NytBestSellersApiClient getInstance(boolean live) {
        if (live) {
            Client client = liveClient();

            WebTarget nytWebTarget = client.target(BASE_URI).queryParam("api-key", API_KEY);

            return new NytBestSellersApiLiveClient(nytWebTarget, client);
        }

        return new NytBestSellersApiStubClient();
    }
}

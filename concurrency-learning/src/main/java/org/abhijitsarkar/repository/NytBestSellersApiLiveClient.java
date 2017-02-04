package org.abhijitsarkar.repository;

import lombok.RequiredArgsConstructor;
import org.abhijitsarkar.domain.NytBestSellersList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collection;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.abhijitsarkar.repository.AbstractClientFactory.liveClient;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
public class NytBestSellersApiLiveClient extends AbstractNytBestSellersApiClient {
    private static final String BASE_URI = "http://api.nytimes.com";
    private static final String API_KEY = "d63f2e638211e5293f4abfd08d96054d:6:73851692";

    private final Client client;
    WebTarget nytWebTarget;

    public NytBestSellersApiLiveClient() {
        client = liveClient();

        nytWebTarget = client.target(BASE_URI).queryParam("api-key", API_KEY);
    }

    @Override
    public Collection<String> bestSellersListsNames() {
        try (InputStream body = nytWebTarget.path("/svc/books/v2/lists/names.json")
                .request()
                .accept(APPLICATION_JSON_TYPE)
                .<InputStream>get(InputStream.class)) {
            return bestSellersListsNames(body);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Collection<NytBestSellersList> bestSellersListsOverview() {
        try (InputStream body = nytWebTarget.path("/svc/books/v2/lists/overview.json")
                .request()
                .accept(APPLICATION_JSON_TYPE)
                .<InputStream>get(InputStream.class)) {
            return bestSellersListsOverview(body);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() {
        client.close();
    }
}

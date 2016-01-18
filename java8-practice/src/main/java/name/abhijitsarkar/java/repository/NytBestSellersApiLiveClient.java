package name.abhijitsarkar.java.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import name.abhijitsarkar.java.domain.NytBestSellersList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collection;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
public class NytBestSellersApiLiveClient extends AbstractNytBestSellersApiClient {
    private final WebTarget target;
    private final Client client;
    private final ObjectMapper objectMapper = new ObjectMapperProvider().getContext(null);

    @Override
    public Collection<String> bestSellersListsNames() {
        try (InputStream body = target.path("/svc/books/v2/lists/names.json")
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
        try (InputStream body = target.path("/svc/books/v2/lists/overview.json")
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

package name.abhijitsarkar.java.masteringlambdas.repository;

import name.abhijitsarkar.java.masteringlambdas.domain.NytBestSellersList;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
public class NytBestSellersApiStubClient extends AbstractNytBestSellersApiClient {
    @Override
    public Collection<String> bestSellersListsNames() {
        try (InputStream body = getClass().getResourceAsStream("/best-sellers-lists.json")) {
            return bestSellersListsNames(body);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Collection<NytBestSellersList> bestSellersListsOverview() {
        try (InputStream body = getClass().getResourceAsStream("/best-sellers-lists-overview.json")) {
            return bestSellersListsOverview(body);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() {

    }
}

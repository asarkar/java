package name.abhijitsarkar.java.repository;

import name.abhijitsarkar.java.domain.NytBestSellersList;

import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
public interface NytBestSellersApiClient {
    void close();

    Collection<String> bestSellersListsNames();

    Collection<NytBestSellersList> bestSellersListsOverview();
}

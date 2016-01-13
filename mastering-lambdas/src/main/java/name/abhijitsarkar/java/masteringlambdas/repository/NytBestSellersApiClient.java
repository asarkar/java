package name.abhijitsarkar.java.masteringlambdas.repository;

import name.abhijitsarkar.java.masteringlambdas.domain.NytBestSellersList;

import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
public interface NytBestSellersApiClient {
    void close();

    Collection<String> bestSellersListsNames();

    Collection<NytBestSellersList> bestSellersListsOverview();
}

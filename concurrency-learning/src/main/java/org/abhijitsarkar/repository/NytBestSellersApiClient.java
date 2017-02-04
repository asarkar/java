package org.abhijitsarkar.repository;

import org.abhijitsarkar.domain.NytBestSellersList;

import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
public interface NytBestSellersApiClient {
    void close();

    Collection<String> bestSellersListsNames();

    Collection<NytBestSellersList> bestSellersListsOverview();
}

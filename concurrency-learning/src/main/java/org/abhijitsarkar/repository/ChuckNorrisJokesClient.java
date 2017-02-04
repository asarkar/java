package org.abhijitsarkar.repository;

import org.abhijitsarkar.domain.Joke;

import java.util.List;

/**
 * @author Abhijit Sarkar
 */
public interface ChuckNorrisJokesClient {
    List<Joke> getRandomJokes(int numJokes);
}

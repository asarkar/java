package name.abhijitsarkar.java.repository;

import name.abhijitsarkar.java.domain.Joke;

import java.util.List;

/**
 * @author Abhijit Sarkar
 */
public interface ChuckNorrisJokesClient {
    List<Joke> getRandomJokes(int numJokes);
}

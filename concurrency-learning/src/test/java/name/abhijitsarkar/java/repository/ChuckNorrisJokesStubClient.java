package name.abhijitsarkar.java.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import name.abhijitsarkar.java.domain.Joke;
import name.abhijitsarkar.java.domain.Jokes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class ChuckNorrisJokesStubClient implements ChuckNorrisJokesClient {
    private static final String JOKES_URI = "/jokes.json";

    private final List<Joke> jokes;
    private final int allJokesCount;

    public ChuckNorrisJokesStubClient() {
        try {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getClass().getResourceAsStream(JOKES_URI), UTF_8))) {
                String text = reader.lines().collect(joining("\n"));

                jokes = new ObjectMapper().readValue(text, Jokes.class).getList();
                allJokesCount = jokes.size();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<Joke> getRandomJokes(int numJokes) {
        if (numJokes < 0) {
            throw new IllegalArgumentException("Cannot get negative number of jokes.");
        }

        return IntStream.range(0, numJokes)
                .map(i -> ThreadLocalRandom.current().nextInt(i, allJokesCount))
                .mapToObj(jokes::get)
                .collect(toList());
    }
}

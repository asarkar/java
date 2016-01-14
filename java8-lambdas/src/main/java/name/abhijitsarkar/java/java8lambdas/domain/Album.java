package name.abhijitsarkar.java.java8lambdas.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
@Getter
public class Album {
    private final String name;
    private Collection<Track> tracks;
    private Collection<Artist> musicians;
}

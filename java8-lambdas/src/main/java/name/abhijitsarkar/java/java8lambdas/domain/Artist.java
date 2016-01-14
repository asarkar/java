package name.abhijitsarkar.java.java8lambdas.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
@Getter
@Builder
public class Artist {
    private final String name;
    private final Collection<String> members;
    private final String origin;
}

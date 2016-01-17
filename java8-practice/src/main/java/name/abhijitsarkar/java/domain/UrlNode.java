package name.abhijitsarkar.java.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
@Getter
@ToString
public class UrlNode {
    private final int depth;
    private final String url;
    private final String parentUrl;

    public boolean isEmpty() {
        return url == null || url.isEmpty();
    }
}

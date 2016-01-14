package name.abhijitsarkar.java.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * @author Abhijit Sarkar
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(getterVisibility = NONE)
public class Book {
    private String title;
    @JsonProperty("author")
    private List<String> authors;
    @JsonProperty("primary_isbn10")
    private String isbn10;
    @JsonProperty("primary_isbn13")
    private String isbn13;
    @JsonProperty("publisher")
    private List<String> publishers;
    @JsonProperty("rank")
    private int nytBestSellersRank;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Book book = (Book) o;

        return Objects.equals(isbn10, book.isbn10);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn10);
    }
}

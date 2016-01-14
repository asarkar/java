package name.abhijitsarkar.java.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Collection;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * @author Abhijit Sarkar
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(getterVisibility = NONE)
public class NytBestSellersList {
    @JsonProperty("list_id")
    private int id;
    @JsonProperty("list_name")
    private String name;
    private Collection<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NytBestSellersList that = (NytBestSellersList) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package name.abhijitsarkar.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Abhijit Sarkar
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"text"})
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Joke {
    private int id;
    @JsonProperty("joke")
    private String text;
}

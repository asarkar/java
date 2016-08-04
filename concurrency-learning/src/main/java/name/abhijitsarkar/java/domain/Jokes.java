package name.abhijitsarkar.java.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * @author Abhijit Sarkar
 */
@Getter
@Setter
public class Jokes {
    @JsonProperty("type")
    private String status;
    @JsonProperty("value")
    private List<Joke> list;

    public Jokes() {
        this("unknown", emptyList());
    }

    public Jokes(String status, List<Joke> list) {
        this.status = status;
        this.list = list;
    }

    public int count() {
        return list.size();
    }
}

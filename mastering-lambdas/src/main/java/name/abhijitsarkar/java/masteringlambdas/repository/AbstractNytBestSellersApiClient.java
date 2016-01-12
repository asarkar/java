package name.abhijitsarkar.java.masteringlambdas.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.abhijitsarkar.java.masteringlambdas.domain.NytBestSellersList;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * @author Abhijit Sarkar
 */
public abstract class AbstractNytBestSellersApiClient implements NytBestSellersApiClient {
    private final ObjectMapper objectMapper = new ObjectMapperProvider().getContext(null);

    protected Collection<String> bestSellersListsNames(InputStream is) {
        try {
            JsonNode jsonNode = objectMapper.readTree(is);

            Iterable<JsonNode> lists = () -> jsonNode.path("results").elements();

            return StreamSupport.stream(lists.spliterator(), false).
                    map(node -> node.path("list_name").asText()).
                    collect(toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected Collection<NytBestSellersList> bestSellersListsOverview(InputStream is) {
        try {
            JsonNode jsonNode = objectMapper.readTree(is);

            Iterable<JsonNode> lists = () -> jsonNode.path("results").path("lists").elements();

            return StreamSupport.stream(lists.spliterator(), false).
                    map(node -> {
                        try {
                            return objectMapper.readerFor(NytBestSellersList.class).<NytBestSellersList>readValue(node);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    }).
                    collect(toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
